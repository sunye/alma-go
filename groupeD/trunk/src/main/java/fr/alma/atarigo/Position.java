package fr.alma.atarigo;


/**
 * Position.java represent a position in a board.
 * @author VINCENT FERREIRA, ADRIEN GUILLE
 * @version 1.0
 * 
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
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

 /** 
  * Copy constructor
  * @param position position we want to copy.
  */
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