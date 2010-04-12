package fr.alma.modele;

import java.util.Collection;
import java.util.HashSet;

public class Groupe {

	// couleur du groupe
	private CouleurPion couleur;

	//groupe de pions
	private HashSet<Pion> pions;

	// libertés du groupe
	private HashSet<Coordonnee>  libertes;

	//un groupe commence par un pion

	public Groupe(CouleurPion coul) {
		this.couleur = coul;
		pions = new HashSet<Pion>();
		libertes = new HashSet<Coordonnee>();
	}
	
	
	public Groupe(Pion pion) {
		this(pion.getCouleur());
		pions.add(pion);
	}

	public boolean aucuneLibertes() {
		return (libertes.isEmpty());
	}

	public int getNbLiberty() {
		return libertes.size();
	}

	public int getNbPions() {
		return pions.size();
	}

	public CouleurPion getCouleur() {
		return couleur;
	}

	public void setCoul(CouleurPion coul) {
		this.couleur = coul;
	}

	public HashSet<Pion> getPions() {
		return pions;
	}

	public void setPions(HashSet<Pion> pions) {
		this.pions = pions;
	}

	public HashSet<Coordonnee> getLibertes() {
		return libertes;
	}

	public void setLibertes(HashSet<Coordonnee> libertes) {
		this.libertes = libertes;
	}

	public boolean addLiberty(Coordonnee cood){
		return this.libertes.add(cood);
	}
	
	public boolean removeLiberty(Coordonnee cood){
		return libertes.remove(cood);
	}
	
	public boolean isALiberty(Coordonnee cood){
		return libertes.contains(cood);
	}
	
	public boolean removePion(Pion pi){
		return this.pions.remove(pi);
	}
	
	public boolean addPion(Pion pi){
		return pions.add(pi);
	}
	
	public boolean containPion(Pion pi){
		return pions.contains(pi);
	}
	
	public void addLibertys(Collection<Coordonnee> calculPionLiberte) {
		this.libertes.addAll(calculPionLiberte);
	}


	public void removeLibertys(Collection<Coordonnee> toRemove) {
		this.libertes.removeAll(toRemove);
	}
	
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
	
	public void clear(){
		this.libertes.clear();
		this.pions.clear();
	}
	
	

}
