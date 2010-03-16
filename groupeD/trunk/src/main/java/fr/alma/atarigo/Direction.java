package fr.alma.atarigo;

 /**
 * Enumeration representation les 8 directions possibles avec,
 * pour chaque, les corrections a apporter a une position.
 */
enum Direction {

 NORD(0, -1),
 //NORD_EST(+1, -1),
 EST(+1, 0),
 //SUD_EST(+1, +1),
 SUD(0, +1),
 //SUD_OUEST(-1, +1),
 OUEST(-1, 0);
 //NORD_OUEST(-1, -1);

 /**
  * correction a apporter a une ligne. 
  */
 private int deltaLigne;

 /**
  * correction a apporter a une colonne.
  */
 private int deltaColonne;

 /**
 * constructeur logique.
 */
 private Direction(int deltaLigne, int deltaColonne) {
	this.deltaLigne = deltaLigne;
	this.deltaColonne = deltaColonne;
 }

 /**
  * accesseur en lecture
  */
 public int lireDeltaLigne() {
	return deltaLigne;
 }
 

 /**
  * accesseur en lecture.
  */
 public int lireDeltaColonne() {
	return deltaColonne;
 }

 /**
  * retourne la direction suivante dans l'ordre de l'enumeration.
  */
 public Direction suivante() {
	return values()[(ordinal() + 1) % values().length];
 }

 /**
 * retourne l'opposee de la direction.
 */
 public Direction opposee() {
	return values()[(ordinal() + 2) % values().length];
 }

}