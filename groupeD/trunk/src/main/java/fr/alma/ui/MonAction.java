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
abstract class MonAction extends AbstractAction {
 
/**
 * application de rattachement
 */
 protected MonApplication monApplication;

/**
 * constructeur logique.
 */
 public MonAction(String nom, 
		     Icon icone,
		     String bulleAide,
		     int mnemonique,
		     KeyStroke accelerateur,
		     MonApplication monApplication) {
	super(nom, icone);
	putValue(Action.SHORT_DESCRIPTION, bulleAide);
	putValue(Action.MNEMONIC_KEY, mnemonique);
	putValue(Action.ACCELERATOR_KEY, accelerateur);
	this.monApplication = monApplication;
 }

}
