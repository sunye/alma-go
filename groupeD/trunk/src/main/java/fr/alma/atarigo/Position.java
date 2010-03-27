package fr.alma.atarigo;


/**
 * Position.java represent a position in a board.
 */
public class Position {

 /**
  * number of the line.
  */
 private int line;

 /**
  * number of the column
  */
 private int column;

 /**
  * logic constructor.
  */
 public Position(int l, int c) {
	this.line = l;
	this.column = c;
 }

 public Position(Position position) {
		this.line = position.line;
		this.column = position.column;
}

/**
 * getter for the line attribute.
 */
 public int getLine() {
	return line;
 }

/**
 * setter for the line attribute.
 */
 public void writeLine(int newLine) {
	this.line = newLine;
 }

/**
 * getter for the column attribute.
 */
 public int getColumn() {
	return column;
 }

/**
 * setter for the column attribute.
 */
 public void writeColumn(int col) {
	this.column = col;
 }

/**
 * return the neighbor position from a given position.
 */
 public Position neighbor(Direction direction) {
	return new Position(line + direction.getDeltaLine(),
			    column + direction.getDeltacolumn());
 }

/**
 * override for the console mode game.
 */
 public String toString() {
	return "{ " + line + ", " + column + " }";
 }
 
 /**
  * verify the equality with an other position
  * @param position tested position
  */
 public boolean isEqual(Position position){
	 return position.getLine()==this.getLine() && position.getColumn()==this.getColumn();
 }

}