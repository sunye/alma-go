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
 * Movement.java
 * @author FREDERIC DUMONT FREDERIC DUMONCEAUX
 * @version 1.0
 * 
 * revision $Revision$
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
