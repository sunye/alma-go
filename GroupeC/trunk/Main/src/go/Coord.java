package go;

/**
 * @author Fr�d�ric Dumonceaux
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
	 * @param other le groupe avec lequel cherch�
	 */
	public Coord getMaxBottomLeft(Coord other)
	{
	}


	/**
	 *  donne la position max
	 * @param other le groupe avec lequel cherch�
	 */
	public Coord getMaxTopRight(Coord other)
	{

	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}
	
}
