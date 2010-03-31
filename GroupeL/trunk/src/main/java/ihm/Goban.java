package ihm;

import ia.AlphaBeta;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import jeu.Coordonnees;
import jeu.Couleur;
import jeu.GobanStructure;

public class Goban extends JPanel{
        
		private static final long serialVersionUID = 1L;
        private ImageIcon plateau;
        private ImageIcon pionB;
        private ImageIcon pionN;
        private ImageIcon vainqueur;
        //tous les points du Goban
        private GobanStructure goban_tab;
        //largeur d'une colonne et hauteur d'une ligne
        private int caseSize;
        
        //bordure de l'image 
        private int bordure;
        private int info;
        
        // jouer avec ou sans IA
        private boolean withAI;
        private AlphaBeta AI;
        
        private boolean partiFini;
        
        //la couleur du joueur qui joue
        Couleur joueur;
        
        public Goban() {
        	super();
                            
            partiFini=false;
                
            /**
	         * Creation de l'image du goban grâce à l'URL donné
	         */
	                
             java.net.URL gobanURL = Fenetre.class.getResource("images/Goban2.png");
             //vérification de l'existence de l'image
             if (gobanURL != null) {
            	 plateau = new ImageIcon(gobanURL);
             }
                
             /* parametre fixer a partir de l'image */
             bordure = 25;
             caseSize = 60;
             info = 100;
             /*-------------------------------------*/
                
             /**
              * Creation de l'image du pion blanc
              */
                
             java.net.URL pionB_URL = Fenetre.class.getResource("images/Pion_blanc2.png");
             //vérification de l'existence de l'image
             if (pionB_URL != null) {
            	 pionB = new ImageIcon(pionB_URL);
             }
             
                
             /**
              * Creation de l'image du pion noir
              */
                
             java.net.URL pionN_URL = Fenetre.class.getResource("images/Pion_noir2.png");
             //vérification de l'existence de l'image
             if (pionN_URL != null) {
            	 pionN = new ImageIcon(pionN_URL);
             }
                
             java.net.URL vainqueur_URL = Fenetre.class.getResource("images/Vainqueur.png");
             //vérification de l'existence de l'image
             if (vainqueur_URL != null) {
            	 vainqueur = new ImageIcon(vainqueur_URL);
             }
             
                
             /**
              * Creation de la matrice de pions
              */
                
             goban_tab = new GobanStructure();                
                
             addMouseListener(new MouseAdapter() {
            	 public void mouseClicked(MouseEvent e) {
            		 processMouseClicked(e);
            	 }
             });
            repaint();
                
            /* on initialise le premier a blanc */
            joueur=Couleur.blanc;
            withAI=true;
            AlphaBeta AI = new AlphaBeta(goban_tab, joueur);    
        }
        
        /**
         * fonction lancer lors d'un clic sur le goban 
         * @param e
         */
        private void processMouseClicked(MouseEvent e) {
            int x, y;

            if(!partiFini){
            
	            x = e.getX();
	            y = e.getY();
	            
	            /* le clic est t'il sur la grille de jeux */
	            if(x>bordure && x< plateau.getIconWidth()-bordure && y>bordure && y< plateau.getIconWidth()-bordure){      
		            
		            // creation des coordonnées du coup par rapport a la position du clic
		            Coordonnees coup = new Coordonnees(((x-bordure)/caseSize)+1, ((y-bordure)/caseSize)+1);
		            
		            // verification de validité du coup
		            if(goban_tab.coupValide(coup, joueur)){
			            // on ajoute la piece
		            	goban_tab.ajoutPiece(coup, joueur);
		            	// on redessine le plateau
			            repaint();
			            // on signifie que le coup est jouer
			            coupFini();
			            
			            // si l'IA doit jouer
			            if(withAI){
			            	
			            	// on recherche le meilleur coup
			            	goban_tab.ajoutPiece(AI.createTree(),joueur);
			            	
			            	// on signifie que le coup est jouer
			            	coupFini();
			            }		            
			            
		            }
	            }
            }else{
            	partiFini = false;
           		resetGame(withAI);
            }
        }
        
        /**
         * fonction appeler pour completer un coup
         */
        private void coupFini() {
        	// verification de la fin de parti
        	if(goban_tab.fin(joueur)){
        		// si un joueur a gagner on termine la parti
        		partiFini = true;
            }else{   
            	// sinon on changhe de joueur
            	joueur=joueur.invCouleur();
            }	
		}

        /**
         * fontion d'affichage du goban
         */
		public void paintComponent(Graphics g){
	        super.paintComponent(g);
	        //Affichage du goban
	        g.drawImage(plateau.getImage(),0,0,this);
	        
	        //Affichage de la couleur du joueur qui doit joueur
		    if(partiFini){
		    	g.drawImage(vainqueur.getImage(),plateau.getIconWidth()-vainqueur.getIconWidth(),plateau.getIconHeight()-vainqueur.getIconHeight(),this);
		    }
	        if(joueur == Couleur.blanc){
	        	g.drawImage(pionB.getImage(),plateau.getIconWidth()-bordure*3,info,this);
		    }else if(joueur == Couleur.noir){
		        g.drawImage(pionN.getImage(),plateau.getIconWidth()-bordure*3,info,this);
		    }
	        
	        //Affichage de tout les pions en parcourant la matrice
	        for(int i=1; i<=9; i++){
	                for(int j=1;j<=9; j++){
	                		if(goban_tab.getPlateau()[i][j] != null){
	                			if(goban_tab.getPlateau()[i][j].getCouleur() == Couleur.noir){
	                				g.drawImage(pionN.getImage(),((i-1)*caseSize)+bordure,((j-1)*caseSize)+bordure,this);
	                			}else{
	                				 g.drawImage(pionB.getImage(),((i-1)*caseSize)+bordure,((j-1)*caseSize)+bordure,this);
	                			}
	                		}
	                }
	        }
        }
        
		/**
		 * 
		 */
        public void update(Graphics g) {
             paintComponent(g);
        }

        /**
         * relance une nouvelle avec ou sans IA 
         * @param type
         */
		public void resetGame(boolean type) {
			goban_tab = new GobanStructure();
			joueur = Couleur.blanc;
			withAI=type;
		}
}
