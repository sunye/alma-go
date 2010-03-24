package fr.alma.atarigo;


/**
 * classe représentant une position dans une grille.
 */
public class Position {

 /**
  * numéro de ligne. 
  */
 private int line;

 /**
  * numéro de colonne
  */
 private int column;

 /*
  * constructeur logique.
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
 * accesseur en lecture.
 */
 public int getLine() {
	return line;
 }

/**
 * accesseur en écriture.
 */
 public void writeLine(int newLine) {
	this.line = newLine;
 }

/**
 * accesseur en lecture.
 */
 public int getColumn() {
	return column;
 }

/**
 * accesseur en écriture
 */
 public void writeColumn(int col) {
	this.column = col;
 }

/**
 * retourne la position voisine selon une direction donnée.
 */
 public Position neighbor(Direction direction) {
	return new Position(line + direction.getDeltaLine(),
			    column + direction.getDeltacolumn());
 }

/**
 * surcharge
 */
 public String toString() {
	return "{ " + line + ", " + column + " }";
 }
 
 /**
  * vérifie l'égalité avec une autre position
  * @param position
  * @return
  */
 public boolean isEqual(Position position){
	 return position.getLine()==this.getLine() && position.getColumn()==this.getColumn();
 }

}