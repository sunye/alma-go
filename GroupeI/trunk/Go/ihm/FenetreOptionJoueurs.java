/**
 * @author Bouvet Frederic
 * @author Altuntas Murat
 */
package ihm;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import coeur.GoDonnees;

/**
 * Classe permettant de creer la fenetre d'option des joueurs
 */
public class FenetreOptionJoueurs extends JFrame {	
	
	/** Generation d'un serial pour cette fenetre */
	private static final long serialVersionUID = 1L;
	/** Le container d'affichage */
	private Container container;
	/** La liste deroulante pour le joueur Blanc */
	private JComboBox listeDeroulanteBlanc;
	/** La liste deroulante pour le joueur Noir */
	private JComboBox listeDeroulanteNoir;
	/** Le bouton pour fermer la fenetre */
	private JButton boutonOK;
	/** Couleur de la fenetre */
	private Color couleur;
	
	
	 /**
	  * Creation de la fenetre d'option permetant le parametrage des deux joueurs	   
	  */
	  public FenetreOptionJoueurs(){ 
		   super("Parametrage des joueurs");	
		   this.couleur = Color.WHITE;
		   this.setResizable(false);
		   this.setSize(370,170);	
		   
		   this.miseEnPage();
	   } 
	
	 /**
	  * Creation et mise en page des composants sur la fenetre courante	   
	  */
	  private void miseEnPage(){
			container = this.getContentPane();			
			container.setLayout(new GridLayout (3,1, 0, 15));
			container.setBackground(this.couleur);
			
			// Creations des composants
			DefaultComboBoxModel listeDeChoix1 = new DefaultComboBoxModel();
			listeDeChoix1.addElement(GoDonnees.texteHumain);
			listeDeChoix1.addElement(GoDonnees.texteIA_Debutant);
			listeDeChoix1.addElement(GoDonnees.texteIA_Intermediaire);
		    listeDeChoix1.addElement(GoDonnees.texteIA_Expert);
			
		    DefaultComboBoxModel listeDeChoix2 = new DefaultComboBoxModel();
		    listeDeChoix2.addElement(GoDonnees.texteHumain);
			listeDeChoix2.addElement(GoDonnees.texteIA_Debutant);
			listeDeChoix2.addElement(GoDonnees.texteIA_Intermediaire);
		    listeDeChoix2.addElement(GoDonnees.texteIA_Expert);
		    
		    listeDeroulanteBlanc = new JComboBox(listeDeChoix1);		    
		    listeDeroulanteNoir = new JComboBox(listeDeChoix2);	
		    
		    //Par defaut le joueur blanc est une IA de niveau intermediaire
		    listeDeroulanteBlanc.setSelectedIndex(2);
		    
			JPanel panelJoueurBlanc = getPanelJoueur("Blanc :",listeDeroulanteBlanc);
			JPanel panelJoueurNoir = getPanelJoueur("Noir :  ",listeDeroulanteNoir);			
			JPanel panelBoutonOK = new JPanel();			
			boutonOK = new JButton (" Ok ");		
			panelBoutonOK.add(boutonOK);
			panelBoutonOK.setBackground(this.couleur);

            // Ajout des composants		
			this.getContentPane().add( panelJoueurBlanc);
			this.getContentPane().add(panelJoueurNoir);
			this.getContentPane().add(panelBoutonOK);
	  }
	
	 /**
	  * Permet de construire un panel rassemblant la couleur du joueur et sa liste deroulante
	  * @param couleurJoueur la couleur du joueur 
	  * @param liste la liste deroulante permettant de choisir le type de joueur et le niveau de l'IA
	  * @return le panel
	  */ 
	  private JPanel getPanelJoueur(String couleurJoueur,JComboBox liste){
		  JPanel temp = new JPanel();		 
		  temp.setLayout(new FlowLayout (FlowLayout.CENTER, 20, 10));
		  temp.setBackground(this.couleur);
		  
		  // on construit le texte 
		  JLabel texte = new JLabel(couleurJoueur);	
		  Font police = new Font("Arial", Font.BOLD, 14);		 
		  texte.setFont(police); 
		  
          // Ajout du texte puis de la liste deroulante 			  
		  temp.add(texte);
		  temp.add(liste);		  
		  
		  return temp;
	  }
	
   /**
	* Permet d'obtenir le bouton servant à fermer cette fenetre
	* @return le bouton
	*/ 
	public JButton getBoutonOK(){
			return this.boutonOK;
	}
	
   /**
	* Permet d'obtenir la liste deroulante concernant le joueur blanc (Humain - Ia debutant,intermediaire,expert)
	* @return la liste deroulante
	*/ 
	public JComboBox getListeDeroulanteBlanc(){
		return this.listeDeroulanteBlanc;
	}
	
   /**
	* Permet d'obtenir la liste deroulante concernant le joueur Noir (Humain - Ia debutant,intermediaire,expert)
	* @return la liste deroulante
	*/ 
	public JComboBox getListeDeroulanteNoir(){
		return this.listeDeroulanteNoir;
	}
	
	
}
