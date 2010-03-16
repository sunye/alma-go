package fr.alma.ui;
import javax.swing.JFrame;

public class Main {


    /**
     * point d'entrée
     */
    public static void main(String[] argv) {

	// Création d'une instance de l'application.
	MonApplication monApplication = new MonApplication();

	// Calcul des dimensions de tous les composants de la fenêtre.
	monApplication.pack();
	monApplication.setSize(640, 450);
	monApplication.setLocationRelativeTo(null);
	// Affichage de la fenêtre : il existe maintenant deux threads.
	monApplication.setVisible(true);
	monApplication.uiplateau.tempo.start();
    }

}