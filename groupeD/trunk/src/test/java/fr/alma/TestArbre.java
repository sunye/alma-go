package test;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.Stack;

import fr.alma.atarigo.Plateau;
import fr.alma.ia.Arbre;
import junit.framework.TestCase;

public class TestArbre extends TestCase {

	public void testAjouterFils() {
		Plateau plateau = new Plateau(1,1);
		Arbre arbre = new Arbre(plateau);
		Arbre arbreFils = new Arbre(plateau);
		
		arbre.ajouterFils(arbreFils);
		
		Field champs;
		try{
			champs = arbre.getClass().getDeclaredField("fils_");
			//L'attribut étant privé, on le rend lisible
			champs.setAccessible(true);
			//On récupère la pile devant être modifiée durant l'exécution
			LinkedList<Arbre> liste = (LinkedList<Arbre>) champs.get(arbre);
			
			assertEquals(liste.size(),1);
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	public void testEstFeuille() {
		Plateau plateau = new Plateau(1,1);
		Arbre arbre = new Arbre(plateau);
		Arbre arbreFils = new Arbre(plateau);
		
		arbre.ajouterFils(arbreFils);
		
		assertTrue(arbreFils.estFeuille());
		assertFalse(arbre.estFeuille());
	}

	public void testGetPere() {
		fail("Not yet implemented");
	}

	public void testGetPlateau() {
		Plateau plateau = new Plateau(1,1);
		Arbre arbre = new Arbre(plateau);
		Arbre arbreFils = new Arbre(plateau);
		
		assertEquals(arbre.getPere(),null);
	}

	public void testGetFils() {
		Plateau plateau = new Plateau(1,1);
		Arbre arbre = new Arbre(plateau);
		Arbre arbreFils1 = new Arbre(plateau);
		Arbre arbreFils2 = new Arbre(plateau);
		
		assertEquals(arbre.getFils().size(),0);
		arbre.ajouterFils(arbreFils1);
		arbre.ajouterFils(arbreFils2);
		assertEquals(arbre.getFils().size(),2);
	}

	public void testGenererFils() {
		fail("Not yet implemented");
	}

	public void testGenererArbre() {
		fail("Not yet implemented");
	}

}
