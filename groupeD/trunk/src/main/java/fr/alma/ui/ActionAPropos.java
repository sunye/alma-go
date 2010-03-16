package fr.alma.ui;

import javax.swing.ImageIcon;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.KeyStroke;
import java.awt.event.InputEvent;
import javax.swing.JOptionPane;
import javax.swing.Action;
/**
 * Action affichant la fenetre "A Propos"
 * @author vincent
 *
 */
class ActionAPropos extends MonAction {

 /**
  * constructeur logique
  * 
  */
 public ActionAPropos(MonApplication monApplication) {
	super("A propos de ...", 
	      new ImageIcon(monApplication.getClass().getResource("/fr/alma/resources/images/a-propos.png")),
	      "Présente la version et l'auteur",
	      KeyEvent.VK_P,
	      KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK),
	      monApplication);
 }

/**
 * redéfinition
 */
 public void actionPerformed(ActionEvent evt) {
	 Thread t = new Thread() {
		 public void run() {
			 monApplication.uiplateau.enPause=true;
			String message = "ObiAtarigo 0.1\n" +
			    "Un Atarigo 9x9\n\n" +
			    "(c) 2010 FERREIRA Vincent et GUILLE Adrien \n" +
			    "Etudiant\n" +
			    "Département d'informatique\n" +
			    "Université de Nantes\n\n";
			JOptionPane.showMessageDialog(monApplication,
						      message,
						      (String)getValue(Action.NAME),
						      JOptionPane.INFORMATION_MESSAGE,
						      (ImageIcon)getValue(Action.SMALL_ICON));
			monApplication.uiplateau.enPause=false;
		 }
     };
     t.start();
 }

}
