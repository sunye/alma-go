package fr.alma.modele;

import java.util.HashSet;


/**
 * Class used to represent group of empty cell
 */
public class Vide {

	private HashSet<Groupe> groupeVoisin;
	private HashSet<Pion> pionVides;
	
	public Vide() {
		groupeVoisin=new HashSet<Groupe>();
		pionVides=new HashSet<Pion>();
	}
	
	public HashSet<Groupe> getGroupeVoisin() {
		return groupeVoisin;
	}
	public void setGroupeVoisin(HashSet<Groupe> groupeVoisin) {
		this.groupeVoisin = groupeVoisin;
	}
	public HashSet<Pion> getPionVides() {
		return pionVides;
	}
	public void setPionVides(HashSet<Pion> pionVides) {
		this.pionVides = pionVides;
	}

	public void addGroup(Groupe g){
		this.groupeVoisin.add(g);
	}
	
	
	public boolean addPion(Pion p){
		return pionVides.add(p);
	}
	
	
	
}
