/**
 * 
 */
package myPack;

import java.util.Iterator;
import java.util.Vector;

/**
 * @author peter
 * 
 */
public class Rules {

	public static Vector colorPonerListBl = new Vector<Cellule>(81);// 81=max
	// size
	// which can
	// occur
	public static Vector colorPonerListWh = new Vector<Cellule>(81);// 81=max

	// size
	// which can
	// occur

	/**
	 * @param color
	 *            : -1=Whites 1=Blacks
	 */

	public static int gobanArray[][] = new int[9][9];
	public static Vector ComponentsAr = new Vector();
	public  static void colorPonerCompute(int color) {
		int elt;
		for (int j = 0; j <= 8; j++) {
			for (int i = 0; i <= 8; i++) {

				elt = gobanArray[i][j];
				if (elt == color)
					colorPonerListBl.addElement(new Cellule(i, j, color));

			}
		}
	}

	connectedComponent(int color) {
		Iterator<Cellule> it = ComponentsAr.iterator();
		
while(it.hasNext()){
	
}
	

	}

	Vector<Cellule> outlineOfConnexComponent() {
	}

	/**
	 * @param color
	 *            : -1=Whites 1=Blacks
	 */
	public boolean	isCapturing(int color){
		/**isCapturing(1) means "is Black capturing ,at least ,so White,poner"**/
		isFree
		for each black
			if 
		return 
	}

	/** Only after isCapturing */
	public boolean isCapturedBlack() {

	}
}
