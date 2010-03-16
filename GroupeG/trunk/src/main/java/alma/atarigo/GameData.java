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
public interface GameData {

    /**
     * Recupere le Goban
     * @return : le Goban
     */
    public Goban getGoban();

    /**
     * Recupere toutes les regles associées au jeu
     * @return : une collection  de regles
     */
    public Collection<Rule> getRules();

    /**
     * Recupere le niveau  de jeu
     * @return : le niveau de jeu
     */
    public Level getLevel();
    
}
