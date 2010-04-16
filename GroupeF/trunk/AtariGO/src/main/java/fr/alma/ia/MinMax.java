package fr.alma.ia;

import java.util.ArrayList;

import fr.alma.jeu.Grille;
import fr.alma.jeu.Pion;
import fr.alma.structure.Arbre;
import fr.alma.structure.Noeud;

/**
 * Class of min max algorithm
 * @author ZERBITA Mohamed El Hadi
 *
 */
public class MinMax {

	private static ArrayList<Pion> coupsJouer;
	int valeur = 0;
	
	/**
	 * Execute the min max
	 * @param a
	 * @return the min or max
	 */
	public static int execute(Arbre arbre){
		return valeurMinMax(arbre.racine, arbre.remplirCoupsNonJouer(), arbre.prof);
	}
	
	/**
	 * Algorithm of MinMax
	 * @param noeud node to be treated
	 * @param coups state of the game
	 * @param prof maximum depth
	 * @return the best mark
	 */
	@SuppressWarnings("unchecked")
	private static int valeurMinMax(Noeud noeud, ArrayList<Pion> coups, int prof) {
		
		ArrayList<Pion> temp;
		int longeur = coups.size();
		int valeur = 0;
		temp = (ArrayList<Pion>) coups.clone();
		
		for(int i=0;i<longeur;i++){
			
			temp = (ArrayList<Pion>) coups.clone();	
			Noeud n = new Noeud(coups.get(i));
			
			coupsJouer.add(coups.get(i));
			
			temp.remove(coups.get(i));
			
			if(temp.size()>prof){
				
				if(temp.size() %2 == 0) valeurMax(n,temp, prof);
				else valeurMin(n, temp, prof);
							
			}else {
				temp = (ArrayList<Pion>) coups.clone();
				//-------------------------------------
				valeur = Ia.fonctionEvaluation(getGrilleFromList(coupsJouer));
				
				}
						
			coupsJouer.remove(coups.get(i));
		}
		return valeur;
	}
	
	/**
	 * return the min
	 * @param n node concerned
	 * @param temp state of the game
	 * @param prof the depth
	 * @return the min
	 */
	private static int valeurMin(Noeud n, ArrayList<Pion> temp, int prof) {
		int min = 100;
		int valeur = 0;
		
		for(int i=0;i<n.getNbFils();i++){
			temp.add(n.listeFils.get(i).getCoup());
			valeur = valeurMinMax(n.listeFils.get(i),temp,prof);
			temp.remove(n.listeFils.get(i).getCoup());
			
			if(valeur<min) min = valeur;
		}
		return min;	
		
	}


	/**
	 * return the max
	 * @param n node concerned
	 * @param temp state of the game
	 * @param prof the depth
	 * @return the max
	 */
	private static int valeurMax(Noeud n , ArrayList<Pion> temp, int prof) {
		int max = -100;
		int valeur = 0;
		
		for(int i=0;i<n.getNbFils();i++){
			temp.add(n.listeFils.get(i).getCoup());
			valeur = valeurMinMax(n.listeFils.get(i),temp,prof);
			temp.remove(n.listeFils.get(i).getCoup());
			
			if(valeur>max) max = valeur;
		}
			
		
		return max;
	}
	
	/**
	 * Return the grid from the list of shots played
	 * @param arrpion list of shots played
	 * @return the grid
	 */
	private static Grille getGrilleFromList(ArrayList <Pion> arrpion){
		
		Grille g = new Grille();
		
		for(Pion each : arrpion){
			g.Contenu[each.position.x][each.position.y] = each;
			
		}
		
		return g;
	}
	
}
