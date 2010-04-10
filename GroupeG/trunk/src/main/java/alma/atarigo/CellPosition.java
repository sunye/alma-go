/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alma.atarigo;

/**
 *
 * @author steg
 */
public interface CellPosition {
    
    /**
     * Recupère l'abscisse de la cellule
     * @return : la ligne
     */
    public int getRow();

    /**
     * Recupere l'ordonnee de la cellule
     * @return : la colonne
     */
    public int getColumn();

}
