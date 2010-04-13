
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package fr.alma.atarigo.utils;

//~--- non-JDK imports --------------------------------------------------------

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
        PionVal color    = PionVal.NOIR;
        Game    instance = new Game();

        instance.posePion(line, column, color);

        Stone pion      = instance.getStone(line, column);
        Stone expResult = new Stone(color, line, column);

        assertEquals(expResult, pion);
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
