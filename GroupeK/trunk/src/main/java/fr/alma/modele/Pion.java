package fr.alma.modele;



public class Pion {

	private CouleurPion couleur;
	private int numero;
	private Coordonnee position;	
	//Groupe auquel il appartient
	private Groupe groupe;
	private boolean marque;

	public Pion(Integer x, Integer y, CouleurPion couleur, int numero) {
		this.position= new Coordonnee(x, y);
		this.couleur = couleur;
		this.numero = numero;
	
	}

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

	public Coordonnee getPosition() {
		return position;
	}

	public void setPosition(Coordonnee position) {
		this.position = position;
	}

	public Groupe getGroupe() {
		return groupe;
	}

	public void setGroupe(Groupe groupe) {
		this.groupe = groupe;
	}

	public boolean isMarque() {
		return marque;
	}

	public void setMarque(boolean marque) {
		this.marque = marque;
	}	
	
	
	
}
