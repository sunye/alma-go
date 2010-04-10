/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.alma.atarigo;

import fr.alma.atarigo.analyse.AlphaBeta;
import fr.alma.atarigo.utils.Game;
import fr.alma.atarigo.utils.PionVal;
import fr.alma.atarigo.ihm.GobanPanel;
import fr.alma.atarigo.utils.PlayMove;

/**
 *
 * @author clotildemassot
 */
public class GameManager {

    private Game game;
    private GobanPanel gobanPanel;
    private Boolean onePlayerGame;

    public GameManager(GobanPanel gobPan) {
        this.gobanPanel = gobPan;
    }

    //Lance une partie :
    private void init() {
        game = new Game();

    }

    //Begin a two players game :
    public void twoPlayers() {
        init();
        this.onePlayerGame = Boolean.FALSE;
        gobanPanel.startGame(this, this.game);
    }

    //Begin a onePlayerGame
    public void onePlayer(PionVal couleur) {
        init();
        this.onePlayerGame = Boolean.TRUE;
        gobanPanel.startGame(this, this.game);
        if (couleur == PionVal.BLANC) {
            coupIA();
        }
    }

    private void coupIA() {
        gobanPanel.desactivateMouse();
        AlphaBeta alphabeta = new AlphaBeta(3, PionVal.NOIR);
        PlayMove coup = new PlayMove();
        coup = alphabeta.init(game);
        game.playAt(coup.getPutStone().getLine(), coup.getPutStone().getColumn());
        gobanPanel.repaint();
        gobanPanel.activateMouse();
    }

    public void appliqueCoup(int lin, int col) {
        if (game.playAt(lin, col)){
            if(this.onePlayerGame){
                coupIA();
            }
        }
    }

    public boolean isEnd(){
        return game.isEnd();
    }

    final public Game getGame() {
        return game;
    }


}
