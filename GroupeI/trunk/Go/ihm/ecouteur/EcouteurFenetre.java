/**
 * @author Bouvet Frederic
 * @author Altuntas Murat
 */
package ihm.ecouteur;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Classe permettant d'ecouter la fenetre principale pour fermer le programme 
 */
public class EcouteurFenetre extends WindowAdapter {

	/**
	 * Pour quitter le programme
	 */
	public void windowClosing(WindowEvent e){
		System.exit(0);
	}	
	
}
