package fr.alma.ia;

import java.util.ArrayList;

import fr.alma.jeu.Grille;
import fr.alma.jeu.Pion;
import fr.alma.structure.Arbre;
import fr.alma.structure.Noeud;

/**
 * Classe de l'algorithme de min max
 * @author ZERBITA Mohamed El Hadi
 *
 */
public class MinMax {

	private static ArrayList<Pion> coupsJouer;
	int valeur = 0;
	
	/**
	 * Execute le minMax
	 * @param a
	 * @return le min ou max
	 */
	public static int execute(Arbre a){
		return valeurMinMax(a.racine, a.getCoupsNonJouer(), a.prof);
	}
	
	/**
	 * Algorithme de MinMax
	 * @param noeud noeud à traiter
	 * @param coups etat du jeu
	 * @param prof la profondeur max
	 * @return la meilleur note
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
				
				//System.out.println("Valeur de fonction = "+value);
			}
						
			coupsJouer.remove(coups.get(i));
		}
		return valeur;
	}
	
	/**
	 * retourne le min
	 * @param n noeud concerné
	 * @param temp l'etat du jeu
	 * @param prof la profondeur
	 * @return le min
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
	 * retourne le max
	 * @param n le noeud concerné
	 * @param temp l'etat du jeu
	 * @param prof la profondeur
	 * @return le max
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
	 * Retourne la grille a partir de la liste des coups joués
	 * @param arrpion la liste des coups jouer
	 * @return la grille
	 */
	private static Grille getGrilleFromList(ArrayList <Pion> arrpion){
		
		Grille g = new Grille();
		
		for(Pion each : arrpion){
			g.Contenu[each.position.x][each.position.y] = each;
			
		}
		
		return g;
	}
	
}
