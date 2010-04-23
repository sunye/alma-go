package myPack;

import junit.framework.TestCase;

public class JeuConsoleTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testAlafbet2IntCoord() {
		JeuConsole.alafbet2IntCoord('c', 1);
		assertEquals(3, JeuConsole.X);
	}

	public void testGobanIntCoord2Matrix() {
		JeuConsole.gobanIntCoord2Matrix(3, 9);//=c9
		assertEquals(2, JeuConsole.X);
		assertEquals(0, JeuConsole.Y);

	}

}
