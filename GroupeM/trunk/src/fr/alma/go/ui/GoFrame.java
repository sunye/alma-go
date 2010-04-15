package fr.alma.go.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import fr.alma.go.Game;

public class GoFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JMenuBar barreMenu = new JMenuBar();
	private JMenu jeu = new JMenu("Let's go play go !");
	private JMenu a_propos = new JMenu("About");
	private JMenu nouv = new JMenu("Enter the matrix");
	private JMenuItem item1 = new JMenuItem("Human versus CPU");
	private JMenuItem item2 = new JMenuItem(
			"Human versus Human (why the hell don't you play IRL ??)");
	private JMenuItem propos = new JMenuItem("?");
	private JMenuItem quitter = new JMenuItem("Take the blue pill");
	private OptionWindow options;
	// Panel représentant le goban
	private GoPanel pan;
	private boolean sendData;
	private Game controleur;

	/**
	 * Open a window with the goban and a menu
	 * 
	 * @param s
	 *            : Title of the window
	 */
	public GoFrame(String s) {

		super(s);
		setSize(750, 680);
		this.setLocationRelativeTo(null);

		pan = new GoPanel();
		/**
		 * On ajoute le GobanPanel dans la fenetre principale
		 */
		add(pan);
		controleur = new Game(pan, this);

		/**
		 * Mise en place de la barre de menu
		 */
		// Un joueur :
		item1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				OptionWindow ow = new OptionWindow(null,
						"Choisissez votre couleur !", true, controleur);
			}
		});
		this.nouv.add(item1);

		// Deux joueurs :
		item2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				controleur.twoPlayers();
			}
		});
		this.nouv.add(item2);
		this.jeu.add(this.nouv);
		// Ajout d'un separateur
		this.jeu.addSeparator();

		// Quitter :
		quitter.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});

		this.jeu.add(quitter);
		this.barreMenu.add(jeu);

		// TODO : changer le "Bla bla bla..." du "A propos" !
		// A propos :
		propos.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				JOptionPane jop = new JOptionPane();
				jop
						.showMessageDialog(
								null,
								"Matrix go interface, all credits to the great architect\n\t© Julien Durillon & ClotHilde Massot",
								"About", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		this.a_propos.add(propos);
		this.barreMenu.add(a_propos);

		this.setJMenuBar(barreMenu);
		this.setVisible(true);

		/**
		 * Arrêt de l'appli quand on clique sur la croix
		 */
		setDefaultCloseOperation(EXIT_ON_CLOSE);

	}

	/**
	 * Open a window endgame to announce the winner of the game.
	 */
	public void finJeu() {
		JOptionPane jop = new JOptionPane();
		StringBuilder strb = new StringBuilder();
		strb.append("Le grand gagnant de cette partie est : Joueur ");
		if (controleur.getGame().getWhiteTaken() > controleur.getGame()
				.getBlackTaken())
			strb.append("Noir !");
		else
			strb.append("Blanc !");
		jop.showMessageDialog(null, strb, "Partie terminée !",
				JOptionPane.INFORMATION_MESSAGE);
	}
}