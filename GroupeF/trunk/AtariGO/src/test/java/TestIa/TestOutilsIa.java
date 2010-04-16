package TestIa;

import static org.junit.Assert.*;
import static fr.alma.ia.OutilsIA.*;

import java.awt.Point;
import java.util.ArrayList;

import org.junit.Test;

import fr.alma.jeu.Couleur;
import fr.alma.jeu.Grille;
import fr.alma.jeu.Pion;
import static fr.alma.ia.OutilsIA.determineMin;
public class TestOutilsIa {
	
	Grille g=new Grille();
	@Test
	public void testExistN() {
		
		assertFalse(existN(g, 1, 2, Couleur.BLANC));
	}

	
	

	@Test
	public void testDeterminerGroupesBlanc() {
		assertEquals(determinerGroupesBlanc(g).size(),0);
	}

	@Test
	public void testDeterminerGroupesNoir() {
		assertEquals(determinerGroupesNoir(g).size(),0);
	}

	@Test
	public void testDetermineMin() {
		ArrayList<Point> p=new ArrayList<Point>();
		g.Contenu[2][3]=new Pion(Couleur.NOIR, new Point(2,3));
		p.add( new Point(2,3));
		assertFalse(determineMin(g, p)==4);
	}

}
