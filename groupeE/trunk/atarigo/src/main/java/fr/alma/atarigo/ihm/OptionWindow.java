/**
 * File {OptionWindow.java}
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
package fr.alma.atarigo.ihm;

import fr.alma.atarigo.GameManager;

import fr.alma.atarigo.utils.StoneVal;
import java.awt.Frame;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 *
 * @author judu
 */
public class OptionWindow extends JDialog {

    /**
     * Constant = Width of the window.
     */
    private static final int WWIDTH = 200;
    /**
     * Constant = Height of the window.
     */
    private static final int WHEIGHT = 80;

    /**
     * Constructor.
     * @param owner : Frame owner.
     * @param title : Title of the window.
     * @param modal : Boolean.
     * @param gaman : The controller of the game.
     */
    public OptionWindow(final Frame owner, final String title,
            final boolean modal, final GameManager gaman) {
        super(owner, title, modal);
        this.setSize(WWIDTH, WHEIGHT);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        this.choixCouleur(gaman);
    }

    /**
     * Initialize the popup and run a game depending of the choice made.
     * @param gaman : The controller of the game.
     */
    private void choixCouleur(final GameManager gaman) {
        String[] pion = {"Noir", "Blanc"};
        int choix = JOptionPane.showOptionDialog(null,
                "Quelle couleur voulez-vous jouer ?",
                "Choix pion",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                pion,
                pion[1]);
        if (pion[choix].equals("Noir")) {
            gaman.onePlayer(StoneVal.WHITE);
        } else {
            gaman.onePlayer(StoneVal.BLACK);
        }
    }
}
