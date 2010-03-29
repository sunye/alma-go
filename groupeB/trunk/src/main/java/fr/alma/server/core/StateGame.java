package fr.alma.server.core;


import fr.alma.client.action.Context;

public class StateGame implements IStateGame {

	private Boolean[][] intersection = null;
	private Context context = null;
	private int countLimitComputer = 0;
	private int countLimitPlayer = 0;
	
	public StateGame(Context context) {
		this.context = context;
		clear();
	}
	
	
	@Override
	public boolean existChild() {
		return false;
	}

	
	@Override
	public int getMaxCol() {
		return context.getSizeGoban();
	}

	
	@Override
	public int getMaxRow() {
		return context.getSizeGoban();
	}

	
	@Override
	public boolean isOver() {
		// catch stone
		// Impossibility to play => the other player win
		return false;
	}

	@Override
	public boolean isPlayable(int col, int row) {
		return (intersection[col][row] == null);
	}

	/**
	 * Verifier les regles du jeu !
	 * @throws Exception 
	 */
	@Override
	public boolean play(int col, int row, boolean computer) throws Exception {
		if (! isFree(col, row)) {
			throw new Exception("busy box at col = " + col + ", row = " + row + " !");
		}
		
		intersection[col][row] = computer;
		return true;
	}

	@Override
	public void remove(int col, int row) {
		intersection[col][row] = null;
	}

	@Override
	public boolean isComputer(int col, int row) {
		return (intersection[col][row] == context.getComputer().getColor());
	}

	@Override
	public boolean isFree(int col, int row) {
		if (onGoban(col, row)) {
			return (intersection[col][row] == null);
		}
		return false;
	}

	@Override
	public boolean onGoban(int col, int row) {
		return (col >= 0 && col < getMaxCol() && row >= 0 &&  row < getMaxRow());
	}


	@Override
	public boolean isPlayer(int col, int row) {
		return (intersection[col][row] == context.getPlayer().getColor());
	}
	
	
	public Boolean[][] getGoban(){
		return intersection;
	}

	
	@Override
	public Object getIntersection(int col, int row) {
		return intersection[col][row];
	}


	@Override
	public void load(Boolean[][] intersection) {
		this.intersection = intersection;
	}

	
	@Override
	public int countLocationOccupied() {
		int cptOccupied = 0;
		for (int cptCol = 0; cptCol < getMaxRow(); cptCol++) {
			for (int cptRow = 0; cptRow < getMaxCol(); cptRow++) {
				if (! isFree(cptCol, cptRow)) {
					cptOccupied++;
				}
			}
		}
		return cptOccupied;
	}

	
	@Override
	public Object clone() {
		StateGame clone = new StateGame(context);
		
		for (int cptCol = 0; cptCol < getMaxRow(); cptCol++) {
			for (int cptRow = 0; cptRow < getMaxCol(); cptRow++) {
				if (! isFree(cptCol, cptRow)) {
					try {
						clone.play(cptCol, cptRow, (Boolean)getIntersection(cptCol, cptRow));
					} catch (Exception e) {
						System.out.println("Internal error in clone operation : " + e.getLocalizedMessage());
					}
				}
			}
		}
		return clone;
	}

	@Override
	public void clear() {
		intersection = new Boolean[context.getSizeGoban()][context.getSizeGoban()];
	}

	@Override
	public int countLimitComputer() {
		return countLimitComputer;
	}

	@Override
	public int countLimitPlayer() {
		return countLimitPlayer;
	}


	@Override
	public void cleanUp() {
		clear();
		
	}
	
}
