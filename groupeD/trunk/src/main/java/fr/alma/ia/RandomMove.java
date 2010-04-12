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
