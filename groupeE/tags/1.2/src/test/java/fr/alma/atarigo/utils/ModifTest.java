/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.alma.atarigo.utils;

import junit.framework.TestCase;

/**
 *
 * @author judu
 */
public class ModifTest extends TestCase {

   private Modif mainMod;
    
    public ModifTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mainMod = new Modif(0, 0, StoneVal.EMPTY, StoneVal.BLACK);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

   /**
    * Test of getAfter method, of class Modif.
    */
   public void testGetAfter() {
      System.out.println("getAfter");
      StoneVal expResult = StoneVal.BLACK;
      StoneVal result = mainMod.getAfter();
      assertEquals(expResult, result);
   }

   /**
    * Test of getBefore method, of class Modif.
    */
   public void testGetBefore() {
      System.out.println("getBefore");
      StoneVal expResult = StoneVal.EMPTY;
      StoneVal result = mainMod.getBefore();
      assertEquals(expResult, result);
   }

   /**
    * Test of getColumn method, of class Modif.
    */
   public void testGetColumn() {
      System.out.println("getColumn");
      int expResult = 0;
      int result = mainMod.getColumn();
      assertEquals(expResult, result);
   }

   /**
    * Test of getLine method, of class Modif.
    */
   public void testGetLine() {
      System.out.println("getLine");
      int expResult = 0;
      int result = mainMod.getLine();
      assertEquals(expResult, result);
   }

   /**
    * Test of getReverted method, of class Modif.
    */
   public void testGetReverted() {
      System.out.println("getReverted");
      Modif expResult = new Modif(0, 0, StoneVal.BLACK, StoneVal.EMPTY);
      Modif result = mainMod.getReverted();
      assertEquals(expResult, result);
   }

   /**
    * Test of revert method, of class Modif.
    */
   public void testRevert() throws Exception {
      System.out.println("revert");
      Goban goban = new Goban();
      goban.setCase(0, 0, StoneVal.BLACK);
      mainMod.revert(goban);
      assertEquals(goban.getCase(0, 0), mainMod.getBefore());
   }

   /**
    * Test of apply method, of class Modif.
    */
   public void testApply() throws Exception {
      System.out.println("apply");
      Goban goban = new Goban();
      mainMod.apply(goban);
      assertEquals(mainMod.getAfter(), goban.getCase(0, 0));
   }

   /**
    * Test of getOldStone method, of class Modif.
    */
   public void testGetOldStone() {
      System.out.println("getOldStone");
      Stone expResult = new Stone(StoneVal.EMPTY, 0, 0);
      Stone result = mainMod.getOldStone();
      assertEquals(expResult, result);
   }

   /**
    * Test of getNewStone method, of class Modif.
    */
   public void testGetNewStone() {
      System.out.println("getNewStone");
      Stone expResult = new Stone(StoneVal.BLACK, 0, 0);
      Stone result = mainMod.getNewStone();
      assertEquals(expResult, result);
   }

   /**
    * Test of toString method, of class Modif.
    */
   public void testToString() {
      System.out.println("toString");
      String expResult = "{ line : 0, column : 0, before : EMPTY, after : BLACK }";
      String result = mainMod.toString();
      assertEquals(expResult, result);
   }

}
