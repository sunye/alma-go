package fr.alma.ihm;

import java.awt.*;
import java.awt.event.MouseEvent;

import javax.swing.*;

import fr.alma.controler.Controler;
import fr.alma.modele.CouleurPion;






public class Fenetre extends JFrame{
	

	private static final long serialVersionUID = 1L;
	private JMenuBar barreMenu;
	private JMenu jeu;
	private JMenu a_propos;
	private JMenuItem nouv;
	private JMenuItem quitter;
	//Panel représentant le goban
	private GobanPanel Pan;
	private Controler control;
	
	public Fenetre(Controler control){
		super("Le jeu de Go qu'il est bien");
		setSize(440,490);
		this.setControl(control);
		/**
         * Creations des différents outils permettant la mise en place de la barre de menu
         */
		
		barreMenu=new JMenuBar();
		
		jeu=new JMenu("Jeu");
		a_propos=new JMenu("A Propos");
		
		nouv=new JMenuItem("Nouvelle partie");
		quitter=new JMenuItem("Quitter");
		
		JMenuItem aienabled=new JMenuItem("Ai enableld");
		
		jeu.add(aienabled);
		aienabled.addActionListener(control.getFactory().modeAi());
		/**
         * Mise en place de la barre de menu
         */
		
		barreMenu.add(jeu);
		barreMenu.add(a_propos);
		
		jeu.add(nouv);
		jeu.add(quitter);
	
		this.setJMenuBar(barreMenu); 
		
		nouv.addActionListener(control.getFactory().newGameListener());
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
			
		/**
	     *  Arrêt de l'appli quand on clique sur la croix
	     */
		
	    setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	
	public void affichageVainqueur(CouleurPion coul){
		Pan.afficheGagnant(coul);
		
	}
	
	
	public void clicPanelGo(MouseEvent e){
		Pan.clicBoard(e);
	}
	
	
	public int getColSize() {
		return Pan.getColSize();
	}

	public int getRowSize() {
		return Pan.getRowSize();
	}
	
	public void repaintBoard(){
		Pan.repaint();
	}


	public void setControl(Controler control) {
		this.control = control;
	}


	public Controler getControl() {
		return control;
	}
}




