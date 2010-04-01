package fr.alma.main;
import java.awt.Point;
import java.util.ArrayList;

import fr.alma.ihm.Ihm;
import fr.alma.jeu.Grille;
import fr.alma.jeu.Pion;
import fr.alma.jeu.Pion.Couleur;
import fr.alma.structure.Arbre;


public class Main {

	/**
	 * https://ia-alma.googlecode.com/svn/trunk/
	 * @param args
	 */
	public static void main(String[] args) {
		
		//new Ihm();
		
		ArrayList<Pion> coups = new ArrayList<Pion>();
		
		coups.add(new Pion(Couleur.NULL,new Point(0,1)));
		coups.add(new Pion(Couleur.NULL,new Point(0,2)));
		coups.add(new Pion(Couleur.NULL,new Point(0,3)));
		
		Grille g = new Grille();
		
		Arbre a = new Arbre(g);
		
		a.remplirArbre();
		//a.ParcoursProf();
		//a.AffichageNA();
		
		System.out.println("C'est bon");
				
		
					
	}

}
