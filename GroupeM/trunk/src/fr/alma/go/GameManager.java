package fr.alma.go;

import fr.alma.go.goban.Goban;
import fr.alma.go.goban.Place;
import fr.alma.go.heuristics.Computer;
import fr.alma.go.ui.GoFrame;
import fr.alma.go.ui.GoPanel;

public class GameManager {

	private Goban goban;
	private GoPanel goPan;
	private GoFrame frame;
	private Boolean onePlayerGame;
	private char iaColor; // A remplacer
	private Computer cpu;
	private char actualColor;

	public GameManager(GoPanel gp, GoFrame frm) {
		this.goPan = gp;
		this.frame = frm;
		this.actualColor = 'b';
	}

	public void switchColor() {
		if (actualColor == 'b') {
			actualColor = 'w';
		} else {
			actualColor = 'b';
		} // if
	} // switchColor()

	/**
	 * Start a game
	 */
	private void init() {
		goban = new Goban();

	}

	/**
	 * Begin a two-players game
	 */
	public void twoPlayers() {
		init();
		this.onePlayerGame = Boolean.FALSE;
		goPan.startGame(this, this.goban);
	}

	/**
	 * Begin a one-player game
	 * 
	 * @param couleur
	 *            : color of the stone player
	 */
	public void onePlayer(char color) {
		init();
		cpu = new Computer((color == 'b'), 1);
		iaColor = color;
		this.onePlayerGame = Boolean.TRUE;
		goPan.startGame(this, this.goban);
		if (color == 'b') {
			coupIA();
			this.switchColor();
		} // if
	}

	// private PlayMove threadizeAB(){
	// // AlphaBeta alphabeta = new AlphaBeta(3, iaColor);
	//        
	// thread = new ABThread(3, iaColor, goban);
	// thread.start();
	// try {
	// thread.join(10000);
	// } catch (InterruptedException ex) {
	// Logger.getLogger(GameManager.class.getName()).log(Level.SEVERE, null,
	// ex);
	// }
	//
	// PlayMove retour = thread.getCurrentBest();
	// thread.interrupt();
	// return retour;
	// }

	/**
	 * The computer plays.
	 */
	private void coupIA() {
		goPan.desactivateMouse();
		Place place = cpu.getPlace(goban);
		goban.play(place.getAbs(), place.getOrd(), iaColor);
		goPan.repaint();
		goPan.activateMouse();
	}

	/**
	 * Do a move (and display it)
	 * 
	 * @param abs
	 *            : line of the move
	 * @param ord
	 *            : column of the move
	 */
	public void appliqueCoup(int ord, int abs) {
		if (goban.play(abs, ord, actualColor)) {
			goPan.paintImmediately(0, 0, goPan.getWidth(), goPan.getHeight());
			if (this.onePlayerGame) {
				coupIA();
			} else {
				this.switchColor();
			} // if
		}
	}

	/**
	 * Test an endgame
	 * 
	 * @return if it is the end of the game
	 */
	public boolean gameOver() {
		return goban.gameOver();
	}

	/**
	 * Accessor : game
	 * 
	 * @return the current game
	 */
	final public Goban getGame() {
		return goban;
	}

	/**
	 * Open a window endgame to announce the winner of the game.
	 */
	public void dialogueFinJeu() {
		this.frame.finJeu();
	}
}