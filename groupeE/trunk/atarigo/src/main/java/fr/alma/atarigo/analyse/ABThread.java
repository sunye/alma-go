/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.alma.atarigo.analyse;

import fr.alma.atarigo.utils.Game;
import fr.alma.atarigo.utils.PionVal;
import fr.alma.atarigo.utils.PlayMove;

/**
 * Embbeds an AlphaBeta, and allows to get the best PlayMove at any time (theorically).
 *
 *
 * @author judu
 */
public class ABThread extends Thread {

    private AlphaBeta alphabeta;
    private Game game;
    private PlayMove best;

    public ABThread(int depth, PionVal color, Game game) {
        alphabeta = new AlphaBeta(depth, color);
        this.game = game;
        best = null;
    }


    @Override
    public void run(){
        best = alphabeta.init(game);
        
    }

    /**
     * Returns the current best move to play.
     * @return
     */
    public PlayMove getCurrentBest(){
        if(best != null){
            return best;
        } else {
            return alphabeta.getLastBest();
        }
    }


}
