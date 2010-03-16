package test;

import java.util.LinkedList;

import fr.alma.atarigo.AtariGo;
import fr.alma.atarigo.Groupe;
import fr.alma.atarigo.ListeGroupes;
import fr.alma.atarigo.Pion;
import fr.alma.atarigo.Plateau;
import fr.alma.atarigo.Position;
import junit.framework.TestCase;

public class TestListeGroupes extends TestCase {

	ListeGroupes listeGroupes;
	Groupe g1;
	Groupe g2;
	LinkedList<Position> linkedPions1;
	LinkedList<Position> linkedPions2;
	Plateau plateau;
	
	public void setUp(){
		plateau = new Plateau(9,9);
		plateau.ecrireCase(new Position(0,0), Pion.BLANC);
		plateau.ecrireCase(new Position(1,0), Pion.BLANC);
		plateau.ecrireCase(new Position(2,0), Pion.BLANC);
		linkedPions1 = new LinkedList();
		linkedPions1.add(new Position(0,0));
		linkedPions1.add(new Position(1,0));
		linkedPions1.add(new Position(2,0));
		g1 = new Groupe(linkedPions1,Pion.BLANC);
		plateau.ecrireCase(new Position(0,1), Pion.NOIR);
		plateau.ecrireCase(new Position(0,2), Pion.NOIR);
		plateau.ecrireCase(new Position(0,3), Pion.NOIR);
		linkedPions2 = new LinkedList();
		linkedPions2.add(new Position(0,1));
		linkedPions2.add(new Position(0,2));
		linkedPions2.add(new Position(0,3));
		g2 = new Groupe(linkedPions2,Pion.NOIR);	
		listeGroupes = new ListeGroupes();
		listeGroupes.listeG.add(g1);
		listeGroupes.listeG.add(g2);
	}

	
	public void testGetGroupe(){
		assertEquals(g1,listeGroupes.getGroupe(new Position(0,0)));
		assertNull(listeGroupes.getGroupe(new Position(0,5)));
	}
	
	public void testAfficher(){
		listeGroupes.afficher();
	}
	
	//TODO tests pas finis
	public void testCaculerGroupes(){
		plateau.ecrireCase(new Position(1,1), Pion.BLANC);
		ListeGroupes nouvListe = listeGroupes.calculerGroupes(plateau,new Position(1,1), Pion.BLANC);
		
	}
	
	
}
