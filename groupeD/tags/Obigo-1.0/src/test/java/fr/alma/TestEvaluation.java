package fr.alma.test;

import fr.alma.atarigo.Goban;
import fr.alma.atarigo.Position;
import fr.alma.atarigo.Stone;
import fr.alma.ia.InitialEvaluation;
import junit.framework.TestCase;

public class TestEvaluation extends TestCase {

	public void testEvaluate() {
	}

	public void testCommonBorder() {
		Goban goban = new Goban(9,9);
		goban.writeCell(null, new Position(3,5), Stone.BLACK, false);
		goban.writeCell(null, new Position(3,4), Stone.BLACK, false);
		goban.writeCell(null, new Position(4,4), Stone.WHITE, false);
		
		assertEquals(InitialEvaluation.commonBorder(goban,new Position(4,4),Stone.BLACK),-1);
	}

	public void testHasCaught() {
		Goban goban = new Goban(9,9);
		goban.writeCell(null, new Position(0,1), Stone.BLACK, false);
		goban.writeCell(null, new Position(0,0), Stone.WHITE, false);
		goban.writeCell(null, new Position(1,0), Stone.BLACK, false);
				
		assertEquals(InitialEvaluation.hasCaught(goban,new Position(0,1),Stone.BLACK),1);
		assertEquals(InitialEvaluation.hasCaught(goban,new Position(0,1),Stone.WHITE),-1);
	}

	public void testUnderNGroups() {
		Goban goban = new Goban(9,9);
		goban.writeCell(null, new Position(0,5), Stone.BLACK, false);
		goban.writeCell(null, new Position(0,0), Stone.WHITE, false);
		goban.writeCell(null, new Position(5,0), Stone.BLACK, false);
		
		assertEquals(InitialEvaluation.underNGroups(goban,Stone.BLACK,2),-1);
		assertEquals(InitialEvaluation.underNGroups(goban,Stone.WHITE,2),0);

		
	}

}
