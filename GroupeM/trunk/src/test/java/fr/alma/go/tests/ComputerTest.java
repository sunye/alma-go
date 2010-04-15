package fr.alma.go.tests;

import fr.alma.go.goban.Goban;
import fr.alma.go.goban.Place;
import fr.alma.go.heuristics.Computer;
import junit.framework.TestCase;

public class ComputerTest extends TestCase {

	public void testGetPlace() {
		Goban goban = new Goban();
		Computer cpu=new Computer(true, 1);
		Place place = cpu.getPlace(goban);
		int[][] coeffs = { { 0, 1, 1, 1, 1, 1, 1, 1, 0 },
				{ 1, 1, 2, 2, 2, 2, 2, 1, 1 }, { 1, 2, 3, 3, 2, 3, 3, 2, 1 },
				{ 1, 2, 3, 2, 2, 2, 3, 2, 1 }, { 1, 2, 2, 2, 1, 2, 2, 2, 1 },
				{ 1, 2, 3, 2, 2, 2, 3, 2, 1 }, { 1, 2, 3, 3, 2, 3, 3, 2, 1 },
				{ 1, 1, 2, 2, 2, 2, 2, 1, 1 }, { 0, 1, 1, 1, 1, 1, 1, 1, 0 } };
		assertEquals(coeffs[place.getAbs()][place.getOrd()], 3);
	}
}
