package fr.alma.ui;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.JMenu;

import fr.alma.atarigo.AtariGo;
import fr.alma.atarigo.Joueur;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Représente l'application graphique du jeu d'Atarigo.
 * @author vincent
 *
 */
class MonApplication extends JFrame {

/**
 * plateau de l'atarigo
 */
 protected UIPlateau uiplateau;
 //TODO décrire les autres attributs
 protected AtariGo atarigo;
 protected JPanel panInfos;
 protected JLabel labelT;
 protected JLabel labelCpsjoues;
 protected JOptionPane message;
 
/**
 * action consistant à quitter l'application. 
 */
 protected ActionQuitter actionQuitter;

/**
 *  construit les barres de menus et d'outils.  
 */
 protected void contruireBarresMenusEtOutils() {
	JMenuBar barreMenus = new JMenuBar();
	JToolBar barreOutils = new JToolBar();
	JMenu menu = new JMenu("Commandes");
	menu.setMnemonic('C');
	ActionNouveau actionNouveau = new ActionNouveau(this);
	menu.add(actionNouveau);
	ActionAPropos actionAPropos = new ActionAPropos(this);
	menu.add(actionAPropos);
	menu.addSeparator();
	actionQuitter = new ActionQuitter(this);
	menu.add(actionQuitter);
	barreOutils.add(actionNouveau);
	barreOutils.add(actionAPropos);
	barreOutils.addSeparator();
	barreOutils.add(actionQuitter);
	barreMenus.add(menu);
	setJMenuBar(barreMenus);
	barreOutils.setFloatable(false);
	getContentPane().add(barreOutils, BorderLayout.NORTH);
 }

/**
 * constructeur logique
 */
 public MonApplication() {

	// Construction de la barre de menus et d'outils.
	contruireBarresMenusEtOutils();

	atarigo = new AtariGo(9,9);
	atarigo.desactiver();

	//création d'une instance du plateau
	//Plateau plateau = new Plateau(9,9);
	//atarigo = new AtariGo(9,9);
	//test
	//plateau.ecrireCase(new Position(0,0), Pion.BLANC);
	//plateau.ecrireCase(new Position(1,2), Pion.NOIR);
	//plateau.ecrireCase(new Position(8,8), Pion.NOIR);
	
	// Création d'une instance graphique du plateau.
	uiplateau = new UIPlateau(this);
	uiplateau.setMaximumSize(new Dimension(360,360));
	
	// Positionnement du plateau au milieu de la fenêtre.
	getContentPane().add(uiplateau, BorderLayout.CENTER);
	panInfos = new JPanel();
	panInfos.add(new JLabel("Infos"));
	labelT = new JLabel();
	labelCpsjoues = new JLabel();
	panInfos.add(labelT);
	panInfos.add(labelCpsjoues);
	panInfos.setPreferredSize(new Dimension(270,450));
	getContentPane().add(panInfos,BorderLayout.EAST);
	//uiplateau.tempo.start();
	// Nom de la fenêtre.
	setTitle("Atarigo");
	
	// Positionnement initial au milieu de l'écran.
	setLocationRelativeTo(null);

	// Comportement par défaut en cas de suppression de la fenêtre : tout
	// doit passer par l'action Quitter.
	addWindowListener(new WindowAdapter() {
		public void windowClosing(WindowEvent evt) {
		    actionQuitter.quitter();
		}
	    });

 }
 
 /**
  * démarre une partie d'Atarigo
  * @param joueur1 le type de joueur humain ou IA
  * @param joueur2 le type de joueur humain ou IA
  * @throws InterruptedException
  */
 public void demarrer(Joueur joueur1, Joueur joueur2,int nb_challenge) throws InterruptedException{
	 atarigo.fini=true;
	 atarigo = new AtariGo(9,9,joueur1,joueur2);
	 atarigo.nb_challenge=nb_challenge;
	 uiplateau.nouvellePartie();
	 System.out.println("nouvelle partie");
 }


}