/**
 * File {Nom du fichier}
 * Last commited $Date$
 * By $Author$
 * Revision $Rev$
 *
 * Copyright (C) 2010 Clotilde Massot & Julien Durillon
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of this program.
 */

/*
 * This class was originaly developed by Anthony Caillaud. Thanks to him for sharing.
 */
package fr.alma.atarigo.ihm;

import fr.alma.atarigo.GameManager;
import fr.alma.atarigo.utils.Game;
import fr.alma.atarigo.utils.Goban;
import fr.alma.atarigo.utils.PionVal;
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
public final class GobanPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private ImageIcon goban;
    private ImageIcon pionB;
    private ImageIcon pionN;
    //The game engine
    //coordonnées propres au Goban
    private int gobanX;
    private int gobanY;
    private final int cotePion;
    private final int debutX = 45;
    private final int debutY = 45;
    private final int coteCase = 67;
    private final int precision = 4;
    private MouseAdapter mouseL;
    private GameManager controleur;

    /**
     * Constructor
     */
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

        mouseL = new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                processMouseClicked(e);
            }
        };
    }

    private void processMouseClicked(MouseEvent evt) {

        int resteX = Math.max((evt.getX() - debutX) % coteCase, 0);
        int col = Math.max(evt.getX() - debutX, 0) / coteCase; // On récupère

        if (resteX > ((precision - 1) * coteCase / precision)) {
            col += (col == Goban.getTaille() - 1) ? 0 : 1;
        } else if (resteX > (coteCase / 3)) {
            return;
        }


        int resteY = Math.max((evt.getY() - debutY) % coteCase, 0);
        int lin = Math.max(evt.getY() - debutY, 0) / coteCase; // On récupère

        if (resteY > ((precision - 1) * coteCase / precision)) {
            lin += (lin == Goban.getTaille() - 1) ? 0 : 1;
        } else if (resteY > (coteCase / 3)) {
            return;
        }

        controleur.appliqueCoup(lin, col);

        repaint();
        /**
         * At a certain point, the game will end. At this point, we need to stop the listener.
         */
        if (controleur.isEnd()) {
            // TODO: display the winner.
            removeMouseListener(mouseL);
            controleur.dialogueFinJeu();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //Affichage du goban
        g.drawImage(goban.getImage(), 0, 0, this);
        if (controleur != null) {
            final Game game2 = controleur.getGame();
            if (game2 != null) {
                //Affichage de tous les pions en parcourant la matrice
                for (int i = 0; i < Goban.getTaille(); ++i) {
                    for (int j = 0; j < Goban.getTaille(); ++j) {
                        try {
                            //System.out.println(goban_tab.getGoban()[i][j].getCouleur());
                            /* Ok, now, let's calculate the coordinates of the image */
                            int xPion;
                            int yPion;
                            xPion = i * coteCase + debutX - (cotePion / 2);
                            yPion = j * coteCase + debutY - (cotePion / 2);

                            switch (game2.getStone(j, i).getCouleur()) {
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
                //Affichage joueur courant :
                g.drawString("Joueur en cours :", goban.getImage().getWidth(this) + 10, 20);
                if (game2.getCurrentPlayer() == PionVal.BLANC) {
                    g.drawImage(pionB.getImage(), goban.getImage().getWidth(this) + 20, 40, this);
                } else {
                    g.drawImage(pionN.getImage(), goban.getImage().getWidth(this) + 20, 40, this);
                }

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

    public void startGame(final GameManager gm, final Game curGame) {
        activateMouse();
        this.controleur = gm;
        repaint();
    }
}
