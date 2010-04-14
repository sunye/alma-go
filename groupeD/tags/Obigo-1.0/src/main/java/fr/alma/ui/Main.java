package fr.alma.ui;
import javax.swing.JFrame;

public class Main {


    /**
     * point d'entrée
     */
    public static void main(String[] argv) {

	// Creating application instance
	MyApplication myApplication = new MyApplication();

	// Calculate dimensions of all the composant of the window application.
	myApplication.pack();
	myApplication.setSize(640, 450);
	myApplication.setLocationRelativeTo(null);

	myApplication.setVisible(true);
    }

}