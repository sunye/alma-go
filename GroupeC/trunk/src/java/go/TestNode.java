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
 * JUnit tests for Node
 * @author Fred Dumont
 * @version 1.0
 *
 */

public class TestNode extends TestCase{

	public byte coordx = 2;
	public byte coordy = 5;
	public byte coordx2 = 3;
	public byte coordy2 = 6;
	
	Main mainTest = new Main();

	Move movementTest = new Move(coordx, coordy);
	Move movementTest2 = new Move(coordx2, coordy2);
	Move movementTest3 = new Move(coordx, coordy2);
	
	State stateTest = new State(mainTest);
	State stateTest2 = new State(mainTest);

	Node nodeTest = new Node(movementTest, stateTest);
	Node otherNodeTest = new Node(movementTest, stateTest);
	Node otherNodeTest2 = new Node(movementTest, stateTest);


	/**
	 * Constructor for TestCoord()
	 */
	public TestNode(){

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
	 * Test Get/Set state
	 */
	@Test
	public void testGetSetState() {
		assertSame(nodeTest.getState(),stateTest);
		otherNodeTest.setState(stateTest2);
		assertSame(otherNodeTest.getState(),stateTest2);
	}
	
	/**
	 * Test Get/Set move
	 */
	@Test
	public void testGetSetMove() {
		assertSame(nodeTest.getMove(),movementTest);
		otherNodeTest.setMove(movementTest);
		assertSame(otherNodeTest.getMove(),movementTest);
	}
	
	/**
	 * Test Get/Set hauteur
	 */
	@Test
	public void testGetSetHauteur() {
		assertSame(nodeTest.getHauteur(),1);
		nodeTest.setHauteur(5);
		assertSame(nodeTest.getHauteur(),5);
	}
	
	/**
	 * Test Get/Set pere
	 */
	@Test
	public void testGetSetPere() {
		assertSame(otherNodeTest.getPere(),null);
		otherNodeTest.setPere(nodeTest);
		assertSame(otherNodeTest.getPere(),nodeTest);
	}
	
	/**
	 * Test ajouterFils
	 */
	@Test
	public void testAjouterFils() {
		otherNodeTest.ajouterFils(otherNodeTest2);
		assertSame(otherNodeTest2.getPere(),otherNodeTest);

	}
	
	/**
	 * Test retirerFils
	 */
	@Test
	public void testRetirerFils() {
		otherNodeTest.retirerFils(otherNodeTest2);
		assertSame(otherNodeTest2.getPere(),null);

	}
	
	/**
	 * Test Node
	 */
	@Test
	public void testNode() {
		assertSame(otherNodeTest2.node(movementTest),otherNodeTest2);
		assertNotSame(otherNodeTest2.node(movementTest3),otherNodeTest2);
	}
}
