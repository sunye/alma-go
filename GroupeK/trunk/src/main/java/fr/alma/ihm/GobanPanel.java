package fr.alma.ihm;

import java.awt.Graphics;


import javax.swing.ImageIcon;
import javax.swing.JPanel;

import fr.alma.controler.Controler;
import fr.alma.modele.Pion;

/**
 * 
 * @author Manoël Fortun et Anthony "Bambinôme" Caillaud 
 * The class that represent the panel of the board
 */
public class GobanPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * The image of the board
	 */
	private ImageIcon goban;

	/**
	 * the image for the black token
	 */
	private ImageIcon pionB;

	/**
	 * the image for the white token
	 */
	private ImageIcon pionN;
	// tous les points du Goban

	// largeur d'une colonne et hauteur d'une ligne
	/**
	 * col size of the panel
	 */
	private int colSize;

	/**
	 * row size of the panel
	 */
	private int rowSize;
	// coordonnées propres au Goban

	/**
	 * The controlerof the game
	 */
	private Controler controler;

	

	
	
	/**
	 * Contructor that initialise the panel
	 * 
	 * @param contrler
	 *            the controler of the application
	 */
	public GobanPanel(Controler contrler) {
		super();
		controler = contrler;
		/**
		 * Creation de l'image du goban grâce à l'URL donné
		 */

		java.net.URL gobanURL = Fenetre.class.getResource("images/Goban.png");
		// vérification de l'existence de l'image
		if (gobanURL != null) {
			goban = new ImageIcon(gobanURL);
		}

		colSize = goban.getIconWidth() / 9;
		rowSize = goban.getIconHeight() / 9;

		/**
		 * Creation de l'image du pion blanc
		 */

		java.net.URL pionB_URL = Fenetre.class
				.getResource("images/Pion_blanc.png");
		// vérification de l'existence de l'image
		if (pionB_URL != null) {
			pionB = new ImageIcon(pionB_URL);
		}

		/**
		 * Creation de l'image du pion noir
		 */

		java.net.URL pionN_URL = Fenetre.class
				.getResource("images/Pion_noir.png");
		// vérification de l'existence de l'image
		if (pionN_URL != null) {
			pionN = new ImageIcon(pionN_URL);
		}

		/**
		 * Creation de la matrice de pions
		 */

		addMouseListener(contrler.getFactory().clicBoardListener());
		repaint();
	}

	/**
	 * repaint the panel used by the hierarchy
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// Affichage du goban
		g.drawImage(goban.getImage(), 0, 0, this);
		// Affichage de tout les pions en parcourant la matrice
		int tailleMatrice = controler.tailleMatrice();
		Pion[][] matrice = controler.getMatricePlateau();
		for (int i = 0; i < tailleMatrice; i++) {
			for (int j = 0; j < tailleMatrice; j++) {
				switch (matrice[i][j].getCouleur()) {
				case NOIR:
					g.drawImage(pionN.getImage(), (i * colSize), (j * rowSize),
							this);
					break;
				case BLANC:
					g.drawImage(pionB.getImage(), (i * colSize), (j * rowSize),
							this);
					break;
				case EMPTY:
					break;
				}
			}
		}

	}

	/**
	 * repaint the panel used by the hierarchy
	 */
	public void update(Graphics g) {
		paintComponent(g);
	}

	/**
	 * get the col Size
	 * 
	 * @return the col size
	 */
	public int getColSize() {
		return colSize;
	}

	/**
	 * get the row Size
	 * 
	 * @return the row size
	 */
	public int getRowSize() {
		return rowSize;
	}
	


}
