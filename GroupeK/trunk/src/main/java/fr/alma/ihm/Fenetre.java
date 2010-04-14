package fr.alma.ihm;

import java.awt.*;
import javax.swing.*;

import fr.alma.controler.Controler;
import fr.alma.modele.CouleurPion;

/*$Author$ 
 * $Date$
 * $Revision$ 
  * 
 *Copyright (C) 2010  Fortun Manoël & Caillaud Anthony
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
 * 
 * @author Manoël Fortun et Anthony "Bambinôme" Caillaud
 * The main class of the UI.
 */
public class Fenetre extends JFrame{
	
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * The panel that containt the representation of the board
	 */
	private GobanPanel Pan;
	
	/**
	 * The controler.
	 */
	private Controler controler;
	
	/**
	 * Constructor with the controler.
	 * @param control the controler
	 */
	public Fenetre(Controler control){
		super("Le jeu de Go qu'il est bien");
		setSize(440,490);
		this.controler=control;
		this.setResizable(false);   
		
		/**
	     *  Arrêt de l'appli quand on clique sur la croix
	     */
		
	    setDefaultCloseOperation(EXIT_ON_CLOSE);
	    
		/**
         * Creations des différents outils permettant la mise en place de la barre de menu
         */
		
		JMenuBar barreMenu=new JMenuBar();
		
		JMenu jeu=new JMenu("Jeu");
		JMenu a_propos=new JMenu("A Propos");
		
		JMenuItem nouv=new JMenuItem("Nouvelle partie");
		JMenuItem quitter=new JMenuItem("Quitter");
		JMenuItem quikafait= new JMenuItem("QuiKaFait ?");
				
		/**
         * Mise en place de la barre de menu
         */
		
		barreMenu.add(jeu);
		barreMenu.add(a_propos);
		a_propos.add(quikafait);
		
				
		jeu.add(nouv);
		jeu.add(quitter);
	
		this.setJMenuBar(barreMenu); 
		
		quikafait.addActionListener(control.getFactory().AfficheAPropos());
		nouv.addActionListener(control.getFactory().afficheDiagNewGame());
		quitter.addActionListener(control.getFactory().quitterListener());
		/**
         * Creation du panel contenant le goban
         */

		Pan=new GobanPanel(control);
		Pan.setBorder(BorderFactory.createLineBorder(Color.black));
			
		/**
         * On ajoute le GobanPanel dans la fenetre principale
         */
		add(Pan);
	}
	
	/**
	 * Show the winner
	 * @param coul the color of the winner
	 */
	public void affichageVainqueur(CouleurPion coul) {
		JOptionPane.showMessageDialog(this,"Les "+coul+"S ont gagnés!");
	}
	
	/**
	 * Show a wonderfull message in a Jdialog
	 * @param aWonderFullMessage 
	 */
	public void affichageMessage(String aWonderFullMessage) {
		
		JOptionPane.showMessageDialog(this,aWonderFullMessage);
		
	}
	
	/**
	 * get the col size of the goban panel
	 * @return the col size
	 */
	public int getColSize() {
		return Pan.getColSize();
	}

	/**
	 * get the row size of the goban panel
	 * @return the row size
	 */
	public int getRowSize() {
		return Pan.getRowSize();
	}
	
	/**
	 * repaint the board
	 */
	public void repaintBoard(){
		Pan.repaint();
	}

	/**
	 * get the controler.
	 * @return the controler
	 */
	public Controler getControler() {
		return controler;
	}
}




