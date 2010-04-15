/**
 * 
 */
package jeu;

import static org.junit.Assert.*;
import fr.alma.jeu.Couleur;
import fr.alma.jeu.Jeu;
import fr.alma.jeu.Grille;
import fr.alma.jeu.Pion;

import java.awt.Point;
import fr.alma.jeu.Tour;
import org.junit.Test;
import static fr.alma.jeu.Jeu.SimulerJeu;
import static fr.alma.jeu.Jeu.miseAjourGrilleApresCapture;
import static fr.alma.jeu.Jeu.jouerMachine;
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
		assertTrue(SimulerJeu(new Grille(),new Point(4,6), Tour.BLANC)==VALIDE);
	}

	/**
	 * Test method for {@link fr.alma.jeu.Jeu#jouerMachine(fr.alma.jeu.Grille, fr.alma.jeu.Tour)}.
	 */
	@Test
	public void testJouerMachine() {
		Grille g=new Grille();
		g.Contenu[3][2]=new Pion(Couleur.NOIR, new Point(3,2));
		assertTrue(jouerMachine(g, Tour.BLANC).equals(new Point(3,3)));
	}

	/**
	 * Test method for {@link fr.alma.jeu.Jeu#miseAjourGrilleApresCapture(fr.alma.jeu.Grille)}.
	 */
	@Test
	public void testMiseAjourGrilleApresCapture() {
		assertFalse(miseAjourGrilleApresCapture(new Grille()).equals(new Grille()));
	}

}
