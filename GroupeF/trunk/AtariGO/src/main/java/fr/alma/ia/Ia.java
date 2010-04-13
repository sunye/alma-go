package fr.alma.ia;

import java.awt.Point;
import java.util.ArrayList;

import fr.alma.jeu.Grille;
import fr.alma.structure.Arbre; 
 
/**
 * Clase de gestion de l'int�lligence artificielle
 * @author lahuidi
 *
 */
public class Ia {
			
	/**
	 * Construit l'arbre a partir d'une grille
	 * @param grille la grille a trait�
	 * @return Arbre construit
	 */
	public static Arbre constuireArbre(Grille grille){
		
		Arbre a = new Arbre(grille);
		return a;
	}
		
	/**
	 * 
	 * @param g
	 * @return
	 */
	public static int fonctionEvaluation(Grille g){
		int minB,minN;
		
		ArrayList<Point> listeGN = OutilsIA.determinerGroupesNoir(g);
		ArrayList<Point> listeGB = OutilsIA.determinerGroupesBlanc(g);
				
		if(listeGB.size()==0) minB = 0;
		else minB = OutilsIA.determineMin(g, listeGB);
		
		if (listeGN.size()==0) minN = 0;
		else minN = OutilsIA.determineMin(g, listeGN);
		
		return minN-minB;
	}
}
