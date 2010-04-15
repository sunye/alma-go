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
 * @author Vincent FERREIRA, Adrien GUILLE
 * 
 * @version 1.0
 * 
 * revision $Revision$
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
	      new ImageIcon(ImageIcon.class.getClassLoader().getSystemResource("images/nouveau.png")),
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
			nvdialog.setVisible(true);
			myApplication.uiGoban.pause=false;
		 }
     };
     t.start();
 }

}
