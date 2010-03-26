package fr.alma.atarigo.ihm;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;

public class Fenetre extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JMenuBar barreMenu;
    private JMenu jeu;
    private JMenu a_propos;
    private JMenuItem nouv;
    private JMenuItem quitter;
    //Panel représentant le goban
    private GobanPanel pan;

    public Fenetre(String s) {
        super(s);
        setSize(700, 700);

        /**
         * Creations des différents outils permettant la mise en place de la barre de menu
         */
        barreMenu = new JMenuBar();

        jeu = new JMenu("Jeu");
        a_propos = new JMenu("A Propos");

        nouv = new JMenuItem("Nouvelle partie");
        quitter = new JMenuItem("Quitter");

        /**
         * Mise en place de la barre de menu
         */
        barreMenu.add(jeu);
        barreMenu.add(a_propos);

        jeu.add(nouv);
        jeu.add(quitter);

        pan = new GobanPanel();
        /**
         * On ajoute le GobanPanel dans la fenetre principale
         */
        add(pan);
        nouv.addActionListener(this);

        this.setJMenuBar(barreMenu);

        /**
         *  Arrêt de l'appli quand on clique sur la croix
         */
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void startGo() {
        pan.startGame();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (nouv.equals(e.getSource())) {
            startGo();
        } else if(quitter.equals(e.getSource())){
            System.exit(0);
        }
    }
}