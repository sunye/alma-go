package test;

import go.Coord;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import junit.framework.*;


/**
 * JUnit tests for Coord
 * @author Fred Dumont
 * @version 1.0
 *
 */
public class TestCoord extends TestCase {
	
	//x and y for tests
	public byte coordX1 = 2;
	public byte coordY1 = 5;
	public byte coordX2 = 0;
	public byte coordY2 = 0;
	
	public byte testY = 5;
	public byte testX = 2;

	Coord coordTest = new Coord(coordX1,coordY1);
	Coord otherCoordTest = new Coord(coordX2,coordY2);
	Coord result = new Coord(coordX2, coordY1);
	Coord otherResult = new Coord(coordX1, coordY2);
	
	/**
	 * Constructor for TestCoord()
	 */
	public TestCoord(){
		
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
	 * Test Get/Set Y
	 */
	@Test
	public void testGetSetCoordY() {
		assertSame(coordTest.getCoordY(),testY);       //if assertion ok, getCoordY() ok
		otherCoordTest.setCoordY(coordY1);
		assertSame(otherCoordTest.getCoordY(),testY);   //if assertion ok, setCoordY() ok
	}
	
	/**
	 * Test Get/Set Y
	 */
	@Test
	public void testGetSetCoordX() {
		assertSame(coordTest.getCoordX(),testX);       //if assertion ok, getCoordX() ok
		otherCoordTest.setCoordX(coordX1);
		assertSame(otherCoordTest.getCoordX(),testX);   //if assertion ok, setCoordX() ok
	}
	
	/**
	 * Test getMaxTopRight
	 */
	@Test
	public void testGetMaxTopRight() {
		Coord temp = coordTest.getMaxTopRight(otherCoordTest);
		assertEquals(temp.getCoordX(),result.getCoordX());
		assertEquals(temp.getCoordY(),result.getCoordY());
	}
	
	/**
	 * Test getMaxBottomLeft
	 */
	@Test
	public void testGetMaxBottomLeft() {
		Coord temp = coordTest.getMaxBottomLeft(otherCoordTest);
		assertEquals(temp.getCoordX(),otherResult.getCoordX());
		assertEquals(temp.getCoordY(),otherResult.getCoordY());
	}
}
