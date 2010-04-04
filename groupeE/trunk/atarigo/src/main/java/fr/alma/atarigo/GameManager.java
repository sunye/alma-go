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

    Game game;
    GobanPanel gobanPanel;

    public GameManager(GobanPanel gobPan) {
        this.gobanPanel = gobPan;
    }

    //Lance une partie :
    public void init() {
        game = new Game();

    }

    //Commencer une partie ˆ deux joueurs :
    public void twoPlayers() {
        init();
        gobanPanel.startGame(this);
    }

    //Commencer une partie ˆ un joueur :
    public void onePlayer(PionVal couleur) {
        init();
        gobanPanel.startGame(this);
        if (couleur == PionVal.NOIR) {
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
            game.playAt(lin, col);
            coupIA();
        }
    }


}
