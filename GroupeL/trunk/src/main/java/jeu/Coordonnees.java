/**
 * @author Dejean Charles - Pottier Vincent
 * 
 * Classe permettant de gerer des coordonnees entieres bidimensionnelles. 
 * 
 */

package jeu;

public class Coordonnees {
	private int x;
	private int y;
	
	/* Getters - Setters */
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	/* Constructors */
	public Coordonnees(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	public Coordonnees() {
		super();
		this.x = 0;
		this.y = 0;
	}
	
	/* function */
	
	/**
	 * @param coord : coordonnee a tester;
	 * @return true si les coordonnees sont identiques
	 */
	public boolean estEgal(Coordonnees coord) {
		return ((coord.getX()==this.x) && (coord.getY()==this.y));
	}
	
	/**
	 * @param coord : coordonnee a tester;
	 * @return true si les coordonnees sont adjacente
	 */
	public boolean estAdjacent(Coordonnees coord) {
		return ( ((coord.getX()+1 == this.x)||(coord.getX()-1 == this.x)) && (coord.getY() == this.y) ) || ( ((coord.getY()+1 == this.y)||(coord.getY()-1 == this.y)) && (coord.getX() == this.x) ) ;
	}	
}
