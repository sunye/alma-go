package fr.alma.go.tests;

import junit.framework.TestCase;
import fr.alma.go.goban.Goban;
import fr.alma.go.goban.Place;
import fr.alma.go.goban.Stone;

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

	public void testGetPlate() {
		Goban goban = new Goban();
		Stone[][] plate = goban.getPlate();
		assertEquals(plate.length, 9);
		for (int i = 0; i < 9; i++) {
			assertEquals(plate[i].length, 9);
		} // for
	}

	public void testGetPlace() {
		Goban goban = new Goban();
		assertTrue(goban.getPlace(goban.getPlate()[0][0]).equals(
				new Place(0, 0)));
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
