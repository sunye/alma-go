package fr.alma.atarigo;

 /**
 * Enumeration of the 4 possible directions.
 * and for each of them, the corrections to give.
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
enum Direction {

 NORTH(0, -1),
 EAST(+1, 0),
 SOUTH(0, +1),
 WEST(-1, 0);

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