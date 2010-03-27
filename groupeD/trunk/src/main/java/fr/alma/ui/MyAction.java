package fr.alma.ui;

import javax.swing.Action;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.KeyStroke;
/**
 * Definition of a classe {MyAction} representing an action
 * from the menu and/or the toolbar.
 * @author vincent
 *
 */
abstract class MyAction extends AbstractAction {
 
/**
 * the attached application
 */
 protected MyApplication myApplication;

/**
 * logic constructor.
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
