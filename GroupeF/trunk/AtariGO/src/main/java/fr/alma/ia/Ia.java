package fr.alma.ia;

import java.awt.Point;
import java.util.ArrayList;

import fr.alma.jeu.Grille; 
 
/**
 * Clase de gestion de l'intélligence artificielle
 * @author lahuidi
 *
 */
public class Ia {
			
			
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
