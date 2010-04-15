/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.alma.atarigo.utils;

import java.util.ArrayList;
import java.util.Set;
import junit.framework.TestCase;

/**
 *
 * @author judu
 */
public class PlayMoveTest extends TestCase {



   private PlayMove instance;


    public PlayMoveTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        instance = new PlayMove();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

   /**
    * Test of getEval method, of class PlayMove.
    */
   public void testGetEval() {
      System.out.println("getEval");
      int expResult = Integer.MIN_VALUE;
      int result = instance.getEval();
      assertEquals(expResult, result);
   }

   /**
    * Test of setEval method, of class PlayMove.
    */
   public void testSetEval() {
      System.out.println("setEval");
      int eval = 0;
      PlayMove instance = new PlayMove();
      instance.setEval(eval);
      assertEquals(eval, instance.getEval());
      instance = new PlayMove();
   }
}
