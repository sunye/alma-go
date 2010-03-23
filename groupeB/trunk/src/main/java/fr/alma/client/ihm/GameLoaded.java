package fr.alma.client.ihm;


public class GameLoaded {

	private Boolean[][] intersection = new Boolean[Goban.LINE_V][Goban.LINE_H];
	
	public Boolean[][] getIntersection() {
		// true = player    false = computer
		/**
		 * COLONNE 0
		 */ 
	
		
		/**
		 * COLONNE 1
		 */
		
		
		
		
		/**
		 * COLONNE 2
		 */
		intersection[2][4] = false;
		intersection[2][5] = false;
		intersection[2][6] = false;

		/**
		 * COLONNE 3
		 */
		intersection[3][3] = false;
		intersection[3][4] = true;
		intersection[3][5] = true;
		intersection[3][6] = true;
		intersection[3][7] = false;
		/**
		 * COLONNE 4
		 */
		intersection[4][3] = false;
		intersection[4][4] = true;
		//intersection[4][5] = true;
		intersection[4][6] = true;
		intersection[4][7] = false;
		
		/**
		 * COLONNE 5
		 */
		intersection[5][3] = false;
		intersection[5][4] = true;
		intersection[5][5] = true;
		intersection[5][6] = true;
		intersection[5][7] = false;
		
		/**
		 * COLONNE 6
		 */
		intersection[6][4] = false;
		intersection[6][5] = false;
		intersection[6][6] = false;

		
		/**
		 * COLONNE 7
		 */
		/**
		 * COLONNE 8
		 */
		return intersection;
	}

}
