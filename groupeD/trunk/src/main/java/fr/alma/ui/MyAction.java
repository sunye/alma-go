package fr.alma.ui;

import javax.swing.Action;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.KeyStroke;
/**
 * définition d'une classe {MonAction} représentant une action
 * commune à une entrée de menu et une entrée de barre d'outils.
 * @author vincent
 *
 */
abstract class MyAction extends AbstractAction {
 
/**
 * application de rattachement
 */
 protected MyApplication myApplication;

/**
 * constructeur logique.
 */
 public MyAction(String nom, 
		     Icon icone,
		     String bulleAide,
		     int mnemonique,
		     KeyStroke accelerateur,
		     MyApplication monApplication) {
	super(nom, icone);
	putValue(Action.SHORT_DESCRIPTION, bulleAide);
	putValue(Action.MNEMONIC_KEY, mnemonique);
	putValue(Action.ACCELERATOR_KEY, accelerateur);
	this.myApplication = monApplication;
 }

}
