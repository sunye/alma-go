// Copyright (C) 2010 Alexandre Garnier & Yann Treguer
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

package fr.alma.go;

import fr.alma.go.goban.Goban;
import fr.alma.go.goban.Place;
import fr.alma.go.heuristics.Computer;
import fr.alma.go.ui.GoFrame;
import fr.alma.go.ui.GoPanel;

public class Game {

	private Goban goban;
	private GoPanel goPan;
	private GoFrame frame;
	private Boolean onePlayerGame;
	private char iaColor;
	private Computer cpu;
	private char actualColor;

	/**
	 * Constructor
	 * 
	 * @param gp
	 * 			Panel containing goban
	 * @param frm
	 * 			Frame containing panel
	 */
	public Game(GoPanel gp, GoFrame frm) {
		this.goPan = gp;
		this.frame = frm;
		this.actualColor = 'b';
	}

	/**
	 * Switch color between 'b' (black) and 'w' (white)
	 */
	public void switchColor() {
		if (actualColor == 'b') {
			actualColor = 'w';
		} else {
			actualColor = 'b';
		} // if
	} // switchColor()

	/**
	 * Start a game
	 */
	private void init() {
		goban = new Goban();
	}

	/**
	 * Begin a two-players game
	 */
	public void twoPlayers() {
		init();
		this.onePlayerGame = Boolean.FALSE;
		goPan.startGame(this, this.goban);
	}

	/**
	 * Begin a one-player game
	 * 
	 * @param couleur
	 *            color of the stone player
	 */
	public void onePlayer(char color) {
		init();
		cpu = new Computer((color == 'b'), 1);
		iaColor = color;
		this.onePlayerGame = Boolean.TRUE;
		goPan.startGame(this, this.goban);
		if (color == 'b') {
			coupIA();
			this.switchColor();
		} // if
	}

	/**
	 * The computer plays.
	 */
	private void coupIA() {
		goPan.desactivateMouse();
		Place place = cpu.getPlace(goban);
		goban.play(place.getAbs(), place.getOrd(), iaColor);
		goPan.repaint();
		goPan.activateMouse();
	}

	/**
	 * Do a move (and display it)
	 * 
	 * @param abs
	 *            line of the move
	 * @param ord
	 *            column of the move
	 */
	public void appliqueCoup(int ord, int abs) {
		if (goban.play(abs, ord, actualColor)) {
			goPan.paintImmediately(0, 0, goPan.getWidth(), goPan.getHeight());
			if (this.onePlayerGame) {
				coupIA();
			} else {
				this.switchColor();
			} // if
		}
	}

	/**
	 * Test an endgame
	 * 
	 * @return if it is the end of the game
	 */
	public boolean gameOver() {
		return goban.gameOver();
	}

	/**
	 * Accessor : game
	 * 
	 * @return the current game
	 */
	final public Goban getGame() {
		return goban;
	}

	/**
	 * Open a window endgame to announce the winner of the game.
	 */
	public void dialogueFinJeu() {
		this.frame.finJeu();
	}
}