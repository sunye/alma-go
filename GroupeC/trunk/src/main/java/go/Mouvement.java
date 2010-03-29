package go; 


/**
 * Classe Mouvement
 * @author Fred Dumont
 *
 */
public class Mouvement implements Constants
{
  private int color;
  
  
 /**
  * Constructeur Mouvement
  * @param param_x
  * @param param_y
  * @param param_color
  */
  Mouvement (int param_color )
  {

  }

  /**
   *  getColor
   */
  public int getColor( )
  {
    return ( color );
  }


	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return true;

	}
}
