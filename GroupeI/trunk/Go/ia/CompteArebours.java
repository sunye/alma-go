/**
 * @author Bouvet Frederic
 * @author Altuntas Murat
 */
package ia;

import ihm.Fenetre;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.Timer;

import coeur.Partie;
import coeur.GoDonnees;

/**
 * Classe permettant de creer un compte a rebours  
 */
public class CompteArebours implements ActionListener
{
	/** Le Label permetant d'afficher sur la fenetre principale le temps restant */
	private JLabel viewTime;  
	
	/** Le bouton de la fenetre principale permettant de forcer l'IA a jouer */
	private JButton boutonForcerAjouer;
	/** Le nombre de seconde maximal accorde a l'IA pour jouer */
	private int tempsMax;	
	/** Le nombre de seconde restant pour l'IA avant d'etre force a jouer */
	private int tempsRestant;	
	/** Le composant qui permet de diminuer le temps restant a chaque seconde */
	private Timer timer;		
	/** La partie du jeu en cours pour pouvoir y recuperer toutes les informations necessaires */
	private Partie partie;
    	
	
	 
	/**
	 * Constructeur d'un compte a rebours qui sera declanche toutes les secondes durant un temps donne dans la partie
	 *@param fenetre La fenetre principale
	 *@param laPartie La partie en cours permettant de savoir la duree du compte a rebours
	 */
	public CompteArebours (Fenetre fenetre, Partie laPartie){			
		this.partie = laPartie;		
		this.viewTime =  fenetre.getTempsRestant();
		this.boutonForcerAjouer = fenetre.getBoutonForcerAjouer();
		this.timer = new Timer (1000, this);	
		this.tempsMax =  GoDonnees.tempsMaxDefaut;
	}
	
	/**
	 * Lance le compte a rebours 	
	 */	  
	public void lancer (){	
		tempsRestant = tempsMax;
	    this.boutonForcerAjouer.setEnabled(true);
	    this.viewTime.setText ("    "+this.tempsRestant+" s");
	    this.timer.start ();
	}
	
	/**
	 * Stop le compte a rebours 
	 */	 
	public void stop (){	
		this.boutonForcerAjouer.setEnabled(false);
		this.viewTime.setText ("    -- s");
	    this.timer.stop ();
	}	
	 
	/**
	 * Permet de recuperer le temps maximal
	 * @return le temps qu'il reste
	 */	 
	public int getTempsMax (){
		return  this.tempsMax ;
	}
	
	/**
	 * Permet de fixer le temps maximal	 
	 */	 
	public void setTempsMax (int temps){
		this.tempsMax = temps ;
	}
	
	
	/**
	 * Permet de savoir si le compte a rebours est en cours
	 * @return si le compte a rebours est en marche
	 */	 
	public boolean estActif (){
		return ( this.timer.isRunning () );
	}
	
	/**
	 * Action declenchee toutes les secondes pour decompter le temps jusqu'a forcer le joueur a jouer si besoin
	 * @param e l'action demandee
	 */	
	public void actionPerformed (ActionEvent e){		
		    this.tempsRestant--;
			this.viewTime.setText ("    "+this.tempsRestant+" s");
			if (this.tempsRestant == 0){
				this.partie.getJoueurActuel().setForceAjouer(true);
				this.stop ();
			}
		
	}
	
}