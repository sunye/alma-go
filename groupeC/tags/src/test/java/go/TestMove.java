package test;


import go.Move;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.*;


/**
 * JUnit tests for Move
 * @author Fred Dumont
 * @version 1.0
 *
 */
public class TestMove extends TestCase{

	byte coordx;
	byte coordy;
	byte color;
	
	byte newCoordx;
	byte newCoordy;
	byte newColor;
	
	Move testMove = new Move(coordx, coordy, color);
	
	
	/**
	 * Constructor for TestMove
	 */
	public TestMove(){

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
	 * Test Set/Get x
	 */
	@Test
	public void testSetGetX() {
		assertSame(testMove.getX(), coordx);
		testMove.setX(coordx);
		assertSame(testMove.getX(), newCoordx);
	}

	/**
	 * Test Set/Get y
	 */
	@Test
	public void testSetGety() {
		assertSame(testMove.getY(), coordy);
		testMove.setY(coordy);
		assertSame(testMove.getY(), newCoordy);
	}

	/**
	 * Test Set/Get color
	 */
	@Test
	public void testSetGetColor() {
		assertSame(testMove.getColor(), color);
		testMove.setColor(coordy);
		assertSame(testMove.getColor(), newColor);
	}

	/**
	 * Test isEvaluate
	 */
	@Test
	public void testIsEvaluate() {
		assertSame(testMove.isEvaluate(), false);
		testMove.setEvaluate(true);
		assertSame(testMove.isEvaluate(), true);
	}
	
	/**
	 * Test setMove
	 */
	@Test
	public void testSetEvaluate() {
		testMove.setMove(newCoordx, newCoordy, newColor);
		assertSame(testMove.getX(),newCoordx);
		assertSame(testMove.getY(),newCoordy);
		assertSame(testMove.getColor(),newColor);
	}
}
