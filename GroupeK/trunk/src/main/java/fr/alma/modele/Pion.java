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
 * Class used to represent a stone.
 */
public class Pion {

	/**
	 * The color of the stone
	 */
	private CouleurPion couleur;
	
	
	/**
	 * The coordinate of the stone
	 */
	private Coordonnee position;	
	/**
	 * The group owner of the stone
	 */
	private Groupe groupe;
	
	/**
	 * Boolean used by the AI, Usefull for some method
	 */
	private boolean marque;
	
	/**
	 *  Constructor
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @param couleur
	 */
	public Pion(Integer x, Integer y, CouleurPion couleur) {
		this.position= new Coordonnee(x, y);
		this.couleur = couleur;
		
	}

	/**
	 * @return the couleur
	 */
	public CouleurPion getCouleur() {
		return couleur;
	}

	/**
	 * @param couleur the couleur to set
	 */
	public void setCouleur(CouleurPion couleur) {
		this.couleur = couleur;
	}

	/**
	 * @return the position
	 */
	public Coordonnee getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(Coordonnee position) {
		this.position = position;
	}

	/**
	 * @return the groupe
	 */
	public Groupe getGroupe() {
		return groupe;
	}

	/**
	 * @param groupe the groupe to set
	 */
	public void setGroupe(Groupe groupe) {
		this.groupe = groupe;
	}

	/**
	 * @return the marque
	 */
	public boolean isMarque() {
		return marque;
	}

	/**
	 * @param marque the marque to set
	 */
	public void setMarque(boolean marque) {
		this.marque = marque;
	}


	
	
}
