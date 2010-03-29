package go;
  
import java.awt.*; 
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Iterator;
import java.util.LinkedHashSet;

import javax.swing.JPanel;


/**
 * Classe Goban
 * @author Fred Dumont
 *
 */
public class Goban extends JPanel implements Constants, MouseListener {


 
  
	/**
	 * Constructeur Goban
	 */
	public Goban () {
  
	}

	/**
	 * Methode init()
	 * @param param_atariGo
	 * @param param_game
	 * @param param_size
	 */
	public void init(FrameGoban param_atariGo, Jeu param_game, int param_size ) {
		

	}
  

	/**
	 * Methode reset()
	 */
	public void reset() {
		

	}

	
	/**
	 * Dessiner le Goban, methode appele par le JPanel
	 */
	public void paintComponent(Graphics g) {
		

	}
  
	/**
	 *  Redimensionnement du Goban 
	 */
	public void reSize(  ) {
		
	}
	



	/**
	 * Pour chaque "clic", calcul de la position
	 * @param e
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean mouseDown(MouseEvent e, int x, int y) {
		return true;
		

	}

	/**
	 * Action de mouvement
	 * @param x
	 * @param y
	 */
	public void actionMove( int x, int y ) {

	}

	/**
	* Dessiner l'ensemble des pierres du Goban
	* @param g
	*/
	public void drawStones( Graphics g ) {

	}


	/**
	 * Dessiner une pierre
	 * @param g
	 * @param m
	 */
	public void drawStone( Graphics g, Mouvement m, int x, int y ) {

	}

  
	/**
	* Dessiner les lignes du tableau
	* @param g
	*/
	public void drawLines(Graphics g) {

	}
  
	/**
	 * Recuperer poition X
	 * @param x
	 * @return
	 */
	public int posx( int x ) {
		return 1;

	}

	/**
	 * Recuperer position Y
	 * @param y
	 * @return
	 */
	public int posy( int y ) {
		return 1;

	}

	public void mouseEntered(MouseEvent e) {
	}
	
	public void mouseExited(MouseEvent e) {
	}
	
	public void mousePressed(MouseEvent e) {
	}
	
	public void mouseReleased(MouseEvent e) {
	}

	/**
	 * Action  realiser lors d'un "clic"
	 * @param e 
	 */
	public void mouseClicked(MouseEvent e) {

	}

}
