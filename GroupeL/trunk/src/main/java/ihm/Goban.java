package ihm;

import game.Coordinates;
import game.Color;
import game.GobanStructure;
import ia.AiThread;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class Goban extends JPanel{
        
		// size of the different element in the windows
		private static final int DIFF_BASE = 3;
		private static final int BORDURE = 25;
		private static final int CASE_SIZE = 60;
		private static final int INFO = 150;
		
		private static final long serialVersionUID = 1L;
		// link to the different images
        private ImageIcon gobanImage;
        private ImageIcon whitePawnImage;
        private ImageIcon blackPawnImage;
        private ImageIcon cross;
        private ImageIcon winnerImage;
        // game
        private GobanStructure goban_tab;
        
        // for play with AI
        private boolean withAI;
        
        // current depth of the decision's tree, it's the AI's difficulty 
        private int currentDepth;
        
        // true when the game is over
        private boolean endGame;
        
        // coordinate of the last move of the game 
        private Coordinates lastMove;
        
        // time for IA's play 
    	private Integer playTimeIA = 10;

        
        // window of the game
        private Fenetre window;
       
        // current player's color
        private Color player;
        
        
        // constructor
        public Goban(Fenetre f) {
        	super();
        	
        	window = f;
        	
            endGame=false;
            
             /* creation of the different image of the panel */
	                
             java.net.URL gobanURL = Fenetre.class.getResource("images/Goban2.png");
             // verification of the image's existence
             if (gobanURL != null) {
            	 gobanImage = new ImageIcon(gobanURL);
             }
        
             java.net.URL pionB_URL = Fenetre.class.getResource("images/blanc.png");
             // verification of the image's existence
             if (pionB_URL != null) {
            	 whitePawnImage = new ImageIcon(pionB_URL);
             }

             java.net.URL pionN_URL = Fenetre.class.getResource("images/noir.png");
             // verification of the image's existence
             if (pionN_URL != null) {
            	 blackPawnImage = new ImageIcon(pionN_URL);
             }
             
             java.net.URL vainqueur_URL = Fenetre.class.getResource("images/Vainqueur.png");
             // verification of the image's existence
             if (vainqueur_URL != null) {
            	 winnerImage = new ImageIcon(vainqueur_URL);
             }
             
             java.net.URL mort_URL = Fenetre.class.getResource("images/mort.png");
             // verification of the image's existence
             if (mort_URL != null) {
            	 cross = new ImageIcon(mort_URL);
             }  
             

             // game creation                
             goban_tab = new GobanStructure();                
             
             // launch click listener
             addMouseListener(new MouseAdapter() {
            	 public void mouseClicked(MouseEvent e) {
            		 processMouseClicked(e);
            	 }
             });
             
            // paint element
            window.repaint();
                
            /* initial value */
            player=Color.BLACK;
            withAI=true;
            currentDepth = DIFF_BASE;
        }
        
        /**
         * click function 
         * @param e
         */
        private void processMouseClicked(MouseEvent e) {
        	
        	int x, y;
            
            if(!endGame){
            
	            x = e.getX();
	            y = e.getY();
	            
	            // verification of the click' position 
	            if(x>BORDURE && x< gobanImage.getIconWidth()-BORDURE-INFO && y>BORDURE && y< gobanImage.getIconWidth()-BORDURE){      
		            
		            // coordinate creation
		            Coordinates coup = new Coordinates(((x-BORDURE)/CASE_SIZE)+1, ((y-BORDURE)/CASE_SIZE)+1);
		            
		            // validity move verification
		            if(goban_tab.moveValid(coup, player)){
			            // add pawn
		            	goban_tab.addPawn(coup, player);
		            	lastMove = coup;
			            // the move is over
			            moveComplet();
			            
			            // AI's turn
			            if(withAI && !endGame){
			            	playAI();
			            	moveComplet();
			            }		              
		            }
	            }
            }else{
            	endGame = false;
           		resetGame(withAI);
            }
        }
        
        /**
         * function call when it's AI's turn 
         */
        private void playAI(){
        	
        	Coordinates coup = new Coordinates();
        	
        	if(goban_tab.getGroups(player).isEmpty()){
        		if(lastMove.getX() <= goban_tab.getSize()/2){
        			if(lastMove.getY() <= goban_tab.getSize()/2){
        				coup = new Coordinates(6, 6);
        			}else{
        				coup = new Coordinates(6, 4);
        			}
        		}else{
        			if(lastMove.getY() <= goban_tab.getSize()/2){
        				coup = new Coordinates(4, 6);
        			}else{
        				coup = new Coordinates(4, 4);
        			}
        		}		            		
        	}else{			            		

            		AiThread aiThread= new AiThread(goban_tab, player,currentDepth,playTimeIA);
            		
            		coup = aiThread.createTree(goban_tab, player);

        	}     
         	// add pawn
        	goban_tab.addPawn(coup,player);
        }
        
        /**
         * function call when the move is complete
         */
        private void moveComplet() {
        	
        	// end game verification
        	if(goban_tab.isWinner(player)){
        		endGame = true;
            }else{   
            	// if it's not the end game change player's color
            	player=player.invColor();
            }
        	
        	// repaint goban
        	window.repaint();        
        	
		}

        /**
         * function of paint panel
         */
		public void paintComponent(Graphics g){
	        super.paintComponent(g);
	    	        
	        // goban's paint
	        g.drawImage(gobanImage.getImage(),0,0,this);
	    
	        
	        // winnner's paint
		    if(endGame){
		    	g.drawImage(winnerImage.getImage(),gobanImage.getIconWidth()-winnerImage.getIconWidth(),gobanImage.getIconHeight()-winnerImage.getIconHeight()-BORDURE*3,this);
		    }

		    // player color's paint
	        if(player == Color.WHITE){
	        	g.drawImage(whitePawnImage.getImage(),gobanImage.getIconWidth()-CASE_SIZE-BORDURE,BORDURE*3,this);
		    }else if(player == Color.BLACK){
		        g.drawImage(blackPawnImage.getImage(),gobanImage.getIconWidth()-CASE_SIZE-BORDURE,BORDURE*3,this);
		    }
		    	        
	        // game's paint
	        for(int i=1; i<=goban_tab.getSize(); i++){
	            for(int j=1;j<=goban_tab.getSize(); j++){
	            	if(goban_tab.getGoban()[i][j] != null){
	            		if(goban_tab.getGoban()[i][j].getColor() == Color.BLACK){
	            			g.drawImage(blackPawnImage.getImage(),((i-1)*CASE_SIZE)+BORDURE,((j-1)*CASE_SIZE)+BORDURE,this);
	              		}else{
	            			g.drawImage(whitePawnImage.getImage(),((i-1)*CASE_SIZE)+BORDURE,((j-1)*CASE_SIZE)+BORDURE,this);
	                	}
	            		
	            		if((goban_tab.getGoban()[i][j].getFreedoms() == 0) && (goban_tab.getGoban()[i][j].getColor()==player.invColor())){
	            			g.drawImage(cross.getImage(),((i-1)*CASE_SIZE)+BORDURE,((j-1)*CASE_SIZE)+BORDURE,this);
	            		}
	                }
	            }
	        }	     	        
		}
        
		/**
		 */
        public void update(Graphics g) {
             paintComponent(g);
        }

        /**
         * reset game
         * @param type
         */
		public void resetGame(boolean type) {
			goban_tab = new GobanStructure();
			player = Color.BLACK;
			withAI=type;
			endGame = false;
			window.repaint();
		}

		/* getter and setter */
		
		public GobanStructure getGoban_tab() {
			return goban_tab;
		}

		public void resetIA(Integer niv) {
			currentDepth = niv;
		}

		public Integer getPlayTimeIA() {
			return playTimeIA;
		}

		public void setPlayTimeIA(Integer playTimeIA) {
			this.playTimeIA = playTimeIA;
		}
		
		
}
