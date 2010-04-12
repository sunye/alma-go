package fr.alma.ui;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.KeyStroke;
import java.awt.event.InputEvent;
import javax.swing.Action;
import java.util.Calendar;
import java.util.Random;

/**
 * NewGameAction is the action launching a new game.
 * @author vincent
 *
 */
class NewGameAction extends MyAction {

	NewGameDialog nvdialog;

/**
 * logic constructor
 * 
 */
 public NewGameAction(MyApplication myApplication) {
	super("Nouvelle partie", 
	      new ImageIcon(ImageIcon.class.getClassLoader().getSystemResource("resources/images/nouveau.png")),
	      "Lance une nouvelle partie",
	      KeyEvent.VK_N,
	      KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK),
	      myApplication);
 }

/**
 * override
 */
 public void actionPerformed(ActionEvent evt) {
	 Thread t = new Thread() {
		 public void run() {
			myApplication.uiGoban.pause=true;
			if ((nvdialog != null) && (nvdialog.isVisible())) return;
			nvdialog = new NewGameDialog(null, "Nouvelle Partie", true,myApplication);
			nvdialog.setLocationRelativeTo(myApplication);
			//monApplication.uiplateau.enPause=true;
			nvdialog.setVisible(true);
			myApplication.uiGoban.pause=false;
		 }
     };
     t.start();
 }

}
