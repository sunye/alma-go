// Copyright (C) 2010 Alexandre Garnier & Yann Treguer
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// as published by the Free Software Foundation; either version 2
// of the License, or (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
//
// The above copyright notice and this permission notice shall be
// included in all copies or substantial portions of this program.

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
