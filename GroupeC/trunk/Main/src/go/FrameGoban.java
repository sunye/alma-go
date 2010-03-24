package go;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.net.URI;
import java.util.Random;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.SwingUtilities;



/**
 * Classe FrameGoban
 * @author Fred Dumont
 *
 */
public class FrameGoban extends JFrame implements Constants, ActionListener {

	
	/**
	 * Constructeur de l'application
	 */
	public FrameGoban() {

	}
	
	/**
	 * Methode d'initialisation de l'interface
	 */
	private void initGUI() {
		
	}
	
	/**
	   *  Initialisation des diffrentes images
	   *    - fond du goban
	   *    - pion noir
	   *    - pion blanc
	   */
	  protected void initialiserImage()
	  {
		
	  }


	  /**
	   *  Initialisation du Jeu et du JPanel Goban
	   */
	  protected void initialiserComposant()
	  {
	    
	  }

	  /**
	   * Retailler les pierres si la taille de la fenetre est modifiee
	   * @param stone_size
	   */
	  void ChangeStoneSize( int stone_size )
	  {

		
	  }

	  /**
	   * Image pour les pierres
	   * @param g
	   * @param color
	   * @param x
	   * @param y
	   * @param size
	   * @param observer
	   */
	  static public void drawStoneImage( Graphics g, int color,
	            int x, int y, int size,  ImageObserver  observer )
	  {
		
	  }


	  /**
	   * Dessiner le fond
	   * @param g
	   * @param size
	   * @param observer
	   */
	  static public void drawBackground( Graphics g, Dimension size, ImageObserver observer )
	  {
	  }

	/**
	 * Action  realiser pour le menu
	 * @param action
	 */
	public void actionPerformed(ActionEvent action) {

				
	}
	
	
	/**
	 * Message affiché dans la barre de status
	 * @param x
	 * @param y
	 */
	public void setStatusMessage(int x, int y){
		
	}
	
	
	/**
	 * Message affiché dans la barre de status
	 * @param x
	 * @param y
	 */
	public void resetProgress(){

	}
	
	
	/**
	 * Message affiché dans la barre de status
	 * @param x
	 * @param y
	 */
	public void modProgress(int val){

	}
	
	
	public void generate(int nb_stones, int depth){
}
