package fr.alma.runner;

import fr.alma.controler.Controler;
/*$Author$ 
 * $Date$ 
 * $Revision$ 
 *  
 * $license$
 * 
 * */
/**
 * 
 * @author Mano�l Fortun et Anthony "Bambin�me" Caillaud
 * Class that launch the game.
 */
public class GoierRunner {

	public static void main(String[] args) {
		
		Controler ctrl= new Controler();
		ctrl.GO();
	}
}
