package fr.alma.atarigo;


/**
 * Enumeration representant les différents types de pions.
 */
public enum Pion {BLANC, NOIR, VIDE;
	
	public Pion oppose(){
		if(this==BLANC){
			return NOIR;
		}else{
			if(this==NOIR)
				return BLANC;
			else return VIDE;
		}	
	}
	
}