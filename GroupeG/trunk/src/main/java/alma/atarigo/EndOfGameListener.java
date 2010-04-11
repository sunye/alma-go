/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alma.atarigo;

/**
 * Ecouteur sur les évènements de fin de jeu
 * @author steg
 */
public interface EndOfGameListener {

    public void gameOver(EndOfGameEvent e);
    
}
