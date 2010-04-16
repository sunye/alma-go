package fr.alma.ia;

import java.awt.Point;
import java.util.ArrayList;
import fr.alma.jeu.Grille; 
 
/**
 * Class Managing Artificial Intelligence
 * @author ZERBITA Mohamed El Hadi
 */
public class Ia {
			
			
	/**
	 * Evaluation function of the Grid
	 * @param goban The grid to evaluate
	 * @return The mark of this grid
	 */
	public static int fonctionEvaluation(Grille goban){
		int minB,minN;
		
		ArrayList<Point> listeGN = OutilsIA.determinerGroupesNoir(goban);
		ArrayList<Point> listeGB = OutilsIA.determinerGroupesBlanc(goban);
				
		if(listeGB.size()==0) minB = 0;
		else minB = OutilsIA.determineMin(goban, listeGB);
		
		if (listeGN.size()==0) minN = 0;
		else minN = OutilsIA.determineMin(goban, listeGN);
		
		return minN-minB;
	}
  }
