package fr.alma.controler;

import fr.alma.ihm.MainFrame;
import fr.alma.ihm.Loadeur;
import fr.alma.ihm.NewGameDialog;
import fr.alma.modele.StoneColor;
import fr.alma.modele.GameHandler;
import fr.alma.modele.Stone;


/*$Author$ 
 * $Date$ 
 * $Revision$ 
 * 
 *Copyright (C) 2010  Fortun Manoël & Caillaud Anthony
 *
 *This program is free software: you can redistribute it and/or modify
 *it under the terms of the GNU General Public License as published by
 *the Free Software Foundation, either version 3 of the License, or
 *(at your option) any later version.
 *
 *This program is distributed in the hope that it will be useful,
 *but WITHOUT ANY WARRANTY; without even the implied warranty of
 *MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *GNU General Public License for more details.
 *
 *You should have received a copy of the GNU General Public License
 *along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * */
/**
 * The controler for the game. He knows all class from the UI and from the
 * modele All transaction between the view and the model pass threw this
 * controler.
 * 
 * @author Manoël Fortun et Anthony "Bambinôme" Caillaud
 * 
 */
public class Controler {

	/**
	 * The model of the game
	 */
	private GameHandler gameMaster;

	/**
	 * the action listener's Factory
	 */
	private ActionListenerFactory factory;

	/**
	 * The main frame of the game
	 */
	private MainFrame frame;

	/**
	 * Frame when the game is loading and think about the
	 */
	private Loadeur loaderDialog;

	/**
	 * Frame use to choose the game option
	 */
	private NewGameDialog newGameDialog;

	/**
	 * Constructor that initialize all the game
	 */
	public Controler() {
		this.gameMaster = new GameHandler(this);
		this.factory = new ActionListenerFactory(this);
		this.frame = new MainFrame(this);
		this.loaderDialog = new Loadeur(frame, this);
		this.newGameDialog = new NewGameDialog(frame, this);
	}

	public GameHandler getGm() {
		return gameMaster;
	}

	public ActionListenerFactory getFactory() {
		return factory;
	}

	/**
	 * Method used to know the matrix size
	 * 
	 * @return the matrix size that contain the pion
	 */
	public int getMatrixSize() {
		return gameMaster.getMatrixSize();
	}

	/**
	 * get the Matrix
	 * 
	 * @return the matrix
	 */
	public Stone[][] getMatrix() {

		return gameMaster.getMatrixBoard();
	}

	/**
	 * Launch the game
	 */
	public void launchTheGame() {
		this.frame.setVisible(true);
	}

	/**
	 * Prepare a new board with the option selected by the player
	 */
	public void newGame() {
		gameMaster.reset();
		frame.repaintBoard();

		newGameDialog.setVisible(false);
		if (newGameDialog.isAiVsHuman()) {
			gameMaster.setModeAiVsHuman();
		} else {
			gameMaster.setModeHumanVsHuman();
		}

		gameMaster.setGoal(newGameDialog.getGoalNumberSelected());
		gameMaster.setDifficulty(newGameDialog.getDifficulty());

	}

	/**
	 * Used to show the winner
	 * 
	 * @param coul
	 *            the winner
	 */
	public void showWinner(StoneColor coul) {
		frame.showWinner(coul);
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
		gameMaster.addStone(x / frame.getColSize(), y / frame.getRowSize());
		
	}

	/**
	 * Used to force to play
	 */
	public void forceToPlay() {
		gameMaster.forceToPlay();
		showLoader(false);

	}

	/**
	 * Show the loader (frame when the ai is thinking)
	 * @param affiche true of false 
	 */
	public void showLoader(boolean affiche) {
		if (affiche) {
			loaderDialog.setVisible(true);
			/*
			 * affichageLoader = new Thread() { public void run() {
			 * 
			 * } }; affichageLoader.start();
			 */
		} else {
			// affichageLoader.interrupt();
			loaderDialog.setVisible(false);
		}
	}

	/**
	 * Repaint the UI board
	 */
	public void repaintBoard() {
		
		frame.repaintBoard();
	}

	/**
	 * Show a message in a JDialog
	 * @param string the message
	 */
	public void showMessage(String string) {
		frame.showMessage(string);
	}

	/**
	 * Enable of disable choice of the difficulty of the Ai
	 * @param b
	 */
	public void enableAiChoice(boolean b) {
		this.newGameDialog.enableChoiceDifficulty(b);
	}

	/**
	 * Show or not the Dialog of the game's option
	 * @param b 
	 */
	public void showNewGameDial(boolean b) {
		this.newGameDialog.affichage(b);
	}

}
