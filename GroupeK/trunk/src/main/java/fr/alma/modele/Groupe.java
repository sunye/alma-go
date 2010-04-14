package fr.alma.modele;

import java.util.Collection;

import java.util.HashSet;
/*$Author$ 
 * $Date$ 
 * $Revision$ 
 *  
 * license
 * 
 * */
/**
 * 
 * @author Manoël Fortun et Anthony "Bambinôme" Caillaud
 * Class that represent stone group's and their liberty
 */
public class Groupe {

	/**
	 * Color of the group
	 */
	private CouleurPion couleur;

	/**
	 * The stone of the group
	 */
	private HashSet<Pion> pions;

	/**
	 * Coordinate of the group's liberty
	 */
	private HashSet<Coordonnee>  libertes;

	/**
	 * constructor
	 * @param coul the color of the group
	 */
	public Groupe(CouleurPion coul) {
		this.couleur = coul;
		pions = new HashSet<Pion>();
		libertes = new HashSet<Coordonnee>();
	}
	
	/**
	 * 
	 * @return the number of liberty
	 */
	public int getNbLiberty() {
		return libertes.size();
	}

	/**
	 * 
	 * @return the number of stone composed by the group
	 */
	public int getNbPions() {
		return pions.size();
	}

	/**
	 * @return the corlor
	 */
	public CouleurPion getCouleur() {
		return couleur;
	}

	/**
	 * @param couleur the color to set
	 */
	public void setCouleur(CouleurPion couleur) {
		this.couleur = couleur;
	}

	/**
	 * @return the pions
	 */
	public HashSet<Pion> getPions() {
		return pions;
	}

	/**
	 * @param pions the pions to set
	 */
	public void setPions(HashSet<Pion> pions) {
		this.pions = pions;
	}

	/**
	 * @return the libertes
	 */
	public HashSet<Coordonnee> getLibertes() {
		return libertes;
	}

	/**
	 * @param libertes the libertes to set
	 */
	public void setLibertes(HashSet<Coordonnee> libertes) {
		this.libertes = libertes;
	}

	/**
	 * Add a liberty to the group
	 * @param cood the coordinate of the liberty
	 * @return if the coordinate is add (typically if the liberty where not already in the set)
	 */
	public boolean addLiberty(Coordonnee cood){
		return this.libertes.add(cood);
	}
	
	/**
	 * remove 
	 * @param cood
	 * @return if the coordinate where removed (typically if the liberty where in the set)
	 */
	public boolean removeLiberty(Coordonnee cood){
		return libertes.remove(cood);
	}
	
	/**
	 * check if a coordinate is a liberty of the group
	 * @param cood
	 * @return
	 */
	public boolean isALiberty(Coordonnee cood){
		return libertes.contains(cood);
	}
	
	/**
	 * remove 
	 * @param pi
	 * @return if the stone where removed (typically if the stone where in the set)
	 */
	public boolean removePion(Pion pi){
		return this.pions.remove(pi);
	}
	
	/**
	 * add 
	 * @param pi
	 * @return if the stone where removed (typically if the stone where not already in the set)
	 */
	public boolean addPion(Pion pi){
		return pions.add(pi);
	}
	
	/**
	 * check if the group containt a stone 
	 * @param pi
	 * @return if the stone is in the group
	 */
	public boolean containPion(Pion pi){
		return pions.contains(pi);
	}
	
	/**
	 * Add a collection of liberty to the group
	 * @param calculPionLiberte
	 */
	public void addLibertys(Collection<Coordonnee> calculPionLiberte) {
		this.libertes.addAll(calculPionLiberte);
	}

	/**
	 * remove a collection of liberty to the group
	 * @param toRemove
	 */
	public void removeLibertys(Collection<Coordonnee> toRemove) {
		this.libertes.removeAll(toRemove);
	}
	
	/**
	 * use to make fusion between group
	 * it destroy the redundant group
	 * @param group the other group to fusion with
	 * @return the fusioned group 
	 */
	public Groupe fusionGroup(Groupe group){
		Groupe result=this;
		
		if(group.getNbPions()>result.getNbPions()){
			result=group;
			group=this;
		}
		
		result.addLibertys(group.getLibertes());
		
		for( Pion pi: group.getPions()){
			pi.setGroupe(result);
			result.addPion(pi);
		}

		group.clear();
		try {
			group.finalize();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	
		return result;
	}
	
	/**
	 * clear the group liberty and stone
	 */
	public void clear(){
		this.libertes.clear();
		this.pions.clear();
	}
	
	

}
