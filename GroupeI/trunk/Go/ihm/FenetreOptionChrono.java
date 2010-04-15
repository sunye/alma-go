/**
 * @author Bouvet Frederic
 * @author Altuntas Murat
 */
package ihm;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

import coeur.GoDonnees;

/**
 * Classe permettant de creer la fenetre d'option pour le chronometre
 */
public class FenetreOptionChrono extends JFrame {
	
	/** Generation d'un serial pour cette fenetre */
	private static final long serialVersionUID = 1L;
	/** Le container d'affichage */
	private Container container;
	/** Le bouton pour fermer la fenetre */
	private JButton boutonOK;	
	/** Le composant permettant de regler le chronometrage */
	private JSlider reglageChrono;
	/** Couleur de la fenetre */
	private Color couleur;
	
	
	 /**
	  * Creation de la fenetre d'option permetant le parametrage du chronometre	   
	  */
	  public FenetreOptionChrono(){ 
		   super("Parametrage du chronometre");	
		   this.couleur = Color.WHITE;
		   this.setResizable(false);
		   this.setSize(500,170);		   		   
		   
		   this.miseEnPage();
	  } 
	
	  /**
	   * Creation et mise en page des composants sur la fenetre courante	   
	   */
	   private void miseEnPage(){
			container = this.getContentPane();			
			container.setLayout(new GridLayout (3,1, 0, 10));
			container.setBackground(this.couleur);
				
			// Creations des composants :			
              // On construit un panel contenant la phrase 
			  JPanel panelPhrase = new JPanel();
			  JLabel phrase = new JLabel("Temps maximal accorde à l'IA pour jouer en seconde :");	
			  Font police = new Font("Arial", Font.BOLD, 14);		 
			  phrase.setFont(police); 
			  panelPhrase.add(phrase);
			  panelPhrase.setBackground(this.couleur);			  
			  // On construit la barre permant de regler le temps			  
			  reglageChrono = new JSlider(JSlider.HORIZONTAL,1, 21, GoDonnees.tempsMaxDefaut);
			  reglageChrono.setMajorTickSpacing(5);
			  reglageChrono.setMinorTickSpacing(1);
			  reglageChrono.setPaintTicks(true);
			  reglageChrono.setPaintLabels(true);
			  Font policeSlider = new Font("Arial", Font.BOLD, 15);	
			  reglageChrono.setFont(policeSlider); 
			  reglageChrono.setBackground(this.couleur);
			  // On construit un panel contenant le bouton OK pour fermer la fenetre
			  JPanel panelBoutonOK = new JPanel();
			  boutonOK = new JButton (" Ok ");		
			  panelBoutonOK.add(boutonOK);
			  panelBoutonOK.setBackground(this.couleur);			
			// Ajout des composants	
			this.getContentPane().add( panelPhrase);
			this.getContentPane().add( reglageChrono);
			this.getContentPane().add( panelBoutonOK);
	   }
	
	   
	   /**
		* Permet d'obtenir le bouton servant à fermer cette fenetre
		* @return le bouton
		*/ 
		public JButton getBoutonOK(){
				return this.boutonOK;
		}
	   
	   /**
		* Permet d'obtenir le composant servant a regler le chronometre
		* @return le composant
		*/ 
		public JSlider getReglageChrono(){
			return this.reglageChrono;
		} 
	   
	   

}
