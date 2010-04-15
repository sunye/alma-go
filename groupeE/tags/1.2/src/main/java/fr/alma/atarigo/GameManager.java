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
// along with this program;
//
// The above copyright notice and this permission notice shall be
// included in all copies or substantial portions of this program.
package fr.alma.atarigo;

import fr.alma.atarigo.analyse.AlphaBeta;
import fr.alma.atarigo.ihm.Fenetre;
import fr.alma.atarigo.utils.Game;
import fr.alma.atarigo.utils.StoneVal;
import fr.alma.atarigo.ihm.GobanPanel;
import fr.alma.atarigo.utils.PlayMove;

/**
 * The Controler of our MVC design.
 * @author clotildemassot
 */
public class GameManager {

   /**
    * In milliseconds, the time we have to search for a good move.
    */
   private static  final int TIMER = 15000;
   /**
    * The maximum depth of the alphabeta search tree.
    */
   private static  final int DEPTH = 3;

   /**
    * The game engine, the center of the model part.
    */
   private Game game;
   /**
    * The visual part of the board.
    */
   private final GobanPanel gobanPanel;
   /**
    * The main frame of the application.
    */
   private final Fenetre window;
   /**
    * Is this a one or two players game ?
    */
   private Boolean onePlayerGame;
   /**
    * If onePlayerGame, this field contains the AI color.
    */
   private StoneVal iaColor;

   /**
    * Unique constructor.
    * @param gobPan the GobanPanel instance.
    * @param fenetre the main frame.
    */
   public GameManager(final GobanPanel gobPan, final Fenetre fenetre) {
      this.gobanPanel = gobPan;
      this.window = fenetre;
   }

   /**
    * Initialize a game.
    */
   private void init() {
      game = new Game();
   }

   /**
    * Begins a two-players game.
    */
   public final void twoPlayers() {
      init();
      this.onePlayerGame = Boolean.FALSE;
      gobanPanel.startGame(this, this.game);
   }

   /**
    * Begins a one-player game.
    * @param couleur : color of the stone player
    */
   public final void onePlayer(final StoneVal couleur) {
      init();
      iaColor = couleur;
      this.onePlayerGame = Boolean.TRUE;
      gobanPanel.startGame(this, this.game);
      if (couleur == StoneVal.BLACK) {
         coupIA();
      }
   }


   /**
    * The computer plays.
    */
   private void coupIA() {
      gobanPanel.desactivateMouse();
      PlayMove coup;
//        coup = threadizeAB();
      AlphaBeta alphabeta = new AlphaBeta(DEPTH, iaColor);
      coup = alphabeta.init(game, TIMER);
      game.playAt(coup.getPutStone().getLine(), coup.getPutStone().getColumn());
      gobanPanel.repaint();
      gobanPanel.activateMouse();
   }

   /**
    * Does a move (and asks the gobanPanel to display it).
    * @param lin : line of the move
    * @param col : column of the move
    */
   public final void appliqueCoup(final int lin, final int col) {
      if (game.playAt(lin, col)) {
         gobanPanel.paintImmediately(0, 0,
                 gobanPanel.getWidth(), gobanPanel.getHeight());
         if (this.onePlayerGame) {
            coupIA();
         }
      }
   }

   /**
    * Test an endgame.
    * @return true if the game is over
    */
   public final boolean isEnd() {
      return game.isEnd();
   }

   /**
    * Getter : game.
    * @return the current game
    */
   public final Game getGame() {
      return game;
   }

   /**
    * Opens a window to announce the winner of the game.
    */
   public final void endGameDialog() {
      this.window.finJeu();
   }
}
