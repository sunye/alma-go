/**
 * @author Bouvet Frederic
 * @author Altuntas Murat
 */
package coeur;

/**
 * Classe permettant de creer le coeur
 */
public class Coeur {

	/** Le plateau de jeu */
	private Plateau plateau;
	/** La partie */
	private Partie partie;
	
	/**
	 * Noyau du jeu
	 */
	public Coeur() {
		plateau = new Plateau();
		partie = new Partie(plateau);
	}
	
	/**
	 * Connaitre le plateau
	 * @return le plateau courant
	 */
	public Plateau getPlateau(){
		return plateau;
	}
	
	/**
	 * Connaitre la partie
	 * @return la partie
	 */
	public Partie getPartie(){
		return partie;
	}

}
