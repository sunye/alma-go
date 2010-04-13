/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.alma.atarigo;

import fr.alma.atarigo.analyse.AlphaBeta;
import fr.alma.atarigo.ihm.Fenetre;
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
    private Fenetre fenetre;
    private Boolean onePlayerGame;

    public GameManager(GobanPanel gobPan, Fenetre fenetre) {
        this.gobanPanel = gobPan;
        this.fenetre = fenetre;
    }

    /**
     * Start a game
     */
    private void init() {
        game = new Game();

    }

    /**
     * Begin a two-players game
     */
    public void twoPlayers() {
        init();
        this.onePlayerGame = Boolean.FALSE;
        gobanPanel.startGame(this, this.game);
    }

    /**
     * Begin a one-player game
     * @param couleur : color of the stone player
     */
    public void onePlayer(PionVal couleur) {
        init();
        this.onePlayerGame = Boolean.TRUE;
        gobanPanel.startGame(this, this.game);
        if (couleur == PionVal.BLANC) {
            coupIA();
        }
    }

    /**
     * The computer plays.
     */
    private void coupIA() {
        gobanPanel.desactivateMouse();
        AlphaBeta alphabeta = new AlphaBeta(3, PionVal.NOIR);
        PlayMove coup = new PlayMove();
        coup = alphabeta.init(game);
        game.playAt(coup.getPutStone().getLine(), coup.getPutStone().getColumn());
        gobanPanel.repaint();
        gobanPanel.activateMouse();
    }

    /**
     * Do a move (and display it)
     * @param lin : line of the move
     * @param col : column of the move
     */
    public void appliqueCoup(int lin, int col) {
        if (game.playAt(lin, col)) {
            gobanPanel.paintImmediately(0, 0, gobanPanel.getWidth(), gobanPanel.getHeight());
            if (this.onePlayerGame) {
                coupIA();
            }
        }
    }

    /**
     * Test an endgame
     * @return if it is the end of the game
     */
    public boolean isEnd() {
        return game.isEnd();
    }

    /**
     * Accessor : game
     * @return the current game
     */
    final public Game getGame() {
        return game;
    }

    /**
     * Open a window endgame to announce the winner of the game.
     */
    public void dialogueFinJeu() {
        this.fenetre.finJeu();
    }
}
