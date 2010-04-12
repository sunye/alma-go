package fr.alma.ui;

import javax.swing.ImageIcon;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.KeyStroke;
import java.awt.event.InputEvent;
import javax.swing.JOptionPane;
import javax.swing.Action;

/**
 * QuitAction.java represent the action that operate the shuting down of the application. 
 * @author vincent
 *
 */
class QuitAction extends MyAction {

/**
 * logic constructor
 */
 public QuitAction(MyApplication myApplication) {
	super("Quitter", 
	      new ImageIcon(ImageIcon.class.getClassLoader().getSystemResource("resources/images/quitter.png")),
	      "Quitter",
	      KeyEvent.VK_Q,
	      KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK),
	      myApplication);
 }

/**
 *  quit the application
 */
 public void quit() {
	System.exit(0);
 }

/**
 * override
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

