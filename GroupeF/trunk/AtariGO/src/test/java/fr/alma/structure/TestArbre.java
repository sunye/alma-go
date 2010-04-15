package fr.alma.structure;

import static org.junit.Assert.*;

import org.junit.Test;
import fr.alma.jeu.Grille;
import fr.alma.structure.Arbre;

public class TestArbre {

	@Test
	public void testArbre() {
		
		Grille grille = new Grille();
		Arbre arbre = new Arbre(grille);
		
		assertTrue(arbre.coupsNonJouer.size() == 81);
		assertTrue(arbre.coupsJouer.size() == 0);
		assertTrue(arbre.grille.Contenu.length == 9);
		assertTrue(arbre.racine.existeFils() == false);
		assertTrue(arbre.prof == 79);
		
	}

	@Test
	public void testRemplirArbre() {
		
		Grille grille = new Grille();
		Arbre arbre = new Arbre(grille);
		arbre.remplirArbre();
		
		assertTrue(arbre.nbNoeuds == 6562);
		
	}

	@Test
	public void testGetCoupsNonJouer() {
		
		Grille grille = new Grille();
		Arbre arbre = new Arbre(grille);
		
		assertTrue(arbre.remplirCoupsNonJouer().size() == 81);
		
	}

	@Test
	public void testGetCoupsJouer() {
		
		Grille grille = new Grille();
		Arbre arbre = new Arbre(grille);
		
		assertTrue(arbre.remplirCoupsJouer().size() == 0);
	}

}
