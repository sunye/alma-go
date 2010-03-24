package go;

import java.util.Iterator;
import java.util.LinkedHashSet;


/**
 * @author Frédéric Dumonceaux
 *
 */


public class State implements Cloneable, Constants {
	
	
	
	/**
	 * Crée une nouvelle instance d'un état de jeu initial
	 * @throws ExceptionGlobal 
	 */
	public State() {
		
	}
	
	
	/**
	 * duplique un état du jeu de manière profonde
	 * @param src l'état actuel du jeu à dupliquer
	 * @throws ExceptionGlobal 
	 */
	public State(State src) throws ExceptionGlobal {
		
		
	}
	
	
	/**
	 * Retourne la position associée au goban par ses coordonnées
	 * @param x l'abscisse de la case
	 * @param y l'ordonnée de la case
	 */
	public Position getPosition(int x, int y) {
		
	}


	/**
	 * calcule le nombre de liberté (vide de pierres) d'une case du goban selon la 4-connexité
	 * @param x l'abscisse de la case
	 * @param y l'ordonnée de la case
	 * @return le nombre de liberté d'une case spécifique
	 */
	public int countLiberties_4Connexe(int x, int y) {
		
	}


	/**
	 * détermine si la pose d'une pierre aux coordonnées spécifiées est valable
	 * en regard des règles du jeu
	 * @param x l'abscisse de la case
	 * @param y l'ordonnée de la case
	 * @param stone la couleur à jouer
	 * @return si le coup est légal
	 */
	public boolean isLegalMove(int x, int y, int stone)
	{
		
	}


	/**
	 * détermine si la pose d'une pierre aux coordonnées spécifiées est valable
	 * en regard des règles du jeu
	 * @param x l'abscisse de la case
	 * @param y l'ordonnée de la case
	 * @param stone la couleur à jouer
	 * @return si le coup prend un groupe
	 */
	public boolean isTakingStones(int x, int y, int stone)
	{	
		
	}


	/**
	 * applique un coup joué et effectue les modifications transversales
	 * sur les groupes de pierres du jeu (retrait, regroupement, etc...)
	 * @param x l'abscisse de la case
	 * @param y l'ordonnée de la case
	 * @param new_state la nouvelle pierre
	 * @param normal doit être faux sur un appel de undo pour éviter de mettre à jour le dernier coup qui est en cours de restauration
	 * @throws ExceptionGlobal 
	 */
	public LinkedHashSet<Coord> applyMove(int x, int y, byte new_state) throws ExceptionGlobal
	{
		
		
	}


	

	/**
	 * recherche les groupes n'ayant plus que le nombre de libertés spécifié en argument
	 * parmi ceux qui ne sont pas invulnérables (pas deux yeux)
	 * @param sg le point de vue à adopter
	 * @param remainder le nombre de libertés 
	 * @param black_subset un sous-ensemble des groupes noirs ou tous
	 * @param white_subset un sous-ensemble des groupes blancs ou tous
	 * @return les groupes trouvés
	 */
	public LinkedHashSet<StoneGroup> groupsWithRemainingLiberties(int color, int remainder) { 
	}
	
	public LinkedHashSet<StoneGroup> groupsWithRemainingLiberties(int color, int remainder, LinkedHashSet<StoneGroup> black_subset, LinkedHashSet<StoneGroup> white_subset)
	{
		
	}


	/**
	 * recherche les positions ayant la propriété d'oeil
	 * soit une liberté saturée par un seul groupe donc liberté intérieure
	 * et n'ayant pas de liberté connexe
	 */
	public void searchEyes()
	{
	}
	

	/**
	 * recense des connexions virtuelles et des fractions 
	 * entre chaque groupe disposé sur le goban
	 * @param sg le point de vue à adopter
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
