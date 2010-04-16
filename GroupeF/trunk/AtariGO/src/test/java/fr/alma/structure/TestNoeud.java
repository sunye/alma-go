package fr.alma.structure;

import static org.junit.Assert.*;

import java.awt.Point;
import org.junit.Test;
import fr.alma.jeu.Pion;
import fr.alma.structure.Noeud;

public class TestNoeud {

	@Test
	public void testNoeud() {
		
		Noeud noeud = new Noeud(new Pion(new Point(0,0)));
		
		assertEquals(noeud.getPere(), null);
		assertTrue(noeud.getNote() == -1);
		assertTrue(noeud.getListeFils().size() == 0);
		assertTrue(noeud.getCoup().position.equals(new Point(0,0)));
	}

	@Test
	public void testAjouterFils() {
		
		Noeud noeud = new Noeud(new Pion(new Point(0,0)));
		
		noeud.AjouterFils(new Noeud(new Pion(new Point(0,1))));	
		noeud.AjouterFils(new Noeud(new Pion(new Point(0,2))));	
		
		assertTrue(noeud.getNbFils() == 2);
	}

	@Test
	public void testExisteFils() {
		
		Noeud noeud = new Noeud(new Pion(new Point(0,0)));
		
		assertFalse(noeud.existeFils());
		noeud.AjouterFils(new Noeud(new Pion(new Point(0,1))));	
		assertTrue(noeud.existeFils());
		
	}
}
