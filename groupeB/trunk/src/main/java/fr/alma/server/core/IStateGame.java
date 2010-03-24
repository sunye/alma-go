package fr.alma.server.core;

import fr.alma.client.ihm.GameLoaded;


public interface IStateGame extends Cloneable {
	public static final Boolean PLAYER = false;
	public static final Boolean COMPUTER = true;
	
	boolean existChild();
	
	boolean isPlayable(short col, short row);
	boolean play(short col, short row, boolean computer);
	void remove(short col, short row);
	short getMaxCol();
	short getMaxRow();
	boolean isOver();
	boolean isFree(short col, short row);
	boolean isComputer(short col, short row);
	boolean isPlayer(short col, short row);

	boolean onGoban(short col, short row);

	Object getIntersection(short col, short row);

	public int countLocationOccupied();
	void loaded(GameLoaded gameLoaded);
	public Object clone();

}
