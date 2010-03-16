package fr.alma.atarigo;


/**
 * classe représentant une position dans une grille.
 */
public class Position {

 /**
  * numéro de ligne. 
  */
 private int ligne;

 /**
  * numéro de colonne
  */
 private int colonne;

 /*
  * constructeur logique.
  */
 public Position(int ligne, int colonne) {
	this.ligne = ligne;
	this.colonne = colonne;
 }

 public Position(Position position) {
		this.ligne = position.ligne;
		this.colonne = position.colonne;
}

/**
 * accesseur en lecture.
 */
 public int lireLigne() {
	return ligne;
 }

/**
 * accesseur en écriture.
 */
 public void ecrireLigne(int ligne) {
	this.ligne = ligne;
 }

/**
 * accesseur en lecture.
 */
 public int lireColonne() {
	return colonne;
 }

/**
 * accesseur en écriture
 */
 public void ecrireColonne(int colonne) {
	this.colonne = colonne;
 }

/**
 * retourne la position voisine selon une direction donnée.
 */
 public Position voisine(Direction direction) {
	return new Position(ligne + direction.lireDeltaLigne(),
			    colonne + direction.lireDeltaColonne());
 }

/**
 * surcharge
 */
 public String toString() {
	return "{ " + ligne + ", " + colonne + " }";
 }
 
 /**
  * vérifie l'égalité avec une autre position
  * @param position
  * @return
  */
 public boolean estEgale(Position position){
	 return position.lireLigne()==this.lireLigne() && position.lireColonne()==this.lireColonne();
 }

}