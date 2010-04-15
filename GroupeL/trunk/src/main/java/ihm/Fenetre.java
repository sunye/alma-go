package ihm;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.*;

/*$Author$ 
 * $Date$ 
 * $Revision$ 
 * 
 *Copyright (C) 2010  Dejean Charles and Pottier Vincent
 *
 *This program is free software: you can redistribute it and/or modify
 *it under the terms of the GNU General Public License as published by
 *the Free Software Foundation, either version 3 of the License, or
 *(at your option) any later version.
 *
 *This program is distributed in the hope that it will be useful,
 *but WITHOUT ANY WARRANTY; without even the implied warranty of
 *MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *GNU General Public License for more details.
 *
 *You should have received a copy of the GNU General Public License
 *along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * */

/**
 * @author DEJEAN Charles, POTTIER Vincent
 *
 * this class create the windows where the game take place. 
 *
 */
public class Fenetre extends JFrame implements ActionListener {
       
        private static final long serialVersionUID = 1L;
        // element of the window
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
           
            // Creation of the different element of the window
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
            
            barreMenu.add(jeu);
            barreMenu.add(diff);
           
            jeu.add(pVp);
            jeu.add(pVc);
            jeu.add(quitter);
            
            diff.add(nul);
            diff.add(moy);
            diff.add(str);
                        
            timeIA = new JComboBox();
            
            timeIA.setBounds(this.getWidth()-140,this.getHeight()-90,120,30);
    		
    		timeIA.addItem("10 s");
    		timeIA.addItem("8 s");
    		timeIA.addItem("6 s");
    		timeIA.addItem("4 s");
    		timeIA.addItem("2 s");
    		
    		timeIA.addItemListener(new ItemListener(){
				public void itemStateChanged(ItemEvent e) {
					if (e.getItem().equals("2 s")) {
						Pan.setPlayTimeIA(2);
					} else if (e.getItem().equals("4 s")) {
						Pan.setPlayTimeIA(4);
					} else if (e.getItem().equals("6 s")) {
						Pan.setPlayTimeIA(6);
					} else if (e.getItem().equals("8 s")) {
						Pan.setPlayTimeIA(8);
					} else if (e.getItem().equals("10 s")) {
						Pan.setPlayTimeIA(10);
					}
				}
    		});

            add(timeIA);
            
                
            this.setJMenuBar(barreMenu);
         
            // game start
            start();      
                   
            // close if click one cross          
            setDefaultCloseOperation(EXIT_ON_CLOSE);
        }

		private void start() {
			// panel creation
	    	Pan=new Goban(this);
            Pan.setBorder(BorderFactory.createLineBorder(Color.black));
            // add the panel to the window
            this.add(Pan);
		}

		public void actionPerformed(ActionEvent evt) {
			if (evt.getSource()instanceof JMenuItem) {
				// management of the different action in the window
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
