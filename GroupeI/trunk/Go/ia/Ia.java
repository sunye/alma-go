/**
 * @author Bouvet Frederic
 * @author Altuntas Murat
 */
package ia;

import coeur.Joueur;
import coeur.Coeur;
import coeur.Partie;
import coeur.Plateau;


/**
 * Classe permettant de creer un thread pour l'IA et d'y faire jouer un ordinateur  
 */
public class Ia implements Runnable {

	/** Le plateau de jeu */
	private Plateau plateau;
	/** La partie qui est en cours */
	private Partie partie;
	/** Le joueur en cours */
	private Joueur joueur;
	/** Le thread pour jouer un coup IA */
	private Thread t;
		
	
	/**
	 * Constructeur
	 * @param coeur Le coeur sur lequel on se branche 
	 */
	public Ia(Coeur coeur) {
		this.plateau = coeur.getPlateau();
		this.partie = coeur.getPartie();
		coeur.getPartie().setIa(this);				
	}
	
	/**
	 * Joue un coup IA
	 * @param joueur le joueur que l'on souahaite faire jouer
	 */
	public void jouer(Joueur joueur){
		this.joueur = joueur;
		t = new Thread(this);
		t.start();
	}
	
	/**
	 * Implementation de l'execution du thread permettant a l'IA de calculer et de jouer un coup
	 */
	public void run(){	
		// On lance le compte a rebours
		this.partie.getCompteArebours().lancer();		
		// L'IA calcul un coup
		AlphaBeta calculCoup = new AlphaBeta(plateau, joueur);		
		// On arrete le compte a rebours si il est encore en marche
		if(this.partie.getCompteArebours().estActif()){
			 this.partie.getCompteArebours().stop();
		}		
		// On joue le coup qui vient d'etre calcule
		joueur.jouerCoup(calculCoup.getLigne(),calculCoup.getColonne());		
		// On actualise le jeu puis on passe au joueur suivant
		joueur.getActualisation().miseAJour();
		this.partie.joueurSuivant();
	}
	
	
}
