package fr.alma.modele;

public enum CouleurPion {

	NOIR,
	BLANC,
	EMPTY;
	
	public static CouleurPion oppose(CouleurPion coul){
		return coul==EMPTY?EMPTY:coul==NOIR?BLANC:NOIR;
	}
	
}
 

