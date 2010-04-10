/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alma.atarigo;

/**
 *
 * @author steg
 */
public interface CellListener {

    /**
     * Change l'état d'une cellule
     * @param event : la cellule
     */
    public void cellChanged(CellEvent event);

}
