package test;

import go.Game;
import go.Goban;
import go.Main;
import go.State;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import junit.framework.*;


/**
 * JUnit tests for Goban
 * @author Fred Dumont
 * @version 1.0
 *
 */


public class TestGoban extends TestCase{
	
	Goban gobanTest = new Goban();
	Goban gobanTest2 = new Goban();
	Main mainTest = new Main();
	Game gameTest = new Game();
	State stateTest = new State(mainTest);
	
	int size = 9;
	
	/**
	 * Constructor for TestGoban()
	 */
	public TestGoban(){
		
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
	 * Test init
	 */
	@Test
	public void testInit() {
		gobanTest.init(mainTest, gameTest, size);
		assertSame(gobanTest.size, size);
		assertSame(gobanTest.color, -1);
		
	}

	/**
	 * Test reset
	 */
	@Test
	public void testReset() {
		gobanTest2.init(mainTest, gameTest, size);
		gobanTest2.reset();
		assertSame(gobanTest2.color, -1);
		
	}
}
