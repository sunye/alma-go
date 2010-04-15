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
 * Action showing the window "A Propos"
 * @author Vincent FERREIRA, Adrien GUILLE
 *
 * @version 1.0
 * 
 * revision $Revision$
 */
class AboutAction extends MyAction {

 /**
  * logic constructor
  * 
  */
 public AboutAction(MyApplication myApplication) {
	super("A propos de ...", 
	      new ImageIcon(ImageIcon.class.getClassLoader().getSystemResource("images/a-propos.png")),
	      "Presente la version et l'auteur",
	      KeyEvent.VK_P,
	      KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK),
	      myApplication);
 }

/**
 * override
 */
 public void actionPerformed(ActionEvent evt) {
	 Thread t = new Thread() {
		 public void run() {
			 myApplication.uiGoban.pause=true;
			String message = "ObiAtarigo 0.1\n" +
			    "Un Atarigo 9x9\n\n" +
			    "(c) 2010 FERREIRA Vincent et GUILLE Adrien \n" +
			    "Etudiant\n" +
			    "Département d'informatique\n" +
			    "Université de Nantes\n\n";
			JOptionPane.showMessageDialog(myApplication,
						      message,
						      (String)getValue(Action.NAME),
						      JOptionPane.INFORMATION_MESSAGE,
						      (ImageIcon)getValue(Action.SMALL_ICON));
			myApplication.uiGoban.pause=false;
		 }
     };
     t.start();
 }

}
