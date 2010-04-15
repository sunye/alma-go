/**
 * File {Fenetre.java}
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

/*
 * This file has been originaly writen by Anthony Caillaud.
 * Thanks to him for sharing.
 */
import fr.alma.atarigo.GameManager;
import fr.alma.atarigo.utils.StoneVal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 * This class describes the main window of the application.
 * It defines main buttons and actions.
 * @author judu
 */
public class Fenetre extends JFrame {

    /**
     * Constant = serialVersionUID.
     */
    private static final long serialVersionUID = 1L;
    /**
     * Constant = Width of the goban.
     */
    private static final int GWIDTH = 750;
    /**
     * Constant = Height of the goban.
     */
    private static final int GHEIGHT = 680;
    /**
     * The menu bar.
     */
    private JMenuBar barreMenu = new JMenuBar();
    /**
     * Menu "Jeu".
     */
    private JMenu jeu = new JMenu("Jeu");
    /**
     * Menu "À propos".
     */
    private JMenu aPropos = new JMenu("À Propos");
    /**
     * Menu "Nouvelle partie".
     */
    private JMenu nouv = new JMenu("Nouvelle partie");
    /**
     * Menu Item "1 joueur" to run a one-player game.
     */
    private JMenuItem item1 = new JMenuItem("1 joueur");
    /**
     * Menu Item "2 joueurs" to run a two-players game.
     */
    private JMenuItem item2 = new JMenuItem("2 joueurs");
    /**
     * Menu Item "?" to know more about this program.
     */
    private JMenuItem propos = new JMenuItem("?");
    /**
     * Menu Item "Quitter" to close the game.
     */
    private JMenuItem quitter = new JMenuItem("Quitter");
    /**
     * Option Window to choose the color of the stone.
     */
    private OptionWindow options;
    /**
     * Panel representing the goban.
     */
    private GobanPanel pan;
    /**
     * The controller of the game.
     */
    private GameManager controleur;

    /**
     * Open a window with the goban and a menu.
     * @param s : Title of the window.
     */
    public Fenetre(final String s) {

        super(s);
        setSize(GWIDTH, GHEIGHT);
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

            @Override
            public void actionPerformed(final ActionEvent arg0) {
                options = new OptionWindow(null,
                        "Choisissez votre couleur !", true, controleur);
            }
        });
        this.nouv.add(item1);

        //Deux joueurs :
        item2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent arg0) {
                controleur.twoPlayers();
            }
        });
        this.nouv.add(item2);
        this.jeu.add(this.nouv);
        //Ajout d'un separateur
        this.jeu.addSeparator();

        //Quitter :
        quitter.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent arg0) {
                System.exit(0);
            }
        });

        this.jeu.add(quitter);
        this.barreMenu.add(jeu);

        //A propos :
        propos.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent arg0) {
                JOptionPane.showMessageDialog(null,
                        "Jeu de Go, version Atari, "
                        + "par Julien Durillon et Clotilde Massot.",
                        "À propos", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        this.aPropos.add(propos);
        this.barreMenu.add(aPropos);



        this.setJMenuBar(barreMenu);
        this.setVisible(true);

        /**
         *  Arrêt de l'appli quand on clique sur la croix
         */
        setDefaultCloseOperation(EXIT_ON_CLOSE);


    }

    /**
     * Opens a window to announce the winner of the game.
     */
    public final void finJeu() {
        StringBuilder strb = new StringBuilder();
        strb.append("Le grand gagnant de cette partie est : Joueur ");
        if (controleur.getGame().getCurrentPlayer() == StoneVal.WHITE) {
            strb.append("Noir !");
        } else {
            strb.append("Blanc !");
        }
        JOptionPane.showMessageDialog(null, strb, "Partie terminée !",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
