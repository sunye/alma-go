package fr.alma.go.tests;

import junit.framework.TestCase;
import fr.alma.go.goban.Goban;
import fr.alma.go.goban.Place;
import fr.alma.go.heuristics.Beginner;

public class BeginnerTest extends TestCase {

	public void testGetBestPlace() {
		Goban goban = new Goban();
		Beginner beg = new Beginner();
		Place place = beg.getBestPlace(goban, true);
		int[][] coeffs = { { 0, 1, 1, 1, 1, 1, 1, 1, 0 },
				{ 1, 1, 2, 2, 2, 2, 2, 1, 1 }, { 1, 2, 3, 3, 2, 3, 3, 2, 1 },
				{ 1, 2, 3, 2, 2, 2, 3, 2, 1 }, { 1, 2, 2, 2, 1, 2, 2, 2, 1 },
				{ 1, 2, 3, 2, 2, 2, 3, 2, 1 }, { 1, 2, 3, 3, 2, 3, 3, 2, 1 },
				{ 1, 1, 2, 2, 2, 2, 2, 1, 1 }, { 0, 1, 1, 1, 1, 1, 1, 1, 0 } };
		assertEquals(coeffs[place.getAbs()][place.getOrd()],3);
	}
}
