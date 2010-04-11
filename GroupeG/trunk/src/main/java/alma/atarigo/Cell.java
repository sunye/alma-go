/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alma.atarigo;

/**
 *Une cellule du jeu
 * @author steg
 */
public interface Cell extends CellPosition{

    /**
     * Recupere l'état de la cellule
     * @return
     */
    public CellContent getContent();

}
