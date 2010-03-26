package fr.alma.server.core;

import java.util.List;

public interface IPlayer {
	
	Boolean getColor();
	void play();
	boolean addPlayListener(PlayListener actionListener);
	boolean removePlayListener(PlayListener playListener);
	public void removePlayListeners();
	List<PlayListener> getPlayerListeners();
	void setEnabled(boolean enable);
	public void setStrategieGame(IStrategy strategieGame);
	public String getName();
}
