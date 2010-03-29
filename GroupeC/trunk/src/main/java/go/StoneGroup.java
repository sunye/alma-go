package go;

import java.util.Iterator;
import java.util.LinkedHashSet;

/**
 * @author Frédéric Dumonceaux
 *
 */


public class StoneGroup implements Cloneable, Constants {
	
	LinkedHashSet<?> linked = new LinkedHashSet();
	byte x;
	
	/**
	 * Créé un groupe vide avec seulement la couleur
	 * (utilisée pour la duplication)
	 * @param color
	 * @throws ExceptionGlobal
	 */
	public StoneGroup(byte color, State s) throws ExceptionGlobal {
		
	}
	
	
	/**
	 * crée un groupe de pierre lors de la pose d'une pierre sur le goban
	 * qui est distinct de tous les autres groupes déjÃ  déclarés (non-connexe donc)
	 * @param o_position la première pierre du groupe
	 * @throws ExceptionGlobal 
	 */
	public StoneGroup(Position o_position, State s) throws ExceptionGlobal {
		
	}
	
	
	/**
	 * renvoie le nombre de pierre du groupe
	 */
	public int countStones() {
		return 1;
	}


	/**
	 * renvoie le nombre de liberté du groupe
	 */
	public int countLiberties()	{
		return 1;
	}


	/**
	 * retourne la liste des pierres du groupes
	 * @return the stones
	 */
	public LinkedHashSet<Position> getStones() {
		return (LinkedHashSet<Position>) linked;
	}


	/**
	 * retourne la liste des positions libres connexes au groupe
	 * @return the liberties
	 */
	public LinkedHashSet<Position> getLiberties() {
		return (LinkedHashSet<Position>) linked;
	}


	/**
	 * retourne la liste des bordures
	 * @return the frontiers
	 */
	public LinkedHashSet<Position> getFrontiers() {
		return (LinkedHashSet<Position>) linked;
	}


	/**
	 * retourne la liste des yeux
	 * @return the eyes
	 */
	public LinkedHashSet<Position> getEyes() {
		return (LinkedHashSet<Position>) linked;
	}


	/**
	 * @return the extern_top
	 */
	public void getBottomLeft() {

	}


	/**
	 * @return the extern_top
	 */
	public void getTopRight() {

	}


	/**
	 * @return retourne l'etat qui le possede
	 */
	public byte getOwner() {
		return x;
	}


	/**
	 * @return retourne l'etat qui le possede
	 */
	public void getState() {

	}

	
	/**
	 * @param s
	 */
	public void setState(State s) {

	}


	/**
	 * ajoute une pierre Ã  un groupe et met Ã  jour les libertés du groupe,
	 * effectue également une fusion de groupes si la pierre est connexe pour plusieurs groupes
	 * @param new_stone la pierre Ã  ajouter au groupe
	 * @throws ExceptionGlobal 
	 */
	public void addStone(Position new_stone) throws ExceptionGlobal
	{
		
	}


	/**
	 * fusionne deux groupes de pierres
	 * @param other le groupe Ã  fusionner
	 * @throws ExceptionGlobal 
	 */
	public void mergeWith(StoneGroup other) throws ExceptionGlobal
	{
		
	}


	/**
	 * remet Ã  zéro un groupe
	 */
	public void resetGroup()
	{
		
	}


	/**
	 * méthodes qui met Ã  jour les libertés du groupe
	 * typiquement après un ajout de pierre et/ou sur une prise de pierres du goban
	 */
	public void updateLiberties()	{
		
	}


	/**
	 * indique si au moins une pierre d'un groupe est dans la fenetre spécifié par deux groupes
	 * dans ce cas, ce groupe est inclus
	 * @param other le groupe avec lequel cherché
	 */
	public boolean isIncludeIn(StoneGroup one, StoneGroup other)	{
		return true;
		
	}

	
	/**
	 * indique si deux groupes sont éventuellement adjacents par l'intersection de leur fenetre 
	 * (PAS obligatoire mais dans ce cas, on a plus de chance que cela soit vrai)
	 * on détermine si la contrainte telle qu'au moins un coin de la fenetre du groupe soit dans
	 * celle de l'autre et/ou inversement est vraie
	 * (protège de l'inclusion qui ne serait pas traité car si a est contenu par b, a coupe donc b)
	 * @param other le groupe avec lequel cherché l'intersection
	 */
	public boolean hasIntersection(StoneGroup other){
		return true;
		
	}
	
	
	/**
	 * cherche une connexité externe (libertés, 4-connexité) entre deux groupes de pièces
	 * @param other le groupe de comparaison
	 * @return énumère les points de connexion possibles
	 */
	public LinkedHashSet<Position> get4cConnection(StoneGroup other) {
		return (LinkedHashSet<Position>) linked;
		
	}


	/**
	 * cherche une connexité externe (libertés [8c] des pierres frontieres) entre deux groupes de pièces du MEME joueur
	 * afin de mettre en évidence si les deux groupes sont <em><b>séparateurs</b></em> et renvoie le nombre de séparations possibles
	 * @param other le groupe de comparaison
	 * @return énumère les points de connexion possibles
	 */
	public LinkedHashSet<Position> get8cConnection(StoneGroup other) {
		return (LinkedHashSet<Position>) linked;
		
	}


	/**
	 * cherche l'existence de groupes adverse Ã  proximité des libertés communes entre deux 
	 * groupes amis
	 * @param other le groupe de comparaison
	 * @param enemy_groups les groupes Ã  tester
	 * @return renvoie les groupes ennemis
	 */
	public LinkedHashSet<Position> getEnemyTraitGroup(StoneGroup other, LinkedHashSet<StoneGroup> enemy_groups)	{
		return (LinkedHashSet<Position>) linked;
		
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "true";
	}
}
