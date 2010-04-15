/**
 * @author Bouvet Frederic
 * @author Altuntas Murat
 */
package ihm;

import ia.AlphaBeta;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import coeur.GoDonnees;
import coeur.Joueur;
import coeur.Plateau;

/**
 * Classe permettant de creer un panneau pour le plateau
 */
public class Panneau extends JPanel {
	
	/** Generation d'un serial pour le panneau */
	private static final long serialVersionUID = 1L;
	/** Le plateau à representer */
	private Plateau plateau;
	/** Image de pion noir */
	private ImageIcon imageNoir;
	/** Image de pion blanc */
	private ImageIcon imageBlanc;
	/** Image du goban */
	private ImageIcon imagegoban;	
	/** Image de piont noir transparent, pour l'aide au joueur **/
	private ImageIcon imagenoirtransp;
	/** Image de piont blanc transparent, pour l'aide au joueur **/
	private ImageIcon imageblanctransp;
	
	 
	private Joueur joueuraide;
	
	/**
	 * Contructeur du panneau
	 * @param plateau le plateau de jeu à representer
	 */
	public Panneau(Plateau plateau){
		this.plateau = plateau;		
		imageNoir = new ImageIcon(getClass().getResource("/ihm/Images/noir.png"));  
		imageBlanc = new ImageIcon(getClass().getResource("/ihm/Images/blanc.png"));  
		imagegoban = new ImageIcon(getClass().getResource("/ihm/Images/Goban99_4242.PNG"));  		
		imagenoirtransp = new ImageIcon(getClass().getResource("/ihm/Images/noir_transparent.png"));  
		imageblanctransp = new ImageIcon(getClass().getResource("/ihm/Images/blanc_transparent.png"));  
	}

	/** getter sur l'image noir transparente
	 * @return imagenoirtransp
	 * **/
	public ImageIcon getimagenoirtransp(){
		return imagenoirtransp;
	}
	
	/** getter sur l'image blanc transparente
	 * @return imageblanctransp
	 *  **/
	public ImageIcon getimageblanctransp(){
		return imageblanctransp;
	}
	
	/**
	 * Permet de changer de panneau
	 * @param plateau le plateau de jeu
	 */
	public void setPlateau(Plateau plateau){
		this.plateau = plateau;
	}
	
	/**
	 * permet de connaitre le plateau
	 * @return le plateau
	 */
	public Plateau getPlateau(){
		return this.plateau;
	}
	
	
	/** setter pour le joueur d'aide 
	 * @param j le joueur aide
	 * **/
	public void setjoueuraide(Joueur j){
		joueuraide=j;
	}
	
	/**
	 * Affichage du panneau
	 * @param g le graphics
	 */
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(imagegoban.getImage(),0,0,this);
		for(int i=0; i<GoDonnees.DIM_GOBAN_MAX; i++)
			for(int j=0;j<GoDonnees.DIM_GOBAN_MAX; j++){
				switch(plateau.getCouleur(i,j)){
					case GoDonnees.NOIR:
						g.drawImage(imageNoir.getImage(),GoDonnees.RAYON_PION+j*GoDonnees.DIM_CASE,GoDonnees.RAYON_PION+i*GoDonnees.DIM_CASE,this);
						break;
					case GoDonnees.BLANC:
						g.drawImage(imageBlanc.getImage(),GoDonnees.RAYON_PION+j*GoDonnees.DIM_CASE,GoDonnees.RAYON_PION+i*GoDonnees.DIM_CASE,this);
						break;
					
				}
			}
	
		if (joueuraide!=null && joueuraide.getCouleur()!=GoDonnees.VIDE) {
			AlphaBeta calculCoup = new AlphaBeta(plateau, joueuraide);		
			if (joueuraide.getCouleur()==GoDonnees.NOIR){
				g.drawImage(imagenoirtransp.getImage(),GoDonnees.RAYON_PION+calculCoup.getColonne()*GoDonnees.DIM_CASE,GoDonnees.RAYON_PION+calculCoup.getLigne()*GoDonnees.DIM_CASE, this);
				
			} else{
				g.drawImage(imageblanctransp.getImage(),GoDonnees.RAYON_PION+calculCoup.getColonne()*GoDonnees.DIM_CASE,GoDonnees.RAYON_PION+calculCoup.getLigne()*GoDonnees.DIM_CASE, this);
				
			}			
			joueuraide=null;
		}
		
	
	}
}
