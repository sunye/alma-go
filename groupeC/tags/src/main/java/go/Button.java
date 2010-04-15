/*****************************************************************************
**
** This program is free software; you can redistribute it and/or modify
** it under the terms of the GNU General Public License as published by
** the Free Software Foundation; either version 2 of the License, or
** (at your option) any later version.
** 
** This program is distributed in the hope that it will be useful,
** but WITHOUT ANY WARRANTY; without even the implied warranty of
** MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
** GNU General Public License for more details.
** 
** You should have received a copy of the GNU General Public License
** along with this program; if not, write to the Free Software
** Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
*****************************************************************************/


package go;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JButton;


/**
 * Button.java
 * @author FREDERIC DUMONT FREDERIC DUMONCEAUX
 * @version 1.0
 * 
 * revision $Revision$
 */


public class Button extends JButton implements ActionListener {

     /**
      * Serial ID.
      */
     private static final long serialVersionUID = 1L;

     /**
      * Constructor.
      */
     public Button() {
          super();
          this.addActionListener(this);
          this.setEnabled(true);
     }

     /**
      * Button actions.
      * @param event event for the action
      */
     public void actionPerformed(final ActionEvent event) {
          //System.out.println("Clic Bouton");
     }

}
