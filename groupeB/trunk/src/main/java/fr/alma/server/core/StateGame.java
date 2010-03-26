package fr.alma.server.core;


import fr.alma.client.action.GameLoader;
import fr.alma.server.rule.Configuration;

public class StateGame implements IStateGame {

	private Boolean[][] intersection = new Boolean[Configuration.LINE_V][Configuration.LINE_H];
	
	
	@Override
	public boolean existChild() {
		return false;
	}

	
	@Override
	public short getMaxCol() {
		return Configuration.LINE_V;
	}

	
	@Override
	public short getMaxRow() {
		return Configuration.LINE_H;
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
	 * @throws Exception 
	 */
	@Override
	public boolean play(short col, short row, boolean computer) throws Exception {
		if (! isFree(col, row)) {
			throw new Exception("busy box !");
		}
			
		intersection[col][row] = computer;
		return true;
	}

	@Override
	public void remove(short col, short row) {
		intersection[col][row] = null;
	}

	@Override
	public boolean isComputer(short col, short row) {
		return (intersection[col][row] == Configuration.getColorComputer());
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
		return (intersection[col][row] == Configuration.getColorPlayer());
	}
	
	
	public Boolean[][] getGoban(){
		return intersection;
	}

	
	@Override
	public Object getIntersection(short col, short row) {
		return intersection[col][row];
	}


	@Override
	public void load(GameLoader partyLoaded, String fileName) {
		this.intersection = partyLoaded.loadGame(fileName);
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
				if (! isFree(cptCol, cptRow)) {
					try {
						clone.play(cptCol, cptRow, (Boolean)getIntersection(cptCol, cptRow));
					} catch (Exception e) {
						System.out.println("Internal error : " + e.getLocalizedMessage());
					}
				}
			}
		}
		return clone;
	}
	
}
