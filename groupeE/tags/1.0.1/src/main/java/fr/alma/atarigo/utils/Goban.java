// File Goban.java
// Last commited $Date$
// By $Author$
// Revision $Rev$
//
// Copyright (C) 2010 Clotilde Massot & Julien Durillon
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// as published by the Free Software Foundation; either version 2
// of the License, or (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
//
// The above copyright notice and this permission notice shall be
// included in all copies or substantial portions of this program.
package fr.alma.atarigo.utils;

import java.util.ArrayList;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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

//        Logger.getAnonymousLogger().log(Level.INFO, "Getting neighbours of "+pion);

        if (pion != null) {
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
        }
//        Logger.getAnonymousLogger().log(Level.INFO, "Got "+pions);


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

    public int nbLibertes(Stone pion) {
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
        for (int line = 0; line < getTaille(); ++line) {
            for (int col = 0; col < getTaille(); ++col) {
                autre.goban[line][col] = this.goban[line][col];
            }
        }
        return autre;
    }

    @Override
    public String toString() {
        StringBuilder sortie = new StringBuilder();
        sortie.append("Goban :\n\n");

        for (int lin = 0; lin < taille; ++lin) {
            for (int col = 0; col < taille; ++col) {
                switch (this.getCase(lin, col)) {
                    case NOIR:
                        sortie.append("—B−");
                        break;
                    case BLANC:
                        sortie.append("—W−");
                        break;
                    case RIEN:
                        sortie.append("—+−");
                        break;
                    default:
                        sortie.append("—+−");
                }
            }
            sortie.append("\n ");
            if (lin != taille - 1) {
                for (int col = 0; col < taille; ++col) {
                    sortie.append("|  ");
                }
            }
            sortie.append('\n');
        }

        sortie.append('\n');
        return sortie.toString();
    }
}
