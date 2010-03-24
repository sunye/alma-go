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
class AboutAction extends MyAction {

 /**
  * constructeur logique
  * 
  */
 public AboutAction(MyApplication myApplication) {
	super("A propos de ...", 
	      new ImageIcon(myApplication.getClass().getResource("/fr/alma/resources/images/a-propos.png")),
	      "Presente la version et l'auteur",
	      KeyEvent.VK_P,
	      KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK),
	      myApplication);
 }

/**
 * redefinition
 */
 public void actionPerformed(ActionEvent evt) {
	 Thread t = new Thread() {
		 public void run() {
			 myApplication.uiGoban.pause=true;
			String message = "ObiAtarigo 0.1\n" +
			    "Un Atarigo 9x9\n\n" +
			    "(c) 2010 FERREIRA Vincent et GUILLE Adrien \n" +
			    "Etudiant\n" +
			    "Département d'informatique\n" +
			    "Université de Nantes\n\n";
			JOptionPane.showMessageDialog(myApplication,
						      message,
						      (String)getValue(Action.NAME),
						      JOptionPane.INFORMATION_MESSAGE,
						      (ImageIcon)getValue(Action.SMALL_ICON));
			myApplication.uiGoban.pause=false;
		 }
     };
     t.start();
 }

}
