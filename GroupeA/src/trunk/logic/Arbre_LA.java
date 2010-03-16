package trunk.logic;

import java.util.LinkedList;

public class Arbre_LA extends Arbre {

	public Noeud_LA c;

	//constructeur par defaut, arborescence sans racine, deconseillé
	public Arbre_LA(){
		c = null;
	}
	
	//constructeur prenant en argument la valeur de sa racine et sans arite,
	// utilisation exclusive pour un arbre LA
	public Arbre_LA(int valeur){
		c = new Noeud_LA(valeur); 	//creation du noeud racine
		arite =0;	
		((Noeud_LA)c).liste_fils = new LinkedList<Noeud_LA>();
	}
	
	public Arbre_LA(int arite,int valeur){
		
		c = new Noeud_LA(valeur);
		
		if(arite==0)
			return;
		
		this.arite = arite;
	}
	
	/*					*/
	/* Fonctions arite  */
	/*					*/
	
	public void etablirArite(int a) {
		if(a<=1)
			return;
		arite = a;
	}

	public int get_arite(){
		return this.arite;
	}
	
	// creer arbre avec la racine r et arite ar
	public Arbre creerArbre(int ar,Noeud r){
		Arbre arbre= new Arbre_LA(ar,r.valeur);
		((Noeud_LA)arbre.c).liste_fils=((Noeud_LA)r).liste_fils;
		return arbre;
	}
	
	// supprimer une arborescence -> l'objet auquel on a appele supprimer
	public Arbre supprimerNoeud(Noeud arbo){
		((Noeud_LA)arbo).supprimer();
		return this;	
	}


	// Ajouter un fils au noeud racine de l'arbre
	// Pour ajouter un fils à un noeud precis, voir la classe ajouterFils(Noeud f, Noeud p) ou voir a partir de la classe Noeud_LA
	public Arbre ajouterFils(Noeud f){
		((Noeud_LA)c).ajouterFils(f);
		
		return this;

	}

	public Arbre ajouterFils(Noeud f,Noeud p){
		((Noeud_LA)f).ajouterFils(p);
		return this;
	}

  	// acceder a la racine -> va retourner l'arbre entier (a partir de la racine)
 	public Arbre racine(){
		return this;
	}
 	
//  acceder a la racine -> va retourner le noeud racine
	public Noeud racinen(){
		return this.c;
	}
 	
 	// acceder a un Noeud de valeur v // differe de la classe Arbre_TM qui accède à l'étiquette du noeud
 	public Noeud node(int v){
		return ((Noeud_LA)c).node(v);
	}
 	
 	// acceder a i-ieme fils d'un Noeud pere -> noeud i-ieme fils du Noeud pere
 	public Noeud ieme(Noeud pere,int i){
		return ((Noeud_LA)pere).ieme(i);
	}
 	
 	// acceder au pere d'un Noeud -> noeud pere du Noeud f
 	public Noeud pere(Noeud f){
		return ((Noeud_LA)f).pere;
	}
 	 	
	//acceder à l'ordinal d'un Noeud dans sa fratrie -> retour un noeud // bonne interpretation
 	public int frere(Noeud f){
		return ((Noeud_LA)f).position();
	}
 	
 	//affichage par liste d'adjacences
	public void affichage_la(){
		((Noeud_LA)c).affichage_la();
	}
	
	//affichage par noeuds/arcs
	public void affichage_na(){
		((Noeud_LA)c).affichage_na();
	}
	
}
