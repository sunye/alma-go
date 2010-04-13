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
 * This file has been originaly developed by Anthony Caillaud. Thanks to him for sharing.
 */
package fr.alma.atarigo.ihm;

import fr.alma.atarigo.GameManager;
import fr.alma.atarigo.utils.PionVal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * This class describes the main window of the application. It defines main buttons and actions.
 * 
 * @author judu
 */
public class Fenetre extends JFrame {

    private static final long serialVersionUID = 1L;
    private JMenuBar barreMenu = new JMenuBar();
    private JMenu jeu = new JMenu("Jeu");
    private JMenu a_propos = new JMenu("À Propos");
    private JMenu nouv = new JMenu("Nouvelle partie");
    private JMenuItem item1 = new JMenuItem("1 joueur");
    private JMenuItem item2 = new JMenuItem("2 joueurs");
    private JMenuItem propos = new JMenuItem("?");
    private JMenuItem quitter = new JMenuItem("Quitter");
    private OptionWindow options;
    //Panel représentant le goban
    private GobanPanel pan;
    private boolean sendData;
    private GameManager controleur;

    /**
     * Open a window with the goban and a menu
     * @param s : Title of the window
     */
    public Fenetre(String s) {

        super(s);
        setSize(750, 680);
        this.setLocationRelativeTo(null);



        pan = new GobanPanel();
        /**
         * On ajoute le GobanPanel dans la fenetre principale
         */
        add(pan);
        controleur = new GameManager(pan, this);


        /**
         * Mise en place de la barre de menu
         */
        //Un joueur :
        item1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                OptionWindow ow = new OptionWindow(null, "Choisissez votre couleur !", true, controleur);
            }
        });
        this.nouv.add(item1);

        //Deux joueurs :
        item2.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                controleur.twoPlayers();
            }
        });
        this.nouv.add(item2);
        this.jeu.add(this.nouv);
        //Ajout d'un separateur
        this.jeu.addSeparator();

        //Quitter :
        quitter.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                System.exit(0);
            }
        });

        this.jeu.add(quitter);
        this.barreMenu.add(jeu);

        //TODO :  changer le "Bla bla bla..." du "A propos" !
        //A propos :
        propos.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                JOptionPane jop = new JOptionPane();
                jop.showMessageDialog(null, "Jeu de Go, version Atari, par Julien Durillon et Clotilde Massot.", "À propos", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        this.a_propos.add(propos);
        this.barreMenu.add(a_propos);



        this.setJMenuBar(barreMenu);
        this.setVisible(true);

        /**
         *  Arrêt de l'appli quand on clique sur la croix
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
            if (controleur.getGame().getCurrentPlayer() == PionVal.BLANC)
                strb.append("Noir !");
            else
                strb.append("Blanc !");
            jop.showMessageDialog(null, strb, "Partie terminée !", JOptionPane.INFORMATION_MESSAGE);
    }
}
