package fr.alma.structure;

import java.awt.Point;
import java.util.ArrayList;

import fr.alma.jeu.Grille;
import fr.alma.jeu.Pion;
import fr.alma.jeu.Couleur;

/**
 * Class for the Tree
 * @author ZERBITA Mohamed El Hadi
 *
 */
public class Arbre {
	
	public Noeud racine;
	public Grille grille;
	public int prof;
	
	public ArrayList<Pion> coupsJouer;
	public ArrayList<Pion> coupsNonJouer;
	
	public int nbNoeuds = 1;
	
	//--------------------------
	
	/**
	 * The constructor of the class
	 */
	public Arbre(Grille grille){
		this.racine = new Noeud(new Pion(null,new Point(-1,-1)));
		this.grille = grille;
		remplirCoupsNonJouer();
		remplirCoupsJouer();
		prof = getProfMax(coupsNonJouer);
		
	}
	
	/**
	 * Method used to fill the tree
	 * @return
	 */
	public void remplirArbre(){
		
		racine = ajouterTousLesfils(racine, coupsNonJouer, prof);
		System.out.println("Nombre de noeuds total : "+nbNoeuds);
	}
	
	/**
	 * Return the maximum depth that we want to treat
	 * @param liste
	 * @return
	 */
	private int getProfMax(ArrayList<Pion> liste){
		// The max number of moves remaining for the computer to play as best as possible : 7
		return liste.size()-2;
		
	}
	@SuppressWarnings("unchecked")
	/**
	 * Method which permits to construct the tree
	 */
	private Noeud ajouterTousLesfils(Noeud noeud, ArrayList<Pion> coups, int prof) {
		
		ArrayList<Pion> temp;
		int longeur = coups.size();
		temp = (ArrayList<Pion>) coups.clone();
		
		for(int i=0;i<longeur;i++){
			
			temp = (ArrayList<Pion>) coups.clone();	
			Noeud n = new Noeud(coups.get(i));
			
			coupsJouer.add(coups.get(i));
			noeud.AjouterFils(n);nbNoeuds++;
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
	 * Return the pawns not played of the grid
	 */
	public ArrayList<Pion> remplirCoupsNonJouer(){
		coupsNonJouer = new ArrayList<Pion>();
		 for(int i=0;i<9;i++) 
        	 for(int j=0;j<9;j++) 
                 if(grille.Contenu[i][j].couleur == Couleur.NULL) coupsNonJouer.add(new Pion(Couleur.NULL,new Point(i,j)));
		return coupsNonJouer; 
		  		 
	 }
	
	/**
	 * Return the played pawns
	 */
	public ArrayList<Pion> remplirCoupsJouer(){
		
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
