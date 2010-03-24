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
import fr.alma.atarigo.Player;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Représente l'application graphique du jeu d'Atarigo.
 * @author vincent
 *
 */
class MyApplication extends JFrame {

/**
 * plateau de l'atarigo
 */
 protected UIGoban uiGoban;
 //TODO decrire les autres attributs
 protected AtariGo atarigo;
 protected InfoPanel panInfos;
 protected JOptionPane message;
 protected JMenuBar menBar;
 protected JToolBar toolBar;
 protected JMenu menu;
 protected NewGameAction newGameAction;
 protected AboutAction aboutAction;
 
 
/**
 * action consistant à quitter l'application. 
 */
 protected QuitAction quitAction;

/**
 *  construit les barres de menus et d'outils.  
 */
 protected void buildAll() {
	menBar = new JMenuBar();
	toolBar = new JToolBar();
	menu = new JMenu("Commandes");
	menu.setMnemonic('C');
	newGameAction = new NewGameAction(this);
	menu.add(newGameAction);
	aboutAction = new AboutAction(this);
	menu.add(aboutAction);
	menu.addSeparator();
	quitAction = new QuitAction(this);
	menu.add(quitAction);
	toolBar.add(newGameAction);
	toolBar.add(aboutAction);
	toolBar.addSeparator();
	toolBar.add(quitAction);
	menBar.add(menu);
	setJMenuBar(menBar);
	toolBar.setFloatable(false);
	getContentPane().add(toolBar, BorderLayout.NORTH);
 }

/**
 * constructeur logique
 */
 public MyApplication() {

	// Construction de la barre de menus et d'outils.
	buildAll();

	atarigo = new AtariGo(9,9);
	atarigo.shutDown();

	//création d'une instance du plateau
	//Plateau plateau = new Plateau(9,9);
	//atarigo = new AtariGo(9,9);
	//test
	//plateau.ecrireCase(new Position(0,0), Pion.BLANC);
	//plateau.ecrireCase(new Position(1,2), Pion.NOIR);
	//plateau.ecrireCase(new Position(8,8), Pion.NOIR);
	
	// Création d'une instance graphique du plateau.
	uiGoban = new UIGoban(this);
	uiGoban.setMaximumSize(new Dimension(360,360));
	
	// Positionnement du plateau au milieu de la fenêtre.
	getContentPane().add(uiGoban, BorderLayout.CENTER);
	panInfos = new InfoPanel();
	panInfos.buildAll();
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
		    quitAction.quit();
		}
	    });

 }
 
 /**
  * démarre une partie d'Atarigo
  * @param player1 le type de joueur humain ou IA
  * @param player2 le type de joueur humain ou IA
  * @throws InterruptedException
  */
 public void startGame(Player player1, Player player2,int challengeNb) throws InterruptedException{
	 atarigo.end=true;
	 atarigo = new AtariGo(9,9,player1,player2);
	 atarigo.captureObjective=challengeNb;
	 uiGoban.newGame();
	 System.out.println("nouvelle partie");
 }


}