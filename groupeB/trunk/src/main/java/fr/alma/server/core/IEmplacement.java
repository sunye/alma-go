package fr.alma.server.core;

import java.util.List;

/**
 * 
 * @author Romain Gournay & Bruno Belin
 * represent a location
 *
 */
public interface IEmplacement {
	/**
	 * @return the column of a context location
	 */
	int getCol();
	
	/**
	 * @return the row of a context location
	 */
	int getRow();
	
	/**
	 * @param emplacements list of locations
	 * @return true if the context location is contained in the list of locations
	 */
	public boolean isIn(List<IEmplacement> emplacements);
	
	/**
	 * compare two locations
	 * @param e1
	 * @return true if context location is the same as the location e1
	 */
	boolean equals(IEmplacement e1);
	
}
