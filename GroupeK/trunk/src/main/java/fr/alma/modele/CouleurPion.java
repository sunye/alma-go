package fr.alma.modele;
/*$Author$ 
 * $Date$ 
 * $Revision$ 
 *  
 * license
 * 
 * */
/**
 * 
 * @author Mano�l Fortun et Anthony "Bambin�me" Caillaud
 * Used to represent the color of the stone
 */

public enum CouleurPion {

	NOIR,
	BLANC,
	EMPTY;
	
	/**
	 * get the opposite color
	 * @param coul
	 * @return Empty-> Empty, Black-> White, White->Black
	 */
	public static CouleurPion oppose(CouleurPion coul){
		return coul==EMPTY?EMPTY:coul==NOIR?BLANC:NOIR;
	}
	
}
 

