package fr.alma.modele;

import java.util.HashSet;

/*$Author$ 
 * $Date$ 
 * $Revision$ 
 *  
 * license
 * 
 * */
/**
 * Class used to represent group of empty cell
 */
public class Vide {

	/**
	 * The neighbors of the empty group
	 */
	private HashSet<Groupe> groupeVoisin;
	
	/**
	 * empty stone composed by this group
	 * 
	 */
	private HashSet<Pion> pionVides;
	
	public Vide() {
		groupeVoisin=new HashSet<Groupe>();
		pionVides=new HashSet<Pion>();
	}

	/**
	 * @return the groupeVoisin
	 */
	public HashSet<Groupe> getGroupeVoisin() {
		return groupeVoisin;
	}




	/**
	 * @param groupeVoisin the groupeVoisin to set
	 */
	public void setGroupeVoisin(HashSet<Groupe> groupeVoisin) {
		this.groupeVoisin = groupeVoisin;
	}

	/**
	 * @return the pionVides
	 */
	public HashSet<Pion> getPionVides() {
		return pionVides;
	}

	/**
	 * @param pionVides the pionVides to set
	 */
	public void setPionVides(HashSet<Pion> pionVides) {
		this.pionVides = pionVides;
	}

	/**
	 * Add a group to the neighbors
	 * @param g
	 */
	public void addGroup(Groupe g){
		this.groupeVoisin.add(g);
	}
	
	/**
	 * add an ampty stone to the 'group'
	 * @param p
	 * @return
	 */
	public boolean addPion(Pion p){
		return pionVides.add(p);
	}
	
	
	
}
