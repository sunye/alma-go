/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.alma.atarigo.utils;

import fr.alma.atarigo.utils.exceptions.BadCouleurException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import junit.framework.TestCase;

/**
 *
 * @author judu
 */
public class GroupTest extends TestCase {

   public GroupTest(String testName) {
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
    * Test of contains method, of class Group.
    */
   public void testContains() throws BadCouleurException {
      System.out.println("contains");
      Stone p = new Stone(StoneVal.BLACK, 2, 2);
      Group instance = new Group(StoneVal.BLACK);
      boolean expResult = false;
      boolean result = instance.contains(p);
      assertEquals(expResult, result);

      instance.addStone(p);
      expResult = true;
      result = instance.contains(p);
      assertEquals(expResult, result);

   }

   /**
    * Test of addStone method, of class Group.
    */
   public void testAddStone() throws Exception {
      System.out.println("addStone");
      Stone p = new Stone(StoneVal.BLACK, 1, 1);
      Group instance = new Group(StoneVal.BLACK);
      instance.addStone(p);
   }

   public void testAddBadStone() {
      System.out.println("addBadStone");
      Stone p = new Stone(StoneVal.WHITE, 1, 1);
      Group instance = new Group(StoneVal.BLACK);
      try {
         instance.addStone(p);
      } catch (BadCouleurException ex) {
         return;
      }
      fail("Should have returned");
   }

   /**
    * Test of notEmptyIntersection method, of class Group.
    */
   public void testIntersectionNonVideSameColorT() throws BadCouleurException {
      System.out.println("intersectionNonVide");
      Stone stone = new Stone(StoneVal.BLACK, 0, 0);
      Group autre = new Group(StoneVal.BLACK);
      autre.addStone(stone);
      Group instance = new Group(StoneVal.BLACK);
      instance.addStone(stone);
      boolean expResult = true;
      boolean result = instance.notEmptyIntersection(autre);
      assertEquals(expResult, result);
   }

   public void testIntersectionNonVideSameColorF() throws BadCouleurException {
      System.out.println("intersectionNonVide");
      Stone stone = new Stone(StoneVal.BLACK, 0, 1);
      Stone stone2 = new Stone(StoneVal.BLACK, 1, 1);
      Group autre = new Group(StoneVal.BLACK);
      autre.addStone(stone);
      Group instance = new Group(StoneVal.BLACK);
      instance.addStone(stone2);
      boolean expResult = false;
      boolean result = instance.notEmptyIntersection(autre);
      assertEquals(expResult, result);
   }

   public void testIntersectionNonVideDiffColor() throws BadCouleurException {
      System.out.println("intersectionNonVide");
      Stone stone = new Stone(StoneVal.BLACK, 0, 0);
      Stone stone2 = new Stone(StoneVal.WHITE, 0, 1);

      Group autre = new Group(StoneVal.WHITE);
      autre.addStone(stone2);

      Group instance = new Group(StoneVal.BLACK);
      instance.addStone(stone);

      boolean expResult = false;
      boolean result = instance.notEmptyIntersection(autre);
      assertEquals(expResult, result);
   }

   /**
    * Test of getCouleur method, of class Group.
    */
   public void testGetCouleur() {
      System.out.println("getCouleur");
      Group instance = new Group(StoneVal.WHITE);
      StoneVal expResult = StoneVal.WHITE;
      StoneVal result = instance.getCouleur();
      assertEquals(expResult, result);

      instance = new Group(StoneVal.BLACK);
      expResult = StoneVal.BLACK;
      result = instance.getCouleur();
      assertEquals(expResult, result);
   }

   /**
    * Test of fusion method, of class Group.
    */
   public void testFusion() throws BadCouleurException {
      System.out.println("fusion");
      Stone stone = new Stone(StoneVal.BLACK, 0, 1);
      Stone stone2 = new Stone(StoneVal.BLACK, 1, 1);
      Stone stone3 = new Stone(StoneVal.BLACK, 2, 3);

      Group autre = new Group(StoneVal.BLACK);
      autre.addStone(stone);
      autre.addStone(stone3);

      Group instance = new Group(StoneVal.BLACK);
      instance.addStone(stone2);
      instance.addStone(stone);

      Group expResult = new Group(StoneVal.BLACK);
      expResult.addStone(stone);
      expResult.addStone(stone2);
      expResult.addStone(stone3);

      Group result = instance.fusion(autre);
      assertEquals(expResult, result);

   }

   /**
    * Test of fusion method, of class Group.
    */
   public void testFusionEmptyInter() throws BadCouleurException {
      System.out.println("fusion");
      Stone stone2 = new Stone(StoneVal.BLACK, 1, 1);
      Stone stone3 = new Stone(StoneVal.BLACK, 2, 3);

      Group autre = new Group(StoneVal.BLACK);
      autre.addStone(stone3);

      Group instance = new Group(StoneVal.BLACK);
      instance.addStone(stone2);

      Group expResult = null;

      Group result = instance.fusion(autre);
      assertEquals(expResult, result);

   }

   /**
    * Test of addAll method, of class Group.
    */
   public void testAddAll_Group() throws BadCouleurException {
      System.out.println("addAll");
      Stone stone = new Stone(StoneVal.BLACK, 0, 1);
      Stone stone2 = new Stone(StoneVal.BLACK, 1, 1);
      Stone stone3 = new Stone(StoneVal.BLACK, 2, 3);

      Group other = new Group(StoneVal.BLACK);
      other.addStone(stone);
      other.addStone(stone2);

      Group instance = new Group(StoneVal.BLACK);
      instance.addStone(stone3);
      instance.addStone(stone);

      instance.addAll(other);

      Group expResult = instance.fusion(other);

      assertEquals(instance, expResult);
   }

   /**
    * Test of addAll method, of class Group.
    */
   public void testAddAll_Collection() throws Exception {
      System.out.println("addAll");

      Stone stone = new Stone(StoneVal.BLACK, 0, 1);
      Stone stone2 = new Stone(StoneVal.BLACK, 1, 1);
      Stone stone3 = new Stone(StoneVal.BLACK, 2, 3);

      Collection<Stone> collec = new HashSet<Stone>();
      collec.add(stone);
      collec.add(stone2);
      Group instance = new Group(StoneVal.BLACK);
      instance.addStone(stone3);
      instance.addAll(collec);
   }

   /**
    * Test of equals method, of class Group.
    */
   public void testEquals() throws BadCouleurException {
      System.out.println("equals");


      Stone stone = new Stone(StoneVal.BLACK, 0, 1);
      Stone stone2 = new Stone(StoneVal.BLACK, 1, 1);
      Stone stone3 = new Stone(StoneVal.BLACK, 2, 3);

      Group other = new Group(StoneVal.BLACK);
      other.addStone(stone);
      other.addStone(stone2);
      other.addStone(stone3);

      Group instance = new Group(StoneVal.BLACK);
      instance.addStone(stone3);
      instance.addStone(stone2);
      instance.addStone(stone);

      boolean expResult = true;
      boolean result = instance.equals(other);
      assertEquals(expResult, result);
   }

   /**
    * Test of toString method, of class Group.
    */
   public void testToString() throws BadCouleurException {
      System.out.println("toString");
      Group instance = new Group(StoneVal.BLACK);
      Stone stone = new Stone(StoneVal.BLACK, 0, 1);
      instance.addStone(stone);

      String expResult = "{ color : BLACK, Stones : [Stone(line:0,col:1,c:BLACK)], Eyes : [] }";
      String result = instance.toString();
      assertEquals(expResult, result);
   }

   /**
    * Test of addEye method, of class Group.
    */
   public void testAddEye() throws BadCouleurException {
      System.out.println("addEye");
      Group group = new Group(StoneVal.EMPTY);
      group.addStone(new Stone(StoneVal.EMPTY, 1, 1));
      Group instance = new Group(StoneVal.BLACK);
      instance.addEye(group);
      assertTrue(instance.getEyes().contains(group));
   }

   /**
    * Test of nbEyes method, of class Group.
    */
   public void testNbEyes() throws BadCouleurException {
      System.out.println("nbEyes");
      Group group = new Group(StoneVal.EMPTY);
      group.addStone(new Stone(StoneVal.EMPTY, 1, 1));
      Group instance = new Group(StoneVal.BLACK);

      int expResult = 0;
      int result = instance.nbEyes();
      assertEquals(expResult, result);

      instance.addEye(group);

      expResult = 1;
      result = instance.nbEyes();
      assertEquals(expResult, result);


   }

   /**
    * Test of getEyes method, of class Group.
    */
   public void testGetEyes() throws BadCouleurException {
      System.out.println("getEyes");
      Group group = new Group(StoneVal.EMPTY);
      group.addStone(new Stone(StoneVal.EMPTY, 1, 1));
      Group instance = new Group(StoneVal.BLACK);
      instance.addEye(group);
      Set<Group> expResult = new HashSet<Group>();
      expResult.add(group);
      assertTrue(instance.getEyes().containsAll(expResult));
   }

   /**
    * Test of size method, of class Group.
    */
   public void testSize() throws BadCouleurException {
      System.out.println("size");
      Group group = new Group(StoneVal.EMPTY);


      int expResult = 0;
      int result = group.size();
      assertEquals(expResult, result);

      group.addStone(new Stone(StoneVal.EMPTY, 1, 1));
      expResult = 1;
      result = group.size();
      assertEquals(expResult, result);

   }
}
