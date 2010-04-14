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
 * special class used by the AI to synchronised thread
 */
public class CoordonneeIA extends Coordonnee {

	/**
	 * An indicator to set is the search is over
	 */
	private boolean termine; 
	
	/**
	 * construct a classical coordinate
	 * @param x
	 * @param y
	 */
	public CoordonneeIA(Integer x, Integer y) {
		super(x, y);
		setTermine(false);
	}

	/**
	 * set the search end
	 * @param termine
	 */
	public void setTermine(boolean termine) {
		this.termine = termine;
	}

	/**
	 * if the seach if over
	 * @return
	 */
	public boolean isTermine() {
		return termine;
	}
	
	/**
	 * set the attribute from a super class
	 * @param coord
	 */
	public void setCoordinate(Coordonnee coord){
		this.setX(coord.getX());
		this.setY(coord.getY());
	}
	
	/**
	 * the equivalent with a super class
	 * @return equivalent
	 */
	public Coordonnee convert(){
		return new Coordonnee(getX(), getY());
	}
	
}
