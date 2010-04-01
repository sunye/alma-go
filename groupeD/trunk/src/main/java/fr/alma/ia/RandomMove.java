package fr.alma.ia;

import java.util.List;

import fr.alma.atarigo.AtariGo;
import fr.alma.atarigo.Goban;
import fr.alma.atarigo.Position;
import fr.alma.atarigo.Stone;

import static java.lang.Math.random;

public class RandomMove {

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
