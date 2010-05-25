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
public class groupOfCells {
	public static Vector gOc = new Vector<Cellule>(81);

	public Vector edgeCellsUnordered() {// unordered
		Vector<Cellule> v = null;

		Iterator<Cellule> it = this.gOc.iterator();
		while (it.hasNext()) {
			Cellule c;
			if (((c = (it.next().north())).getValeur() == -1)
					| it.next().north().getValeur() == 0
					| // so for black stain
					(((it.next().east())).getValeur() == -1)
					| it.next().east().getValeur() == 0
					| (((it.next().south())).getValeur() == -1)
					| it.next().south().getValeur() == 0
					| (((it.next().west())).getValeur() == -1)
					| it.next().west().getValeur() == 0)
				v.add(c);
		}
		return v;
	}

	Vector outlineOfConnexComponentOrdered() {
		// to better for edges
		Vector v;
		// cf Image Synthesis lecture about outline
		// Modelisation_des_images_6.pdf
		// Iterator<Cellule> it = this.cc.iterator();
		Vector oOCC = this.edgeCellsUnordered();
		oOCC.Iterator<Cellule> it = oOCC.iterator();
		while (it.hasNext()) {

		}

	}

	public static int sortedMin;

	Vector selectMinVert(Boolean condition) {
		int minVert = 10;// because only 9*9
		// !!be carefull orientation of axis
		Vector v;
		Iterator<Cellule> it = groupOfCells.gOc.iterator();
		Cellule elt;
		while (it.hasNext()) {
			if ((elt = it.next()).getFile() <= minVert) {
				// Object paradigm (think map too)
				minVert = elt.getFile();
			}
		}
		while (it.hasNext()) {
			if ((elt = it.next()).getFile() <= minVert) {
				// Object paradigm (think map too)
				v.add(elt);
			}
		}

		return v;
	}

	public Boolean selectX(int x) {
		// because only 9*9
		// !!be carefull orientation of axis
		Vector v = null;
		Iterator<Cellule> it = groupOfCells.gOc.iterator();
		Cellule elt;
		while (it.hasNext()) {
			if ((elt = it.next()).getColumn() == x) {
				// Object paradigm (think map too)
				v.add(elt);
			}
		}
		return v;
	}
}
