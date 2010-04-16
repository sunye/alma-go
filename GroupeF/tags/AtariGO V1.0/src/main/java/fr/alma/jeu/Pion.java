package fr.alma.jeu;

import java.awt.Point;

/**
 * Class which constructs the pawn
 * @author ZERBITA Mohamed El Hadi
 *
 */
public class Pion {

	
	
	public Couleur couleur;
	public Point position;
	
	/**
	 * Constructor
	 * @param couleur color of the pawn
	 * @param position position of the pawn
	 */
	public Pion(Couleur couleur, Point position){
		
		this.couleur = couleur; 
		this.position = position; 
	}

	/**
	 * Constructor
	 * @param point position of the pawn
	 */
	public Pion(Point point) {
		this.couleur = Couleur.NULL;
		this.position = point;
	}

	/**
	 * Test the equality between two pawns
	 * @param pion the other pawn
	 * @return the result
	 */
	public boolean equals(Pion pion) {
		return this.couleur.equals(pion.couleur)&&
		(this.position.x == pion.position.x)&&
		(this.position.y == pion.position.y);
	}
}
