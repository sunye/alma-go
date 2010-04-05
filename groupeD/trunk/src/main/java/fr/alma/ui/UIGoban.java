package fr.alma.ui;

import fr.alma.atarigo.AtariGo;
import fr.alma.atarigo.Position;
import fr.alma.atarigo.AtariGo.Move;
import fr.alma.ia.AlphaBeta;
import fr.alma.ia.Evaluation;
import fr.alma.ia.RandomMove;
import fr.alma.ia.Tree;
import fr.alma.ia.ValuedGoban;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
public class UIGoban extends JPanel implements MouseListener,MouseMotionListener,ActionListener{

	
	private static final long serialVersionUID = 1L;
	private MyApplication myApplication;
	private AtariGo atariGo;
	// variables pour le plateau ui
	int currentStoneX;
	int currentStoneY;
	// variables pour le thread de jeu
	private int time=0;
	boolean running = true;
	public boolean stop=false;
	public boolean pause=false;
	public int nbMove;
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
		nbMove=0;
		time=0;
		while(!atariGo.currentPlayer.isHuman() && !atariGo.isOver()){
			//System.out.println(atariGo.goban.toString());
			Tree jeu = new Tree(atariGo.goban);
			ValuedGoban plv = new ValuedGoban(0);
			
			if(nbMove>5){
				AlphaBeta.init(atariGo.currentPlayer.getDifficulty(),atariGo.goban);
				plv = AlphaBeta.value(0, jeu, Evaluation.VERYGOOD,atariGo.currentPlayer.color,atariGo,new Position(0,0));
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				System.out.println("random");
				plv.goban_ = RandomMove.play(atariGo,atariGo.goban,atariGo.currentPlayer.color);
			}
			nbMove++;
			putStone(atariGo.goban.getDifference(plv.goban_).getLine(),atariGo.goban.getDifference(plv.goban_).getColumn());					
			System.out.println("--------> nombre de noeuds parcourus = "+AlphaBeta.totalNodes+" meilleur score = "+plv.evaluation_);
			repaint();
		}		
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
				putStone(x,y);
				//TODO ajouter une actualisation du panneau info...
				repaint();
				if(!atariGo.currentPlayer.isHuman()){
						//removeMouseListener(this);
						Tree jeu = new Tree(atariGo.goban);
						ValuedGoban plv = new ValuedGoban(0);
						if(nbMove>5){
							AlphaBeta.init(atariGo.currentPlayer.getDifficulty(),atariGo.goban);
							plv = AlphaBeta.value(0, jeu, Evaluation.VERYGOOD,atariGo.currentPlayer.color,atariGo,new Position(0,0));
						}else{
							System.out.println("random");
							plv.goban_ = RandomMove.play(atariGo,atariGo.goban,atariGo.currentPlayer.color);
						}
						nbMove++;
						
						
						if(putStone(atariGo.goban.getDifference(plv.goban_).getLine(),atariGo.goban.getDifference(plv.goban_).getColumn())==Move.INVALID){
							System.out.println(atariGo.goban.getDifference(plv.goban_).toString());
							atariGo.shutDown();
						    myApplication.message.showMessageDialog(null, "Le joueur "+atariGo.currentPlayer.color+" abandonne !", "Information", JOptionPane.INFORMATION_MESSAGE);			
						}
						
						
						System.out.println("--------> nombre de noeuds parcourus = "+AlphaBeta.totalNodes+" meilleur score = "+plv.evaluation_);
						addMouseListener(this);
						repaint();
				}
			}
		}
	}
	
	public Move putStone(int x,int y){
		//System.out.println("clic sur la case"+"["+x+","+y+"]");
		//ici on joue le coup  et on test avant pr savoir etat du jeu en cours...
		switch (atariGo.playMove(atariGo.currentPlayer.color, new Position(x,y))) {
	    case WIN:
		myApplication.panInfos.setBlackLife(atariGo.captureObjective-atariGo.caughtBlack);
		myApplication.panInfos.setWhiteLife(atariGo.captureObjective-atariGo.caughtWhite);
		repaint();
		nbMove++;
	    atariGo.shutDown();
	    myApplication.message.showMessageDialog(null, "Le joueur "+atariGo.currentPlayer.color+" a gagné !", "Information", JOptionPane.INFORMATION_MESSAGE);
		//sortie.println(plateau.toString());
		//sortie.println("=> Vous avez gagne"); 
		//scanner.close();
	    return Move.WIN;
	    case NEUTRAL:
		//nombreCoups --;
	    atariGo.currentPlayer = atariGo.currentPlayer == atariGo.player2 ? atariGo.player1 : atariGo.player2;
	    myApplication.panInfos.setBlackLife(atariGo.captureObjective-atariGo.caughtBlack);
	    myApplication.panInfos.setWhiteLife(atariGo.captureObjective-atariGo.caughtWhite);
		System.out.println("onpasse au joueur"+atariGo.currentPlayer.toString());
		nbMove++;
		return Move.NEUTRAL;
	    default:
	    	
	    //sortie.println("=> Erreur : position invalide");
		return Move.INVALID;
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
		
}
