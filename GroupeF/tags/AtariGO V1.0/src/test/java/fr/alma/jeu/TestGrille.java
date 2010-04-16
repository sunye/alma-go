package fr.alma.jeu;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Test;

public class TestGrille {

	@Test
	public void testGrille() {
		
		Grille grille = new Grille();
		
		assertTrue(grille.Contenu != null);
		assertEquals(grille.Contenu.length, 9);
		assertTrue(grille.Contenu[0][1].position.equals(new Point(0,1)));
		assertTrue(grille.Contenu[8][8].position.equals(new Point(8,8)));
	}
		
	@Test
	public void testinitHashMap(){
		
		Grille grille = new Grille();
		assertEquals(grille.ghmp.get(new Point(0,0)), new Point(45,45));
		
	}

}
