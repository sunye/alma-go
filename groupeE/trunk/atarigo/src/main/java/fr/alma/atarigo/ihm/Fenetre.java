/**
 * @file Fenetre.java
 *
 * This file has been originaly developed by Anthony Caillaud. Thanks to him for sharing.
 */
package fr.alma.atarigo.ihm;

import fr.alma.atarigo.GameManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeListener;

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
    private JMenu a_propos = new JMenu("A Propos");
    private JMenu nouv = new JMenu("Nouvelle partie");
    private JMenuItem item1 = new JMenuItem("1 joueur");
    private JMenuItem item2 = new JMenuItem("2 joueurs");
    private JMenuItem propos = new JMenuItem("?");
    private JMenuItem quitter = new JMenuItem("Quitter");
    private OptionWindow options;
    //Panel repr√©sentant le goban
    private GobanPanel pan;
    private boolean sendData;
    private GameManager gaman;

    public Fenetre(String s) {

        super(s);
        setSize(700, 700);
        this.setLocationRelativeTo(null);



        pan = new GobanPanel();
        /**
         * On ajoute le GobanPanel dans la fenetre principale
         */
        add(pan);
        gaman = new GameManager(pan);


        /**
         * Mise en place de la barre de menu
         */
        //Un joueur :
        item1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                OptionWindow ow = new OptionWindow(null, "Choisissez votre couleur !", true, gaman);
            }
        });
        this.nouv.add(item1);

        //Deux joueurs :
        item2.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                gaman.twoPlayers();
            }
        });
        this.nouv.add(item2);
        this.jeu.add(this.nouv);
        //Ajout d'un séparateur
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
                jop.showMessageDialog(null, "Bla bla bla...", "À propos", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        this.a_propos.add(propos);
        this.barreMenu.add(a_propos);



        this.setJMenuBar(barreMenu);
        this.setVisible(true);

        /**
         *  Arr√™t de l'appli quand on clique sur la croix
         */
        setDefaultCloseOperation(EXIT_ON_CLOSE);


    }
}
