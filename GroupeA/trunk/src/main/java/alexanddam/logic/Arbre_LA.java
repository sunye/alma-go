package main.java.alexanddam.logic;

import java.util.LinkedList;

public class Arbre_LA extends AbstractArbre {

	public Noeud_LA racine;

	//constructeur par defaut, arborescence sans racine, deconseillé
	public Arbre_LA(){
		racine = null;
	}
	
	//constructeur prenant en argument la valeur de sa racine et sans arite,
	// utilisation exclusive pour un arbre LA
	public Arbre_LA(int valeur){
		racine = new Noeud_LA(valeur); 	//creation du noeud racine
		arite =0;	
		((Noeud_LA)racine).liste_fils = new LinkedList<Noeud_LA>();
	}
	
	public Arbre_LA(int arite,int valeur){
		
		racine = new Noeud_LA(valeur);
		
		if(arite==0){
			return;
		}
		
		this.arite = arite;
	}
	
	/*					*/
	/* Fonctions arite  */
	/*					*/
	
	public void etablirArite(int arite) {
		if(arite<=1){
			return;
		}
		this.arite = arite;
	}

	public int getArite(){
		return this.arite;
	}
	
	// creer arbre avec la racine r et arite ar
	public AbstractArbre creerArbre(int arite,AbstractNoeud racine){
		AbstractArbre arbre= new Arbre_LA(arite,racine.valeur);
		((Noeud_LA)arbre.noeud).liste_fils=((Noeud_LA)racine).liste_fils;
		return arbre;
	}
	
	// supprimer une arborescence -> l'objet auquel on a appele supprimer
	public AbstractArbre supprimerNoeud(AbstractNoeud arbo){
		((Noeud_LA)arbo).supprimer();
		return this;	
	}


	// Ajouter un fils au noeud racine de l'arbre
	// Pour ajouter un fils à un noeud precis, voir la classe ajouterFils(Noeud f, Noeud p) ou voir a partir de la classe Noeud_LA
	public AbstractArbre ajouterFils(AbstractNoeud fils){
		((Noeud_LA)racine).ajouterFils(fils);
		
		return this;

	}

	public AbstractArbre ajouterFils(AbstractNoeud fils,AbstractNoeud noeud){
		((Noeud_LA)fils).ajouterFils(noeud);
		return this;
	}

  	// acceder a la racine -> va retourner l'arbre entier (a partir de la racine)
 	public AbstractArbre getRacine(){
		return this;
	}
 	
//  acceder a la racine -> va retourner le noeud racine
	public AbstractNoeud racinen(){
		return this.racine;
	}
 	
 	// acceder a un Noeud de valeur v // differe de la classe Arbre_TM qui accède à l'étiquette du noeud
 	public AbstractNoeud node(int val){
		return ((Noeud_LA)racine).node(val);
	}
 	
 	// acceder a i-ieme fils d'un Noeud pere -> noeud i-ieme fils du Noeud pere
 	public AbstractNoeud ieme(AbstractNoeud pere,int ieme){
		return ((Noeud_LA)pere).ieme(ieme);
	}
 	
 	// acceder au pere d'un Noeud -> noeud pere du Noeud f
 	public AbstractNoeud getPere(AbstractNoeud fils){
		return ((Noeud_LA)fils).pere;
	}
 	 	
	//acceder à l'ordinal d'un Noeud dans sa fratrie -> retour un noeud // bonne interpretation
 	public int getFrere(AbstractNoeud fils){
		return ((Noeud_LA)fils).position();
	}
 	
 	//affichage par liste d'adjacences
	public void affichageLA(){
		((Noeud_LA)racine).affichageLA();
	}
	
	//affichage par noeuds/arcs
	public void affichageNA(){
		((Noeud_LA)racine).affichageNA();
	}
	
}
