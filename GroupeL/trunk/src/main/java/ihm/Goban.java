package ihm;

import game.Coordinates;
import game.Color;
import game.GobanStructure;
import ia.AiThread;
import ia.AlphaBeta;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;


public class Goban extends JPanel{
        
		private static final int DIFF_BASE = 3;
	
		private static final long serialVersionUID = 1L;
        private ImageIcon plateau;
        private ImageIcon pionB;
        private ImageIcon pionN;
        private ImageIcon mort;
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
        
        private int currentDepth;
        
        private boolean partiFini;
        
        private boolean premCoup;
        
        private boolean moveForced;
        private Coordinates move;
        
        Future<Coordinates> future;
        
       
        //la couleur du joueur qui joue
        Color joueur;
        
        public Goban() {
        	super();
                            
            partiFini=false;
            premCoup=true;
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
                
             java.net.URL pionB_URL = Fenetre.class.getResource("images/blanc.png");
             //vérification de l'existence de l'image
             if (pionB_URL != null) {
            	 pionB = new ImageIcon(pionB_URL);
             }
             
                
             /**
              * Creation de l'image du pion noir
              */
                
             java.net.URL pionN_URL = Fenetre.class.getResource("images/noir.png");
             //vérification de l'existence de l'image
             if (pionN_URL != null) {
            	 pionN = new ImageIcon(pionN_URL);
             }
                
             /**
              * Creation de l'image de victoire
              */
             
             java.net.URL vainqueur_URL = Fenetre.class.getResource("images/Vainqueur.png");
             //vérification de l'existence de l'image
             if (vainqueur_URL != null) {
            	 vainqueur = new ImageIcon(vainqueur_URL);
             }
             
             java.net.URL mort_URL = Fenetre.class.getResource("images/mort.png");
             //vérification de l'existence de l'image
             if (mort_URL != null) {
            	 mort = new ImageIcon(mort_URL);
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
            joueur=Color.BLACK;
            withAI=true;
            
            //creation de l'IA
            //AI = new AlphaBeta(DIFF_BASE);
            currentDepth = DIFF_BASE;
        }
        
        /**
         * fonction lancer lors d'un clic sur le goban 
         * @param e
         */
        private void processMouseClicked(MouseEvent e) {
            int x, y;
            
            moveForced = false;

            if(!partiFini){
            
	            x = e.getX();
	            y = e.getY();
	            
	            /* le clic est t'il sur la grille de jeux */
	            if(x>bordure && x< plateau.getIconWidth()-bordure && y>bordure && y< plateau.getIconWidth()-bordure){      
		            
		            // creation des coordonnées du coup par rapport a la position du clic
		            Coordinates coup = new Coordinates(((x-bordure)/caseSize)+1, ((y-bordure)/caseSize)+1);
		            
		            // verification de validité du coup
		            if(goban_tab.moveValid(coup, joueur)){
			            // on ajoute la piece
		            	goban_tab.addPawn(coup, joueur);
		            	
			            // on signifie que le coup est joueur
			            coupFini();
			            
			            // si l'IA doit jouer
			            if(withAI && !partiFini){
			            	
			            	if(premCoup){
			            		if(coup.getX() <= goban_tab.getSize()/2){
			            			if(coup.getY() <= goban_tab.getSize()/2){
			            				coup = new Coordinates(7, 7);
			            			}else{
			            				coup = new Coordinates(7, 3);
			            			}
			            		}else{
			            			if(coup.getY() <= goban_tab.getSize()/2){
			            				coup = new Coordinates(3, 7);
			            			}else{
			            				coup = new Coordinates(3, 3);
			            			}
			            		}
			            		premCoup = false;
			            		
			            	}else{
			            		
			            		//Thread t = new Thread() {
			            			
			            			//public void run(){
					            		// on recherche le meilleur coup
					            		ExecutorService pool = Executors.newCachedThreadPool();

					            		Callable<Coordinates> aiThread= new AiThread(goban_tab, joueur,currentDepth);
					            		future = pool.submit(aiThread);
					            		
										while(!future.isDone()){
											if(moveForced){
												future.cancel(true);
												
											}
										}
					            		
					            		try {
					            			if ((!moveForced) && (future.isDone())){
					            				coup = future.get();
					            			} else {
					            				moveForced = false;
					            			}
											
										} catch (InterruptedException e1) {
											e1.printStackTrace();
										} catch (ExecutionException e1) {
											e1.printStackTrace();
										}
			            			//}
			            			
								//};
								//t.start();
			            		
			            		

								
			            	}     
			            	
			            	// on joue le coup
			            	goban_tab.addPawn(coup,joueur);
			            	
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
        	if(goban_tab.isWinner(joueur)){
        		// si un joueur a gagner on termine la parti
        		partiFini = true;
            }else{   
            	// sinon on changhe de joueur
            	joueur=joueur.invColor();
            }
        	// onredessine le plateau
        	repaint();
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
		    	g.drawImage(vainqueur.getImage(),plateau.getIconWidth()-vainqueur.getIconWidth(),plateau.getIconHeight()-vainqueur.getIconHeight()-info,this);
		    }
		    	    
		    // affichage de la couleur du joueur
	        if(joueur == Color.WHITE){
	        	g.drawImage(pionB.getImage(),plateau.getIconWidth()-info,bordure*3,this);
		    }else if(joueur == Color.BLACK){
		        g.drawImage(pionN.getImage(),plateau.getIconWidth()-info,bordure*3,this);
		    }
		    	        
	        //Affichage de tout les pions en parcourant la matrice
	        for(int i=1; i<=goban_tab.getSize(); i++){
	            for(int j=1;j<=goban_tab.getSize(); j++){
	            	if(goban_tab.getGoban()[i][j] != null){
	            		if(goban_tab.getGoban()[i][j].getColor() == Color.BLACK){
	            			g.drawImage(pionN.getImage(),((i-1)*caseSize)+bordure,((j-1)*caseSize)+bordure,this);
	              		}else{
	            			g.drawImage(pionB.getImage(),((i-1)*caseSize)+bordure,((j-1)*caseSize)+bordure,this);
	                	}
	            		
	            		if((goban_tab.getGoban()[i][j].getFreedoms() == 0) && (goban_tab.getGoban()[i][j].getColor()==joueur.invColor())){
	            			g.drawImage(mort.getImage(),((i-1)*caseSize)+bordure,((j-1)*caseSize)+bordure,this);
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
			joueur = Color.BLACK;
			withAI=type;
			premCoup=true;
			partiFini = false;
			repaint();
		}

		public GobanStructure getGoban_tab() {
			return goban_tab;
		}

		public void forceToPlay() {
			if (future.isDone()){
				moveForced = true;
			}
		}
		
		public void resetIA(Integer niv) {
			currentDepth = niv;
		}

}
