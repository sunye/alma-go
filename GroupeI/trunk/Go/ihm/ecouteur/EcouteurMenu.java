/**
 * @author Bouvet Frederic
 * @author Altuntas Murat
 */
package ihm.ecouteur;

import ihm.Fenetre;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

import coeur.GoDonnees;
import coeur.Partie;

/**
 * Classe permettant d'ecouter toutes les actions que l'on peut faire sur le menu
 */
public class EcouteurMenu implements ActionListener {
	/** La fenetre du jeu principale */
	private Fenetre fenetre;
	/** La partie en cours */
	private Partie partie;
	
	
	/**
	 * Constructeur d'un ecouteur du menu
	 * @param fenetre La fenetre du jeu principale
	 * @param partie La partie en cours
	 */
	public EcouteurMenu(Fenetre fenetre, Partie partie) {
		this.fenetre = fenetre;
		this.partie = partie;
	}
	
	/**
	 * Implementation de la reaction aux evenements
	 */
	public void actionPerformed(ActionEvent ae){
		// Nouvelle partie
		if(ae.getSource()==this.fenetre.getNouvellePartie()){
			this.partie.nouvelle();
		}
		// Quittter
		else if(ae.getSource()==this.fenetre.getQuitter()){
			System.exit(0);
		}
        // Parametrage des joueurs
		else if(ae.getSource()==this.fenetre.getOptionJoueur()){
			Point point = this.fenetre.getLocation();
			point.x += 180; 
			point.y += 160; 
			this.fenetre.getFenetreOptionJoueurs().setLocation(point);
			this.fenetre.getFenetreOptionJoueurs().setVisible(true);			
		}
		//parametrage des objectifs de capture
		else if(ae.getSource()==this.fenetre.getOptionobjectifcapture()){
			Point point = this.fenetre.getLocation();
			point.x += 180; 
			point.y += 160; 
			this.fenetre.getfenetreobjectifcapture().setLocation(point);
			this.fenetre.getfenetreobjectifcapture().setVisible(true);
		}
        // Parametrage du chronometrage
		else if(ae.getSource()==this.fenetre.getOptionChronometre()){
			Point point = this.fenetre.getLocation();
			point.x += 180; 
			point.y += 160; 
			this.fenetre.getFenetreOptionChrono().setLocation(point);
			this.fenetre.getFenetreOptionChrono().setVisible(true);			
		}		
		// A propos
		else if(ae.getSource()==this.fenetre.getApropos()){
			String s=GoDonnees.texte_a_propos;
			JOptionPane.showMessageDialog(this.fenetre,s,"A propos",JOptionPane.INFORMATION_MESSAGE  );
		}
	
	
	}

}
