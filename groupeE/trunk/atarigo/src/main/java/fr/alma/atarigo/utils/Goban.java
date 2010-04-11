package fr.alma.atarigo.utils;

import java.util.ArrayList;

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

    public static int getTaille() {
        return taille;
    }

    /**
     * The constructor basically initialize the game.
     */
    public Goban() {
        super();
        goban = new PionVal[taille][taille];

        for (int i = 0; i < Goban.taille; ++i) {
            for (int j = 0; j < Goban.taille; ++j) {
                goban[i][j] = PionVal.RIEN;
            }
        }
    }

    /*
     * Checks if the coordinates fit in the game board.
     */
    public boolean bonneCoords(int ligne, int colonne) {
        return ((ligne < Goban.getTaille()) && (ligne >= 0) && (colonne >= 0) && (colonne < Goban.getTaille()));
    }

    /**
     * Cette méthode permet de définir la valeur d'un pion. Elle est publique pour le cas où on doit ajouter rapidement des pions dans le goban.
     * Attention toutefois, si on tente de modifier une case en dehors du plateau, rien n'est fait, mais on ne récupère aucune exception.
     * @param line
     * @param colonne
     * @param pion
     */
    public void setCase(int ligne, int colonne, PionVal pion) {
        goban[ligne][colonne] = pion;
    }

    public PionVal getCase(int ligne, int colonne) {
        return goban[ligne][colonne];
    }

    public List<Stone> getVoisins(Stone pion) {
        ArrayList<Stone> pions = new ArrayList<Stone>(4);

        if (bonneCoords(pion.getLine() + 1, pion.getColumn()) && (goban[pion.getLine() + 1][pion.getColumn()] != PionVal.RIEN)) {
            pions.add(new Stone(goban[pion.getLine() + 1][pion.getColumn()], pion.getLine() + 1, pion.getColumn()));
        }

        if (bonneCoords(pion.getLine() - 1, pion.getColumn()) && (goban[pion.getLine() - 1][pion.getColumn()] != PionVal.RIEN)) {
            pions.add(new Stone(goban[pion.getLine() - 1][pion.getColumn()], pion.getLine() - 1, pion.getColumn()));
        }

        if (bonneCoords(pion.getLine(), pion.getColumn() + 1) && (goban[pion.getLine()][pion.getColumn() + 1] != PionVal.RIEN)) {
            pions.add(new Stone(goban[pion.getLine()][pion.getColumn() + 1], pion.getLine(), pion.getColumn() + 1));
        }

        if (bonneCoords(pion.getLine(), pion.getColumn() - 1) && (goban[pion.getLine()][pion.getColumn() - 1] != PionVal.RIEN)) {
            pions.add(new Stone(goban[pion.getLine()][pion.getColumn() - 1], pion.getLine(), pion.getColumn() - 1));
        }

        return pions;
    }

    public List<Stone> getLibertes(Stone pion) {
        ArrayList<Stone> pions = new ArrayList<Stone>(4);

        if (bonneCoords(pion.getLine() + 1, pion.getColumn()) && (goban[pion.getLine() + 1][pion.getColumn()] == PionVal.RIEN)) {
            pions.add(new Stone(PionVal.RIEN, pion.getLine() + 1, pion.getColumn()));
        }

        if (bonneCoords(pion.getLine() - 1, pion.getColumn()) && (goban[pion.getLine() - 1][pion.getColumn()] == PionVal.RIEN)) {
            pions.add(new Stone(PionVal.RIEN, pion.getLine() - 1, pion.getColumn()));
        }

        if (bonneCoords(pion.getLine(), pion.getColumn() + 1) && (goban[pion.getLine()][pion.getColumn() + 1] == PionVal.RIEN)) {
            pions.add(new Stone(PionVal.RIEN, pion.getLine(), pion.getColumn() + 1));
        }

        if (bonneCoords(pion.getLine(), pion.getColumn() - 1) && (goban[pion.getLine()][pion.getColumn() - 1] == PionVal.RIEN)) {
            pions.add(new Stone(PionVal.RIEN, pion.getLine(), pion.getColumn() - 1));
        }

        return pions;
    }

    public int libertesPion(Stone pion) {
        int ligne = pion.getLine();
        int col = pion.getColumn();

        int libertes = 4;
        if (!bonneCoords(ligne + 1, col)) {
            --libertes;
        } else if (goban[ligne + 1][col] != PionVal.RIEN) {
            --libertes;
        }

        if (!bonneCoords(ligne - 1, col)) {
            --libertes;
        } else if ((goban[ligne - 1][col] != PionVal.RIEN)) {
            --libertes;
        }

        if (!bonneCoords(ligne, col + 1)) {
            --libertes;
        } else if (goban[ligne][col + 1] != PionVal.RIEN) {
            --libertes;
        }

        if (!bonneCoords(ligne, col - 1)) {
            --libertes;
        } else if (goban[ligne][col - 1] != PionVal.RIEN) {
            --libertes;
        }
        return libertes;
    }

    public PionVal[][] getGoban() {
        return goban;
    }

    @Override
    public Goban clone() {
        Goban autre = new Goban();
        autre.goban = this.goban.clone();
        return autre;
    }

    @Override
    public String toString() {
        StringBuilder sortie = new StringBuilder();
        //Affichage du plateau :
        for (int lin = 0; lin < taille; ++lin) {
            for (int col = 0; col < taille; ++col) {
                switch (this.getCase(lin, col)) {
                    case NOIR:
                        sortie.append('N');
                        break;
                    case BLANC:
                        sortie.append('B');
                        break;
                    case RIEN:
                        sortie.append('+');
                        break;
                    default:
                        sortie.append('+');
                }
            }
            sortie.append('\n');
        }

        return sortie.toString();
    }
}
