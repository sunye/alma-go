package fr.alma.go.tests;

import fr.alma.go.goban.Goban;
import fr.alma.go.goban.Place;
import fr.alma.go.heuristics.GameTree;
import junit.framework.TestCase;

public class GameTreeTest extends TestCase {
	
//	public void testGenerateTree(){
//		Goban goban=new Goban();
//		GameTree tree=new GameTree();
//		tree.generateTree(goban, true, 0);
//		assertTrue(tree.getSons().get(0).isLeaf());
//	}
	
	public void test(){
		Goban goban=new Goban();
		GameTree tree=new GameTree();
		int[][] coeffs = { { 0, 1, 1, 1, 1, 1, 1, 1, 0 },
				{ 1, 1, 2, 2, 2, 2, 2, 1, 1 }, { 1, 2, 3, 3, 2, 3, 3, 2, 1 },
				{ 1, 2, 3, 2, 2, 2, 3, 2, 1 }, { 1, 2, 2, 2, 1, 2, 2, 2, 1 },
				{ 1, 2, 3, 2, 2, 2, 3, 2, 1 }, { 1, 2, 3, 3, 2, 3, 3, 2, 1 },
				{ 1, 1, 2, 2, 2, 2, 2, 1, 1 }, { 0, 1, 1, 1, 1, 1, 1, 1, 0 } };
		Place place=tree.alphaBetaGetPlace(goban, true);
		int abs=place.getAbs();
		int ord=place.getOrd();
		assertEquals(coeffs[abs][ord],3);
	}
	
}
