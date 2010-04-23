package myPack;


import java.util.Set;
/**
 * 
 */

/**
 * @author peter
 *
 */
public class situation {
Set<Case>	jeuJoueur;//voir Dico
/**
 * @return the jeuJoueur
 */
public Set<Case> getJeuJoueur() {
	return jeuJoueur;
}

/**
 * @param jeuJoueur the jeuJoueur to set
 */
public void setJeuJoueur(Set<Case> jeuJoueur) {
	this.jeuJoueur = jeuJoueur;
}

/**
 * @return the jeuAdvers
 */
public Set<Case> getJeuAdvers() {
	return jeuAdvers;
}

/**
 * @param jeuAdvers the jeuAdvers to set
 */
public void setJeuAdvers(Set<Case> jeuAdvers) {
	this.jeuAdvers = jeuAdvers;
}

Set<Case>	jeuAdvers;

public double fonctionEval(Set<Case> jeuJoueur,Set<Case> jeuAdvers){
return 0.0;	
}
}



//biblio de parties
//replay
//suicide
//casesAjouer
// optimisation fx par jeu auto , gdes parties, src d applis
//partie  en ..  match -> adaptation IA 