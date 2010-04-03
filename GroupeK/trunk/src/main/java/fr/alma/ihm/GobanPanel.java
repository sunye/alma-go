package fr.alma.ihm;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import fr.alma.controler.Controler;
import fr.alma.modele.CouleurPion;
import fr.alma.modele.GameHandler;
import fr.alma.modele.GoBan;
import fr.alma.modele.Pion;

public class GobanPanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ImageIcon goban;
	private ImageIcon pionB;
	private ImageIcon pionN;
	//tous les points du Goban
	
	//largeur d'une colonne et hauteur d'une ligne
	private int colSize;
	private int rowSize;
	//coordonnées propres au Goban
	private int gobanX;
	private int gobanY;
	
	private Controler controler;
	
	
	
	public GobanPanel(Controler contrler) {
		super();
		controler= contrler;
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
	
		
		addMouseListener(contrler.getFactory().clicBoardListener( ));
		repaint();
		
		
	}
	
	public void clicBoard(MouseEvent e) {
	    int x, y;

	    x = e.getX();
	    y = e.getY();

	    gobanX=x/colSize;
	    gobanY=y/rowSize;
	    //System.out.println("gobanX:"+gobanX+"gobanY:"+gobanY);
	    //System.out.println("drawImage- x:"+x+"y:"+y);
	    controler.ajouterPion(gobanX, gobanY);
	    repaint();
	
	}
	
	public void paintComponent(Graphics g){
        super.paintComponent(g);
        //Affichage du goban
        g.drawImage(goban.getImage(),0,0,this);
        //Affichage de tout les pions en parcourant la matrice
        int tailleMatrice= controler.tailleMatrice();
        Pion[][] matrice=controler.getMatricePlateau();
        for(int i=0; i<tailleMatrice; i++){
                for(int j=0;j<tailleMatrice; j++){
                	//System.out.println(goban_tab.getGoban()[i][j].getCouleur());
                        switch(matrice[i][j].getCouleur()){
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
	




	public static void afficheGagnant(CouleurPion coul) {
		
		JOptionPane.showMessageDialog(null,"Les "+coul+"S ont gagnés!");
		
	}

	public int getColSize() {
		return colSize;
	}

	public int getRowSize() {
		return rowSize;
	}
	
	
	
	
}
	
	
	

