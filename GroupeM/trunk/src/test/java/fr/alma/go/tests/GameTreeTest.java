package fr.alma.go.tests;

import fr.alma.go.goban.Goban;
import fr.alma.go.goban.Place;
import fr.alma.go.heuristics.GameTree;
import junit.framework.TestCase;

public class GameTreeTest extends TestCase {
	
	public void testAlphaBetaGetPlace(){
		Goban goban=new Goban();
		GameTree tree=new GameTree();
		int[][] coeffs = { { 0, 1, 1, 1, 1, 1, 1, 1, 0 },
				{ 1, 1, 2, 2, 2, 2, 2, 1, 1 }, { 1, 2, 3, 3, 2, 3, 3, 2, 1 },
				{ 1, 2, 3, 2, 2, 2, 3, 2, 1 }, { 1, 2, 2, 2, 1, 2, 2, 2, 1 },
				{ 1, 2, 3, 2, 2, 2, 3, 2, 1 }, { 1, 2, 3, 3, 2, 3, 3, 2, 1 },
				{ 1, 1, 2, 2, 2, 2, 2, 1, 1 }, { 0, 1, 1, 1, 1, 1, 1, 1, 0 } };
		Place place=tree.alphaBetaGetPlace(goban, true);
		assertEquals(coeffs[place.getAbs()][place.getOrd()],3);
	}
	
}
