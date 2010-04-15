package test;


import go.Game;
import go.Main;
import go.Move;
import go.Node;
import go.State;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import junit.framework.*;


/**
 * JUnit tests for Game
 * @author Fred Dumont
 * @version 1.0
 *
 */

public class TestGame extends TestCase{

	//Game for tests
	Game gameTest = new Game();
	Game gameTest2 = new Game();
	Main mainTest = new Main();

	private int fictiveNode=6;
	private int size = 100;
	final byte BLACK = -1;
	
	public byte coordx = 2;
	public byte coordy = 5;
	
	State stateTest = new State(mainTest);
	Move movementTest = new Move(coordx, coordy);
	
	Node nodeTest = new Node(movementTest, stateTest);

	/**
	 * Constructor for TestGame
	 */
	public TestGame(){

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
	 * Test Get/Set FictiveNode
	 */
	@Test
	public void testGetSetFictiveNode() {
		gameTest.setFictiveNode(fictiveNode);
		assertSame(gameTest.getFictiveNode(),6);       //if assertion ok, get and set ok
	}

	/**
	 * Test Get/Set Size
	 */
	@Test
	public void testSize() {
		gameTest.setSize(size);
		assertSame(gameTest.getSize(),100);       //if assertion ok, get and set ok
	}
	
	/**
	 * Test Get/Set Node
	 */
	@Test
	public void testNode() {
		assertSame(gameTest.getNode(), 0);
		gameTest.setNode(fictiveNode);
		assertSame(gameTest.getNode(),fictiveNode);       //if assertion ok, get and set ok
	}

	/**
	 * Test Init
	 */
	@Test
	public void testInit() {
		gameTest.init();
		assertSame(gameTest.getNode(),0);
		assertSame(gameTest.getFictiveNode(),0);
	}

	/**
	 * Test Stop
	 */
	@Test
	public void testStop() {
		gameTest2.init();
		gameTest2.stop();
		assertSame(gameTest.getNode(),0);
	}

	/**
	 * Test FictiveEnd
	 */
	@Test
	public void testFictiveEnd() {
		gameTest2.init();
		gameTest2.fictiveEnd();
		assertSame(gameTest.getNode(),0);
	}
}
