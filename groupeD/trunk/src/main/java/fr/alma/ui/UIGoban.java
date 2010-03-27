package fr.alma.ui;

import fr.alma.atarigo.AtariGo;
import fr.alma.atarigo.Stone;
import fr.alma.atarigo.Position;
import fr.alma.ia.AlphaBeta;
import fr.alma.ia.Tree;
import fr.alma.ia.MinMax;
import fr.alma.ia.ValuedGoban;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * UIGoban.java is the graphic representation of the board game of Atarigo
 * @author vincent
 *
 */
public class UIGoban extends JPanel implements MouseListener,MouseMotionListener,ActionListener,Runnable{

	
	private static final long serialVersionUID = 1L;
	private MyApplication myApplication;
	private AtariGo atariGo;
	// variables pour le plateau ui
	int currentStoneX;
	int currentStoneY;
	// variables pour le thread de jeu
	public Thread tempo;
	private int time=0;
	boolean running = true;
	public boolean stop=false;
	public boolean pause=false;
	// variables pour l'ia
	Position bestMove;
	int pos = 0;
	
	/**
	 * logic constructor
	 * @param myApp attached application
	 */
	public UIGoban(MyApplication myApp){
		this.myApplication=myApp;
		this.atariGo = myApplication.atarigo;
		addMouseListener(this);
		addMouseMotionListener(this);
		tempo = new Thread(this);
	}

	/**
	 * drawing of the board
	 */
	public void paintComponent(Graphics g){
		if(atariGo!=null){
			//dessin du plateau
			Color background = new Color(128,128,128);
			Color line = new Color(0,0,0);
			g.setColor(background);
			g.fillRect(0, 0, 360, 360);
			g.setColor(line);
			
			for(int i=0;i<9;i++){
				g.drawLine(20, (40*i)+20, 340, (40*i)+20);
				g.drawLine((40*i)+20, 20, (40*i)+20,340);
			}
			//fin dessin plateau
			
			//dessin des pions joués
			for(int i=0;i<9;i++){
				for(int j=0;j<9;j++){
				//g.drawOval((40/4)+(40*i)-(1*8),(40/4)-(1*8),(40/2)+(2*8),(40/2)+(2*8));
					switch (atariGo.goban.matrice[i][j]) {
				    	case BLACK:
				    		g.setColor(Color.BLACK);
				    		g.fillOval((40/4)+(40*i)-(1*8),(40/4)+(40*j)-(1*8),(40/2)+(2*8),(40/2)+(2*8));
				    		break;
				    	case WHITE:
				    		g.setColor(Color.WHITE);
				    		g.fillOval((40/4)+(40*i)-(1*8),(40/4)+(40*j)-(1*8),(40/2)+(2*8),(40/2)+(2*8));
				    		break;
				    	case EMPTY:break;
				    	default:break;
					}
				}
			}
			//fin dessin des pions joués
			
			//dessin du pion à jouer (depend du joueur en cours)
			if(!atariGo.isOver()){
				g.setColor(Color.BLACK);
				g.drawOval((40/4)+(40*currentStoneX)-(1*8),(40/4)+(40*currentStoneY)-(1*8),(40/2)+(2*8),(40/2)+(2*8));
			}
		}
	}
	
	/**
	 * initialisation of the board
	 */
	public void newGame(){
		//ici on reinit...
		this.atariGo = myApplication.atarigo;
		time=0;
		repaint();
	}
	
	
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub	

	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * define the behavior of the mouse click on the board game.
	 * 
	 */
	public void mouseReleased(MouseEvent e) {
		if (!atariGo.isOver()) {
			if(atariGo.currentPlayer.isHuman()){
				// TODO Auto-generated method stub
				int x=e.getX();
				int y=e.getY();
				x=(x)/40;
				y=(y)/40;
				//System.out.println("clic sur la case"+"["+x+","+y+"]");
				//ici on joue le coup  et on test avant pr savoir etat du jeu en cours...
				switch (atariGo.playMove(atariGo.currentPlayer.color, new Position(x,y))) {
			    case WIN:
			    repaint();
			    atariGo.shutDown();
			    myApplication.message.showMessageDialog(null, "Le joueur "+atariGo.currentPlayer.color+" a gagné !", "Information", JOptionPane.INFORMATION_MESSAGE);
				//sortie.println(plateau.toString());
				//sortie.println("=> Vous avez gagne"); 
				//scanner.close();
				return;
			    case NEUTRAL:
				//nombreCoups --;
			    atariGo.currentPlayer = atariGo.currentPlayer == atariGo.player2 ? atariGo.player1 : atariGo.player2;
				System.out.println("onpasse au joueur"+atariGo.currentPlayer.toString());
			    break;
			    default:
				//sortie.println("=> Erreur : position invalide");
				break;
			    }
				//TODO ajouter une actualisation du panneau info...
				repaint();
			}
		}
	}

	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
/**
 * define the behavior of the mouse move on the board. 
 */
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		if (!atariGo.isOver()) {
			currentStoneX=e.getX()/40;
			currentStoneY=e.getY()/40;
			//System.out.println("clic sur la case"+"["+x+","+y+"]");
			repaint();
		}
	}
	
/**
 * Game loop. need to be removed...
 */
	public void run() {
		//boucle de jeu
		pause=false;
		System.out.println("tempo start");
		while(true){
			//si décommenté boucle marche...prob EDT ?
			//System.out.println("true");
			synchronized(this){
				while(!atariGo.isOver()){
					//boucles des joueurs...
					//test
					time++;
					//System.out.println("ok");
					if(!atariGo.currentPlayer.isHuman()){
						System.out.println("dns la bcle");
						stop=true;
							Tree jeu = new Tree(atariGo.goban);
							ValuedGoban plv = new ValuedGoban(0);
							AlphaBeta.init(3);
							
							plv = AlphaBeta.value(0, jeu, 0,atariGo.currentPlayer.color);
							
							atariGo.playMove(atariGo.currentPlayer.color, atariGo.goban.getDifference(plv.goban_));
							
							System.out.println("--------> nombre de noeuds parcourus = "+AlphaBeta.totalNodes);
						//on sort de la boucle on joue le coup
						/*switch (atariGo.jouerCoup(atariGo.joueurEnCours.couleur, meilleurCoup)) {
					    case GAGNANT:
					    //fini = true;
						//sortie.println(plateau.toString());
						//sortie.println("=> Vous avez gagne"); 
						//scanner.close();
						return;
					    case NEUTRE:
						//nombreCoups --;
					    atariGo.joueurEnCours = atariGo.joueurEnCours == atariGo.joueur2 ? atariGo.joueur1 : atariGo.joueur2;
						System.out.println("onpasse au joueur"+atariGo.joueurEnCours.toString());
					    break;
					    default:
						//sortie.println("=> Erreur : position invalide");
						break;
					    }*/
						repaint();
						
						try{
							Thread.sleep(1000);
						}catch(InterruptedException e){}
						
						//aprs avoir jouer on passe a l'autre joueur
						atariGo.currentPlayer = atariGo.currentPlayer == atariGo.player2 ? atariGo.player1 : atariGo.player2;
						//System.out.println("on passe au joueur "+atariGo.joueurEnCours.toString());
					}
				}
			}
		}
	}
	
	
}