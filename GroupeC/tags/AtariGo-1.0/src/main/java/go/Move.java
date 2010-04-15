/*****************************************************************************
**
** This program is free software; you can redistribute it and/or modify
** it under the terms of the GNU General Public License as published by
** the Free Software Foundation; either version 2 of the License, or
** (at your option) any later version.
** 
** This program is distributed in the hope that it will be useful,
** but WITHOUT ANY WARRANTY; without even the implied warranty of
** MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
** GNU General Public License for more details.
** 
** You should have received a copy of the GNU General Public License
** along with this program; if not, write to the Free Software
** Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
*****************************************************************************/


package go;

/**
 * Move.java
 * @author FREDERIC DUMONT FREDERIC DUMONCEAUX
 * @version 1.0
 * 
 * revision $Revision$
 */

public class Move {
     
     public byte x;
     public byte y;
     
     public byte color;
     
     public float evaluation;
     public boolean evaluate;
     
     /**
      * @param x
      * @param y
      */
     public Move(byte x, byte y) {
          this.x = x;
          this.y = y;
     }
     
     
     /**
      * @param x
      * @param y
      */
     public Move(int x, int y, byte color) {
          this.x = (byte) x;
          this.y = (byte) y;
          this.color = color;
          
          evaluate = false;
     }
     
     
     /**
      * @param x
      * @param y
      */
     public void setMove(int x, int y, byte color) {
          this.x = (byte) x;
          this.y = (byte) y;
          this.color = color;
     }


     /**
      * @param evaluate the evaluate to set
      */
     public void setEvaluate(boolean evaluate) {
          this.evaluate = evaluate;
     }


     /**
      * @return the evaluate
      */
     public boolean isEvaluate() {
          return evaluate;
     }


     /* (non-Javadoc)
      * @see java.lang.Object#equals(java.lang.Object)
      */
     @Override
     public boolean equals(Object obj) {
          Move other = (Move) obj;
          System.out.println("test");
          if (x != other.x)
               return false;
          if (y != other.y)
               return false;
          return true;
     }


     /* (non-Javadoc)
      * @see java.lang.Object#toString()
      */
     @Override
     public String toString() {
          return "(" + x + "," + y + ")";
     }
     
     /**
 	 * Get X.
 	 * @return x
 	 */
 	public byte getX() {
 		return x;
 	}

 	/**
 	 * Set coordX.
 	 * @param coordx
 	 */
 	public void setX(byte coordx) {
 		this.x = coordx;
 	}

 	/**
 	 * Get coordY.
 	 * @return y
 	 */
 	public byte getY() {
 		return y;
 	}

 	/**
 	 * SetY.
 	 * @param coordy
 	 */
 	public void setY(byte coordy) {
 		this.y = coordy;
 	}

 	/**
 	 * Get Color.
 	 * @return
 	 */
 	public byte getColor() {
 		return color;
 	}

 	/**
 	 * Set color.
 	 * @param color
 	 */
 	public void setColor(byte color) {
 		this.color = color;
 	}
}
