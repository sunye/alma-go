package fr.alma.atarigo;

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

/**
 * Player.java is an abstract class representing a type of player.
 * @author VINCENT FERREIRA, ADRIEN GUILLE
 * @version 1.0
 * 
 * revision $Revision$
 * 
 */

public abstract class AbstractPlayer {

	/** Color of the player (black or white) */
	public Stone color;
	/** Name of the player (not used finally) */
	public String name;

	/**
	 * Logic constructor
	 * @param color color of the stones played by the player
	 * @param name name of the player (not used yet)
	 */
	public AbstractPlayer(Stone color, String name){
		this.color = color;
		this.name = name;
	}
	
	/**
	 * verify if the player is human
	 */
	public abstract boolean isHuman();

	/**
	 * Difficulty of a human player is 0. only usefull for AI @see IAPlayer
	 * @return 0
	 */
	public int getDifficulty() {
		// TODO Auto-generated method stub
		return 0;
	}

}
