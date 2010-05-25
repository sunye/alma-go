package myPack;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class Cellule {
	static CC indix;

	/**
	 * @return the File
	 */
	public int getFile() {
		return File;
	}

	/**
	 * @param File
	 *            the File to put
	 */
	public void putFile(int File) {
		this.File = File;
	}

	/**
	 * @return the column
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * @param column
	 *            the column to put
	 */
	public void putColumn(int column) {
		this.column = column;
	}

	/**
	 * @return the valeur
	 */
	public int getValeur() {
		return valeur;
	}

	/**
	 * @param valeur
	 *            the valeur to put
	 */
	public void putValeur(int valeur) {
		this.valeur = valeur;
	}

	int File;
	int column;
	int valeur;

	public static Vector neighboursV = new Vector();

	public void givNeighbVect(int color) {
		if (File >= 0)
			neighboursV.add(new Pos(File - 1, column));
		if (File <= 8)
			neighboursV.add(new Pos(File + 1, column));

		if (column >= 0)
			neighboursV.add(new Pos(File - 1, column));
		if (column <= 8)
			neighboursV.add(new Pos(File + 1, column));

	}

	Cellule() {
		File = 0;
		column = 0;
		valeur = 0;
	}

	Cellule(int f, int c, int v) {
		File = f;
		column = c;
		valeur = v;
	}

	/**
	 * @param x
	 *            x in 0-7 coordinates result 1..81
	 **/
	public int linearize(int x, int y) {
		return (x + 1) + 8 * y;
	}

	/** !!diag=other group */
	public Cellule north() {
		Cellule c = new Cellule();
		c.column = this.column;
		c.File = this.File - 1;
		c.valeur = this.valeur;
		return c;
	}

	public Cellule east() {
		Cellule c = new Cellule();
		c.column = this.column + 1;
		c.File = this.File;
		c.valeur = this.valeur;
		return c;
	}

	public Cellule south() {
		Cellule c = new Cellule();
		c.column = this.column;
		c.File = this.File + 1;
		c.valeur = this.valeur;
		return c;
	}

	public Cellule west() {
		Cellule c = new Cellule();
		c.column = this.column - 1;
		c.File = this.File - 1;
		c.valeur = this.valeur;
		return c;
	}

	public static CC CCcurrent;

	public Boolean isBelong(Vector ccl) {
		Iterator<CC> it = ((List) ccl).iterator();
		boolean isBelongingCC = false;
		while (it.hasNext()) {
			CCcurrent = it.next();
			Iterator<Cellule> it2 = ((List) CCcurrent).iterator();
			while (it2.hasNext()) {
				if (this.equals(it2.next())) {
					isBelongingCC = true;// tester le equals()
					this.indix = CCcurrent;
				}
			}
		}
		return isBelongingCC;
	}
	
	public Boolean contition(){
		return this 
	}
	
	
	//operator
	
}