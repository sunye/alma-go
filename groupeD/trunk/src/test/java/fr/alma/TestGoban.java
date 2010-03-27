package fr.alma.test;

import java.util.LinkedList;

import fr.alma.atarigo.Stone;
import fr.alma.atarigo.Goban;
import fr.alma.atarigo.Position;
import junit.framework.TestCase;

public class TestGoban extends TestCase {
	
	public void testGobanIntInt() {
		Goban goban = new Goban(3,3);
		
		for (int i = 0; i < goban.getLines(); i ++) {
		    for (int j = 0; j < goban.getColumns(); j ++) {
		    	assertEquals(goban.matrice[i][j],Stone.EMPTY);
		    }
		}
	}
	
	public void testGobanGoban() {
		Goban goban = new Goban(3,3);
		goban.writeCell(new Position(0,0), Stone.BLACK);
		goban.writeCell(new Position(2,0), Stone.WHITE);
		goban.writeCell(new Position(1,2), Stone.BLACK);
		
		Goban gobanCopie = new Goban(goban);
		assertEquals(gobanCopie.readCell(new Position(0,0)),Stone.BLACK);
		assertEquals(gobanCopie.readCell(new Position(2,0)),Stone.WHITE);
		assertEquals(gobanCopie.readCell(new Position(1,2)),Stone.BLACK);
	}
	
	public void testNouvellePartie() {
		Goban goban = new Goban(3,3);
		
		goban.newGame();
		
		for (int i = 0; i < goban.getLines(); i ++) {
		    for (int j = 0; j < goban.getColumns(); j ++) {
		    	assertEquals(goban.matrice[i][j],Stone.EMPTY);
		    }
		}
	}
	
	public void testLireLignes() {
		Goban goban = new Goban(3,2);
		assertEquals(goban.getLines(),3);
	}
	
	public void testLireColonnes() {
		Goban goban = new Goban(3,2);
		assertEquals(goban.getColumns(),2);
	}
	
	public void testLireCase() {
		Goban goban = new Goban(3,3);
		goban.matrice[0][0]=Stone.BLACK;
		
		assertEquals(goban.readCell(new Position(0,0)),Stone.BLACK);
	}
	
	public void testEcrireCase() {
		Goban goban = new Goban(3,3);
		goban.writeCell(new Position(0,0), Stone.BLACK);
		
		assertEquals(goban.readCell(new Position(0,0)),Stone.BLACK);
	}
	
	public void testViderCase() {
		Goban goban = new Goban(3,3);
		goban.writeCell(new Position(0,0), Stone.BLACK);
		goban.emptyCell(new Position(0,0));
		
		assertEquals(goban.readCell(new Position(0,0)),Stone.EMPTY);
	}
	
	public void testEstValide() {
		Goban goban = new Goban(3,3);
		assertTrue(goban.isValid(new Position(2,2)));
		assertFalse(goban.isValid(new Position(4,1)));
		assertFalse(goban.isValid(new Position(4,4)));
	}
	
	/**
	 * @deprecated
	 * le retour de la fonction isSuicidal est inversŽ...
	 * @param position
	 */
	public void testEstSuicide() {
		Goban goban = new Goban(3,3);
		goban.writeCell(new Position(1,0), Stone.BLACK);
		goban.writeCell(new Position(1,2), Stone.BLACK);
		goban.writeCell(new Position(0,1), Stone.BLACK);
		goban.writeCell(new Position(2,1), Stone.BLACK);
		
		assertFalse(goban.isSuicidal(new Position(1,1), Stone.WHITE));
		assertFalse(goban.isSuicidal(new Position(2,0), Stone.WHITE));
		assertTrue(goban.isSuicidal(new Position(2,0), Stone.BLACK));
		assertTrue(goban.isSuicidal(new Position(1,1), Stone.BLACK));
		assertFalse(goban.isSuicidal(new Position(2,3), Stone.WHITE));
	}
	
	public void testEstPris() {
		//fail("Not yet implemented");
	}
	
	public void testLireLibertes() {
		Goban goban = new Goban(3,3);
		goban.writeCell(new Position(0,0), Stone.BLACK);
		assertEquals(goban.getLiberty(new Position(0,0)),2);
		goban.writeCell(new Position(0,1), Stone.WHITE);
		assertEquals(goban.getLiberty(new Position(0,0)),1);
		
		Goban goban2 = new Goban(6,6);
		goban2.writeCell(new Position(2,3), Stone.BLACK);
		assertEquals(goban2.getLiberty(new Position(2,3)),4);
	}
	
	public void testEstGagnant() {
		//fail("Not yet implemented");
	}
	
	public void testCasesVides() {
		Goban goban = new Goban(2,2);
		assertEquals(goban.emptyCells().size(),4);
		
		goban.writeCell(new Position(1,0), Stone.BLACK);
		goban.writeCell(new Position(1,1), Stone.WHITE);
		
		assertEquals(goban.emptyCells().size(),2);
	}
	
	public void testGetCases() {
		Goban goban = new Goban(2,2);
		assertEquals(goban.getCells(Stone.EMPTY).size(),4);
		
		goban.writeCell(new Position(1,0), Stone.BLACK);
		goban.writeCell(new Position(1,1), Stone.WHITE);
		goban.writeCell(new Position(0,1), Stone.WHITE);
		
		assertEquals(goban.getCells(Stone.BLACK).size(),1);
		assertEquals(goban.getCells(Stone.WHITE).size(),2);
	}
	
	public void testGenererCoups() {
		Goban goban = new Goban(2,2);
		assertEquals(goban.computeMoves(Stone.BLACK).size(),4);
		goban.writeCell(new Position(0,0), Stone.BLACK);
		assertEquals(goban.computeMoves(Stone.BLACK).size(),3);
	}
	
	public void testLiberte() {
		Goban goban = new Goban(3,3);
		goban.writeCell(new Position(0,0), Stone.BLACK);
		assertEquals(goban.liberty(new Position(0,0)),2);
		goban.writeCell(new Position(0,1), Stone.WHITE);
		assertEquals(goban.liberty(new Position(0,0)),1);
		
		Goban goban2 = new Goban(6,6);
		goban2.writeCell(new Position(2,3), Stone.BLACK);
		assertEquals(goban2.liberty(new Position(2,3)),4);
	}
	
}