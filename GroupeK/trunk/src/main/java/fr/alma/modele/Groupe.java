package fr.alma.modele;

import java.util.HashSet;
import java.util.List;

public class Groupe {

	// couleur du groupe
	private CouleurPion coul;

	//groupe de pions
	private HashSet<Pion> pions;

	// libertés du groupe
	private HashSet<Coordonnee>  libertes;

	//un groupe commence par un pion

	public Groupe(CouleurPion coul) {
		this.coul = coul;
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

	public int liberty() {
		return libertes.size();
	}

	public int nbPions() {
		return pions.size();
	}

	public CouleurPion getCoul() {
		return coul;
	}

	public void setCoul(CouleurPion coul) {
		this.coul = coul;
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
	
	public Groupe fusionGroup(Groupe group){
		Groupe result=this;
		
		if(group.nbPions()>result.nbPions()){
			result=group;
			group=this;
		}
		
		result.getLibertes().addAll(group.getLibertes());
		
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
	
	private void clear(){
		this.libertes.clear();
		this.pions.clear();
	}


	public void addLibertys(List<Coordonnee> calculPionLiberte) {
		this.libertes.addAll(calculPionLiberte);
		
	}
	

}
