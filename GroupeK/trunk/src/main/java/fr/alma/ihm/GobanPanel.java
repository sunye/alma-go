package fr.alma.ihm;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class GobanPanel extends JPanel{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private ImageIcon goban;
	private ImageIcon pionB;
	private ImageIcon pionN;
	//tous les points du Goban
	private GoBan goban_tab;
	//largeur d'une colonne et hauteur d'une ligne
	private int colSize;
	private int rowSize;
	//coordonnées propres au Goban
	private int gobanX;
	private int gobanY;

	public GobanPanel() {
		super();

		/**
         * Creation de l'image du goban grâce à l'URL donné
         */

		java.net.URL gobanURL = Fenetre.class.getResource("images/Goban.png");
		//vérification de l'existence de l'image
		if (gobanURL != null) {
		    goban = new ImageIcon(gobanURL);
		}

		colSize=goban.getIconWidth()/9;
		rowSize=goban.getIconHeight()/9;

		/**
         * Creation de l'image du pion blanc
         */

		java.net.URL pionB_URL = Fenetre.class.getResource("images/Pion_blanc.png");
		//vérification de l'existence de l'image
		if (pionB_URL != null) {
		    pionB = new ImageIcon(pionB_URL);
		}

		/**
         * Creation de l'image du pion noir
         */

		java.net.URL pionN_URL = Fenetre.class.getResource("images/Pion_noir.png");
		//vérification de l'existence de l'image
		if (pionN_URL != null) {
		    pionN = new ImageIcon(pionN_URL);
		}


		/**
         * Creation de la matrice de pions
         */

		goban_tab = new GoBan();
		goban_tab.init();


		addMouseListener(new MouseAdapter() {
	        public void mouseClicked(MouseEvent e) {
		          processMouseClicked(e);
		        }
		      });
		repaint();


	}

	private void processMouseClicked(MouseEvent e) {
	    int x, y;

	    x = e.getX();
	    y = e.getY();

	    gobanX=x/colSize;
	    gobanY=y/rowSize;
	    //System.out.println("gobanX:"+gobanX+"gobanY:"+gobanY);
	    //System.out.println("drawImage- x:"+x+"y:"+y);
	    goban_tab.ajouterPion(gobanX, gobanY);
	    repaint();
	}

	public void paintComponent(Graphics g){
        super.paintComponent(g);
        //Affichage du goban
        g.drawImage(goban.getImage(),0,0,this);
        //Affichage de tout les pions en parcourant la matrice
        for(int i=0; i<9; i++){
                for(int j=0;j<9; j++){
                	//System.out.println(goban_tab.getGoban()[i][j].getCouleur());
                        switch(goban_tab.getGoban()[i][j].getCouleur()){
                                case NOIR:
                                        g.drawImage(pionN.getImage(),(i*colSize),(j*rowSize),this);
                                        break;
                                case BLANC:
                                        g.drawImage(pionB.getImage(),(i*colSize),(j*rowSize),this);
                                        break;
                                case EMPTY:
                    					break;
                        }
                }
        }
        //System.out.println("");
	}

	public void update(Graphics g) {
	     paintComponent(g);
	}
}




