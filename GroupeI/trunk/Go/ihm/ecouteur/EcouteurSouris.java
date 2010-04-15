/**
 * @author Bouvet Frederic
 * @author Altuntas Murat
 */
package ihm.ecouteur;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import coeur.Partie;
import coeur.GoDonnees;

/**
 * Classe permettant de detecter les cliques de souris
 */
public class EcouteurSouris implements MouseListener {	
	/** La partie en cours */
	private Partie partie;
	
	
	/**
	 * Contructeur de l'ecouteur de souris	 
	 * @param partie La partie
	 */
	public EcouteurSouris(Partie partie){		
		this.partie = partie;
	}
	
	/**
	 * Action effectuee lorsque l'on clique sur le bouton de la souris 
	 */
	public void mouseClicked(MouseEvent me){
		int col,ligne;
	
		// Si le joueur courant est humain, que la partie est en cours et qu'on a clique sur le bouton gauche
		if((this.partie.enCours()) && (me.getButton()==MouseEvent.BUTTON1) && (this.partie.getJoueurActuel().getType() == GoDonnees.HUMAIN)){
			col = me.getX()/GoDonnees.DIM_CASE;
			ligne = me.getY()/GoDonnees.DIM_CASE; 
			// On joue le coup
						
			if((col>=0) && (col<9) && (ligne>=0) && (ligne<9) && this.partie.getJoueurActuel().jouerCoup(ligne,col)){
				this.partie.joueurSuivant();
			}
		
		}
	}

	/** Non implementee */
	public void mousePressed(MouseEvent me){ }
	
	/** Non implementee */
	public void mouseReleased(MouseEvent me){ }	
	
	/** Non implementee */
	public void mouseExited(MouseEvent me){	}
	
	/** Non implementee */
	public void mouseEntered(MouseEvent me){}	
	
}
