package fr.alma.atarigo;


/**
 * Enumeration of the different types of stones.
 * <li>
 * WHITE for a white stone
 * <li>
 * BLACK for a black stone
 * <li>
 * EMPTY for an empty stone
 */
public enum Stone {WHITE, BLACK, EMPTY;
	
	/**
	 * Return the opponent color
	 */
	public Stone opponent(){
		if(this==WHITE){
			return BLACK;
		}else
			return WHITE;
	}
	
}