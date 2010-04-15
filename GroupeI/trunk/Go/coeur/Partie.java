/**
 * @author Bouvet Frederic
 * @author Altuntas Murat
 */
package coeur;

import ia.CompteArebours;
import ia.Ia;

/**
 * Classe permettant de creer une partie de jeu
 */
public class Partie {
	
	/** Le joueur actuel */
	private Joueur joueurActuel;
	/** Le joueur noir */
	private Joueur joueurNoir;
	/** Le joueur blanc */
	private Joueur joueurBlanc;
	/** Statut de partie en cours */
	private boolean enCours;	
	/** Le plateau de jeu*/
	private Plateau plateau;
	/** L'ia du jeu */
	private Ia ia;	
	/** Pour l'actualisation */
	private Actualisation actualisation;
	/** le compte à rebours pour forcer l'ia a jouer au bout de n secondes */
	private CompteArebours compteArebours;		
	/** Couleur du joueur qui gagne (en cas d'abandon de l'adversaire ou si l'adversaire ne peut plus jouer) **/
	private int joueur_gagnant;
	
	/**
	 * Constructeur de la partie
	 * @param plateau le plateau de jeu
	 */
	public Partie(Plateau plateau) {		
		this.enCours = false;
		this.plateau = plateau;		
		this.joueurNoir = new Joueur(GoDonnees.HUMAIN,GoDonnees.NOIR, plateau, actualisation);
		this.joueurBlanc = new Joueur(GoDonnees.IA,GoDonnees.BLANC, plateau, actualisation);		
		this.joueur_gagnant= GoDonnees.VIDE;
	}
	
	public Plateau getPlateau(){
		return plateau;		
	}
	
	/**
	 * Pour commencer une nouvelle partie	 
	 */
	public void nouvelle(){
		this.enCours = true;		
		this.joueurActuel = joueurNoir;
		this.plateau.initialiser();		
		this.actualisation.miseAJour();
		
		// Si c'est au tour de l'IA, on le fait jouer		
		if(this.joueurActuel.getType()==GoDonnees.IA){
			ia.jouer(this.joueurActuel);			
		}		
	}
	
	/**
	 * Pour connaitre l'état de la partie
	 * @return si la partie est en cours
	 */
	public boolean enCours(){
		return this.enCours;
	}
	
		
	/**
	 * Pour connaitre à qui est le tour
	 * @return le joueur en cours
	 */
	public Joueur getJoueurActuel(){
		return this.joueurActuel;
	}
	
	/** Renvoie le joueur gagnant 
	 * @return le joueur gagnant
	 * **/
	public int getjoueur_gagant(){
		return this.joueur_gagnant;
	}
	
	/** Permet de modofier le joueur gagnant
	 * @param couleur la couleur du nouveau joueur gagnant
	 *  **/
	public void setjoueur_gagant(int couleur){
		if (couleur!=0){
			enCours=false;
		}
		joueur_gagnant=couleur;
	}
	
	/**
	 * Pour changer de joueur
	 * @throws InterruptedException	 
	 */
	public void joueurSuivant() {
		if(!this.plateau.existeCoupPossible(joueurActuel.getCouleur())){
			this.enCours=false;			
			this.actualisation.miseAJour();
		}
		else{
			// On change de joueur
			if(joueurActuel.getCouleur()==GoDonnees.NOIR)
				this.joueurActuel = joueurBlanc;
			else
				this.joueurActuel = joueurNoir;
			// On actualise
			this.actualisation.miseAJour();
			// On regarde si le joueur peut jouer
			if(plateau.nbrCoupPossible(joueurActuel.getCouleur())==0){
				// Si il ne peut pas l'adversaire a gagne
				if(joueurActuel.getCouleur()==GoDonnees.NOIR)
					this.joueur_gagnant=GoDonnees.BLANC;
				else
					this.joueur_gagnant=GoDonnees.NOIR;
				
				this.enCours = false;				
				this.actualisation.miseAJour();
			} else{
				if(this.joueurActuel.getType()==GoDonnees.IA){
					ia.jouer(this.joueurActuel);				
				}
			}			
			
		}
	}

	/**
	 * Fixe une ia
	 * @param ia l'Ia à fixer
	 */
	public void setIa(Ia ia) {
		this.ia = ia;		
	}
	
	/**
	 * Fixer l'actualisation
	 * @param actualisation l'actualisation que l'on souhaite fixer
	 */
	public void setActualisation(Actualisation actualisation){
		this.actualisation = actualisation;
		this.joueurBlanc.setActualisation(actualisation);
		this.joueurNoir.setActualisation(actualisation);
	}
	
	/**
	 * Permet de connaitre l'actualisation
	 * @return l'actualisation
	 */
	public Actualisation getActualisation(){
		return this.actualisation;
	}

	/**
	 * Fixer le compte a rebours
	 * @param LeCompteArebours le compte a rebours que l'on souhaite fixer
	 */
	public void setCompteArebours( CompteArebours LeCompteArebours){
		this.compteArebours = LeCompteArebours;
	}
	
	/**
	 * Permet de connaitre le compte a rebours
	 * @return le compte a rebours
	 */
	public  CompteArebours getCompteArebours(){
		return this.compteArebours;
	}
		
	/**
	 * Permet de connaitre le joueur noir
	 * @return la valeur du joueur noir
	 */
	public Joueur getJoueurNoir() {
		return this.joueurNoir;		
	}
	
	/**
	 * Permet de connaitre le joueur blanc
	 * @return la valeur du joueur blanc
	 */
	public Joueur getJoueurBlanc() {
		return this.joueurBlanc;		
	}	
	
	/**
	 * Permet de connaire l'IA
	 * @return l'ia 
	 */
	public Ia getIa(){
		return this.ia;
	}
	
}
