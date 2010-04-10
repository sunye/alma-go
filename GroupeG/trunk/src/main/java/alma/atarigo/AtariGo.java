/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alma.atarigo;

import alma.atarigo.ihm.MainWindow;
import java.lang.reflect.InvocationTargetException;
import javax.swing.SwingUtilities;

/**
 *
 * @author steg
 */
public class AtariGo {

    public static void main(String ... args) throws InterruptedException, InvocationTargetException{
        final MainWindow window = MainWindow.getWindow();
        SwingUtilities.invokeAndWait(new Runnable() {

            public void run() {
                window.setVisible(true);
            }

        });
    }

}
