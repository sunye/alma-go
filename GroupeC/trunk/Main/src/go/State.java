package go;

import java.util.Iterator;
import java.util.LinkedHashSet;


/**
 * @author Fr�d�ric Dumonceaux
 *
 */


public class State implements Cloneable, Constants {
	
	
	
	/**
	 * Cr�e une nouvelle instance d'un �tat de jeu initial
	 * @throws ExceptionGlobal 
	 */
	public State() {
		
	}
	
	
	/**
	 * duplique un �tat du jeu de mani�re profonde
	 * @param src l'�tat actuel du jeu � dupliquer
	 * @throws ExceptionGlobal 
	 */
	public State(State src) throws ExceptionGlobal {
		
		
	}
	
	
	/**
	 * Retourne la position associ�e au goban par ses coordonn�es
	 * @param x l'abscisse de la case
	 * @param y l'ordonn�e de la case
	 */
	public Position getPosition(int x, int y) {
		
	}


	/**
	 * calcule le nombre de libert� (vide de pierres) d'une case du goban selon la 4-connexit�
	 * @param x l'abscisse de la case
	 * @param y l'ordonn�e de la case
	 * @return le nombre de libert� d'une case sp�cifique
	 */
	public int countLiberties_4Connexe(int x, int y) {
		
	}


	/**
	 * d�termine si la pose d'une pierre aux coordonn�es sp�cifi�es est valable
	 * en regard des r�gles du jeu
	 * @param x l'abscisse de la case
	 * @param y l'ordonn�e de la case
	 * @param stone la couleur � jouer
	 * @return si le coup est l�gal
	 */
	public boolean isLegalMove(int x, int y, int stone)
	{
		
	}


	/**
	 * d�termine si la pose d'une pierre aux coordonn�es sp�cifi�es est valable
	 * en regard des r�gles du jeu
	 * @param x l'abscisse de la case
	 * @param y l'ordonn�e de la case
	 * @param stone la couleur � jouer
	 * @return si le coup prend un groupe
	 */
	public boolean isTakingStones(int x, int y, int stone)
	{	
		
	}


	/**
	 * applique un coup jou� et effectue les modifications transversales
	 * sur les groupes de pierres du jeu (retrait, regroupement, etc...)
	 * @param x l'abscisse de la case
	 * @param y l'ordonn�e de la case
	 * @param new_state la nouvelle pierre
	 * @param normal doit �tre faux sur un appel de undo pour �viter de mettre � jour le dernier coup qui est en cours de restauration
	 * @throws ExceptionGlobal 
	 */
	public LinkedHashSet<Coord> applyMove(int x, int y, byte new_state) throws ExceptionGlobal
	{
		
		
	}


	

	/**
	 * recherche les groupes n'ayant plus que le nombre de libert�s sp�cifi� en argument
	 * parmi ceux qui ne sont pas invuln�rables (pas deux yeux)
	 * @param sg le point de vue � adopter
	 * @param remainder le nombre de libert�s 
	 * @param black_subset un sous-ensemble des groupes noirs ou tous
	 * @param white_subset un sous-ensemble des groupes blancs ou tous
	 * @return les groupes trouv�s
	 */
	public LinkedHashSet<StoneGroup> groupsWithRemainingLiberties(int color, int remainder) { 
	}
	
	public LinkedHashSet<StoneGroup> groupsWithRemainingLiberties(int color, int remainder, LinkedHashSet<StoneGroup> black_subset, LinkedHashSet<StoneGroup> white_subset)
	{
		
	}


	/**
	 * recherche les positions ayant la propri�t� d'oeil
	 * soit une libert� satur�e par un seul groupe donc libert� int�rieure
	 * et n'ayant pas de libert� connexe
	 */
	public void searchEyes()
	{
	}
	

	/**
	 * recense des connexions virtuelles et des fractions 
	 * entre chaque groupe dispos� sur le goban
	 * @param sg le point de vue � adopter
	 * @param black_subset un sous-ensemble des groupes noirs ou tous
	 * @param white_subset un sous-ensemble des groupes blancs ou tous
	 */
	public void searchConnectionsAndFractions(int sg) {  }
	public void searchConnectionsAndFractions(int sg, LinkedHashSet<StoneGroup> black_subset, LinkedHashSet<StoneGroup> white_subset, boolean debug)
	{
		
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
	}
}
