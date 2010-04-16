package fr.alma.jeu;

import static org.junit.Assert.*;
import fr.alma.jeu.Couleur;
import fr.alma.jeu.Grille;
import fr.alma.jeu.Pion;

import java.awt.Point;
import fr.alma.jeu.Tour;
import org.junit.Test;
import static fr.alma.jeu.Jeu.*;

import static fr.alma.jeu.Jeu.VALIDE;



/**
 * @author ngass
 *
 */
public class TestJeu {

	/**
	 * Test method for {@link fr.alma.jeu.Jeu#SimulerJeu(fr.alma.jeu.Grille, java.awt.Point, fr.alma.jeu.Tour)}.
	 */
	@Test
	public void testSimulerJeu() {
		assertTrue(SimulerJeu(new Grille(),new Point(4,6), Tour.NOIR)==VALIDE);
	}

	/**
	 * Test method for {@link fr.alma.jeu.Jeu#jouerMachine(fr.alma.jeu.Grille, fr.alma.jeu.Tour)}.
	 */
	@Test
	public void testJouerMachine() {
		Grille g = new Grille();
		g.Contenu[4][2] = new Pion(Couleur.NOIR, new Point(4,2));
		assertFalse(jouerMachine(g, Tour.NOIR).equals(new Point(4,3)));
	}

}
