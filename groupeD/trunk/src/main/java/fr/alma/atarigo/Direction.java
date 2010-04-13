package fr.alma.atarigo;

 /**
 * Enumeration of the 4 possible directions.
 * and for each of them, the corrections to give.
 * @version 1.0
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
  * correction to give to a line. 
  */
 private int deltaLine;

 /**
  * correction to give to a column.
  */
 private int deltacolumn;

 /**
 * logic constructor.
 */
 private Direction(int line, int column) {
	this.deltaLine = line;
	this.deltacolumn = column;
 }

 /**
  * accessor of deltaline
  */
 public int getDeltaLine() {
	return deltaLine;
 }
 

 /**
  * accessor of deltacolumn.
  */
 public int getDeltacolumn() {
	return deltacolumn;
 }

 /**
  * return the next direction in the order of the enumeration.
  */
 public Direction next() {
	return values()[(ordinal() + 1) % values().length];
 }

 /**
 * return the oposite direction.
 */
 public Direction opposit() {
	return values()[(ordinal() + 2) % values().length];
 }

}