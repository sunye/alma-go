package test;

import go.Main;
import go.Move;
import go.Node;
import go.State;
import go.Tree;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.*;


/**
 * JUnit tests for Tree
 * @author Fred Dumont
 * @version 1.0
 *
 */

public class TestTree extends TestCase {

	
	Main mainTest = new Main();

	State firstState = new State(mainTest);
	State secondState = new State(mainTest);
		
	public byte coordx2 = 3;
	public byte coordy2 = 6;
	
	byte coordx = 3;
	byte coordy = 6;
	byte coord1 = 1;
	byte coord2 = 2;
	int stone = 1;
	byte color = 1;
	byte black = -1;
	int case0 = 0;
	int case1 = 1;
	
	
	Move movementTest = new Move(coordx, coordy);
	Move movementTest2 = new Move(coordx2, coordy2);
	Move movementTest3 = new Move(coordx, coordy2);
	
	State stateTest = new State(mainTest);
	State stateTest2 = new State(mainTest);

	Node nodeTest = new Node(movementTest, stateTest);
	Node otherNodeTest = new Node(movementTest, stateTest);
	Node otherNodeTest2 = new Node(movementTest3, stateTest2);
		
	Move move = new Move(coord1, coord2);
	Node root = new Node(move, secondState);
	
	Tree treeTest = new Tree();
	Tree treeTest2 = new Tree(movementTest3, stateTest2);
	
	/**
	 * Constructor for TestTree()
	 */
	public TestTree(){

	}

	/** 
	 * @throws java.lang.Exception
	 * @see junit.framework.TestCase#setUp()
	 */
	@Before
	protected void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 * @see junit.framework.TestCase#tearDown()
	 */
	@After
	protected void tearDown() throws Exception {
	}

	/**
	 * Test creerArbre
	 */
	@Test
	public void testCreerArbre() {
		assertNotSame(treeTest.creerArbre(nodeTest, firstState),treeTest);	
	}
	
	/**
	 * Test pere
	 */
	@Test
	public void testPere() {
		treeTest.ajouterFils(otherNodeTest, nodeTest);
		assertSame(treeTest.pere(otherNodeTest),nodeTest);	
	}
	
	/**
	 * Test racine
	 */
	@Test
	public void testRacine() {
		assertSame(treeTest.racine(),treeTest);	
	}
	
	/**
	 * Test rootNode
	 */
	@Test
	public void testRootNode() {
		assertNotSame(treeTest2.rootNode(),otherNodeTest2);	
	}
	
	/**
	 * Test count
	 */
	@Test
	public void testCount() {
		assertSame(treeTest2.count(),1);
	}
	
	
}
