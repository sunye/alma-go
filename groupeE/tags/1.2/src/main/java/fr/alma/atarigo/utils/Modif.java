// File Modif.java
// Last commited $Date$
// By $Author$
// Revision $Rev$
//
// Copyright (C) 2010 Clotilde Massot & Julien Durillon
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// as published by the Free Software Foundation; either version 2
// of the License, or (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
//
// The above copyright notice and this permission notice shall be
// included in all copies or substantial portions of this program.
package fr.alma.atarigo.utils;

import fr.alma.atarigo.utils.exceptions.BadGobanStateException;

/**
 * This Class defines a modification on the Goban. A modification is a 4-uplet, defining coordinates (line and column), and the before and after values
 * @author judu
 */
public class Modif {

    private int line;
    private int column;
    private StoneVal before;
    private StoneVal after;

    public Modif(int line, int column, StoneVal before, StoneVal after) {
        this.line = line;
        this.column = column;
        this.before = before;
        this.after = after;
    }

    public StoneVal getAfter() {
        return after;
    }

    public StoneVal getBefore() {
        return before;
    }

    public int getColumn() {
        return column;
    }

    public int getLine() {
        return line;
    }

    public Modif getReverted(){
        return new Modif(line, column, after, before);
    }

    /**
     * Reverse-patches the given goban from <em>after</em> to <em>before</em>.
     * If goban[line][column] does not contains the <em>after</em> value, there is an exception.
     * @param goban
     * @throws BadGobanStateException
     */
    public void revert(Goban goban) throws BadGobanStateException{
        if(goban.getCase(line, column) != this.after){
            throw new BadGobanStateException("Cannot apply this modification to the goban, since the after state is not right.");
        } else {
            goban.setCase(line, column, before);
        }
    }

    /**
     * Patches the given goban from <em>before</em> to <em>after</em>.
     * If goban[line][column] does not contains the <em>before</em> value, there is an exception.
     * @param goban
     * @throws BadGobanStateException
     */
    public void apply(Goban goban) throws BadGobanStateException{
        if(goban.getCase(line, column) != this.before){
            throw new BadGobanStateException("Cannot apply this modification to the goban, since the before state is not right.");
        } else {
            goban.setCase(line, column, after);
        }
    }

    /**
     * Creates a Stone with <em>line</em>, <em>column</em> and <em>before</em>.
     * @return
     */
    public Stone getOldStone(){
        return new Stone(getBefore(), line, column);
    }

    /**
     * Creates a Stone with <em>line</em>, <em>column</em> and <em>after</em>.
     * @return
     */
    public Stone getNewStone(){
        return new Stone(getAfter(), line, column);
    }

    @Override
    public String toString() {
        StringBuilder strb = new StringBuilder();
        return strb.append("{ ").append("line : ").append(line)
                .append(", column : ").append(column)
                .append(", before : ").append(before)
                .append(", after : ").append(after)
                .append(" }").toString();
    }

   @Override
   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      }
      if (getClass() != obj.getClass()) {
         return false;
      }
      final Modif other = (Modif) obj;
      if (this.line != other.line) {
         return false;
      }
      if (this.column != other.column) {
         return false;
      }
      if (this.before != other.before) {
         return false;
      }
      if (this.after != other.after) {
         return false;
      }
      return true;
   }
}
