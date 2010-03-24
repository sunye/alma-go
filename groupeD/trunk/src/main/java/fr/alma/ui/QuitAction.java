package fr.alma.ui;

import javax.swing.ImageIcon;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.KeyStroke;
import java.awt.event.InputEvent;
import javax.swing.JOptionPane;
import javax.swing.Action;

/**
 * l'action consistant à quitter une application consiste à effectuer 
 * d'éventuelles sauvegardes de fichiers avant d'interrompre l'exécution de l'application.
 * @author vincent
 *
 */
class QuitAction extends MyAction {

/**
 * constructeur logique
 */
 public QuitAction(MyApplication myApplication) {
	super("Quitter", 
	      new ImageIcon(myApplication.getClass().getResource("/fr/alma/resources/images/quitter.png")),
	      "Quitter",
	      KeyEvent.VK_Q,
	      KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK),
	      myApplication);
 }

/**
 *  effectue les sauvegardes éventuelles avant d'interrompre l'application.
 */
 public void quit() {
	System.exit(0);
 }

/**
 * redefinition
 */
 public void actionPerformed(ActionEvent evt) {
	 Thread t = new Thread() {
		 public void run() {
			 myApplication.uiGoban.pause=true;
			 JOptionPane jop = new JOptionPane();			
			 int option = jop.showConfirmDialog(null, "Voulez-vous vraiment quitter l'application ? Votre partie sera définitivement perdue", "Quitter", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			 			
			 if(option == JOptionPane.OK_OPTION)
			 {
				 quit();
			 }
			 myApplication.uiGoban.pause=false;
		 }
     };
     t.start();
 }

}

