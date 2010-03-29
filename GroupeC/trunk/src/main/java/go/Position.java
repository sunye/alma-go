package go;

import java.util.LinkedHashSet;

/**
 * @author Frédéric Dumonceaux
 *
 */


public class Position implements Constants {
	

	byte x;
	byte y;
	byte z;
	
	State state = new State();
	State state1 = new State();

	/**
	 * crée une position sans pierre (case vide)
	 */
	public Position() {

	}

	
	/**
	 * @param x son abscisse
	 * @param y son ordonnée
	 * @param o_state l'état initial de la case lors de l'initialisation
	 * @param glob_state le goban oÃ¹ se trouve la case
	 * @throws ExceptionGlobal 
	 */
	public Position(byte x, byte y, byte o_state, State glob_state) throws ExceptionGlobal {

	}

	
	/**
	 * @return l'abscisse de la position
	 */
	public byte getX() {
		return x;
	}


	/**
	 * @return l'ordonnée de la position
	 */
	public byte getY() {
		return y;
	}


	/**
	 * @return son propre état (occupé ou pas, si oui par qui) 
	 */
	public byte getOwn_state() {
		return x;
	}


	/**
	 * @return la référence vers le goban
	 */
	public State getState() {
		return state;
	}


	/**
	 * @return la référence vers son groupe
	 */
	public void getGroup() {

	}


	/**
	 * @return the liberties_in_groups
	 */
	public void getLibertiesGroups() {

	}


	/**
	 * @param state the state to set
	 */
	public void setState(State state) {

	}


	/**
	 * @param new_state l'état de la case lors du coup
	 * @throws ExceptionGlobal 
	 */
	public void setStone(byte new_state) {

	}

	
	/**
	 * @param the_group le groupe auquel appartient la case
	 * @throws ExceptionGlobal 
	 */
	public void setGroup(StoneGroup the_group) {

	}

	
	/**
	 * 
	 */
	public void reset() {

	}


	/**
	 * @return
	 */
	public void addFriendGroup(StoneGroup s) {

	}


	/**
	 * @return
	 */
	public void removeFriendGroup(StoneGroup s) {

	}


	/**
	 * @return
	 */
	public void clearFriendGroup() {
	}


	/**
	 * @return le groupe ami sur la liberté en x
	 */
	public boolean isFriendGroup(StoneGroup s) {
		return true;

	}
	
	
	/**
	 * @return l'ensemble des libertés d'une case spécifique
	 */
	public void get4cLiberties() {
	   
	}
	
	
	/**
	 * @param color la couleur Ã  chercher
	 * @return l'ensemble des case adjacentes de la couleur
	 */
	public void get4cDiagLiberties() {
	   
	}


	/**
	 * @param color la couleur Ã  chercher
	 * @return l'ensemble des case adjacentes de la couleur
	 */
	public void getNeighborhoodList(int color) {
	    
	}
	
	
	/**
	 * @return si la case est jouable : non-occupée
	 */
	public boolean isPlayable() {
		return false;

	}


	/**
	 * @return si la pierre n'a plus de liberté
	 */
	public boolean isInside() {
		return false;

	}



	/**
	 * @return si la pierre est inclus dans l'union de deux groupes
	 */
	public boolean isInterior(StoneGroup one, StoneGroup other) {
		return false;
		
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return false;
		
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "true";
		
	}
}
