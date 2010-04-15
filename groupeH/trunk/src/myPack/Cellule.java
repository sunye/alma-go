package myPack;

import java.util.Vector;

public class Cellule {

	/**
	 * @return the row
	 */
	public int getRow() {
		return row;
	}

	/**
	 * @param row the row to set
	 */
	public void setRow(int row) {
		this.row = row;
	}

	/**
	 * @return the column
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * @param column the column to set
	 */
	public void setColumn(int column) {
		this.column = column;
	}

	/**
	 * @return the valeur
	 */
	public int getValeur() {
		return valeur;
	}

	/**
	 * @param valeur the valeur to set
	 */
	public void setValeur(int valeur) {
		this.valeur = valeur;
	}

	int row;
	int column;
	int valeur;

	public static Vector neighboursV = new Vector();
public void 	givNeighbVect(int color){
	if (row>=0)
	neighboursV.add(Cellule(row-1));
	if (row<=8)
		neighboursV.add(Cellule(row+1));
		
}

	
	
	Cellule() {
		row = 0;
		column = 0;
		valeur = 0;
	}

	Cellule(int f, int c, int v) {
		row = f;
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