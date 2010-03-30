package ihm;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import jeu.Coordonnees;
import jeu.Couleur;
import jeu.GobanStructure;

public class Goban extends JPanel{
        
        /**
         * 
         */
        private static final long serialVersionUID = 1L;
        private ImageIcon plateau;
        private ImageIcon pionB;
        private ImageIcon pionN;
        //tous les points du Goban
        private GobanStructure goban_tab;
        //largeur d'une colonne et hauteur d'une ligne
        private int caseSize;
        //coordonnées propres au Goban
        private int gobanX;
        private int gobanY;
        
        private int bordure;
        
        private boolean IA;
        
        //la couleur du joueur qui joue
        Couleur joueur;
        
        public Goban() {
                super();
                
                IA=true;
                
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
                
                joueur=Couleur.blanc;
        }
        
        private void processMouseClicked(MouseEvent e) {
            int x, y;

            x = e.getX();
            y = e.getY();
            
            if(x>bordure && x< plateau.getIconWidth()-bordure && y>bordure && y< plateau.getIconWidth()-bordure){
	            
            	gobanX=((x-bordure)/caseSize)+1;
	            gobanY=((y-bordure)/caseSize)+1;
	            //System.out.println("gobanX:"+gobanX+"gobanY:"+gobanY);
	            //System.out.println("drawImage- x:"+x+"y:"+y);
	            
	            Coordonnees coup = new Coordonnees(gobanX, gobanY);
	            
	            if(goban_tab.coupValide(coup, joueur)){
		            goban_tab.ajoutPiece(coup, joueur);
		            repaint();    
		            
		            coupFini();
		            
		            if(IA){

		            	// coup de l'IA
		            	
		            	coupFini();
		            }		            
		            
	            }
            }
        }
        
        private void coupFini() {
        	if(goban_tab.fin(joueur)){
            	System.out.println("le joueur "+ joueur.toString()+" a gagner");
            	resetGame(IA);
            }else{           
            	joueur=joueur.invCouleur();
            }	
		}

		public void paintComponent(Graphics g){
        super.paintComponent(g);
        //Affichage du goban
        g.drawImage(plateau.getImage(),0,0,this);
        //Affichage de tout les pions en parcourant la matrice
        for(int i=1; i<=9; i++){
                for(int j=1;j<=9; j++){
                        //System.out.println(goban_tab.getGoban()[i][j].getCouleur());
                		if(goban_tab.getPlateau()[i][j] != null){
                			if(goban_tab.getPlateau()[i][j].getCouleur() == Couleur.noir){
                				g.drawImage(pionN.getImage(),((i-1)*caseSize)+bordure,((j-1)*caseSize)+bordure,this);
                			}else{
                				 g.drawImage(pionB.getImage(),((i-1)*caseSize)+bordure,((j-1)*caseSize)+bordure,this);
                			}
                		}
                }
        }
        //System.out.println("");
        }
        
        public void update(Graphics g) {
             paintComponent(g);
        }

		public void resetGame(boolean type) {
			goban_tab = new GobanStructure();
			joueur = Couleur.blanc;
			IA=type;
		}
}
