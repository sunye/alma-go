package fr.alma.structure;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

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

	
	private Grille getGrilleFromList(ArrayList <Pion> arrpion){
		
		Grille g = new Grille();
		
		for(Pion each : arrpion){
			g.Contenu[each.position.x][each.position.y] = each;
			
		}
		
		return g;
	}
	
	/**
	 * 
	 * @return
	 */
	private void getCoupsNonJouer(){
		cnj = new ArrayList<Pion>();
		 for(int i=0;i<9;i++) 
        	 for(int j=0;j<9;j++) 
                 if(grille.Contenu[i][j].couleur == Couleur.NULL) cnj.add(new Pion(Couleur.NULL,new Point(i,j))); 
		  		 
	 }
	
	/**
	 * 
	 */
	public void getCoupsJouer(){
		//System.out.println("Appelé");
		cj = new ArrayList<Pion>();
		for(int i=0;i<9;i++) 
       	 for(int j=0;j<9;j++) {
       		if(grille.Contenu[i][j].couleur != Couleur.NULL) {cj.add(grille.Contenu[i][j]); 
       		//System.out.println("Coup jouer : "+grille.Contenu[i][j].position);
       		}
       	 }
                
		 
	}
	
	 /**
	  * Algorithme de parcours en Profondeur d'abord
	  * (Depth-first search)
	  * @param r	racine du graph a parcourir
	  *
	  */	
	 public void ParcoursProf(){
		 Noeud n;
		 Noeud r = racine;
		 	 
		 Stack <Noeud> E = new Stack<Noeud>();
		 E.push(r);
		 while(!E.isEmpty()){
			 n = E.pop();
			 System.out.println(n.getCoup().position);
			 
			 for(int j=0;j<n.getNbFils();j++){
				 E.push(n.getListeFils().get(j));
				 
			 }
		 }
		 
		
	 }

	 /**
	  * Algorithme de parcours en Largeur d'abord
	  * (Breadth-first search)
	  * @param r racine du graphe a parcourir
	  *
	  */	
	 public void ParcoursLarg(){
		 Noeud n;
		 Noeud r = racine;
		 
		 Queue<Noeud> E = new LinkedList<Noeud> ();//Queue n'est qu'une interface
		 E.add(r);
		 while(!E.isEmpty()){
			 n=E.element();
			 E.remove();
			 System.out.println(n.getCoup());
			 for(int j=0;j<n.getNbFils();j++){
				 E.add(n.getListeFils().get(j));
			 }
		 }
	 }
	

	/**
	 * Affichage liste d'adjacence	
	 */
	public void AffichageLA(){
		
		 Noeud noeudToPrint;
		 String listNoeudsString="";
		 Stack <Noeud> E = new Stack<Noeud>();
		 
		 E.push(racine);
		 while(!E.isEmpty()){
			 noeudToPrint=E.pop();
			 for (int j=0; j<noeudToPrint.getNbFils();j++ ){
				 listNoeudsString += "\n	Noeud("+ 
				 	noeudToPrint.getListeFils().get(j).getCoup()+ ")";
			 }
			 if (listNoeudsString==""){
				 System.out.println("Le Noeud("+
						noeudToPrint.getCoup()+
						 ") n'a pas de fils");
			 }else{
				 System.out.println("Le Noeud("+
							noeudToPrint.getCoup()+
							") a comme fils: "+listNoeudsString);
			 }
			 for(int j=0;j<noeudToPrint.getNbFils();j++){
				 E.push(noeudToPrint.getListeFils().get(j));
			 }
			 listNoeudsString="";
		 }
	 }
	 
	 /**
	  * Afficher le graph par liste des noeud/arcs
	  * (une ligne avec tous les noeuds, une ligne avec tout les arcs)
	  *
	  */	 
	 public void AffichageNA(){
		
		 Noeud noeudToPrint;
		 String listNoeudsString="";
		 String listArcsString="";
		 Stack <Noeud> E = new Stack<Noeud>();
		 
		 E.push(racine);
		 while(!E.isEmpty()){
			 noeudToPrint=E.pop();
			 
			 listNoeudsString+=" Noeud("+noeudToPrint.getCoup().position+")\n";
			 for (int j=0; j<noeudToPrint.getNbFils();j++ ){
				 listArcsString += " Noeud("+noeudToPrint.getCoup().position + ") --> Noeud("+noeudToPrint.getListeFils().get(j).getCoup().position+")\n";
			 }
			 for(int j=0;j<noeudToPrint.getNbFils();j++){
				 E.push(noeudToPrint.getListeFils().get(j));
			 }
		 }
		 
		 System.out.println("Liste des noeuds :\n"+listNoeudsString);
		 System.out.println("Liste des arcs :\n"+listArcsString);
		 
	 }
	

}
