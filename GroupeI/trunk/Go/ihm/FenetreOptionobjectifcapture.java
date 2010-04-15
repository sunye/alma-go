/**
 * @author frederic Bouvet
 * @author murat Altuntas
 *  **/
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

/** Classe permentant de creer la fenetre d'option pour les objectif de captures 
 * **/
public class FenetreOptionobjectifcapture extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** Le container d'affichage */
	private Container container;
	/** Liste deroulante */
	private JComboBox listeDeroulanteBlanc;
	private JComboBox listeDeroulanteNoir;
	/** Le bouton pour fermer la fenetre */
	private JButton boutonOK;
	/** Couleur de la fenetre */
	private Color couleur;
	
	public FenetreOptionobjectifcapture (){
		  super("Objectif de capture");	
		   this.couleur = Color.WHITE;
		   this.setResizable(false);
		   this.setSize(370,170);	
		   
		   this.miseEnPage();
	}

	private void miseEnPage() {
	
		container = this.getContentPane();			
		container.setLayout(new GridLayout (3,1, 0, 15));
		container.setBackground(this.couleur);
		
		// Creations des composants
		DefaultComboBoxModel listeDeChoix1 = new DefaultComboBoxModel();
		for (int i=1;i<=GoDonnees.capturemax;i++){
			listeDeChoix1.addElement(i);
		}
				
	    DefaultComboBoxModel listeDeChoix2 = new DefaultComboBoxModel();
	    
	    for (int i=1;i<=GoDonnees.capturemax;i++){
	    	listeDeChoix2.addElement(i);
		}
	    
	    listeDeroulanteBlanc = new JComboBox(listeDeChoix1);		    
	    listeDeroulanteNoir = new JComboBox(listeDeChoix2);	
	    listeDeroulanteBlanc.setSelectedIndex(4);
	    listeDeroulanteNoir.setSelectedIndex(4);
	  	    
		JPanel panelJoueurBlanc = getPanelJoueur("Objectif du joueur blanc :",listeDeroulanteBlanc);
		JPanel panelJoueurNoir = getPanelJoueur("Objectif du joueur noir :  ",listeDeroulanteNoir);			
		JPanel panelBoutonOK = new JPanel();			
		boutonOK = new JButton (" Ok ");		
		panelBoutonOK.add(boutonOK);
		panelBoutonOK.setBackground(this.couleur);

        // Ajout des composants		
		this.getContentPane().add( panelJoueurBlanc);
		this.getContentPane().add(panelJoueurNoir);
		this.getContentPane().add(panelBoutonOK);
	}

	private JPanel getPanelJoueur(String couleurJoueur, JComboBox liste) {
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
	* Permet d'obtenir la liste deroulante concernant le joueur blanc 
	* @return la liste deroulante
	*/ 
	public JComboBox getListeDeroulanteBlanc(){
		return this.listeDeroulanteBlanc;
	}
	
   /**
	* Permet d'obtenir la liste deroulante concernant le joueur Noir 
	* @return la liste deroulante
	*/ 
	public JComboBox getListeDeroulanteNoir(){
		return this.listeDeroulanteNoir;
	}
	
}
