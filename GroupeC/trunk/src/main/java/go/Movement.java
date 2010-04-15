package go; 


/**
 * Classe Mouvement
 * @author Fred Dumont
 *
 */
public class Movement implements Constants
{
     private int color;


     /**
      * Constructor
      * @param param_x
      * @param param_y
      * @param param_color
      */
     public Movement (final int param_color )
     {
          color = param_color;
     }

     /**
      * Set a color
      * @param color
      * @return
      */
     
     public int setColor(final int color){
          return this.color = color;
     }
     
     
     /**
      *  return the color of the movement
      */
     public int getColor( )
     {
          return color;
     }


     /* (non-Javadoc)
      * @see java.lang.Object#equals(java.lang.Object)
      */
     @Override
     public boolean equals(final Object obj) {
          boolean exit = true;
          final Movement other = (Movement) obj;
          System.out.println(color + " " +other.color);
          if (color != other.color){
               exit = false;
          }
          return exit;
     }
}
