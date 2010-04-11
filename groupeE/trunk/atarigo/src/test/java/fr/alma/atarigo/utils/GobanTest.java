/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.alma.atarigo.utils;

import java.util.ArrayList;
import java.util.List;
import junit.framework.Test;
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
     * Test of getTaille method, of class Goban.
     */
//    public void testGetTaille() {
//        System.out.println("getTaille");
//        int expResult = 9;
//        int result = Goban.getTaille();
//        assertEquals(expResult, result);
//    }

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
        PionVal pion = PionVal.BLANC;
        Goban instance = new Goban();
        instance.setCase(ligne, colonne, pion);
        PionVal sortie = instance.getCase(ligne, colonne);
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
        PionVal expResult = PionVal.RIEN;
        PionVal result = instance.getCase(ligne, colonne);
        assertEquals(expResult, result);
    }

    /**
     * Test of getVoisins method, of class Goban.
     */
    public void testGetVoisins() {
        System.out.println("getVoisins");
        Stone pion = new Stone(PionVal.NOIR, 0, 0);
        Goban instance = new Goban();
        List expResult = new ArrayList();
        List result = instance.getVoisins(pion);
        assertEquals(expResult, result);
        assertEquals(0, result.size());
    }

    /**
     * Test of getLibertes method, of class Goban.
     */
    public void testGetLibertesCorner() {
        System.out.println("getLibertes");
        Stone pion = new Stone(PionVal.BLANC, 0,0);
        Goban instance = new Goban();
        List<Stone> expResult = new ArrayList(4);
        expResult.add(new Stone(PionVal.RIEN,1,0));
        expResult.add(new Stone(PionVal.RIEN,0,1));

        List<Stone> result = instance.getLibertes(pion);
        assertEquals(expResult, result);
    }


    public void testGetLibertesSurrounded(){
        System.out.println("getLibertes 2");
        Stone pion = new Stone(PionVal.BLANC, 3,3);
        Goban instance = new Goban();
        instance.setCase(2, 3, PionVal.NOIR);
        instance.setCase(4, 3, PionVal.NOIR);
        
        List<Stone> expResult = new ArrayList(2);
        expResult.add(new Stone(PionVal.RIEN,3,4));
        expResult.add(new Stone(PionVal.RIEN,3,2));
        List<Stone> result = instance.getLibertes(pion);
        assertEquals(expResult, result);


    }

    /**
     * Test of libertesPion method, of class Goban.
     */
    public void testLibertesPion() {
        System.out.println("libertesPion");
        int ligne = 0;
        int col = 0;
        Goban instance = new Goban();
        int expResult = 2;
        int result = instance.libertesPion(new Stone(PionVal.BLANC, ligne, col));
        assertEquals(expResult, result);
    }
}
