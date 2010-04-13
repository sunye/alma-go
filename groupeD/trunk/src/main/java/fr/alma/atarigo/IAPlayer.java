package fr.alma.atarigo;

/**
 * AIPlayer represent a AI player.
 * @author 
 * @version 1.0
 * 
 *   This program is free software: you can redistribute it and/or modify
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
public class IAPlayer extends Player{

	/** the bestmove kept by an AI player. */
	public Position bestMove;
	/** the depth that the AI player can go in the search tree. */
	private int difficulty;
	
	/**
	 * Logic constructor.
	 * @param color Color of the player (black or white)
	 * @param name Name of the player (not used finally).
	 * @param dif Difficulty of the player @see difficulty
	 */
	public IAPlayer(Stone color, String name,int dif) {
		super(color, name);
		difficulty = dif;
		// TODO Auto-generated constructor stub
	}

	/**
	 * Return if the player is human type.
	 */
	public boolean isHuman(){
		return false;
	}
	
	/**
	 * An AI player can keep the best move to play. This method return this move.
	 * @return
	 */
	public Position playBestShot(){
		return bestMove;
	}
	
	
	/**
	 * Return the depth of the AI search in the search tree. Can be seen like a difficulty.
	 */
	public int getDifficulty(){
		return difficulty;
	}
	
}
