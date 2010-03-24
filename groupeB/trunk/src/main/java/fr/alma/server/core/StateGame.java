package fr.alma.server.core;


import fr.alma.client.ihm.GameLoaded;
import fr.alma.client.ihm.Goban;

public class StateGame implements IStateGame {

	private Boolean[][] intersection = new Boolean[Goban.LINE_V][Goban.LINE_H];
	
	
	@Override
	public boolean existChild() {
		return false;
	}

	@Override
	public short getMaxCol() {
		return Goban.LINE_V;
	}

	@Override
	public short getMaxRow() {
		return Goban.LINE_H;
	}

	@Override
	public boolean isOver() {
		// catch stone
		// Impossibility to play => the other player win
		return false;
	}

	@Override
	public boolean isPlayable(short col, short row) {
		return (intersection[col][row] == null);
	}

	/**
	 * Verifier les regles du jeu !
	 */
	@Override
	public boolean play(short col, short row, boolean computer) {
		/*
<<<<<<< .mine

		 *  Verifier les regles du jeu
		 *  - Piece deja presente a l'emplacement
=======
		 *  V�rifier les r�gles du jeu
		 *  - Pi�ce d�j� pr�sente � l'emplacement
>>>>>>> .r81
		 *  - ...
		 */
		
		intersection[col][row] = computer;
		return true;
	}

	@Override
	public void remove(short col, short row) {
		intersection[col][row] = null;
	}

	@Override
	public boolean isComputer(short col, short row) {
		return (intersection[col][row] == COMPUTER);
	}

	@Override
	public boolean isFree(short col, short row) {
		if(onGoban(col, row)){
			return (intersection[col][row] == null);
		}
		return false;
	}

	@Override
	public boolean onGoban(short col, short row) {
		return (col >= 0 && col < getMaxCol() && row >= 0 &&  row < getMaxRow());
	}


	@Override
	public boolean isPlayer(short col, short row) {
		return (intersection[col][row] == PLAYER);
	}
	
	public Boolean[][] getGoban(){
		return intersection;
	}

	@Override
	public Object getIntersection(short col, short row) {
		return intersection[col][row];
	}


	@Override
	public void loaded(GameLoaded gameLoaded) {
		this.intersection = gameLoaded.getIntersection();
	}	
	
	
	@Override
	public int countLocationOccupied() {
		int cptOccupied = 0;
		for (short cptCol = 0; cptCol < getMaxRow(); cptCol++) {
			for (short cptRow = 0; cptRow < getMaxCol(); cptRow++) {
				if (! isFree(cptCol, cptRow)) {
					cptOccupied++;
				}
			}
		}
		return cptOccupied;
	}

	
	@Override
	public Object clone() {
		StateGame clone = new StateGame();
		
		for (short cptCol = 0; cptCol < getMaxRow(); cptCol++) {
			for (short cptRow = 0; cptRow < getMaxCol(); cptRow++) {
				if (! isFree(cptCol, cptRow))
					clone.play(cptCol, cptRow, (Boolean)getIntersection(cptCol, cptRow));
			}
		}
		return clone;
	}
	
}
