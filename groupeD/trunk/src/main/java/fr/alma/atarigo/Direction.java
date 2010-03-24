package fr.alma.atarigo;

 /**
 * Enumeration representation les 8 directions possibles avec,
 * pour chaque, les corrections a apporter a une position.
 */
enum Direction {

 NORTH(0, -1),
 //NORD_EST(+1, -1),
 EAST(+1, 0),
 //SUD_EST(+1, +1),
 SOUTH(0, +1),
 //SUD_OUEST(-1, +1),
 WEST(-1, 0);
 //NORD_OUEST(-1, -1);

 /**
  * correction a apporter a une ligne. 
  */
 private int deltaLine;

 /**
  * correction a apporter a une colonne.
  */
 private int deltacolumn;

 /**
 * constructeur logique.
 */
 private Direction(int line, int column) {
	this.deltaLine = line;
	this.deltacolumn = column;
 }

 /**
  * accesseur en lecture
  */
 public int getDeltaLine() {
	return deltaLine;
 }
 

 /**
  * accesseur en lecture.
  */
 public int getDeltacolumn() {
	return deltacolumn;
 }

 /**
  * retourne la direction suivante dans l'ordre de l'enumeration.
  */
 public Direction next() {
	return values()[(ordinal() + 1) % values().length];
 }

 /**
 * retourne l'opposee de la direction.
 */
 public Direction opposit() {
	return values()[(ordinal() + 2) % values().length];
 }

}