/*   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package fr.alma.atarigo;

import java.util.ArrayList;

/**
 * 
 * @author Vincent FERREIRA, Adrien GUILLE
 * 
 * revision $Revision$
 *
 */

public interface IBoard {

	/**
	 * remove all the stones from the board for a new game.
	 */
	public abstract void newGame();

	/**
	 * accessor for lines
	 */
	public abstract int getLines();

	/**
	 * accessor for columns
	 */
	public abstract int getColumns();

	/**
	 * indicate if the position belongs to the board.
	 * @param position tested position
	 */
	public abstract boolean isValid(Position position);

	/**
	 * Find all cells of a given color
	 * @param stone
	 * @return a list of the stones
	 */
	public abstract ArrayList<Position> getCells(Stone stone);

	/**
	 * Compares two goban and find the difference
	 * @param goban
	 * @return the position of the difference, if there's one, or returns an invalid position.
	 */
	public abstract Position getDifference(Goban goban);

	public abstract boolean canPlay(Stone color);

}