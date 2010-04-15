/**
 * @author Bouvet Frederic
 * @author Altuntas Murat
 */
package coeur;




/**
 * Classe permettant de creer un joueur
 */
public class Joueur {

	/** Le type du joueur(humain ou Ia) */
	private int type;
	/** Si ce joueur est de type IA, cela permet de savoir son niveau (debutant-intermediaire-expert)  */
	private int niveauIA;
	/** Pour savoir si l'on doit forcer ce joueur a jouer dans le cas où il est de type IA */
	private boolean forcerAjouer;		
	/** La couleur du joueur */
	private int couleur;	
	/** Le plateau sur lequel on va jouer */
	private Plateau plateau;
	/** l'actualisation */
	private Actualisation actualisation;
	


	
	
	/**
	 * Constructeur d'un joueur
	 * @param type Le type humain ou IA
	 * @param couleur La couleur des pions du joueur
	 * @param plateau Le plateau sur lequel le joueur joue
	 * @param actualisation Pour mettre le jeu a jour
	 */
	public Joueur(int type, int couleur, Plateau plateau, Actualisation actualisation) {
		this.type = type;
		this.niveauIA = GoDonnees.IA_Intermediaire;
		this.forcerAjouer = false;
		this.couleur = couleur;		
		this.plateau = plateau;
		this.actualisation = actualisation;		
	}
	
	public Plateau getplateau(){
		return plateau;
	}
	
	/**
	 * Connaitre le type d'un joueur
	 * @return son type
	 */
	public int getType(){
		return this.type;
	}
	
	
	/**
	 * Connaitre le niveau de l'IA pour ce joueur
	 * @return son niveau
	 */
	public int getNiveauIA(){
		return this.niveauIA;
	}
		
	/**
	 * Savoir si le joueur doit jouer immediatement
	 * @return si le joueur est oblige de jouer
	 */
	public boolean estForceAjouer(){
	    return this.forcerAjouer;		
	}
	
	/**
	 * Connaitre la couleur du joueur
	 * @return sa couleur
	 */
	public int getCouleur(){
		return this.couleur;
	}
		
	
	/**
	 * Fixer le type du joueur, humain ou IA
	 * @param type Le type de joueur
	 */
	public void setType(int type){
		this.type = type;
	}
	
	/**
	 * Fixer le niveau de l'IA pour ce joueur
	 * @param  niveau Le niveau de l'IA
	 */
	public void setNiveauIA(int niveau){
		this.niveauIA = niveau;
	}
	
	/**
	 * Fixer si le joueur doit jouer immediatement	
	 * @param force Si l'on doit forcer l'IA a jouer
	 */
	public void setForceAjouer(boolean force){
	     this.forcerAjouer = force;		
	}
	
	/**
	 * Joue un coup
	 * @param i la ligne 
	 * @param j la colonne
	 * @return si le coup a pu être joue
	 */
	public boolean jouerCoup(int i, int j){
		if(plateau.coupPossible(i, j, this.couleur)){
			   
		        plateau.poserPion(i,j,this.couleur);
		        this.actualisation.miseAJour();
		        return true;
		}else return false;
	}
	
	
	
	
	/**
	 * Fixe l'actualisation
	 * @param actualisation L'actualisation à fixer
	 */
	public void setActualisation(Actualisation actualisation){
		this.actualisation = actualisation;
	}


	
	/**
	 * Permet de connaitre l'actualisation
	 * @return l'actualisation
	 */
	public Actualisation getActualisation() {
		return this.actualisation;
	}
	
	
}
