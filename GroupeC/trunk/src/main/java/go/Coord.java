package go;


/**
 * @author Frédéric Dumonceaux
 *
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
