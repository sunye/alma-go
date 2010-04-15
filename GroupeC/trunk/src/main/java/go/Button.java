package go;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JButton;

/**
 * Class Button.
 * @author Fred Dumont
 *
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
