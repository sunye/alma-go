package fr.alma.test;

import fr.alma.atarigo.AtariGo;
import fr.alma.atarigo.Goban;
import fr.alma.atarigo.Position;
import fr.alma.atarigo.Stone;
import fr.alma.ia.RandomMove;
import junit.framework.TestCase;

public class TestRandomMove extends TestCase {

	public void testPlay() {
		Goban goban = new Goban(4,4);
		goban.writeCell(null, new Position(1,1), Stone.WHITE, false);
		assertTrue(goban.isValid(goban.getDifference(RandomMove.play(null,goban,Stone.BLACK))));
	}

}
