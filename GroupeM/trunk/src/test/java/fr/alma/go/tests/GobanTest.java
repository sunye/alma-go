package fr.alma.go.tests;

import junit.framework.TestCase;
import fr.alma.go.goban.Goban;

public class GobanTest extends TestCase {

	public void testSuicide() {
		Goban goban = new Goban();
		goban.play(1, 0, 'b');
		goban.play(0, 1, 'b');
		goban.play(1, 2, 'b');
		goban.play(2, 1, 'b');
		assertFalse(goban.play(1, 1, 'w'));
	}

	public void testGameOver() {
		Goban goban = new Goban();
		goban.play(1, 0, 'b');
		goban.play(0, 1, 'b');
		goban.play(1, 2, 'b');
		goban.play(1, 1, 'w');
		goban.play(2, 1, 'b');
		assertTrue(goban.gameOver());
	}

	// public void testGroups(){
	// Goban goban = new Goban();
	// goban.play(1, 0, 'b');
	// goban.play(0, 1, 'b');
	// goban.play(1, 2, 'b');
	// goban.play(1, 1, 'w');
	// goban.play(0, 0, 'b');
	// assertTrue(goban.testGroups());
	// }

}
