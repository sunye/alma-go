// File GobanTest.java
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

import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;

/**
 *
 * @author judu
 */
public class GobanTest extends TestCase {

   public GobanTest(String testName) {
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
    * Test of bonneCoords method, of class Goban.
    */
   public void testBonneCoords() {
      System.out.println("bonneCoords");
      int ligne = 0;
      int colonne = 0;
      Goban instance = new Goban();
      boolean expResult = true;
      boolean result = instance.bonneCoords(ligne, colonne);
      assertEquals(expResult, result);

      expResult = false;
      result = instance.bonneCoords(-1, 10);
      assertEquals(expResult, result);
   }

   /**
    * Test of setCase method, of class Goban.
    */
   public void testSetCase() {
      System.out.println("setCase");
      int ligne = 0;
      int colonne = 0;
      StoneVal pion = StoneVal.WHITE;
      Goban instance = new Goban();
      instance.setCase(ligne, colonne, pion);
      StoneVal sortie = instance.getCase(ligne, colonne);
      assertEquals(sortie, pion);
   }

   /**
    * Test of getCase method, of class Goban.
    */
   public void testGetCase() throws Exception {
      System.out.println("getCase");
      int ligne = 0;
      int colonne = 0;
      Goban instance = new Goban();
      StoneVal expResult = StoneVal.EMPTY;
      StoneVal result = instance.getCase(ligne, colonne);
      assertEquals(expResult, result);
   }

   /**
    * Test of getVoisins method, of class Goban.
    */
   public void testGetVoisins() {
      System.out.println("getVoisins");
      Stone pion = new Stone(StoneVal.BLACK, 0, 0);
      Goban instance = new Goban();
      List<Stone> expResult = new ArrayList();
      List<Stone> result = instance.getVoisins(pion);
      assertEquals(expResult, result);
      assertEquals(0, result.size());


      instance.setCase(0, 1, StoneVal.WHITE);
      instance.setCase(2, 1, StoneVal.WHITE);
      instance.setCase(1, 0, StoneVal.BLACK);
      instance.setCase(0, 0, StoneVal.BLACK);
      pion = new Stone(StoneVal.WHITE, 1, 1);
      expResult.add(new Stone(StoneVal.WHITE, 2, 1));
      expResult.add(new Stone(StoneVal.WHITE, 0, 1));
      expResult.add(new Stone(StoneVal.BLACK, 1, 0));
      result = instance.getVoisins(pion);
      assertEquals(expResult, result);

   }

   /**
    * Test of getLibertes method, of class Goban.
    */
   public void testGetLibertesCorner() {
      System.out.println("getLibertes");
      Stone pion = new Stone(StoneVal.WHITE, 0, 0);
      Goban instance = new Goban();
      List<Stone> expResult = new ArrayList(4);
      expResult.add(new Stone(StoneVal.EMPTY, 1, 0));
      expResult.add(new Stone(StoneVal.EMPTY, 0, 1));

      List<Stone> result = instance.getLibertes(pion);
      assertEquals(expResult, result);
   }

   public void testGetLibertesSurrounded() {
      System.out.println("getLibertes 2");
      Stone pion = new Stone(StoneVal.WHITE, 3, 3);
      Goban instance = new Goban();
      instance.setCase(2, 3, StoneVal.BLACK);
      instance.setCase(4, 3, StoneVal.BLACK);

      List<Stone> expResult = new ArrayList(2);
      expResult.add(new Stone(StoneVal.EMPTY, 3, 4));
      expResult.add(new Stone(StoneVal.EMPTY, 3, 2));
      List<Stone> result = instance.getLibertes(pion);
      assertEquals(expResult, result);


   }

   /**
    * Test of nbLibertes method, of class Goban.
    */
   public void testLibertesPion() {
      System.out.println("libertesPion");
      int ligne = 0;
      int col = 0;
      Goban instance = new Goban();
      int expResult = 2;
      int result = instance.nbLibertes(new Stone(StoneVal.WHITE, ligne, col));
      assertEquals(expResult, result);
   }

   public void testClone() {
      System.out.println("clone");
      Goban instance = new Goban();
      instance.setCase(5, 3, StoneVal.WHITE);
      instance.setCase(5, 4, StoneVal.BLACK);
      Goban copy = instance.clone();

      for (int line = 0; line < Goban.getTaille(); ++line) {
         for (int col = 0; col < Goban.getTaille(); ++col) {
            assertTrue(instance.getCase(line, col) == copy.getCase(line, col));
         }
      }
   }
}
