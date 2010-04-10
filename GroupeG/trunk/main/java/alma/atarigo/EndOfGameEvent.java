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
public interface EndOfGameEvent {

    /**
     * Obtenir le gagnant du jeu
     * @return
     */
    public Player getWinner();

    /**
     * Obtenir le perdant du jeu
     * @return
     */
    public Player getLooser();

    /**
     * Obtenir le territoire gagnant
     * @return
     */
    public List<CellPosition> getWinningArea();

    /**
     * Obtenir le territoire perdant
     * @return
     */
    public List<Territory> getLosingArea();

}
