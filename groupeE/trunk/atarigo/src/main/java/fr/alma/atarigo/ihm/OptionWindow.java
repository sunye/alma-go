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

package fr.alma.atarigo.ihm;

import fr.alma.atarigo.GameManager;

import fr.alma.atarigo.utils.PionVal;
import java.awt.Frame;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 *
 * @author judu
 */
public class OptionWindow extends JDialog {

    /**
     * Constructor
     * @param owner
     * @param title
     * @param modal
     */
    public OptionWindow(Frame owner, String title, boolean modal, GameManager gaman) {
        super(owner, title, modal);
        this.setSize(200, 80);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        this.choixCouleur(gaman);
    }


    /**
     * Initialise le contenu de la boite
     */
    private void choixCouleur(GameManager gaman) {
        String[] pion = {"Noir", "Blanc"};
        //JOptionPane jop = new JOptionPane();
        int choix = JOptionPane.showOptionDialog(null,
                "Quelle couleur voulez-vous jouer ?",
                "Choix pion",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                pion,
                pion[1]);
        if (pion[choix].equals("Noir")) {
            gaman.onePlayer(PionVal.BLANC);
        } else {
            gaman.onePlayer(PionVal.NOIR);
        }
    }
}
