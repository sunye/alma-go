package fr.alma.structure;

import java.awt.Point;
import java.util.ArrayList;
import fr.alma.ia.Ia;
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
	
	public ArrayList<Pion> cj;
	private ArrayList<Pion> cnj;
	
	int compteur = 0;
	
	//--------------------------
	@SuppressWarnings("unused")
	private int max ,parit = 1; 
	//--------------------------
			
	public Arbre(Grille grille){
		this.racine = new Noeud(new Pion(null,new Point(-1,-1)));
		this.grille = grille;
		getCoupsJouer();
		//System.out.println("Taille coup jouer : "+cj.size());
		
	}
	
	
	public Point  remplirArbre(){
		
		getCoupsNonJouer();
		//System.out.println("Nombre de noeuds total : "+compteur);
		return ajouterTousLesfils(racine, cnj);
		
	}
	
	
	public Noeud remValA(int nparite ,Noeud nC,Noeud nF){
		if(nparite%2==0)
			if(nC.getNote()>= nF.getNote())
				return nC;
			else
				return nF;
		else
			if(nC.getNote()>= nF.getNote())
				return nF;
			else
				return nC;
			
	}
	 
	@SuppressWarnings("unchecked")
	/**
	 * Méthode qui permet de contruire l'arbe et d'atribué les notes
	 */
	private Point ajouterTousLesfils(Noeud noeud, ArrayList<Pion> coups) {
		ArrayList<Pion> temp;
		//--------
		Noeud nc = new Noeud(new Pion(null));
			
		int longeur = coups.size();
		
		temp = (ArrayList<Pion>) coups.clone();
		for(int i=0;i<longeur;i++){
			
			temp = (ArrayList<Pion>) coups.clone();	
			
			Noeud n = new Noeud(coups.get(i));
			
			cj.add(coups.get(i));
			noeud.AjouterFils(n);compteur++;
			temp.remove(coups.get(i));
			parit++;
			if(temp.size()>78){
				ajouterTousLesfils(n,temp);
				//------------------------------------
				if (i==0){
					nc=n;
					}
				else{
					nc = remValA(parit,nc, n);
				}
				
				noeud.setNote(nc.getNote());
				
			}else {
				int value = Ia.fonctionEvaluation(getGrilleFromList(cj));
				n.setNote(value);
				//System.out.println("Valeur de fonction = "+value);
				temp = (ArrayList<Pion>) coups.clone();
				//-------------------------------------
				if(i==0)
					nc.setNote(value);
				else
					nc = remValA(parit,nc, n);
			}
			
			cj.remove(coups.get(i));
			
		}
		
		return nc.getCoup().position;
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
	
	/**
	 * Retoune les coups non jouer de la grille
	 */
	private void getCoupsNonJouer(){
		cnj = new ArrayList<Pion>();
		 for(int i=0;i<9;i++) 
        	 for(int j=0;j<9;j++) 
                 if(grille.Contenu[i][j].couleur == Couleur.NULL) cnj.add(new Pion(Couleur.NULL,new Point(i,j))); 
		  		 
	 }
	
	/**
	 * Retourne les coups jouer
	 */
	public void getCoupsJouer(){
		//System.out.println("Appelé");
		cj = new ArrayList<Pion>();
		for(int i=0;i<9;i++) 
       	 for(int j=0;j<9;j++) {
       		if(grille.Contenu[i][j].couleur != Couleur.NULL) {
       			cj.add(grille.Contenu[i][j]); 
       		//System.out.println("Coup jouer : "+grille.Contenu[i][j].position);
       		}
       	 }
      		 
	}
		
}
