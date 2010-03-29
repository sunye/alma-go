package go;

/**
 * @author Frédéric Dumonceaux
 *
 */


public class Coord {
	
	public byte x;
	public byte y;
	
	
	/**
	 * @param x
	 * @param y
	 */
	public Coord(byte x, byte y) {
		this.x = x;
		this.y = y;
	}


	/**
	 *  donne la position max
	 * @param other le groupe avec lequel cherché
	 */
	public Coord getMaxBottomLeft(Coord other)	{
		
		Coord a = new Coord(x,y);
		
		return a;
	}


	/**
	 *  donne la position max
	 * @param other le groupe avec lequel cherché
	 */
	public Coord getMaxTopRight(Coord other) {
		
		Coord a = new Coord(x,y);
		
		return a;

	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}
	
}
