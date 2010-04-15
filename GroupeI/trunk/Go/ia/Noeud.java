/**
 * @author Bouvet Frederic
 * @author Altuntas Murat
 */
package ia;

import coeur.GoDonnees;
import coeur.Plateau;

/**
 * Classe permettant de creer un noeud contenant une representation du jeu  
 */
public class Noeud {

	/** La configuration de jeu */
	private Plateau plateau;
	/** La couleur du noeud */
	private int couleur;	
	/** La profondeur du noeud */
	private int profondeur;
		
	
	/**
	 * Constructeur pour la racine
	 * @param plateau le plateau de jeu
	 * @param couleur la couleur du joueur en cours
	 */
	public Noeud(Plateau plateau, int couleur){
		this.plateau = plateau;		
		this.profondeur = 0;
		this.couleur = couleur;		
	}	
	
	/**
	 * Constructeur pour noeud general
	 * @param plateau le plateau de jeu	 
	 * @param couleur la couleur du joueur en cours
	 * @param profondeur La profondeur du noeud dans l'arbre
	 */
	public Noeud(Plateau plateau, int couleur, int profondeur){
		this.plateau = plateau;		
		this.couleur = couleur;
		this.profondeur = profondeur;		
	}

	/**
	 * Permet d'obtenir la couleur adverse au joueur courant de ce noeud
	 * @return la couleur
	 */
	public int getCouleurAdverse(){
		if (this.couleur == GoDonnees.BLANC){
			return GoDonnees.NOIR;
		}else return GoDonnees.BLANC;		
	}                  	
	
	/**
	 * Retourne le plateau
	 * @return le plateau
	 */
	public Plateau getPlateau(){
		return this.plateau;
	}	
		
	/**
	 * Retourne la couleur du joueur courant de ce noeud
	 * @return la couleur
	 */
	public int getCouleur(){
		return this.couleur;
	}
	
	
	/**
	 * Retourne la profondeur du noeud
	 * @return la profondeur
	 */
	public int getProfondeur(){
		return this.profondeur;
	}
	
}
