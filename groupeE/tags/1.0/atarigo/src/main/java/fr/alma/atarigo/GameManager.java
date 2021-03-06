// File GameManager.java
// Last commited $Date$
// By $Author$
// Revision $Rev$
//
// Copyright (C) 2010 Clotilde Massot & Julien Durillon
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// as published by the Free Software Foundation; either version 2
// of the License, or (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
//
// The above copyright notice and this permission notice shall be
// included in all copies or substantial portions of this program.
package fr.alma.atarigo;

import fr.alma.atarigo.analyse.ABThread;
import fr.alma.atarigo.analyse.AlphaBeta;
import fr.alma.atarigo.ihm.Fenetre;
import fr.alma.atarigo.utils.Game;
import fr.alma.atarigo.utils.PionVal;
import fr.alma.atarigo.ihm.GobanPanel;
import fr.alma.atarigo.utils.PlayMove;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author clotildemassot
 */
public class GameManager {

    private Game game;
    private GobanPanel gobanPanel;
    private Fenetre fenetre;
    private Boolean onePlayerGame;
    private PionVal iaColor;
    private ABThread thread;

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
        iaColor = couleur;
        this.onePlayerGame = Boolean.TRUE;
        gobanPanel.startGame(this, this.game);
        if (couleur == PionVal.NOIR) {
            coupIA();
        }
    }


    private PlayMove threadizeAB(){
//        AlphaBeta alphabeta = new AlphaBeta(3, iaColor);
        
        thread = new ABThread(3, iaColor, game);
        thread.start();
        try {
            thread.join(10000);
        } catch (InterruptedException ex) {
            Logger.getLogger(GameManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        PlayMove retour = thread.getCurrentBest();
        thread.interrupt();
        return retour;
    }

    /**
     * The computer plays.
     */
    private void coupIA() {
        gobanPanel.desactivateMouse();
        PlayMove coup;
        coup = threadizeAB();
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
