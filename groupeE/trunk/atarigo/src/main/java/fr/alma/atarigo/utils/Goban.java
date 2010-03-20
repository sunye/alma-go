package fr.alma.atarigo.utils;

import java.util.ArrayList;

import fr.alma.atarigo.utils.exceptions.BadCouleurException;
import fr.alma.atarigo.utils.exceptions.BadPlaceException;
import fr.alma.atarigo.utils.tree.Node;
import fr.alma.atarigo.utils.tree.Tree;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

/**
 * Describes the Goban, and more than that, the whole game.
 * We probably should split it in a "low-level" Goban class and a Game one.
 *
 * @author judu
 */
public class Goban {

    //* Size of the goban. For now, it's static.
    private final static int taille = 9;
    // 0 = rien, noir = 1, blanc = 2
    PionVal goban[][];
    // History of the game : contains all the moves since the beginning.
    private Tree<PlayMove> history;
    // Last move played (faster backtracking).
    private Node<PlayMove> lastMove;

    public static int getTaille() {
        return taille;
    }

    /**
     * The constructor basically initialize the game.
     */
    public Goban() {
        super();
        history = new Tree<PlayMove>();
        lastMove = new Node<PlayMove>(new PlayMove());
        history.setRootElement(lastMove);


        goban = new PionVal[Goban.taille][Goban.taille];

        for (int i = 0; i < Goban.taille; ++i) {
            for (int j = 0; j < Goban.taille; ++j) {
                goban[i][j] = PionVal.RIEN;
            }
        }
    }

    /*
     * Checks if the coordinates fit in the game board.
     */
    private boolean bonneCoords(int ligne, int colonne) {
        return ligne < Goban.getTaille() && ligne >= 0 && colonne >= 0 && colonne < Goban.getTaille();
    }

    /**
     * This method put a stone on the goban, checking whether it is actually <em>on</em> the goban,
     * and manages all the changes involved (such as stone taken, and groups making)
     * 
     * @param line The line where to put the stone
     * @param column The column where to put the stone
     * @param color The color of the stone.
     * @throws BadPlaceException Threw if the coordinates do not fit in the goban
     */
    public void posePion(int line, int column, PionVal color) throws BadPlaceException {
        int realLigne = line;
        int realCol = column;
        if (bonneCoords(realLigne, realCol)) {
            if (goban[realLigne][realCol] == PionVal.RIEN) {
                if (!isSuicide(line, column, color)) {
                    /*TODO: check stones taken + add PlayMove Node;
                     * + Find how to manage group history (Maybe in PlayMove) spacy, but faster.
                     */

                    Pion current = new Pion(color, realLigne, realCol);

                    Modif modif = new Modif(realLigne, realCol, goban[realLigne][realCol], color);
                    PlayMove currentMove = new PlayMove();
                    currentMove.addModif(modif);

                    //prepare the group modifications
                    currentMove.setGroupes((ArrayList<Groupe>) lastMove.getData().getGroupes().clone());

                    startMove(currentMove);

                    //Let's modify the goban !
                    setCase(realLigne, realCol, color);

                    //Calculates the new groups
                    calculateGroups(current);

                    //Removes taken stones, and update the currentMove with the Modifs.
                    calculateTaken(current);

                } else {
                    throw new BadPlaceException("This move is a suicide, I cannot let you do that !");
                }

            } else {
                throw new BadPlaceException("Already a stone here");
            }
        } else {
            throw new BadPlaceException("It's actually better to play ON the board…");
        }
    }

    /**
     * Calculates the new groups, after putting the last stone
     * @param last The last stone played
     */
    private void calculateGroups(Pion last) {
        Groupe lastAdded = new Groupe(last.getCouleur());
        try {
            lastAdded.addPion(last);
        } catch (BadCouleurException e) {
        }

        HashSet<Groupe> groups = getVoisins(last);
        List<Groupe> groupes = lastMove.getData().getGroupes();

        for (Groupe groupe : groups) {
            if (groupe.getCouleur() == last.getCouleur()) {
                lastAdded.addAll(groupe);
                groupes.remove(groupe);
            }
        }
        groupes.add(lastAdded);
    }

    /**
     * Cette méthode permet de définir la valeur d'un pion. Elle est publique pour le cas où on doit ajouter rapidement des pions dans le goban.
     * Attention toutefois, si on tente de modifier une case en dehors du plateau, rien n'est fait, mais on ne récupère aucune exception.
     * @param line
     * @param colonne
     * @param pion
     */
    public void setCase(int ligne, int colonne, PionVal pion) {
        if (bonneCoords(ligne, colonne)) {
            goban[ligne][colonne] = pion;
        }
    }

    public PionVal getCase(int ligne, int colonne) throws Exception {
        if (bonneCoords(ligne, colonne)) {
            return goban[ligne][colonne];
        } else {
            throw new Exception("This spot is not on the board");
        }
    }

    public HashSet<Groupe> getVoisins(Pion pion) {
        ArrayList<Pion> pions = new ArrayList<Pion>();
        HashSet<Groupe> groups = new HashSet<Groupe>();

        if (bonneCoords(pion.getLigne() + 1, pion.getColonne()) && (goban[pion.getLigne() + 1][pion.getColonne()] != PionVal.RIEN)) {
            groups.add(lastMove.getData().getGroupeContaining(new Pion(goban[pion.getLigne() + 1][pion.getColonne()], pion.getLigne() + 1, pion.getColonne())));
        }

        if (bonneCoords(pion.getLigne() - 1, pion.getColonne()) && (goban[pion.getLigne() - 1][pion.getColonne()] != PionVal.RIEN)) {
            groups.add(lastMove.getData().getGroupeContaining(new Pion(goban[pion.getLigne() - 1][pion.getColonne()], pion.getLigne() - 1, pion.getColonne())));
        }

        if (bonneCoords(pion.getLigne(), pion.getColonne() + 1) && (goban[pion.getLigne()][pion.getColonne() + 1] != PionVal.RIEN)) {
            groups.add(lastMove.getData().getGroupeContaining(new Pion(goban[pion.getLigne()][pion.getColonne() + 1], pion.getLigne(), pion.getColonne() + 1)));
        }

        if (bonneCoords(pion.getLigne(), pion.getColonne() - 1) && (goban[pion.getLigne()][pion.getColonne() - 1] != PionVal.RIEN)) {
            groups.add(lastMove.getData().getGroupeContaining(new Pion(goban[pion.getLigne()][pion.getColonne() - 1], pion.getLigne(), pion.getColonne() - 1)));
        }

        return groups;
    }

    public int libertesPion(int ligne, int col) {
        int libertes = 4;
        if (bonneCoords(ligne + 1, col) && (goban[ligne + 1][col] != PionVal.RIEN)) {
            --libertes;
        } else if (!bonneCoords(ligne + 1, col)) {
            --libertes;
        }

        if (bonneCoords(ligne - 1, col) && (goban[ligne - 1][col] != PionVal.RIEN)) {
            --libertes;
        } else if (!bonneCoords(ligne - 1, col)) {
            --libertes;
        }

        if (bonneCoords(ligne, col + 1) && (goban[ligne][col + 1] != PionVal.RIEN)) {
            --libertes;
        } else if (!bonneCoords(ligne, col + 1)) {
            --libertes;
        }

        if (bonneCoords(ligne, col - 1) && (goban[ligne][col - 1] != PionVal.RIEN)) {
            --libertes;
        } else if (!bonneCoords(ligne, col - 1)) {
            --libertes;
        }

        return libertes;

    }

    private boolean isSuicide(int line, int column, PionVal pionVal) {
        //TODO: Check if the stone fills the last liberty of its group (=> suicide), except if it also fills the last liberty of a ennemy group.
        return true;
    }

    private void calculateTaken(Pion current) {
        Set<Groupe> voisins = getVoisins(current);
        for (Groupe groupe : voisins) {
            if (!groupe.getCouleur().equals(current.getCouleur())) {
                if (groupe.getLibertes() == 1) {
                    // Dans ce cas là, on a bouché la dernière libertée,
                    //il faut donc supprimer les pierres du goban, et le groupe du pm.
                    removeGroupe(groupe);
                }
            }
        }
    }

    private void removeGroupe(Groupe groupe) {
        
        for (Pion pion : groupe.getPions()) {
            // Don't forget to register the modification.
            lastMove.getData().addModif(new Modif(pion.getLigne(), pion.getColonne(), pion.getCouleur(), PionVal.RIEN));
            setCase(pion.getLigne(), pion.getColonne(), PionVal.RIEN);
        }

        lastMove.getData().getGroupes().remove(groupe);
    }

    private void startMove(PlayMove currentMove) {
        Node<PlayMove> newMove = new Node<PlayMove>(currentMove);
        lastMove.addChild(newMove);
        lastMove = newMove;
    }
}
