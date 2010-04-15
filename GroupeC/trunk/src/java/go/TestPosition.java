package test;

import go.Main;
import go.Position;
import go.State;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import junit.framework.*;


/**
 * JUnit tests for Position
 * @author Fred Dumont
 * @version 1.0
 *
 */
public class TestPosition extends TestCase{

	byte coordx = 3;
	byte coordy = 6;
	byte oState = 0;
	byte empty = 0;
	byte black = 1;
	
	Main mainTest = new Main();
	
	State myState = new State(mainTest);
	
	Position testPosition = new Position();
	
	/**
	 * Constructor for TestPosition
	 */
	public TestPosition(){

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
	 * Test Set/Get State
	 */
	@Test
	public void testSetGetState() {
		testPosition.setState(myState);
		assertSame(testPosition.getState(), myState);
		
	}
	
	/**
	 * Test Set/Get x
	 */
	@Test
	public void testSetGetX() {
		testPosition.setX(coordx);
		assertSame(testPosition.getX(), coordx);
	}
	
	/**
	 * Test Set/Get y
	 */
	@Test
	public void testSetGetY() {
		testPosition.setY(coordy);
		assertSame(testPosition.getY(), coordy);
		
	}
	
	/**
	 * Test getOwnState
	 */
	@Test
	public void testGetOwnState() {
		assertSame(testPosition.getOwnState(), empty);
	}
	
	/**
	 * Test setState
	 */
	@Test
	public void testSetState() {
		testPosition.setState(myState);
		assertSame(testPosition.getState(), myState);
	}
	
	/**
	 * Test group
	 */
	@Test
	public void testGroup() {
		assertSame(testPosition.getGroup(), null);
	}
	
	/**
	 * Test reset
	 */
	@Test
	public void testReset() {
		testPosition.reset();
		assertSame(testPosition.getOwnState(), empty);
	}
}
