package test;

import java.util.LinkedList;

import fr.alma.atarigo.Groupe;
import fr.alma.atarigo.Pion;
import fr.alma.atarigo.Position;
import junit.framework.TestCase;

public class TestGroupe extends TestCase {

	Groupe groupe;
	LinkedList<Position> linkedPions;
	Pion pion;
	
	public void setUp(){
		linkedPions = new LinkedList();
		linkedPions.add(new Position(0,1));
		linkedPions.add(new Position(0,2));
		linkedPions.add(new Position(0,0));
		pion = Pion.BLANC;
		groupe = new Groupe(linkedPions,pion);
	}
	
	public void testClone(){
		assertEquals(groupe.linkedPions.getFirst().lireLigne(),groupe.clone().linkedPions.getFirst().lireLigne());
		assertEquals(groupe.linkedPions.getFirst().lireColonne(),groupe.clone().linkedPions.getFirst().lireColonne());
	}
	
	public void testHasPos(){
		assertTrue(groupe.hasPos(new Position(0,1)));
		assertFalse(groupe.hasPos(new Position(2,2)));
	}
	
	public void testAfficher(){
		groupe.afficher();
	}
	
	public void testAjouter(){
		groupe.ajouter(new Position(0,3));
		assertTrue(groupe.hasPos(new Position(0,3)));
		
	}
	
}
