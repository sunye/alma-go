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
 * Coord.java
 * @author FREDERIC DUMONT FREDERIC DUMONCEAUX
 * @version 1.0
 * 
 * revision $Revision$
 */
public class Coord {

    /**
     * Variable coordX.
     */
     public byte coordX;

    /**
     * Variable coordY.
     */
     public byte coordY;

     /**
      * @param newcoordX coordX
      * @param newcoordY coordY
      */
     public Coord(final byte newcoordX, final byte newcoordY) {
          this.coordX = newcoordX;
          this.coordY = newcoordY;
     }


     /**
      * returns max bottom-left position.
      * @param other group with which search
      * @return Coord a new coord
      */
     public final Coord getMaxBottomLeft(final Coord other) {

          final byte byteB = coordX;
          final byte byteL = coordY;
          final byte otherX = other.coordX;
          final byte otherY = other.coordY;

          return new Coord((byteB > otherX ? byteB : otherX),
                    (byteL < otherY ? byteL : otherY));
     }


     /**
      * returns max top-right position.
      * @param other group with which search
      * @return Coord a nex Coord
      */
     public final Coord getMaxTopRight(final Coord other) {

          final byte byteT = coordX;
          final byte byteR = coordY;
          final byte otherX = other.coordX;
          final byte otherY = other.coordY;

          return new Coord((byteT < otherX ? byteT : otherX)
                    , (byteR > otherY ? byteR : otherY));
     }

     /**
      * Get Y.
      * @return coordY
      */
     public final byte getCoordY() {
          return coordY;
     }

     /**
      * Initialize Y.
      * @param newcoordY newcoordY
      */
     public final void setCoordY(final byte newcoordY) {
          this.coordY = newcoordY;
     }

     /**
      * Get X.
      * @return coordX
      */
     public final byte getCoordX() {
          return coordX;
     }

     /**
      * Initialize X.
      * @param newcoordX newcoordX
      */
     public final void setCoordX(final byte newcoordX) {
          this.coordX = newcoordX;
     }

     /* (non-Javadoc)
      * @see java.lang.Object#toString()
      */
     @Override
     public final String toString() {
          return "(" + coordX + "," + coordY + ")";
     }
}
