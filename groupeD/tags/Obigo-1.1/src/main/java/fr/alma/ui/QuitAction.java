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
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.KeyStroke;
import java.awt.event.InputEvent;
import javax.swing.JOptionPane;
import javax.swing.Action;

/**
 * QuitAction.java represent the action that operate the shuting down of the application. 
 * @author Vincent FERREIRA, Adrien GUILLE
 * 
 * @version 1.0
 * 
 * revision $Revision$
 *
 */
class QuitAction extends MyAction {

/**
 * logic constructor
 */
 public QuitAction(MyApplication myApplication) {
	super("Quitter", 
	      new ImageIcon(ImageIcon.class.getClassLoader().getSystemResource("images/quitter.png")),
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

