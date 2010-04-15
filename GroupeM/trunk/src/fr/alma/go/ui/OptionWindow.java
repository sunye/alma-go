/**
 * File {OptionWindow.java}
 * Last commited $Date: 2010-04-14 00:18:51 +0200 (mer. 14 avril 2010) $
 * By $Author: clotilde.massot $
 * Revision $Rev: 375 $
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

package fr.alma.go.ui;

import fr.alma.go.Game;
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
    public OptionWindow(Frame owner, String title, boolean modal, Game gaman) {
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
    private void choixCouleur(Game gaman) {
        String[] pion = {"Take the black pill", "Take the white pill"};
        //JOptionPane jop = new JOptionPane();
        int choix = JOptionPane.showOptionDialog(null,
                "Choice is all yours, Neo...",
                "Follow the white rabbit",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                pion,
                pion[1]);
        if (pion[choix].equals("Noir")) {
             gaman.onePlayer('w');
        } else {
            gaman.onePlayer('b');
        }
    }
}