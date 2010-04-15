package test;

import go.Movement;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import junit.framework.*;


/**
 * JUnit tests for Movement
 * @author Fred Dumont
 * @version 1.0
 *
 */


public class TestMovement extends TestCase{
	
	final int BLACK = -1;
	final int WHITE = 1;
	
	Movement movementTest = new Movement(BLACK);
	Movement movementTestBis = new Movement(BLACK);
	Movement movementTest2 = new Movement(WHITE);
	
	
	/**
	 * Constructor for TestMovement.
	 */
	public TestMovement(){

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
	 * Test Get/Set Color
	 */
	@Test
	public void testGetSetColor() {
		movementTest.setColor(BLACK);
		movementTest2.setColor(WHITE);
		assertSame(movementTest.getColor(),BLACK);       //if assertion ok, get and set ok
		assertSame(movementTest2.getColor(),WHITE);       //if assertion ok, get and set ok
	}
	
	/**
	 * Test Equals
	 */
	@Test
	public void testEquals() {
		assertSame(movementTest.equals(movementTestBis),true);
		assertNotSame(movementTest.equals(movementTest2),true);
	}

}
