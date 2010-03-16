package fr.alma.ui;
import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import fr.alma.atarigo.Joueur;
import fr.alma.atarigo.JoueurHumain;
import fr.alma.atarigo.JoueurIA;
import fr.alma.atarigo.Pion;

/**
 * Classe implémentant la boite de dialogue pour les paramètres d'une nouvelle partie.
 * @author vincent
 *
 */
public class NouvellePartieDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	
	private MonApplication monApp;

	public NouvellePartieDialog(JFrame parent, String title, boolean modal,MonApplication monApp){
		super(parent, title, modal);
		this.monApp = monApp;
		this.setSize(250, 220);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.initComponent();
	}
	
	JPanel jpan1 = new JPanel();
	GridLayout gridLayout1 = new GridLayout(3, 2, 5, 5);;
	JLabel label_noir = new JLabel("NOIR",JLabel.CENTER);
	JLabel label_blanc = new JLabel("BLANC",JLabel.CENTER);
	JComboBox combo1 = new JComboBox();
	JComboBox combo2 = new JComboBox();
	JButton bouton = new JButton("Go !");
    JButton bouton2 = new JButton("Quitter");

	/**
	 * initialise les différents composants de la boite de dialogue
	 *
	 */
	public void initComponent(){
		jpan1.setLayout(gridLayout1);
		label_noir.setVerticalTextPosition(JLabel.NORTH);
		label_noir.setHorizontalTextPosition(JLabel.CENTER);
		label_noir.setIcon(new ImageIcon(monApp.getClass().getResource("/fr/alma/resources/images/black-stone.gif")));
		label_blanc.setIcon(new ImageIcon(monApp.getClass().getResource("/fr/alma/resources/images/white-stone.gif")));
		label_blanc.setVerticalTextPosition(JLabel.NORTH);
		label_blanc.setHorizontalTextPosition(JLabel.CENTER);
		combo1.addItem("Humain");
		combo1.addItem("Ordinateur");
        combo2.addItem("Humain");
        combo2.addItem("Ordinateur");
		jpan1.add(label_noir);
		jpan1.add(label_blanc);
		jpan1.add(combo1);
		jpan1.add(combo2);
		jpan1.add(bouton);
		jpan1.add(bouton2);
		bouton.addActionListener(new GoListener());             
		bouton2.addActionListener(new QuitterListener());
		this.getContentPane().add(jpan1);
	}
	
	/**
     * classe qui écoute notre bouton Go !
     */
    public class GoListener implements ActionListener{

            /**
             * Redéfinitions de la méthode actionPerformed
             */
            public void actionPerformed(ActionEvent arg0) {
           	 	Thread t = new Thread() {
        		public void run() {   	
            	//test des options et demarrage
            	JOptionPane jop = new JOptionPane();			
        		if(!monApp.atarigo.estTermine()){
	            	int option = jop.showConfirmDialog(null, "Voulez-vous vraiment faire une nouvelle partie ? Votre partie sera définitivement perdue", "Nouvelle partie", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
	        					
	        		if(option == JOptionPane.OK_OPTION)
	        		{
	        			try {
							demarrer();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	        		}
	        		else{
	        			setVisible(false);
	        		}
        		}
        		else{
        			try {
						demarrer();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
        		}
        		
       		 }
                };
                t.start();
            }
            
    }
    
    /**
     * enregistre les paramètres de la nouvelle partie
     * et appelle la méthode demarrer de la classe {MonApplication}
     * @throws InterruptedException
     */
    public void demarrer() throws InterruptedException {
    	Joueur joueur1;
    	Joueur joueur2;
    	if(combo1.getSelectedItem().toString().equals("Humain")){
    		joueur1 = new JoueurHumain(Pion.NOIR,"bob");
    	}
    	else{
    		joueur1 = new JoueurIA(Pion.NOIR,"bob");
    	}
    	if(combo2.getSelectedItem().toString().equals("Humain")){
    		joueur2 = new JoueurHumain(Pion.BLANC,"obi");
    	}
    	else{
    		joueur2 = new JoueurIA(Pion.BLANC,"obi");
    	}
    	monApp.demarrer(joueur1,joueur2);
    	setVisible(false);
    	
    }
    
    /**
     * classe qui écoute notre bouton Quitter
     */
    public class QuitterListener implements ActionListener{

            /**
             * Redéfinitions de la méthode actionPerformed
             */
            public void actionPerformed(ActionEvent arg0) {
            	Thread t = new Thread() {
            		public void run() {  
            	setVisible(false);
           		 }
                };
                t.start();
            	
            }
            
    }


}
