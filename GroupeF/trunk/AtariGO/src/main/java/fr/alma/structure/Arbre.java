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
	
	public ArrayList<Pion> coupsJouer;
	private ArrayList<Pion> coupsNonJouer;
	
	int compteur = 0;
	
	//--------------------------
			
	public Arbre(Grille grille){
		this.racine = new Noeud(new Pion(null,new Point(-1,-1)));
		this.grille = grille;
		getCoupsJouer();
		//System.out.println("Taille coup jouer : "+cj.size());
		
	}
	
	
	public Point remplirArbre(){
		
		Point p = null;
		getCoupsNonJouer();
		int prof = getProfMax(coupsNonJouer);
		//-----------
		//for(Pion each : cnj);
		//---------
		//racine = ajouterTousLesfils(racine, cnj, prof);
		System.out.println((ajouterTousLesfils(new Noeud(null), coupsNonJouer, prof)).getNote());
		System.out.println("Nombre de noeuds total : "+compteur);
		System.out.println("note remont� : "+racine.getNote());
		p=racine.getCoup().position;
		return p;
	}
	
	/**
	 * 
	 * @param liste
	 * @return
	 */
	private int getProfMax(ArrayList<Pion> liste){
		
		return liste.size()-2;
		
	}
	@SuppressWarnings("unchecked")
	/**
	 * M�thode qui permet de contruire l'arbe et d'atribu� les notes
	 */
	private Noeud ajouterTousLesfils(Noeud noeud, ArrayList<Pion> coups, int prof) {
		ArrayList<Pion> temp;
		//--------
					
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
				//-------------------------------------
				int value = i;//Ia.fonctionEvaluation(getGrilleFromList(coupsJouer));
				n.setNote(value);
				//System.out.println("Valeur de fonction = "+value);
			}
						
			coupsJouer.remove(coups.get(i));
		}
		return noeud;
	}

	/**
	 * Retourne la grille a partir de la liste des coups jou�s
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
	
	/**
	 * Retoune les coups non jouer de la grille
	 */
	private void getCoupsNonJouer(){
		coupsNonJouer = new ArrayList<Pion>();
		 for(int i=0;i<9;i++) 
        	 for(int j=0;j<9;j++) 
                 if(grille.Contenu[i][j].couleur == Couleur.NULL) coupsNonJouer.add(new Pion(Couleur.NULL,new Point(i,j))); 
		  		 
	 }
	
	/**
	 * Retourne les coups jouer
	 */
	public void getCoupsJouer(){
		
		coupsJouer = new ArrayList<Pion>();
		for(int i=0;i<9;i++) 
       	 for(int j=0;j<9;j++) {
       		if(grille.Contenu[i][j].couleur != Couleur.NULL) {
       			coupsJouer.add(grille.Contenu[i][j]); 
       		
       		}
       	 }
      		 
	}
		
}
