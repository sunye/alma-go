package fr.alma.server.core;

import java.util.List;

public interface IPlayer {
	public static final Boolean WHITE = true;
	public static final Boolean BLACK = false;
	
	Boolean getColor();
	void play();
	boolean addPlayListener(PlayListener actionListener);
	boolean removePlayListener(PlayListener playListener);
	List<PlayListener> getPlayerListeners();
	void setEnabled(boolean enable);
}
