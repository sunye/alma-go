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

package fr.alma.ia;

import java.util.List;

import fr.alma.atarigo.AtariGo;
import fr.alma.atarigo.Goban;
import fr.alma.atarigo.Position;
import fr.alma.atarigo.Stone;

import static java.lang.Math.random;

/**
 * RandomMove is a static class which implements is called at the beginning of a game.
 * It returns a random valid move. 
 *
 * @author      Adrien GUILLE
 * @author      Vincent FERREIRA
 * 
 * @version 1.0
 * 
 * revision $Revision$
 * 
 */ 

public class RandomMove {

	/**
	 * 
	 * @param atariGo
	 * @param goban
	 * @param stone
	 * @return a valid random move
	 */
	public static Goban play(AtariGo atariGo,Goban goban,Stone stone){
		int x,y;
		x=(int)(random()*6+1);
		y=(int)(random()*6+1);
		Position position = new Position(x,y);
		while(!goban.isValid(position)){
			x=(int)(random()*6+1);
			y=(int)(random()*6+1);
			position = new Position(x,y);
		}
		Goban result = new Goban(goban);
		result.writeCell(atariGo,position,stone,false);
		return result;
	}
}
