package fr.alma.ui;
import javax.swing.JFrame;

public class Main {


    /**
     * point d'entrée
     */
    public static void main(String[] argv) {

	// Création d'une instance de l'application.
	MyApplication myApplication = new MyApplication();

	// Calcul des dimensions de tous les composants de la fenêtre.
	myApplication.pack();
	myApplication.setSize(640, 450);
	myApplication.setLocationRelativeTo(null);
	// Affichage de la fenêtre : il existe maintenant deux threads.
	myApplication.setVisible(true);
	myApplication.uiGoban.tempo.start();
    }

}