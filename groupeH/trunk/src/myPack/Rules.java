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
	// public static Vector colorPonerList = new
	// Vector<Vector<Cellule>(81)>(2);// whites and black

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

	public static void colorPonerCompute() {
		int elt;
		for (int j = 0; j <= 8; j++) {
			for (int i = 0; i <= 8; i++) {

				elt = gobanArray[i][j];
				if (elt == -1)
					// colorPonerListBl.addElement(new Cellule(i, j, color));
					colorPonerListWh.addElement(new Cellule(i, j, -1)); // add
				// to
				// White's
				// list
				else if (elt == -1) {
					colorPonerListBl.addElement(new Cellule(i, j, 1));
				}
			}
		}
	}

	/** split poners into adjacent Components ***/
	public static void connectedComponentBlack() {// !!linked just by diagonal
		// means separation
		// Iterator<Cellule> it = ComponentsAr.iterator();
		Iterator<Cellule> it = colorPonerListBl.iterator();

		while (it.hasNext()) {
			Cellule item = it.next();
			Cellule n = item.north();// n=North value
			Cellule e = item.east();
			Cellule s = item.south();
			Cellule w = item.west();

			// if ((JeuConsole.tableauFixe[n.getFile()][n.getColumn()])>=0){//so
			// is Black
			if ((JeuConsole.tableauFixe.getValeur(n.getFile(), n.getColumn())) >= 0) {// so
				// is
				// Black
				if (!(it.next().isBelong(CCl.ccl)))
					(Cellule.indix).add(it.next());
			}
		}

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
