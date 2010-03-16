package test;

import fr.alma.atarigo.AtariGo;
import fr.alma.atarigo.Joueur;
import fr.alma.atarigo.JoueurHumain;
import fr.alma.atarigo.Pion;
import fr.alma.atarigo.Position;
import fr.alma.atarigo.AtariGo.Coup;
import junit.framework.TestCase;

public class TestAtarigo extends TestCase {

	private AtariGo atarigo;
	private Joueur j1;
	private Joueur j2;
	
    protected void setUp() {
    	atarigo = new AtariGo(9,9);
    	j1 = new JoueurHumain(Pion.BLANC, "bob");
    	j2 = new JoueurHumain(Pion.NOIR, "bill");
    	
    }
    
    public void testjouerCoup(){
    	Coup coupNeutre = atarigo.jouerCoup(j1.couleur, new Position(0,0));
    	Coup coupHorsPlateau = atarigo.jouerCoup(j2.couleur, new Position(10,10));
    	Coup coupSurCoup = atarigo.jouerCoup(j1.couleur, new Position(0,0));
    	assertEquals(coupNeutre,Coup.NEUTRE);
    	assertEquals(coupHorsPlateau,Coup.INVALIDE);
    	assertEquals(coupSurCoup,Coup.INVALIDE);
    	//mise en place des cas de prise
    	atarigo.jouerCoup(j2.couleur, new Position(0,1));
    	Coup gagnant1 = atarigo.jouerCoup(j2.couleur, new Position(1,0));
    	assertEquals(gagnant1,Coup.GAGNANT);
    	//on reinit pour tester d'autres coups
    	setUp();
    	//mise en place de pions blanc pour test contre le suicide
    	atarigo.jouerCoup(j2.couleur, new Position(0,1));
    	atarigo.jouerCoup(j2.couleur, new Position(1,0));
    	Coup coupSuicide = atarigo.jouerCoup(j1.couleur, new Position(0,0));
    	assertEquals(coupSuicide,Coup.INVALIDE);
    	//mise en place pour le test contre le suicide en groupe
    	atarigo.jouerCoup(j2.couleur, new Position(2,1));
    	atarigo.jouerCoup(j2.couleur, new Position(0,2));
    	atarigo.jouerCoup(j2.couleur, new Position(2,2));
    	atarigo.jouerCoup(j2.couleur, new Position(1,3));
    	atarigo.jouerCoup(j1.couleur, new Position(1,1));
    	Coup coupSuicideGroupe = atarigo.jouerCoup(j1.couleur, new Position(1,2));
    	assertEquals(coupSuicideGroupe,Coup.INVALIDE); 
    	//mise en place du suicide pour gagner
    	setUp();
    	atarigo.jouerCoup(j2.couleur, new Position(0,1));
    	atarigo.jouerCoup(j2.couleur, new Position(1,0));
    	atarigo.jouerCoup(j1.couleur, new Position(2,0));
    	atarigo.jouerCoup(j1.couleur, new Position(1,1));
    	Coup coupSuicideWin = atarigo.jouerCoup(j1.couleur, new Position(0,0));
    	//TODO Ancienne version ou le suicide pour gagner n'etait pas encore implemente
    	//assertEquals(coupSuicideWin,Coup.GAGNANT); 
    }

	public void testAtariGo(){
    	j1 = new JoueurHumain(Pion.BLANC, "bob");
    	j2 = new JoueurHumain(Pion.NOIR, "bill");
		atarigo = new AtariGo(9,9,j1,j2);
	}
	
	public void testEstTermine(){
		atarigo.desactiver();
		assertTrue(atarigo.estTermine());
	}
	
	public void testAccesseurs(){
		assertEquals(atarigo.lireLignes(),9);
		assertEquals(atarigo.lireColonnes(),9);
	}
	
	public void testNouvellePartie(){
		atarigo.nouvellePartie();
		assertFalse(atarigo.fini);
	}
	
	
	
	
}
