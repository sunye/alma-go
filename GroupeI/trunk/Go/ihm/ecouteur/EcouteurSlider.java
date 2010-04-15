/**
 * @author Bouvet Frederic
 * @author Altuntas Murat
 */
package ihm.ecouteur;

import ihm.Fenetre;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import coeur.Partie;

/**
 * Classe permettant d'ecouter toutes les actions des composants de type Slider 
 */
public class EcouteurSlider implements  ChangeListener{
	/** La fenetre du jeu principale */
	private Fenetre fenetre;
	/** La partie en cours */
	private Partie partie;
	
	
	/**
	 * Constructeur d'un ecouteur de slider
	 * @param fenetre La fenetre du jeu principale
	 * @param partie La partie en cours
	 */
	public EcouteurSlider(Fenetre fenetre, Partie partie){
		this.fenetre = fenetre;
		this.partie = partie;
		
	}
	
	/**
	 * Implementation des reactions aux evenements des composants de type Slider
	 */
	public void stateChanged(ChangeEvent e) {		
        // Si le Slider concerne est celui de la fenetre du reglage du chronometre 
		if(e.getSource() == this.fenetre.getFenetreOptionChrono().getReglageChrono()){	
			// On recupere le temps modifie par le Slider
			int temps = (int)this.fenetre.getFenetreOptionChrono().getReglageChrono().getValue();	        
	        // On fixe le compte a rebours avec la nouvelle valeur maximale pour le temps
	        this.partie.getCompteArebours().setTempsMax(temps);			
		}		
	}	
	

}
