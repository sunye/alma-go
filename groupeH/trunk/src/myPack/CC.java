/**
 * 
 */
package myPack;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/**
 * @author peter Connected Component
 */
public class CC extends groupOfCells{
	public static Vector cc = new Vector<Cellule>(81);

	public void add(Cellule c) {
		this.cc.add(c);
	}
	
}
