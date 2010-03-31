package ia;

import jeu.Couleur;
import jeu.GobanStructure;
import jeu.GroupePieces;

public class Evaluation {

	public Integer evalMaxMinLib(GobanStructure goban, Couleur joueur){
		
		Integer max = 0;
		Integer min = goban.getTaille() * goban.getTaille();
		
		for(GroupePieces g : goban.getGroupes(joueur)){
			if(max < g.getLiberte()){
				max = g.getLiberte();
			}
		}
		
		for(GroupePieces g : goban.getGroupes(joueur.invCouleur())){
			if(min > g.getLiberte()){
				min = g.getLiberte();
			}
		}
		
		return max - min;
	}	
	
	
}
