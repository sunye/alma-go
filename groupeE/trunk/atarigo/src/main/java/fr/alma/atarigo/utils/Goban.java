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

    public List<Pion> getVoisins(Pion pion) {
        ArrayList<Pion> pions = new ArrayList<Pion>(4);

        if (bonneCoords(pion.getLigne() + 1, pion.getColonne()) && (goban[pion.getLigne() + 1][pion.getColonne()] != PionVal.RIEN)) {
            pions.add(new Pion(goban[pion.getLigne() + 1][pion.getColonne()], pion.getLigne() + 1, pion.getColonne()));
        }

        if (bonneCoords(pion.getLigne() - 1, pion.getColonne()) && (goban[pion.getLigne() - 1][pion.getColonne()] != PionVal.RIEN)) {
            pions.add(new Pion(goban[pion.getLigne() - 1][pion.getColonne()], pion.getLigne() - 1, pion.getColonne()));
        }

        if (bonneCoords(pion.getLigne(), pion.getColonne() + 1) && (goban[pion.getLigne()][pion.getColonne() + 1] != PionVal.RIEN)) {
            pions.add(new Pion(goban[pion.getLigne()][pion.getColonne() + 1], pion.getLigne(), pion.getColonne() + 1));
        }

        if (bonneCoords(pion.getLigne(), pion.getColonne() - 1) && (goban[pion.getLigne()][pion.getColonne() - 1] != PionVal.RIEN)) {
            pions.add(new Pion(goban[pion.getLigne()][pion.getColonne() - 1], pion.getLigne(), pion.getColonne() - 1));
        }

        return pions;
    }


    public List<Pion> getLibertes(Pion pion){
        ArrayList<Pion> pions = new ArrayList<Pion>(4);

        if (bonneCoords(pion.getLigne() + 1, pion.getColonne()) && (goban[pion.getLigne() + 1][pion.getColonne()] == PionVal.RIEN)) {
            pions.add(new Pion(PionVal.RIEN, pion.getLigne() + 1, pion.getColonne()));
        }

        if (bonneCoords(pion.getLigne() - 1, pion.getColonne()) && (goban[pion.getLigne() - 1][pion.getColonne()] == PionVal.RIEN)) {
            pions.add(new Pion(PionVal.RIEN, pion.getLigne() - 1, pion.getColonne()));
        }

        if (bonneCoords(pion.getLigne(), pion.getColonne() + 1) && (goban[pion.getLigne()][pion.getColonne() + 1] == PionVal.RIEN)) {
            pions.add(new Pion(PionVal.RIEN, pion.getLigne(), pion.getColonne() + 1));
        }

        if (bonneCoords(pion.getLigne(), pion.getColonne() - 1) && (goban[pion.getLigne()][pion.getColonne() - 1] == PionVal.RIEN)) {
            pions.add(new Pion(PionVal.RIEN, pion.getLigne(), pion.getColonne() - 1));
        }

        return pions;
    }

    public int libertesPion(int ligne, int col) {
        int libertes = 4;
        if (!bonneCoords(ligne + 1, col)){
            --libertes;
        } else if(goban[ligne + 1][col] != PionVal.RIEN) {
            --libertes;
        }

        if (!bonneCoords(ligne - 1, col))  {
            --libertes;
        } else if ((goban[ligne - 1][col] != PionVal.RIEN)) {
            --libertes;
        }

        if (!bonneCoords(ligne, col + 1))  {
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

    public PionVal[][] getGoban(){
        return goban;
    }
}
