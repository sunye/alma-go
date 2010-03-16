package utils;

import java.util.HashSet;

import utils.exceptions.BadCouleurException;

public class Groupe {
	
	private HashSet<Pion> pions;
	private PionVal couleur;
	
	
	public Groupe(PionVal couleur) {
		super();
		this.couleur = couleur;
		pions = new HashSet<Pion>();
	}
	
	public boolean contains(Pion p){
		return pions.contains(p);
	}
	
	public void addPion(Pion p) throws BadCouleurException {
		if (p.getCouleur() == couleur){
			this.pions.add(p);
		} else {
			throw new BadCouleurException("Ce groupe est de couleur "+couleur.valeur());
		}
	}

	
	public boolean intersectionNonVide(Groupe autre){
		if(!couleur.equals(autre.getCouleur())) {
			return false;
		}
		for(Pion p:pions){
			if(autre.contains(p)){
				return true;
			}
		}
		return false;
	}
	
	public Object getCouleur() {
		return couleur;
	}

	public Groupe fusion(Groupe autre){
		Groupe sortie = null;
		if(this.intersectionNonVide(autre)){
			//Si on partage un pion, alors on doit fusionner les deux groupes.
			sortie = new Groupe(couleur);
			sortie.pions.addAll(pions);
			sortie.pions.addAll(autre.pions);
		}
		
		return sortie;
	}

	public void addAll(Groupe autre) {
		if(this.getCouleur() == autre.getCouleur()){
			this.pions.addAll(autre.pions);
		}
	}
	
	
	
	

}
