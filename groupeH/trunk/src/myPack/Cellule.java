package myPack;

import java.util.Vector;

public class Cellule {

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
	int linearize(int x, int y) {
		return (x + 1) + 8 * y;
	}

}