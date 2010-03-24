package fr.alma.atarigo;


/**
 * Enumeration representant les différents types de pions.
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