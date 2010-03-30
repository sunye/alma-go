/*
 * IA Project ATARI-GO
 * UNIVERSITY OF NANTES
 * MASTER ALMA 1
 * 2009 - 2010
 * Version 1.0
 * @author Romain Gournay & Bruno Belin
 * 
 * Copyright 2010 Romain Gournay & Bruno Belin, All rights reserved.
 * Use is subject to license terms.
 */
package fr.alma.server.core;

import java.util.List;

public class Emplacement implements ILocation {

	private int row;
	private int col;
	
	public Emplacement(int col, int row) {
		this.col = col;
		this.row = row;
	}
	
	@Override
	public int getCol() {
		return col;
	}

	@Override
	public int getRow() {
		return row;
	}
	
	@Override
	public boolean equals(ILocation e1){
		//System.out.println("Emplacement.equals()["+e1.getCol()+"]["+e1.getRow()+"]");
		return (this.col == e1.getCol() && this.row == e1.getRow());
	}
	
	@Override
	public boolean isIn(List<ILocation> emplacements){
		for(ILocation e :emplacements ){
			if(this.equals(e))
				return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "col : " + col + " - row : " + row;
	}

}
