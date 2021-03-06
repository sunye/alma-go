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

package fr.alma.go.heuristics;

import fr.alma.go.goban.Goban;
import fr.alma.go.goban.Place;
import fr.alma.go.interfaces.IHeuristics;

public class Computer {

	private boolean color;

	private IHeuristics level;

	/**
	 * Constructor
	 * 
	 * @param col
	 *            Computer color
	 * @param lvl
	 *            Computer level
	 */
	public Computer(boolean col, int lvl) {
		color = col;
		switch (lvl) {
		case 1:
			level = new Beginner();
			break;
		case 2:
			level = new Medium();
			break;
		case 3:
			level = new Expert();
			break;
		default:
			level = null;
			System.err.println("Error : inexistant level.");
		} // switch
	} // Computer()

	/**
	 * Get place to play
	 * 
	 * @param goban
	 *            Actual state of the goban
	 * @return The best place to play among the heuristics
	 */
	public Place getPlace(Goban goban) {
		return level.getBestPlace(goban, color);
	} // Place getPlace()

}
