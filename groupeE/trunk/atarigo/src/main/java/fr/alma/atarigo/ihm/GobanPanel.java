/*
 * This class was originaly developed by Anthony Caillaud. Thanks to him for sharing.
 */
package fr.alma.atarigo.ihm;

import fr.alma.atarigo.utils.Game;
import fr.alma.atarigo.utils.exceptions.BadPlaceException;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * This class describes the Goban board, with the stones.
 * We start and play the game threw it.
 * @author judu
 */
class GobanPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private ImageIcon goban;
    private ImageIcon pionB;
    private ImageIcon pionN;
    //The game engine
    private Game game;
    //coordonnées propres au Goban
    private int gobanX;
    private int gobanY;
    private final int cotePion;
    private final int debutX = 45;
    private final int debutY = 45;
    private final int coteCase = 67;
    private final int precision = 4;
    private MouseAdapter mouseL;

    public GobanPanel() {
        super();

        /**
         * Creation de l'image du goban grâce à l'URL donné
         */
        java.net.URL gobanURL = Fenetre.class.getResource("images/goban.png");
        //vérification de l'existence de l'image
        if (gobanURL != null) {
            goban = new ImageIcon(gobanURL);
        }

        /**
         * Creation de l'image du pion blanc
         */
        java.net.URL pionB_URL = Fenetre.class.getResource("images/blanc.png");
        //vérification de l'existence de l'image
        if (pionB_URL != null) {
            pionB = new ImageIcon(pionB_URL);
        }

        /**
         * Creation de l'image du pion noir
         */
        java.net.URL pionN_URL = Fenetre.class.getResource("images/noir.png");
        //vérification de l'existence de l'image
        if (pionN_URL != null) {
            pionN = new ImageIcon(pionN_URL);
        }

        cotePion = pionB.getIconWidth();
        //repaint();
    }

    private void processMouseClicked(MouseEvent evt) {

        int resteX = Math.max((evt.getX() - debutX) % coteCase, 0);
        int col = Math.max(evt.getX() - debutX, 0) / coteCase; // On récupère

        if (resteX > ((precision - 1) * coteCase / precision)) {
            col += (col == 8) ? 0 : 1;
        } else if (resteX > (coteCase / 3)) {
            return;
        }


        int resteY = Math.max((evt.getY() - debutY) % coteCase, 0);
        int lin = Math.max(evt.getY() - debutY, 0) / coteCase; // On récupère

        if (resteY > ((precision - 1) * coteCase / precision)) {
            lin += (lin == 8) ? 0 : 1;
        } else if (resteY > (coteCase / 3)) {
            return;
        }

        game.playAt(lin, col);

        repaint();
        /**
         * At a certain point, the game will end. At this point, we need to stop the listener.
         */

        if (game.isEnd()) {
            // TODO: display the winner.
            removeMouseListener(mouseL);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //Affichage du goban
        g.drawImage(goban.getImage(), 0, 0, this);
        if (game != null) {
            //Affichage de tout les pions en parcourant la matrice
            for (int i = 0; i < 9; ++i) {
                for (int j = 0; j < 9; ++j) {
                    try {
                        //System.out.println(goban_tab.getGoban()[i][j].getCouleur());
                    /* Ok, now, let's calculate the coordinates of the image */
                        int xPion;
                        int yPion;
                        xPion = i * coteCase + debutX - (cotePion / 2);
                        yPion = j * coteCase + debutY - (cotePion / 2);

                        switch (game.getStone(j, i).getCouleur()) {
                            case NOIR:
                                g.drawImage(pionN.getImage(), xPion, yPion, this);
                                break;
                            case BLANC:
                                g.drawImage(pionB.getImage(), xPion, yPion, this);
                                break;
                            case RIEN:
                                break;
                        }
                    } catch (BadPlaceException ex) {
                        Logger.getLogger(GobanPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }

    @Override
    public void update(Graphics g) {
        paintComponent(g);
    }

    public void startGame() {
        game = new Game();

        addMouseListener(mouseL = new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                processMouseClicked(e);
            }
        });

        repaint();
    }
}
