/**
 * File {GobanPanel.java}
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
 * along with this program;
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of this program.
 */

/*
 * This class was originaly developed by Anthony Caillaud.
 * Thanks to him for sharing.
 */
package fr.alma.atarigo.ihm;

import fr.alma.atarigo.GameManager;
import fr.alma.atarigo.utils.Game;
import fr.alma.atarigo.utils.Goban;
import fr.alma.atarigo.utils.StoneVal;
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

    /**
     * Constant = serialVersionUID.
     */
    private static final long serialVersionUID = 1L;
    /**
     * Constant = 10.
     */
    private static final int TEN = 10;
    /**
     * Constant = 20.
     */
    private static final int TWENTY = 20;
    /**
     * Constant = 40.
     */
    private static final int FORTY = 40;
    /**
     * Image of the goban.
     */
    private ImageIcon goban;
    /**
     * Icon of a white stone.
     */
    private ImageIcon pionB;
    /**
     * Icon of a black stone.
     */
    private ImageIcon pionN;
    /**
     * Size of a stone.
     */
    private final int cotePion;
    /**
     * Constant = X beginning.
     */
    private final int debutX = 45;
    /**
     * Constant = Y beginning.
     */
    private final int debutY = 45;
    /**
     * Constant = Size of a box.
     */
    private final int coteCase = 67;
    /**
     * Constant = Precision, for some adjustments.
     */
    private final int precision = 4;
    /**
     * Mouse Adapter.
     */
    private MouseAdapter mouseL;
    /**
     * The controller of the game.
     */
    private GameManager controleur;

    /**
     * Constructor.
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
        java.net.URL pionBlancURL =
                Fenetre.class.getResource("images/blanc.png");
        //vérification de l'existence de l'image
        if (pionBlancURL != null) {
            pionB = new ImageIcon(pionBlancURL);
        }

        /**
         * Creation de l'image du pion noir
         */
        java.net.URL pionNoirURL = Fenetre.class.getResource("images/noir.png");
        //vérification de l'existence de l'image
        if (pionNoirURL != null) {
            pionN = new ImageIcon(pionNoirURL);
        }

        cotePion = pionB.getIconWidth();
        //repaint();

        mouseL = new MouseAdapter() {

            @Override
            public void mouseClicked(final MouseEvent e) {
                processMouseClicked(e);
            }
        };
    }

    /**
     * What happens when the player clics on the Goban.
     * (Apply a move).
     * @param evt : clic of the mouse.
     */
    private void processMouseClicked(final MouseEvent evt) {

        // We clic on (x, y). On the goban, it equals (x-debutX)
        // We get what remains after dividing by coteCase.
        int resteX = Math.max((evt.getX() - debutX) % coteCase, 0);
        // col is the quotient of x-debutX by coteCase.
        int col = Math.max(evt.getX() - debutX, 0) / coteCase;


        // Let's make some adjustments
        if (resteX > ((precision - 1) * coteCase / precision)) {
            col += (col == Goban.getTaille() - 1) ? 0 : 1;
        } else if (resteX > (coteCase / precision)) {
            // If out of the goban, return
            return;
        }


        int resteY = Math.max((evt.getY() - debutY) % coteCase, 0);
        int lin = Math.max(evt.getY() - debutY, 0) / coteCase;

        if (resteY > ((precision - 1) * coteCase / precision)) {
            lin += (lin == Goban.getTaille() - 1) ? 0 : 1;
        } else if (resteY > (coteCase / precision)) {
            // If out of the goban, return
            return;
        }

        controleur.appliqueCoup(lin, col);

        repaint();
        /**
         * At a certain point, the game will end.
         * At this point, we need to stop the listener.
         */
        if (controleur.isEnd()) {
            removeMouseListener(mouseL);
            controleur.endGameDialog();
        }
    }

    @Override
    public void paintComponent(final Graphics g) {
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
                            /* Ok, now,
                             let's calculate the coordinates of the image */
                            int xPion;
                            int yPion;
                            xPion = i * coteCase + debutX - (cotePion / 2);
                            yPion = j * coteCase + debutY - (cotePion / 2);

                            switch (game2.getStone(j, i).getCouleur()) {
                                case BLACK:
                                    g.drawImage(pionN.getImage(),
                                            xPion, yPion, this);
                                    break;
                                case WHITE:
                                    g.drawImage(pionB.getImage(),
                                            xPion, yPion, this);
                                    break;
                                default:
                                    break;
                            }
                        } catch (BadPlaceException ex) {
                            Logger.getLogger(GobanPanel.class.
                                    getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                //Affichage joueur courant :
                g.drawString("Joueur en cours :",
                        goban.getImage().getWidth(this) + TEN, TWENTY);
                if (game2.getCurrentPlayer() == StoneVal.WHITE) {
                    g.drawImage(pionB.getImage(),
                            goban.getImage().getWidth(this) + TWENTY,
                            FORTY, this);
                } else {
                    g.drawImage(pionN.getImage(),
                            goban.getImage().getWidth(this) + TWENTY,
                            FORTY, this);
                }

            }
        }
    }

    @Override
    public void update(final Graphics g) {
        paintComponent(g);
    }

    /**
     * Activate the mouse, as its name says !
     */
    public void activateMouse() {
        addMouseListener(mouseL = new MouseAdapter() {

            @Override
            public void mouseClicked(final MouseEvent e) {
                processMouseClicked(e);
            }
        });
    }

    /**
     * Desactivate the mouse, as its name says !
     */
    public void desactivateMouse() {
        removeMouseListener(mouseL);
    }

    /**
     * Start a game !
     * @param gm : Controller of the game.
     * @param curGame : Current game.
     */
    public void startGame(final GameManager gm, final Game curGame) {
        activateMouse();
        this.controleur = gm;
        repaint();
    }
}
