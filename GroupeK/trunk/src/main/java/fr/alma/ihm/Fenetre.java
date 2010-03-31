package fr.alma.ihm;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

import fr.alma.modele.Coordonnee;
import fr.alma.modele.CouleurPion;
import fr.alma.modele.Coup;
import fr.alma.modele.intelligence.SunTsu;

public class Fenetre extends JFrame{
	

	private static final long serialVersionUID = 1L;
	private JMenuBar barreMenu;
	private JMenu jeu;
	private JMenu a_propos;
	private JMenuItem nouv;
	private JMenuItem quitter;
	//Panel représentant le goban
	private GobanPanel Pan;
	
	public Fenetre(String s){
		super(s);
		setSize(600,600);
		
		/**
         * Creations des différents outils permettant la mise en place de la barre de menu
         */
		
		barreMenu=new JMenuBar();
		
		jeu=new JMenu("Jeu");
		a_propos=new JMenu("A Propos");
		
		nouv=new JMenuItem("Nouvelle partie");
		quitter=new JMenuItem("Quitter");
		
		JMenuItem jouer= new JMenuItem("JOUE !");
		jeu.add(jouer);
		jouer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SunTsu intel=new SunTsu();
				Coordonnee cp= intel.nextMove(Pan.getGoban(), CouleurPion.NOIR);
				Pan.getGoban().ajouterPion(cp.getX(), cp.getY());
				
			}
		});
		
		/**
         * Mise en place de la barre de menu
         */
		
		barreMenu.add(jeu);
		barreMenu.add(a_propos);
		
		jeu.add(nouv);
		jeu.add(quitter);
	
		this.setJMenuBar(barreMenu); 
		
		/**
         * Creation du panel contenant le goban
         */

		Pan=new GobanPanel();
		
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
	
}




