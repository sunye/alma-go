package fr.alma.ihm;

import java.awt.Graphics;


import javax.swing.ImageIcon;
import javax.swing.JPanel;

import fr.alma.controler.Controler;
import fr.alma.modele.Stone;
/*$Author$ 
 * $Date$ 
 * $Revision$ 
  * 
 *Copyright (C) 2010  Fortun Mano�l & Caillaud Anthony
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
 * The class that represent the panel of the board
 * @author Mano�l Fortun et Anthony "Bambin�me" Caillaud 
 * 
 */
public class GobanPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * The image of the board
	 */
	private ImageIcon goban;

	/**
	 * the image for the white token
	 */
	private ImageIcon whiteStoneImg;

	/**
	 * the image for the black token
	 */
	private ImageIcon blackStoneImg;
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
	// coordonn�es propres au Goban

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
		 * Creation de l'image du goban gr�ce � l'URL donn�
		 */

		java.net.URL gobanURL = MainFrame.class.getResource("images/Goban.png");
		// v�rification de l'existence de l'image
		if (gobanURL != null) {
			goban = new ImageIcon(gobanURL);
		}

		colSize = goban.getIconWidth() / 9;
		rowSize = goban.getIconHeight() / 9;

		/**
		 * Creation de l'image du pion blanc
		 */

		java.net.URL pionB_URL = MainFrame.class
				.getResource("images/Pion_blanc.png");
		// v�rification de l'existence de l'image
		if (pionB_URL != null) {
			whiteStoneImg = new ImageIcon(pionB_URL);
		}

		/**
		 * Creation de l'image du pion noir
		 */

		java.net.URL pionN_URL = MainFrame.class
				.getResource("images/Pion_noir.png");
		// v�rification de l'existence de l'image
		if (pionN_URL != null) {
			blackStoneImg = new ImageIcon(pionN_URL);
		}

		/**
		 * Creation de la matrice de pions
		 */

		addMouseListener(contrler.getFactory().getClicBoardListener());
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
		int tailleMatrice = controler.getMatrixSize();
		Stone[][] matrice = controler.getMatrix();
		for (int i = 0; i < tailleMatrice; i++) {
			for (int j = 0; j < tailleMatrice; j++) {
				switch (matrice[i][j].getColor()) {
				case BLACK:
					g.drawImage(blackStoneImg.getImage(), (i * colSize), (j * rowSize),
							this);
					break;
				case WHITE:
					g.drawImage(whiteStoneImg.getImage(), (i * colSize), (j * rowSize),
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
