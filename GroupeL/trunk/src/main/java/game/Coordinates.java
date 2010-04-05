/**
 * @author Dejean Charles - Pottier Vincent
 * 
 * Classe permettant de gerer des coordonnees entieres bidimensionnelles. 
 * 
 */

package game;

public class Coordinates {
	private Integer x;
	private Integer y;
	
	/* Getters - Setters */
	public Integer getX() {
		return x;
	}
	public void setX(Integer x) {
		this.x = x;
	}
	public Integer getY() {
		return y;
	}
	public void setY(Integer y) {
		this.y = y;
	}
	
	/* Constructors */
	public Coordinates(Integer x, Integer y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	public Coordinates() {
		super();
		this.x = 0;
		this.y = 0;
	}
	
	/* function */
	/**
	 * @param coord : Coordinates to test;
	 * @return true if the Coordinates are the same
	 */
	public boolean equals(Coordinates coord) {
		return ((coord.getX()==this.x) && (coord.getY()==this.y));
	}
	/**
	 * @param coord : Coordinates to test;
	 * @return true if the Coordinates are adjacent
	 */
	public boolean isAdjacent(Coordinates coord) {
		return ( ((coord.getX()+1 == this.x)||(coord.getX()-1 == this.x)) && (coord.getY() == this.y) ) || ( ((coord.getY()+1 == this.y)||(coord.getY()-1 == this.y)) && (coord.getX() == this.x) ) ;
	}
	
	@Override
	public String toString() {
		return  "(" + x + "," + y + ")";
	}	
	
	
	
	
}
