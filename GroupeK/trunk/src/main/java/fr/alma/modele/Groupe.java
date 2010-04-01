package fr.alma.modele;

import java.util.Vector;

public class Groupe {

	// couleur du groupe
	private CouleurPion coul;

	//groupe de pions
	private Vector<Pion> pions;

	// libertés du groupe
	private Vector<Pion>  libertes;

	//un groupe commence par un pion
	Groupe(Pion pion) {
		coul = pion.getCouleur();

		pions = new Vector<Pion>();
		pions.add(pion);

		// libertés du groupe = libertés du pion de depart
		libertes = new Vector<Pion>(pion.getListeLibertes());
	}

	public boolean aucuneLibertes() {
		return (libertes.isEmpty());
	}

	public int nbLibertes() {
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

	public Vector<Pion> getPions() {
		return pions;
	}

	public void setPions(Vector<Pion> pions) {
		this.pions = pions;
	}

	public Vector<Pion> getLibertes() {
		return libertes;
	}

	public void setLibertes(Vector<Pion> libertes) {
		this.libertes = libertes;
	}

	public CouleurPion coulOppose(CouleurPion coul){
		if (coul==CouleurPion.BLANC){
			return CouleurPion.NOIR;
		}else{
			return CouleurPion.BLANC;
		}
	}

}
