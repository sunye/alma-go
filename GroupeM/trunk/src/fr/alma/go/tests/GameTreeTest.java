package fr.alma.go.tests;

import fr.alma.go.goban.Goban;
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
		System.out.println(tree.alphaBetaGetPlace(goban, true));
		assertTrue(true);
	}
	
}
