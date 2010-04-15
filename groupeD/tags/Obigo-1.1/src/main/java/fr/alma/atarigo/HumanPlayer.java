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

/**
 * HumanPlayer.java represent a human player
 * @author VINCENT FERREIRA, ADRIEN GUILLE
 * @version 1.0
 * 
 * revision $Revision$
 * 
 */
public class HumanPlayer extends AbstractPlayer{

	/**
	 * Logic contructor
	 * @param color Color played
	 * @param name Name of the player (not used finally)
	 */
	public HumanPlayer(Stone color, String name) {
		super(color, name);
		// TODO Auto-generated constructor stub
	}

	/**
	 * return if the player is human type
	 */
	public boolean isHuman(){
		return true;
	}

}
