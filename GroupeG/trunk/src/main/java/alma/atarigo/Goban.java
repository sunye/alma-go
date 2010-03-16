/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alma.atarigo;

import java.util.*;

/**
 *
 * @author steg
 */
public interface Goban {

    /**
     * Met à jour une cellule
     * @param position : la position de la cellule
     * @param content : le nouvel état de la cellule
     */
    public void setCellContent(CellPosition position,CellContent content);

    /**
     * Surcharge : met à jour une cellule
     * @param row : le numéro de la ligne de la cellule
     * @param column : le numéro de la colonne de la cellule
     * @param content : le nouvel état de la cellule
     */
    public void setCellContent(int row,int column,CellContent content);

    /**
     * Recupere l'état d'une cellule
     * @param position : la position de la cellule
     * @return : l'état
     */
    public CellContent getCellContent(CellPosition position);

    /**
     * Surcharge : recupere l'état d'une cellule
     * @param row : le numéro de la ligne de la cellule
     * @param column : le numéro de la colonne de la cellule
     * @return : l'état
     */
    public CellContent getCellContent(int row,int column);

    /**
     * Recupere toutes les libertés d'un territoire à partir d'une cellule
     * @param position : la position de la cellule en question
     * @return : une liste de cellules
     */
    public List<CellPosition> getLiberties(CellPosition position);

    /**
     * Recupere un territoire à partir d'une cellule
     * @param position : la position de la cellule
     * @return : une liste de cellule
     */
    public List<CellPosition> getTerritoty(CellPosition position);
}
