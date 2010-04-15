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
import javax.swing.JFrame;

/**
 * Main
 * @author Vincent FERREIRA, Adrien GUILLE
 *
 * @version 1.0
 * 
 * revision $Revision$
 */

public class Main {


    /**
     * point d'entrée
     */
    public static void main(String[] argv) {

	// Creating application instance
	MyApplication myApplication = new MyApplication();

	// Calculate dimensions of all the composant of the window application.
	myApplication.pack();
	myApplication.setSize(640, 450);
	myApplication.setLocationRelativeTo(null);

	myApplication.setVisible(true);
    }

}