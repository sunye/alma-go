/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
            gaman.onePlayer(PionVal.NOIR);
        } else {
            gaman.onePlayer(PionVal.BLANC);
        }
    }
}
