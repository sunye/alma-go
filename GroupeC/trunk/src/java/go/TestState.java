package test;

import go.Main;
import go.Move;
import go.Node;
import go.State;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import junit.framework.*;


/**
 * JUnit tests for State
 * @author Fred Dumont
 * @version 1.0
 *
 */

public class TestState extends TestCase {
	
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
	Node otherNodeTest2 = new Node(movementTest, stateTest);
		
	Move move = new Move(coord1, coord2);
	Node root = new Node(move, secondState);
	
	
	
	/**
	 * Constructor for TestState()
	 */
	public TestState(){

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
	 * Test computerNext
	 */
	@Test
	public void testComputerNext() {
		assertNotSame(firstState.computeNext(coordx, coordy, stone),move);	
	}
	
	/**
	 * Test raiseChild
	 */
	@Test
	public void testRaiseChild() {
		root.ajouterFils(nodeTest);
		nodeTest.ajouterFils(otherNodeTest);
		assertSame(secondState.raiseChild(otherNodeTest, case0),false);
		assertSame(secondState.raiseChild(otherNodeTest, case1),false);	
	}
	
	/**
	 * Test isLegalMove
	 */
	@Test
	public void testIsLegalMove() {
		assertSame(secondState.isLegalMove(coordx, coordy, stone),true);
		assertSame(secondState.isLegalMove(coordx, coordy, black),true);
	}
	
	/**
	 * Test isTakingStones
	 */
	@Test
	public void testIsTakingStones() {
		assertSame(secondState.isTakingStones(coordx, coordy, stone),false);
		assertSame(secondState.isTakingStones(coordx, coordy, black),false);
	}
}
