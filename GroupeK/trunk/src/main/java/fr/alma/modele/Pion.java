package fr.alma.modele;

import java.util.Vector;

public class Pion {

	private CouleurPion couleur;
	private int numero;
	private Vector<Pion> listeLibertes;
	//Groupe auquel il appartient
	private Groupe groupe;

	public CouleurPion getCouleur() {
		return couleur;
	}
	public void setCouleur(CouleurPion couleur) {
		this.couleur = couleur;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}

	public Vector<Pion> getListeLibertes() {
		return listeLibertes;
	}

	public Groupe getGroupe() {
		return this.groupe;
	}
	public void setGroupe(Groupe groupe) {
		this.groupe = groupe;
	}

	public void setListeLibertes() {
		this.listeLibertes = new Vector<Pion>();
	}

	public CouleurPion coulOppose(CouleurPion coul){
		if (coul==CouleurPion.BLANC){
			return CouleurPion.NOIR;
		}else{
			return CouleurPion.BLANC;
		}
	}

	public Pion(CouleurPion couleur, int numero) {
		this.couleur = couleur;
		this.numero = numero;
		this.listeLibertes = new Vector<Pion>();
	}

}