package go;

/**
 * @author Frédéric Dumonceaux
 *
 */


public class Move {
	

	
	/**
	 * @param x
	 * @param y
	 */
	public Move(byte x, byte y) {

	}
	
	
	/**
	 * @param x
	 * @param y
	 */
	public Move(int x, int y, byte color) {

	}
	
	
	/**
	 * @param x
	 * @param y
	 */
	public void setMove(int x, int y, byte color) {


	}


	/**
	 * @param evaluate the evaluate to set
	 */
	public void setEvaluate(boolean evaluate) {

	}


	/**
	 * @return the evaluate
	 */
	public boolean isEvaluate() {
		return true;

	}


	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return true;

	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "true";

	}
}
