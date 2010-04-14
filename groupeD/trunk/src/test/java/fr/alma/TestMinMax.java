package fr.alma.test;

import fr.alma.atarigo.Goban;
import fr.alma.atarigo.Position;
import fr.alma.atarigo.Stone;
import fr.alma.ia.AlphaBeta;
import fr.alma.ia.InitialEvaluation;
import fr.alma.ia.MinMax;
import fr.alma.ia.Tree;
import junit.framework.TestCase;

public class TestMinMax extends TestCase {

	public void testValue() {
		Goban goban = new Goban(3,3);
		goban.writeCell(null, new Position(0,1), Stone.BLACK, true);
		goban.writeCell(null, new Position(0,0), Stone.WHITE, true);
		Tree tree = new Tree(goban);
		AlphaBeta.init(1, goban,Stone.BLACK);
				
		assertTrue(goban.getDifference(AlphaBeta.value(0, tree, InitialEvaluation.VERYGOOD, Stone.BLACK, null, null).goban_).isEqual(new Position(1,0)));
		
		MinMax.init(2, goban,Stone.BLACK);
		MinMax.value(0, tree, Stone.BLACK, null, null);
	}

}
