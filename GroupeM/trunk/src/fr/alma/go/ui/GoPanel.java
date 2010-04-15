package fr.alma.go.ui;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import fr.alma.go.Game;
import fr.alma.go.goban.Goban;

public final class GoPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private ImageIcon goban;
	private ImageIcon pionB;
	private ImageIcon pionN;
	private final int cotePion;
	private final int debutX = 45;
	private final int debutY = 45;
	private final int coteCase = 67;
	private final int precision = 4;
	private MouseAdapter mouseL;
	private Game controleur;

	/**
	 * Constructor
	 */
	public GoPanel() {
		super();

		/**
		 * Creation de l'image du goban grâce à l'URL donné
		 */
		java.net.URL gobanURL = GoFrame.class.getResource("images/goban.png");
		// vérification de l'existence de l'image
		if (gobanURL != null) {
			goban = new ImageIcon(gobanURL);
		}

		/**
		 * Creation de l'image du pion blanc
		 */
		java.net.URL pionB_URL = GoFrame.class.getResource("images/blanc.png");
		// vérification de l'existence de l'image
		if (pionB_URL != null) {
			pionB = new ImageIcon(pionB_URL);
		}

		/**
		 * Creation de l'image du pion noir
		 */
		java.net.URL pionN_URL = GoFrame.class.getResource("images/noir.png");
		// vérification de l'existence de l'image
		if (pionN_URL != null) {
			pionN = new ImageIcon(pionN_URL);
		}

		cotePion = pionB.getIconWidth();
		// repaint();

		mouseL = new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				processMouseClicked(e);
			}
		};
	}

	private void processMouseClicked(MouseEvent evt) {

		int resteX = Math.max((evt.getX() - debutX) % coteCase, 0);
		int abs = Math.max(evt.getX() - debutX, 0) / coteCase; // On récupère

		if (resteX > ((precision - 1) * coteCase / precision)) {
			abs += (abs == 8) ? 0 : 1;
		} else if (resteX > (coteCase / 3)) {
			return;
		}

		int resteY = Math.max((evt.getY() - debutY) % coteCase, 0);
		int ord = Math.max(evt.getY() - debutY, 0) / coteCase; // On récupère

		if (resteY > ((precision - 1) * coteCase / precision)) {
			ord += (ord == 8) ? 0 : 1;
		} else if (resteY > (coteCase / 3)) {
			return;
		}

		controleur.appliqueCoup(ord, abs);

		repaint();
		/**
		 * At a certain point, the game will end. At this point, we need to stop
		 * the listener.
		 */
		if (controleur.gameOver()) {
			// TODO: display the winner.
			removeMouseListener(mouseL);
			controleur.dialogueFinJeu();
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// Affichage du goban
		g.drawImage(goban.getImage(), 0, 0, this);
		if (controleur != null) {
			final Goban game2 = controleur.getGame();
			if (game2 != null) {
				// Affichage de tous les pions en parcourant la matrice
				for (int i = 0; i < 9; ++i) {
					for (int j = 0; j < 9; ++j) {
						// System.out.println(goban_tab.getGoban()[i][j].getCouleur());
						/* Ok, now, let's calculate the coordinates of the image */
						int xPion;
						int yPion;
						xPion = i * coteCase + debutX - (cotePion / 2);
						yPion = j * coteCase + debutY - (cotePion / 2);

						switch (game2.getPlate()[i][j].getColor()) {
						case 'b':
							g.drawImage(pionN.getImage(), xPion, yPion, this);
							break;
						case 'w':
							g.drawImage(pionB.getImage(), xPion, yPion, this);
							break;
						default:
							break;
						}
					}
				}
				// Affichage joueur courant :
//				g.drawString("Joueur en cours :", goban.getImage().getWidth(
//						this) + 10, 20);
//				if (game2.getCurrentPlayer() == PionVal.BLANC) {
//					g.drawImage(pionB.getImage(), goban.getImage().getWidth(
//							this) + 20, 40, this);
//				} else {
//					g.drawImage(pionN.getImage(), goban.getImage().getWidth(
//							this) + 20, 40, this);
//				}

			}
		}
	}

	@Override
	public void update(Graphics g) {
		paintComponent(g);
	}

	public void activateMouse() {
		addMouseListener(mouseL = new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				processMouseClicked(e);
			}
		});
	}

	public void desactivateMouse() {
		removeMouseListener(mouseL);
	}

	public void startGame(final Game gm, final Goban curGame) {
		activateMouse();
		this.controleur = gm;
		repaint();
	}
}