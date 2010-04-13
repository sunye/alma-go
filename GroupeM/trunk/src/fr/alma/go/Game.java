// File Game.java
// Last commited $Date$
// By $Author$
// Revision $Rev$
//
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
import fr.alma.go.interfaces.IPlayer;
import fr.alma.go.player.Computer;
import fr.alma.go.player.Human;
import fr.alma.go.ui.GUI;

public class Game {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Goban goban = new Goban();
		GUI.init(goban);
		IPlayer black = new Human('b');
		IPlayer white = new Computer(false, 1);
		boolean turn = true; // true for black turn, false for white turn
		while (true) {
			if (turn) {
				Place place = black.getPlace(goban);
				int abs = place.getAbs();
				int ord = place.getOrd();
				goban.play(abs, ord, 'b');
			} else {
				Place place = white.getPlace(goban);
				int abs = place.getAbs();
				int ord = place.getOrd();
				goban.play(abs, ord, 'w');
			} // if
			GUI.update();
			if (goban.gameOver()) {
				GUI.gameOver(turn);
				break;
			}
			turn = !turn;
		} // while
	}

}
