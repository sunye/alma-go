package fr.alma.ia;

import java.awt.Point;
import java.util.ArrayList;

import fr.alma.jeu.Grille;
import fr.alma.structure.Arbre;
import fr.alma.structure.Noeud;


/**
 * Clase de gestion de l'intélligence artificielle
 * @author lahuidi
 *
 */
public class Ia {
	
	public Point alphaBeta(Arbre a){
		return null;
		
	}
	
	/**
	 * Evalue l'arbre en appliquant la fonction d'evaluation a chaque noeud
	 * @param a Arbre a évalué
	 * @return L'arbre noté
	 */
	private Arbre noterArbre(Arbre a){
		
		a.racine.setNote(fonctionDevaluation(a.racine));
		return a;
		
	}
	
	/**
	 * Construit l'arbre a partir d'une grille
	 * @param grille la grille a traité
	 * @return Arbre construit
	 */
	public Arbre constuireArbre(Grille grille){
		
		Arbre a = new Arbre(grille);
		
		a.AffichageNA();		
		System.out.println("------------------------");
		//a = noterArbre(a);
		
		return a;
				
	}
	
	/**
	 * Fonction qui evalue un arbre
	 * @param n noeud a traité
	 * @return la valeur de l'evaluation
	 */
	private int fonctionDevaluation(Noeud n){
		
		return 0;
	}
	
}
