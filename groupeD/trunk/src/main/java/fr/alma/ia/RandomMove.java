package fr.alma.ia;

import java.util.List;

import fr.alma.atarigo.Goban;
import fr.alma.atarigo.Stone;

public class RandomMove {

	public static Goban play(Goban goban,Stone stone){
		List<Goban> list = goban.computeMoves(stone);
		int randomPosition = (int)((Math.random())*list.size());
		return list.get(randomPosition);
	}
}
