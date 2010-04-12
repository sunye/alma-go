package ihm;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.*;

public class Fenetre extends JFrame implements ActionListener {
       

        private static final long serialVersionUID = 1L;
        private JMenuBar barreMenu;
        private JMenu jeu;
        private JMenu diff;
        private JMenuItem pVp;
        private JMenuItem pVc;
        private JMenuItem quitter;
        private JMenuItem nul;
        private JMenuItem moy;
        private JMenuItem str;
        private JComboBox timeIA;
        
        //Panel représentant le goban
        private Goban Pan;
       
        public Fenetre(String s){
        	super(s);
            setSize(750,650);
           
            /**
	         * Creations des différents outils permettant la mise en place de la barre de menu
	         */
           
            barreMenu=new JMenuBar();
           
            jeu=new JMenu("Nouvelle partie");
              
            pVp=new JMenuItem("2 joueurs");
            pVp.addActionListener(this);
            
            pVc=new JMenuItem("joueur contre IA");
            pVc.addActionListener(this);
                        
            quitter=new JMenuItem("Quitter");
            quitter.addActionListener(this);
            
            diff= new JMenu("Difficulté");
            
            nul = new JMenuItem("Nulle");
            nul.addActionListener(this);
            moy = new JMenuItem("Moyen");
            moy.addActionListener(this);
            str = new JMenuItem("Fort");
            str.addActionListener(this);
            
            /**
            * Mise en place de la barre de menu
     		*/
           
            barreMenu.add(jeu);
            barreMenu.add(diff);
           
            jeu.add(pVp);
            jeu.add(pVc);
            jeu.add(quitter);
            
            diff.add(nul);
            diff.add(moy);
            diff.add(str);
            
            /*JButton forceToPlay = new JButton("Forcer à jouer");
            forceToPlay.setBounds(this.getWidth()-140,this.getHeight()-90,120,30);
            add(forceToPlay);
            forceToPlay.addActionListener(this); 
            forceToPlay.addActionListener(
            		  new ActionListener() {
            		    public void actionPerformed(ActionEvent e) {
            		      // création d'un Thread d'exécution
            		      Thread t = new Thread() {
            		        public void run() {
            		        	Pan.forceToPlay();
            		        }
            		      };
            		      t.start();
            		    }
            		  }
            		);*/
            
            timeIA = new JComboBox();
            
            timeIA.setBounds(this.getWidth()-140,this.getHeight()-90,120,30);
    		
    		timeIA.addItem("10");
    		timeIA.addItem("8");
    		timeIA.addItem("6");
    		timeIA.addItem("4");
    		timeIA.addItem("2");
    		
    		timeIA.addItemListener(new ItemListener(){
				public void itemStateChanged(ItemEvent e) {
					// TODO Auto-generated method stub
					if (e.getItem().equals("2")) {
						Pan.setPlayTimeIA(2);
					} else if (e.getItem().equals("4")) {
						Pan.setPlayTimeIA(4);
					} else if (e.getItem().equals("6")) {
						Pan.setPlayTimeIA(6);
					} else if (e.getItem().equals("8")) {
						Pan.setPlayTimeIA(8);
					} else if (e.getItem().equals("10")) {
						Pan.setPlayTimeIA(10);
					}
				}
    		});

            add(timeIA);
            
                
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
	    	Pan=new Goban(this);
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
	            }else if(ChoixOption.equals("joueur contre IA")){
	            	Pan.resetGame(true);
	            	this.repaint();
	            }else if(ChoixOption.equals("Quitter")){
	            	System.exit(ABORT);
	            }else if(ChoixOption.equals("Nulle")){
	            	Pan.resetIA(2);
	            }else if(ChoixOption.equals("Moyen")){
	            	Pan.resetIA(3);
	            }else if(ChoixOption.equals("Fort")){
	            	Pan.resetIA(4);
	            }
			} 
		}
		       
}
