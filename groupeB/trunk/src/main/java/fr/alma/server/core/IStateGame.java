package fr.alma.server.core;

import fr.alma.client.action.GameLoader;
import fr.alma.client.ihm.GoObject;


public interface IStateGame extends Cloneable, GoObject {
	
	boolean existChild();
	
	boolean isPlayable(int col, int row);
	boolean play(int col, int row, boolean computer) throws Exception;
	void remove(int col, int row);
	int getMaxCol();
	int getMaxRow();
	boolean isOver();
	boolean isFree(int col, int row);
	boolean isComputer(int col, int row);
	boolean isPlayer(int col, int row);

	boolean onGoban(int col, int row);

	Object getIntersection(int col, int row);

	void load(GameLoader gameLoaded, String fileName);
	public int countLocationOccupied();
	public Object clone();
	public void clear();
	public int countLimitComputer();
	public int countLimitPlayer();

}
