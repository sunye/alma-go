/**
 * @author Bouvet Frederic
 * @author Altuntas Murat
 */
package ihm.ecouteur;


import ihm.Fenetre;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import coeur.Partie;
import coeur.GoDonnees;

/**
 * Classe permettant d'ecouter toutes les actions des boutons 
 */
public class EcouteurBouton implements ActionListener{
	/** La fenetre du jeu principale */
	private Fenetre fenetre;
	/** La partie en cours */
	private Partie partie;
	
	/**
	 * Constructeur d'un ecouteur de bouton
	 * @param fenetre La fenetre du jeu principale
	 * @param partie La partie en cours
	 */
	public EcouteurBouton(Fenetre fenetre, Partie partie) {
		this.fenetre = fenetre;
		this.partie = partie;
	}

	/**
	 * Implementation des reactions aux evenements
	 */
	public void actionPerformed(ActionEvent e) {
        // Forcer l'ia a jouer
		if (e.getSource() == this.fenetre.getBoutonForcerAjouer()){
			if(partie.getJoueurActuel().getType() == GoDonnees.IA){
			    partie.getJoueurActuel().setForceAjouer(true);
			}
		}	
		//Abandonner
		else if ((e.getSource() == this.fenetre.getBoutonAbandonner()) && (partie.getJoueurActuel()!=null) && (partie.getJoueurActuel().getType() == GoDonnees.HUMAIN)){
			if (partie.getJoueurActuel().getCouleur() == GoDonnees.BLANC){
				partie.setjoueur_gagant(GoDonnees.NOIR);					
			} else {
				partie.setjoueur_gagant(GoDonnees.BLANC);
			}	
			fenetre.miseAJour();
		}
		//Aide
		else if (e.getSource() == this.fenetre.getBoutonAide() && (partie.getJoueurActuel()!=null) && (partie.getJoueurActuel().getType() == GoDonnees.HUMAIN)){
					
			fenetre.getPanneau().setjoueuraide(partie.getJoueurActuel());
			fenetre.miseAJour();
				
			
		}
		// On ferme la fenetre d'option des joueurs si l'on a clique sur le bouton Ok
		else if (e.getSource() == this.fenetre.getFenetreOptionJoueurs().getBoutonOK()){			
		 	this.fenetre.getFenetreOptionJoueurs().setVisible(false);
            // On regarde si les changements d'option font que le joueur actuel soit devenu un ordi
		 	if((this.partie.enCours()) && (this.partie.getJoueurActuel().getType() == GoDonnees.IA)){
		 		this.partie.getIa().jouer(this.partie.getJoueurActuel());
		 	}
		}
        // On ferme la fenetre d'option du chronometre si l'on a clique sur le bouton Ok
		else if (e.getSource() == this.fenetre.getFenetreOptionChrono().getBoutonOK()){			
		 	this.fenetre.getFenetreOptionChrono().setVisible(false);            
		}
		else if (e.getSource() == this.fenetre.getfenetreobjectifcapture().getBoutonOK()){
			this.fenetre.getfenetreobjectifcapture().setVisible(false);
		}
		
		
	}
	
	

}
