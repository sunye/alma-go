package fr.alma.controler;

import fr.alma.ihm.Fenetre;
import fr.alma.ihm.Loadeur;
import fr.alma.ihm.NouvellePartieDialog;
import fr.alma.modele.CouleurPion;
import fr.alma.modele.GameHandler;
import fr.alma.modele.Pion;

/**
 * 
 * @author Manoël Fortun et Anthony "Bambinôme" Caillaud 
 * The controler for the game.
 * He knows all class from the UI and from the modele All
 * transaction between the view and the model pass threw this controler.
 */
public class Controler {

	/**
	 * The model of the game
	 */
	private GameHandler gm;

	/**
	 * the action listener's Factory
	 */
	private ActionListenerFactory factory;

	/**
	 * The main frame of the game
	 */
	private Fenetre jeu;

	/**
	 * Frame when the game is loading and think about the
	 */
	private Loadeur chargement;

	/**
	 * Frame use to choose the game option
	 */
	private NouvellePartieDialog fenetreNewGame;

	/**
	 * Constructor that initialize all the game
	 */
	public Controler() {
		this.gm = new GameHandler(this);
		this.factory = new ActionListenerFactory(this);
		this.jeu = new Fenetre(this);
		this.chargement = new Loadeur(jeu, this);
		this.fenetreNewGame = new NouvellePartieDialog(jeu, this);
	}

	public GameHandler getGm() {
		return gm;
	}

	public ActionListenerFactory getFactory() {
		return factory;
	}

	/**
	 * Method used to know the matrix size
	 * 
	 * @return the matrix size that contain the pion
	 */
	public int tailleMatrice() {
		return gm.tailleMatrice();
	}

	/**
	 * get the Matrix
	 * 
	 * @return the matrix
	 */
	public Pion[][] getMatricePlateau() {

		return gm.getMatricePlateau();
	}

	/**
	 * Launch the game
	 */
	public void GO() {
		this.jeu.setVisible(true);
	}

	/**
	 * Prepare a new board with the option selected by the player
	 */
	public void newGame() {
		gm.remiseZero();
		jeu.repaintBoard();

		fenetreNewGame.setVisible(false);
		if (fenetreNewGame.isAiVsHuman()) {
			gm.setModeAiVsHuman();
		} else {
			gm.setModeHumanVsHuman();
		}

		gm.setObjectif(fenetreNewGame.getNbObjectifSelected());
		gm.setDifficulte(fenetreNewGame.getDifficulte());

	}

	/**
	 * Used to show the winner
	 * 
	 * @param coul
	 *            the winner
	 */
	public void afficheGagnant(CouleurPion coul) {
		jeu.affichageVainqueur(coul);
	}

	
	
	/**
	 * Method used by the UI to transmit the position of the next playd position
	 * 
	 * @param gobanX
	 *            x position
	 * @param gobanY
	 *            y position
	 * @return if the move is playable and played
	 */
	public void clicBoard(int x, int y) {
		gm.ajouterPion(x / jeu.getColSize(), y / jeu.getRowSize());
		
	}

	/**
	 * Used to force to play
	 */
	public void forcerJouer() {
		gm.forcerCoup();
		afficheLoader(false);

	}

	/**
	 * Show the loader (frame when the ai is thinking)
	 * @param affiche true of false 
	 */
	public void afficheLoader(boolean affiche) {
		if (affiche) {
			chargement.setVisible(true);
			/*
			 * affichageLoader = new Thread() { public void run() {
			 * 
			 * } }; affichageLoader.start();
			 */
		} else {
			// affichageLoader.interrupt();
			chargement.setVisible(false);
		}
	}

	/**
	 * Repaint the UI board
	 */
	public void repaintBoard() {
		
		jeu.repaintBoard();
	}

	/**
	 * Show a message in a JDialog
	 * @param string the message
	 */
	public void afficheMessage(String string) {
		jeu.affichageMessage(string);
	}

	/**
	 * Enable of disable choice of the difficulty of the Ai
	 * @param b
	 */
	public void enableAiChoice(boolean b) {
		this.fenetreNewGame.enableChoiceDifficulte(b);
	}

	/**
	 * Show or not the Dialog of the game's option
	 * @param b 
	 */
	public void afficheNouvellePartie(boolean b) {
		this.fenetreNewGame.affichage(b);
	}

}
