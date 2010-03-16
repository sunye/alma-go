package test;

import java.util.LinkedList;

import fr.alma.atarigo.Pion;
import fr.alma.atarigo.Plateau;
import fr.alma.atarigo.Position;
import junit.framework.TestCase;

public class TestPlateau extends TestCase {

	public void testPlateauIntInt() {
		Plateau plateau = new Plateau(3,3);
		
		for (int i = 0; i < plateau.lireLignes(); i ++) {
		    for (int j = 0; j < plateau.lireColonnes(); j ++) {
		    	assertEquals(plateau.grille[i][j],Pion.VIDE);
		    }
		}
	}

	public void testPlateauPlateau() {
		Plateau plateau = new Plateau(3,3);
		plateau.ecrireCase(new Position(0,0), Pion.NOIR);
		plateau.ecrireCase(new Position(2,0), Pion.BLANC);
		plateau.ecrireCase(new Position(1,2), Pion.NOIR);
		
		Plateau plateauCopie = new Plateau(plateau);
		assertEquals(plateauCopie.lireCase(new Position(0,0)),Pion.NOIR);
		assertEquals(plateauCopie.lireCase(new Position(2,0)),Pion.BLANC);
		assertEquals(plateauCopie.lireCase(new Position(1,2)),Pion.NOIR);
	}

	public void testNouvellePartie() {
		Plateau plateau = new Plateau(3,3);
		
		plateau.nouvellePartie();
		
		for (int i = 0; i < plateau.lireLignes(); i ++) {
		    for (int j = 0; j < plateau.lireColonnes(); j ++) {
		    	assertEquals(plateau.grille[i][j],Pion.VIDE);
		    }
		}
	}

	public void testLireLignes() {
		Plateau plateau = new Plateau(3,2);
		assertEquals(plateau.lireLignes(),3);
	}

	public void testLireColonnes() {
		Plateau plateau = new Plateau(3,2);
		assertEquals(plateau.lireColonnes(),2);
	}

	public void testLireCase() {
		Plateau plateau = new Plateau(3,3);
		plateau.grille[0][0]=Pion.NOIR;
		
		assertEquals(plateau.lireCase(new Position(0,0)),Pion.NOIR);
	}

	public void testEcrireCase() {
		Plateau plateau = new Plateau(3,3);
		plateau.ecrireCase(new Position(0,0), Pion.NOIR);
		
		assertEquals(plateau.lireCase(new Position(0,0)),Pion.NOIR);
	}

	public void testViderCase() {
		Plateau plateau = new Plateau(3,3);
		plateau.ecrireCase(new Position(0,0), Pion.NOIR);
		plateau.viderCase(new Position(0,0));
		
		assertEquals(plateau.lireCase(new Position(0,0)),Pion.VIDE);
	}

	public void testEstValide() {
		Plateau plateau = new Plateau(3,3);
		assertTrue(plateau.estValide(new Position(2,2)));
		assertFalse(plateau.estValide(new Position(4,1)));
		assertFalse(plateau.estValide(new Position(4,4)));
	}

	 /**
	  * @deprecated
	  * le retour de la fonction estSuicide est inversé...
	  * @param position
	  */
	public void testEstSuicide() {
		Plateau plateau = new Plateau(3,3);
		plateau.ecrireCase(new Position(1,0), Pion.NOIR);
		plateau.ecrireCase(new Position(1,2), Pion.NOIR);
		plateau.ecrireCase(new Position(0,1), Pion.NOIR);
		plateau.ecrireCase(new Position(2,1), Pion.NOIR);

		assertFalse(plateau.estSuicide(new Position(1,1), Pion.BLANC));
		assertFalse(plateau.estSuicide(new Position(2,0), Pion.BLANC));
		assertTrue(plateau.estSuicide(new Position(2,0), Pion.NOIR));
		assertTrue(plateau.estSuicide(new Position(1,1), Pion.NOIR));
		assertFalse(plateau.estSuicide(new Position(2,3), Pion.BLANC));
	}

	public void testEstPris() {
		fail("Not yet implemented");
	}

	public void testLireLibertes() {
		Plateau plateau = new Plateau(3,3);
		plateau.ecrireCase(new Position(0,0), Pion.NOIR);
		assertEquals(plateau.lireLibertes(new Position(0,0)),2);
		plateau.ecrireCase(new Position(0,1), Pion.BLANC);
		assertEquals(plateau.lireLibertes(new Position(0,0)),1);
		
		Plateau plateau2 = new Plateau(6,6);
		plateau2.ecrireCase(new Position(2,3), Pion.NOIR);
		assertEquals(plateau2.lireLibertes(new Position(2,3)),4);
	}

	public void testEstGagnant() {
		fail("Not yet implemented");
	}

	public void testCasesVides() {
		Plateau plateau = new Plateau(2,2);
		assertEquals(plateau.casesVides().size(),4);
		
		plateau.ecrireCase(new Position(1,0), Pion.NOIR);
		plateau.ecrireCase(new Position(1,1), Pion.BLANC);
		
		assertEquals(plateau.casesVides().size(),2);
	}

	public void testGetCases() {
		Plateau plateau = new Plateau(2,2);
		assertEquals(plateau.getCases(Pion.VIDE).size(),4);
		
		plateau.ecrireCase(new Position(1,0), Pion.NOIR);
		plateau.ecrireCase(new Position(1,1), Pion.BLANC);
		plateau.ecrireCase(new Position(0,1), Pion.BLANC);
		
		assertEquals(plateau.getCases(Pion.NOIR).size(),1);
		assertEquals(plateau.getCases(Pion.BLANC).size(),2);
	}

	public void testGenererCoups() {
		Plateau plateau = new Plateau(2,2);
		assertEquals(plateau.genererCoups(Pion.NOIR).size(),4);
		plateau.ecrireCase(new Position(0,0), Pion.NOIR);
		assertEquals(plateau.genererCoups(Pion.NOIR).size(),3);
		}

	public void testLiberte() {
		Plateau plateau = new Plateau(3,3);
		plateau.ecrireCase(new Position(0,0), Pion.NOIR);
		assertEquals(plateau.liberte(new Position(0,0)),2);
		plateau.ecrireCase(new Position(0,1), Pion.BLANC);
		assertEquals(plateau.liberte(new Position(0,0)),1);
		
		Plateau plateau2 = new Plateau(6,6);
		plateau2.ecrireCase(new Position(2,3), Pion.NOIR);
		assertEquals(plateau2.liberte(new Position(2,3)),4);
	}

}
