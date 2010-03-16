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
 * Classe implémentant une l'action consistant à lancer une nouvelle partie.
 * @author vincent
 *
 */
class ActionNouveau extends MonAction {

	NouvellePartieDialog nvdialog;

/**
 * constructeur logique
 */
 public ActionNouveau(MonApplication monApplication) {
	super("Nouvelle partie", 
	      new ImageIcon(monApplication.getClass().getResource("../resources/images/nouveau.png")),
	      "Lance une nouvelle partie",
	      KeyEvent.VK_N,
	      KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK),
	      monApplication);
 }

/**
 * redéfinition
 */
 public void actionPerformed(ActionEvent evt) {
	 Thread t = new Thread() {
		 public void run() {
			monApplication.uiplateau.enPause=true;
			if ((nvdialog != null) && (nvdialog.isVisible())) return;
			nvdialog = new NouvellePartieDialog(null, "Nouvelle Partie", true,monApplication);
			nvdialog.setLocationRelativeTo(monApplication);
			//monApplication.uiplateau.enPause=true;
			nvdialog.setVisible(true);
			monApplication.uiplateau.enPause=false;
		 }
     };
     t.start();
 }

}
