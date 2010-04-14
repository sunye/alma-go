package fr.alma.ia;

import java.util.ArrayList;

import fr.alma.jeu.Grille;
import fr.alma.jeu.Pion;
import fr.alma.structure.Arbre;
import fr.alma.structure.Noeud;

/**
 * Classe du min max
 * @author lahuidi
 *
 */
public class MinMax {

	private ArrayList<Pion> coupsJouer;
	int valeur = 0;
	
	/**
	 * Execute le minMax
	 * @param a
	 * @return le min ou max
	 */
	public int execute(Arbre a){
		return valeurMinMax(a.racine, a.getCoupsNonJouer(), a.prof);
	}
	
	/**
	 * Test MinMax
	 * @param noeud
	 * @param coups
	 * @param prof
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private int valeurMinMax(Noeud noeud, ArrayList<Pion> coups, int prof) {
		
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
				
				//System.out.println("Valeur de fonction = "+value);
			}
						
			coupsJouer.remove(coups.get(i));
		}
		return valeur;
	}
	
	/**
	 * retourne le min
	 * @param n
	 * @param temp
	 * @param prof
	 */
	private void valeurMin(Noeud n, ArrayList<Pion> temp, int prof) {
		int min = 100;
		int valeur = 0;
		
		for(int i=0;i<n.getNbFils();i++){
			temp.add(n.listeFils.get(i).getCoup());
			valeur = valeurMinMax(n.listeFils.get(i),temp,prof);
			temp.remove(n.listeFils.get(i).getCoup());
			
			if(valeur<min) min = valeur;
		}
			
		
	}


	/**
	 * retourne le max
	 * @param n
	 * @param temp
	 * @param prof
	 * @return
	 */
	private int valeurMax(Noeud n , ArrayList<Pion> temp, int prof) {
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
	 * Retourne la grille a partir de la liste des coups joués
	 * @param arrpion la liste des coups jouer
	 * @return la grille
	 */
	private Grille getGrilleFromList(ArrayList <Pion> arrpion){
		
		Grille g = new Grille();
		
		for(Pion each : arrpion){
			g.Contenu[each.position.x][each.position.y] = each;
			
		}
		
		return g;
	}
	
}
