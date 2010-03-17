package fr.alma.ihm;

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

	/*Groupe(CouleurPion color, Vector<Pion> pions, Vector<Pion> libertes) {
		     this.coul = color;
		     this.pions = pions;
		     this.libertes = libertes;
		   }*/

	public boolean hasNoLiberty() {
		return (libertes.isEmpty());
	}

	public int nbLiberties() {
		return libertes.size();
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

}
