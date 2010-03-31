package ihm;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Fenetre extends JFrame implements ActionListener {
       

        private static final long serialVersionUID = 1L;
        private JMenuBar barreMenu;
        private JMenu jeu;
        private JMenu a_propos;
        private JMenuItem pVp;
        private JMenuItem pVc;
        private JMenuItem quitter;
        
        //Panel représentant le goban
        private Goban Pan;
       
        public Fenetre(String s){
                super(s);
                setSize(710,650);
               
                /**
         * Creations des différents outils permettant la mise en place de la barre de menu
         */
               
                barreMenu=new JMenuBar();
               
                jeu=new JMenu("Nouvelle partie");
                a_propos=new JMenu("A Propos");
               
                pVp=new JMenuItem("2 joueurs");
                pVp.addActionListener(this);
                
                pVc=new JMenuItem("contre IA");
                pVc.addActionListener(this);
                
                quitter=new JMenuItem("Quitter");
                quitter.addActionListener(this);
                
                /**
         * Mise en place de la barre de menu
         */
               
                barreMenu.add(jeu);
                barreMenu.add(a_propos);
               
                jeu.add(pVp);
                jeu.add(pVc);
                jeu.add(quitter);
       
                this.setJMenuBar(barreMenu);
             
                // lancement d'une parti de base
                start();      
                       
                /**
             *  Arrêt de l'appli quand on clique sur la croix
             */
               
                setDefaultCloseOperation(EXIT_ON_CLOSE);
        }

		private void start() {
			// Creation du panel contenant le goban
	    	Pan=new Goban();
            Pan.setBorder(BorderFactory.createLineBorder(Color.black));
            // On ajoute le GobanPanel dans la fenetre principale
            this.add(Pan);
		}

		public void actionPerformed(ActionEvent evt) {
			if (evt.getSource()instanceof JMenuItem) {
				// gestion des événements liés aux menus
	            String ChoixOption = evt.getActionCommand();
	            
	            if (ChoixOption.equals("2 joueurs")){
	            	Pan.resetGame(false);
	            	this.repaint();
	            }else if(ChoixOption.equals("contre IA")){
	            	Pan.resetGame(true);
	            	this.repaint();
	            }else if(ChoixOption.equals("Quitter")){
	            	System.exit(ABORT);
	            }	
			}
		}
       
}
