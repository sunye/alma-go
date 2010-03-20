package fr.alma.atarigo.utils;

import java.util.ArrayList;

import fr.alma.atarigo.utils.exceptions.BadCouleurException;
import fr.alma.atarigo.utils.exceptions.BadPlaceException;
import fr.alma.atarigo.utils.tree.Node;
import fr.alma.atarigo.utils.tree.Tree;
import java.util.HashSet;

public class Goban {

    private final static int taille = 9;
    //public static enum  PionVal{rien, noir, blanc};
    private static ArrayList<Groupe> groupes;
    // 0 = rien, noir = 1, blanc = 2
    PionVal goban[][];

    private Tree<PlayMove> history;
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

        groupes = new ArrayList<Groupe>();
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
                if(!isSuicide(line,column,color)){
                    /*TODO: check stones taken + add PlayMove Node;
                     * + Find how to manage group history (Maybe in PlayMove) spacy, but faster.
                     */

                    setCase(realLigne, realCol, color);

                    Pion current = new Pion(color, realLigne, realCol);

                    calculateGroups(current);
                }
                
                

            } else {
                throw new BadPlaceException("Il y a déjà une pierre ici");
            }
        } else {
            throw new BadPlaceException("Il est préférable de jouer sur le plateau");
        }
    }

    /**
     * Calculates the new groups, after putting the last stone
     * @param last The last stone played
     */
    private void calculateGroups(Pion last){
        Groupe lastAdded = new Groupe(last.getCouleur());
                try {
                    lastAdded.addPion(last);
                } catch (BadCouleurException e) {
                }

                HashSet<Groupe> groups = getVoisins(last);

                for (Groupe groupe : groups) {
                    if (groupe.getCouleur() == last.getCouleur()) {
                        lastAdded.addAll(groupe);
                        groupes.remove(groupe);
                    }
                }
                groupes.add(lastAdded);
    }

    /**
     * Retourne le premier (normalement le seul) groupe de pierres contenant la pierre passée en paramètre.
     * @param pion
     * @return
     */
    public Groupe getGroupeContenant(Pion pion) {
        for (Groupe groupe : groupes) {
            if (groupe.contains(pion)) {
                return groupe;
            }
        }
        return null;
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
            throw new Exception("Case en dehors du goban");
        }
    }

    public HashSet<Groupe> getVoisins(Pion pion) {
        ArrayList<Pion> pions = new ArrayList<Pion>();
        HashSet<Groupe> groups = new HashSet<Groupe>();

        if (bonneCoords(pion.getLigne() + 1, pion.getColonne()) && (goban[pion.getLigne() + 1][pion.getColonne()] != PionVal.RIEN)) {
            groups.add(getGroupeContenant(new Pion(goban[pion.getLigne() + 1][pion.getColonne()], pion.getLigne() + 1, pion.getColonne())));
        }

        if (bonneCoords(pion.getLigne() - 1, pion.getColonne()) && (goban[pion.getLigne() - 1][pion.getColonne()] != PionVal.RIEN)) {
            groups.add(getGroupeContenant(new Pion(goban[pion.getLigne() - 1][pion.getColonne()], pion.getLigne() - 1, pion.getColonne())));
        }

        if (bonneCoords(pion.getLigne(), pion.getColonne() + 1) && (goban[pion.getLigne()][pion.getColonne() + 1] != PionVal.RIEN)) {
            groups.add(getGroupeContenant(new Pion(goban[pion.getLigne()][pion.getColonne() + 1], pion.getLigne(), pion.getColonne() + 1)));
        }

        if (bonneCoords(pion.getLigne(), pion.getColonne() - 1) && (goban[pion.getLigne()][pion.getColonne() - 1] != PionVal.RIEN)) {
            groups.add(getGroupeContenant(new Pion(goban[pion.getLigne()][pion.getColonne() - 1], pion.getLigne(), pion.getColonne() - 1)));
        }

        return groups;
    }

    public ArrayList<Groupe> getGroupes() {
        return groupes;
    }

    public int libertesPion(int ligne, int col) {
        int libertes = 4;
        if (bonneCoords(ligne + 1, col) && (goban[ligne + 1][col] != PionVal.RIEN)) {
            --libertes;
        } else if(!bonneCoords(ligne + 1, col)){
            --libertes;
        }


        if (bonneCoords(ligne - 1, col) && (goban[ligne - 1][col] != PionVal.RIEN)) {
            --libertes;
        } else if(!bonneCoords(ligne - 1, col)){
            --libertes;
        }

        if (bonneCoords(ligne, col+1) && (goban[ligne][col+1] != PionVal.RIEN)) {
            --libertes;
        } else if(!bonneCoords(ligne, col+1)){
            --libertes;
        }

        if (bonneCoords(ligne, col-1) && (goban[ligne][col-1] != PionVal.RIEN)) {
            --libertes;
        } else if(!bonneCoords(ligne, col-1)){
            --libertes;
        }

        return libertes;

    }

    private boolean isSuicide(int line, int column, PionVal pionVal) {
        //TODO: Check if the stone fills the last liberty of its group (=> suicide), except if it also fills the last liberty of a ennemy group.
        return true;
    }
}
