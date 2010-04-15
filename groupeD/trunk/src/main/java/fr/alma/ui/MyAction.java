/*   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package fr.alma.ui;

import javax.swing.Action;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.KeyStroke;
/**
 * Definition of a classe {MyAction} representing an action
 * from the menu and/or the toolbar.
 * @author Vincent FERREIRA, Adrien GUILLE
 * 
 * @version 1.0
 * 
 * revision $Revision$
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
