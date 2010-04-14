package fr.alma.structure;

import java.awt.Point;
import java.util.ArrayList;

import fr.alma.jeu.Grille;
import fr.alma.jeu.Pion;
import fr.alma.jeu.Pion.Couleur;

/**
 * Classe de l'arbre
 * @author lahuidi
 *
 */
public class Arbre {
	
	public Noeud racine;
	public Grille grille;
	public int prof;
	
	private ArrayList<Pion> coupsJouer;
	private ArrayList<Pion> coupsNonJouer;
	
	int compteur = 0;
	
	//--------------------------
			
	public Arbre(Grille grille){
		this.racine = new Noeud(new Pion(null,new Point(-1,-1)));
		this.grille = grille;
		getCoupsJouer();
		prof = getProfMax(coupsNonJouer);
		getCoupsNonJouer();
	}
	
	
	public Point remplirArbre(){
		
		Point p = null;
				
		racine = ajouterTousLesfils(racine, coupsNonJouer, prof);
		System.out.println((ajouterTousLesfils(new Noeud(null), coupsNonJouer, prof)).getNote());
		System.out.println("Nombre de noeuds total : "+compteur);
		
		return p;
	}
	
	/**
	 * 
	 * @param liste
	 * @return
	 */
	private int getProfMax(ArrayList<Pion> liste){
		//Le nombre de coups restants max pour que l'odinateur joue le mieux possible : 7
		
		return liste.size()-11;
		
	}
	@SuppressWarnings("unchecked")
	/**
	 * Méthode qui permet de contruire l'arbe.
	 */
	private Noeud ajouterTousLesfils(Noeud noeud, ArrayList<Pion> coups, int prof) {
		
		ArrayList<Pion> temp;
		int longeur = coups.size();
		temp = (ArrayList<Pion>) coups.clone();
		
		for(int i=0;i<longeur;i++){
			
			temp = (ArrayList<Pion>) coups.clone();	
			Noeud n = new Noeud(coups.get(i));
			
			coupsJouer.add(coups.get(i));
			noeud.AjouterFils(n);compteur++;
			temp.remove(coups.get(i));
			
			if(temp.size()>prof){
								
				ajouterTousLesfils(n,temp, prof);
							
			}else {
				temp = (ArrayList<Pion>) coups.clone();
				
			}
			coupsJouer.remove(coups.get(i));
		}
		return noeud;
	}

	
	
	
	/**
	 * Retoune les coups non jouer de la grille
	 */
	public ArrayList<Pion> getCoupsNonJouer(){
		coupsNonJouer = new ArrayList<Pion>();
		 for(int i=0;i<9;i++) 
        	 for(int j=0;j<9;j++) 
                 if(grille.Contenu[i][j].couleur == Couleur.NULL) coupsNonJouer.add(new Pion(Couleur.NULL,new Point(i,j)));
		return coupsNonJouer; 
		  		 
	 }
	
	/**
	 * Retourne les coups jouer
	 */
	public ArrayList<Pion> getCoupsJouer(){
		
		coupsJouer = new ArrayList<Pion>();
		for(int i=0;i<9;i++) 
       	 for(int j=0;j<9;j++) {
       		if(grille.Contenu[i][j].couleur != Couleur.NULL) {
       			coupsJouer.add(grille.Contenu[i][j]); 
       		
       		}
       	 }
		return coupsJouer;
      		 
	}
}
