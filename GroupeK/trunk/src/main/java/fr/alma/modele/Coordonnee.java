package fr.alma.modele;
/*$Author$ 
 * $Date$ 
 * $Revision$ 
 * 
 * $license$
 * 
 * */
/**
 * 
 * @author Manoël Fortun et Anthony "Bambinôme" Caillaud
 * Class that represent a coordinate on the board
 */
public class Coordonnee {
	/**
	 * the x coordinate
	 */
	private Integer x;
	
	/**
	 * the y coordinate 
	 */
	private Integer y;
	
	
	/**
	 * @return the x
	 */
	public Integer getX() {
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(Integer x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public Integer getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(Integer y) {
		this.y = y;
	}

	/**
	 * Construct the coordinate with the value(that can be null)
	 * @param x
	 * @param y
	 */
	public Coordonnee(Integer x, Integer y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * is the coordinate are not empty
	 * @param max the maximum
	 * @return y>=0 && x>=0 && x<max && y<max
	 */
	public boolean isValid(int max){
		return isCoordinateNotEmpty() && x>=0&&y>=0&&x<max&&y<max;
	}
	
	/**
	 * check if the x and y are not null
	 * @return x!=null && y!=null
	 */
	public boolean isCoordinateNotEmpty(){
		return this.getX()!=null && this.getY()!=null;
	}
	
}
