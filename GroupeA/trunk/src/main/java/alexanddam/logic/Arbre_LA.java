package main.java.alexanddam.logic;

import java.util.LinkedList;

public class Arbre_LA extends AbstractArbre {

	public Noeud_LA racine;

	public Arbre_LA(){
		racine = null;
	}	

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

	public void etablirArite(int arite) {
		if(arite<=1){
			return;
		}
		this.arite = arite;
	}

	/**
	 * Get the arity
	 */
	public int getArite(){
		return this.arite;
	}
	

	public AbstractArbre creerArbre(int arite,AbstractNoeud racine){
		AbstractArbre arbre= new Arbre_LA(arite,racine.valeur);
		((Noeud_LA)arbre.noeud).liste_fils=((Noeud_LA)racine).liste_fils;
		return arbre;
	}
	

	public AbstractArbre supprimerNoeud(AbstractNoeud arbo){
		((Noeud_LA)arbo).supprimer();
		return this;	
	}



	public AbstractArbre ajouterFils(AbstractNoeud fils){
		((Noeud_LA)racine).ajouterFils(fils);
		
		return this;

	}

	public AbstractArbre ajouterFils(AbstractNoeud fils,AbstractNoeud noeud){
		((Noeud_LA)fils).ajouterFils(noeud);
		return this;
	}


 	public AbstractArbre getRacine(){
		return this;
	}
 	

	public AbstractNoeud racinen(){
		return this.racine;
	}
 	

 	public AbstractNoeud node(int val){
		return ((Noeud_LA)racine).node(val);
	}
 	

 	public AbstractNoeud ieme(AbstractNoeud pere,int ieme){
		return ((Noeud_LA)pere).ieme(ieme);
	}
 	

 	public AbstractNoeud getPere(AbstractNoeud fils){
		return ((Noeud_LA)fils).pere;
	}
 	 	

 	public int getFrere(AbstractNoeud fils){
		return ((Noeud_LA)fils).position();
	}
 	

	public void affichageLA(){
		((Noeud_LA)racine).affichageLA();
	}
	

	public void affichageNA(){
		((Noeud_LA)racine).affichageNA();
	}
	
}
