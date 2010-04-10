/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alma.atarigo;

/**
 *
 * @author steg
 */
public interface CellEvent {

    /**
     * recupere la position de la cellule => acces à x et à y
     * @return : la position
     */
    public CellPosition getPosition();

    /**
     * recupere l'état de la cellule (vide,noire ou blanche)
     * @return : l'énumération correspondant à l'état
     */
    public CellContent getContent();
    
}
