// File GameTest.java
// Last commited $Date$
// By $Author$
// Revision $Rev$
//
// Copyright (C) 2010 Clotilde Massot & Julien Durillon
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
package fr.alma.atarigo.utils;

import junit.framework.TestCase;

/**
 *
 * @author judu
 */
public class GameTest extends TestCase {
    public GameTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of posePion method, of class Game.
     */
    public void testPosePion() throws Exception {
        System.out.println("posePion");

        int     line     = 0;
        int     column   = 0;
        StoneVal color    = StoneVal.BLACK;
        Game    instance = new Game();

        instance.posePion(line, column, color);

        Stone pion      = instance.getStone(line, column);
        Stone expResult = new Stone(color, line, column);

        assertEquals(expResult, pion);
    }
}