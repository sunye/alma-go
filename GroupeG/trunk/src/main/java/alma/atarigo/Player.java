/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alma.atarigo;

/**
 *
 * @author steg
 */
public interface Player {

    /**
     * Le joueur joue son prochain coup
     * @return : la position de la cellule souhaitée
     */
    public CellPosition nextPlay();
    
}
