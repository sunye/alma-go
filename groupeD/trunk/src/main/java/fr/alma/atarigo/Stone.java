package fr.alma.atarigo;


/**
 * Enumeration of the different types of stones.
 */
public enum Stone {WHITE, BLACK, EMPTY;
	
	public Stone opponent(){
		if(this==WHITE){
			return BLACK;
		}else{
			if(this==BLACK)
				return WHITE;
			else return EMPTY;
		}	
	}
	
}