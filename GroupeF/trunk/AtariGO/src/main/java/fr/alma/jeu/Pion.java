package fr.alma.jeu;

import java.awt.Point;

/**
 * Classe qui construit un pion
 * @author ZERBITA Mohamed El Hadi
 *
 */
public class Pion {

	
	
	public Couleur couleur;
	public Point position;
	
	/**
	 * Constructeur
	 * @param couleur couleur du pion
	 * @param position position du pion
	 */
	public Pion(Couleur couleur, Point position){
		
		this.couleur = couleur; 
		this.position = position; 
	}

	/**
	 * Constructeur
	 * @param point position du pion
	 */
	public Pion(Point point) {
		this.couleur = Couleur.NULL;
		this.position = point;
	}

	/**
	 * Test l'égalité entre deux pions
	 * @param pion l'autre pion
	 * @return le resultat
	 */
	public boolean equals(Pion pion) {
		return this.couleur.equals(pion.couleur)&&
		(this.position.x == pion.position.x)&&
		(this.position.y == pion.position.y);
	}
}
