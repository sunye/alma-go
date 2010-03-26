package fr.alma.server.core;

import fr.alma.client.action.GameLoader;


public interface IStateGame extends Cloneable {
	
	boolean existChild();
	
	boolean isPlayable(short col, short row);
	boolean play(short col, short row, boolean computer) throws Exception;
	void remove(short col, short row);
	short getMaxCol();
	short getMaxRow();
	boolean isOver();
	boolean isFree(short col, short row);
	boolean isComputer(short col, short row);
	boolean isPlayer(short col, short row);

	boolean onGoban(short col, short row);

	Object getIntersection(short col, short row);

	void load(GameLoader gameLoaded, String fileName);
	public int countLocationOccupied();
	public Object clone();

}
