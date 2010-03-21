package fr.alma.ui;

import fr.alma.atarigo.AtariGo;
import fr.alma.atarigo.Pion;
import fr.alma.atarigo.Position;
import fr.alma.ia.AlphaBeta;
import fr.alma.ia.Arbre;
import fr.alma.ia.MinMax;
import fr.alma.ia.PlateauValue;

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
 * Classe implémentant la représentation graphique du Goban
 * @author vincent
 *
 */
public class UIPlateau extends JPanel implements MouseListener,MouseMotionListener,ActionListener,Runnable{

	
	private static final long serialVersionUID = 1L;
	private MonApplication monApplication;
	private AtariGo atariGo;
	// variables pour le plateau ui
	int pion_cours_x;
	int pion_cours_y;
	// variables pour le thread de jeu
	public Thread tempo;
	private int time=0;
	boolean running = true;
	public boolean stopper=false;
	public boolean enPause=false;
	// variables pour l'ia
	Position meilleurCoup;
	int pos = 0;
	
	/**
	 * constructeur logique
	 * @param monapp Application rattachée
	 */
	public UIPlateau(MonApplication monapp){
		this.monApplication=monapp;
		this.atariGo = monApplication.atarigo;
		addMouseListener(this);
		addMouseMotionListener(this);
		tempo = new Thread(this);
	}

	/**
	 * dessin du plateau
	 */
	public void paintComponent(Graphics g){
		if(atariGo!=null){
			//dessin du plateau
			Color fond = new Color(128,128,128);
			Color ligne = new Color(0,0,0);
			g.setColor(fond);
			g.fillRect(0, 0, 360, 360);
			g.setColor(ligne);
			
			for(int i=0;i<9;i++){
				g.drawLine(20, (40*i)+20, 340, (40*i)+20);
				g.drawLine((40*i)+20, 20, (40*i)+20,340);
			}
			//fin dessin plateau
			
			//dessin des pions joués
			for(int i=0;i<9;i++){
				for(int j=0;j<9;j++){
				//g.drawOval((40/4)+(40*i)-(1*8),(40/4)-(1*8),(40/2)+(2*8),(40/2)+(2*8));
					switch (atariGo.plateau.grille[i][j]) {
				    	case NOIR:
				    		g.setColor(Color.BLACK);
				    		g.fillOval((40/4)+(40*i)-(1*8),(40/4)+(40*j)-(1*8),(40/2)+(2*8),(40/2)+(2*8));
				    		break;
				    	case BLANC:
				    		g.setColor(Color.WHITE);
				    		g.fillOval((40/4)+(40*i)-(1*8),(40/4)+(40*j)-(1*8),(40/2)+(2*8),(40/2)+(2*8));
				    		break;
				    	case VIDE:break;
				    	default:break;
					}
				}
			}
			//fin dessin des pions joués
			
			//dessin du pion à jouer (depend du joueur en cours)
			if(!atariGo.estTermine()){
				g.setColor(Color.BLACK);
				g.drawOval((40/4)+(40*pion_cours_x)-(1*8),(40/4)+(40*pion_cours_y)-(1*8),(40/2)+(2*8),(40/2)+(2*8));
			}
		}
	}
	
	/**
	 * réinitialisation du plateau
	 */
	public void nouvellePartie(){
		//ici on reinit...
		this.atariGo = monApplication.atarigo;
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
	 * définition du comportement du relachement du bouton gauche de la souris sur le plateau
	 */
	public void mouseReleased(MouseEvent e) {
		if (!atariGo.estTermine()) {
			if(atariGo.joueurEnCours.estHumain()){
				// TODO Auto-generated method stub
				int x=e.getX();
				int y=e.getY();
				x=(x)/40;
				y=(y)/40;
				//System.out.println("clic sur la case"+"["+x+","+y+"]");
				//ici on joue le coup  et on test avant pr savoir etat du jeu en cours...
				switch (atariGo.jouerCoup(atariGo.joueurEnCours.couleur, new Position(x,y))) {
			    case GAGNANT:
			    repaint();
			    atariGo.desactiver();
			    monApplication.message.showMessageDialog(null, "Le joueur "+atariGo.joueurEnCours.couleur+" a gagné !", "Information", JOptionPane.INFORMATION_MESSAGE);
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
			    }
				monApplication.labelCpsjoues.setText(Integer.toString(atariGo.nbCoups));
				repaint();
			}
		}
	}

	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
/**
 * définition du comportement du mouvement de la souris sur le plateau
 */
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		if (!atariGo.estTermine()) {
			pion_cours_x=e.getX()/40;
			pion_cours_y=e.getY()/40;
			//System.out.println("clic sur la case"+"["+x+","+y+"]");
			repaint();
		}
	}
	
/**
 * boucle de jeu
 */
	public void run() {
		//boucle de jeu
		enPause=false;
		System.out.println("tempo start");
		while(true){
			//si décommenté boucle marche...prob EDT ?
			//System.out.println("true");
			synchronized(this){
				while(!atariGo.estTermine()){
					//boucles des joueurs...
					//test
					time++;
					monApplication.labelT.setText(String.valueOf(time));
					//System.out.println("ok");
					if(!atariGo.joueurEnCours.estHumain()){
						System.out.println("dns la bcle");
						stopper=true;
							Arbre jeu = new Arbre(atariGo.plateau);
							PlateauValue plv = new PlateauValue(0);
							AlphaBeta.init(2);
							
							plv = AlphaBeta.Valeur(0, jeu, 0,atariGo.joueurEnCours.couleur);	
							atariGo.plateau=plv.plateau_;
							System.out.println("--------> nombre de noeuds parcourus = "+AlphaBeta.nb_noeuds);
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
						monApplication.labelCpsjoues.setText(Integer.toString(atariGo.nbCoups));
						repaint();
						
						try{
							Thread.sleep(1000);
						}catch(InterruptedException e){}
						
						//aprs avoir jouer on passe a l'autre joueur
						atariGo.joueurEnCours = atariGo.joueurEnCours == atariGo.joueur2 ? atariGo.joueur1 : atariGo.joueur2;
						//System.out.println("on passe au joueur "+atariGo.joueurEnCours.toString());
					}
				}
			}
		}
	}
	
	
}