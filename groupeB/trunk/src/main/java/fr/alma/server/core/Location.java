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

/**
 * Implement a Location
 */
public class Location implements ILocation {

	private int row;
	private int col;
	
	public Location(int col, int row) {
		this.col = col;
		this.row = row;
	}
	
	/*
	 * (non-Javadoc)
	 * @see fr.alma.server.core.ILocation#getCol()
	 */
	@Override
	public int getCol() {
		return col;
	}

	/*
	 * (non-Javadoc)
	 * @see fr.alma.server.core.ILocation#getRow()
	 */
	@Override
	public int getRow() {
		return row;
	}
	
	/*
	 * (non-Javadoc)
	 * @see fr.alma.server.core.ILocation#equals(fr.alma.server.core.ILocation)
	 */
	@Override
	public boolean equals(ILocation e1){
		return (this.col == e1.getCol() && this.row == e1.getRow());
	}
	
	/*
	 * (non-Javadoc)
	 * @see fr.alma.server.core.ILocation#isIn(java.util.List)
	 */
	@Override
	public boolean isIn(List<ILocation> emplacements){
		for(ILocation e :emplacements ){
			if(this.equals(e))
				return true;
		}
		return false;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "col : " + col + " - row : " + row;
	}

}
