package fr.alma.server.core;

import java.util.List;

import fr.alma.client.ihm.GoObject;
import fr.alma.server.ia.IEvaluation;

public interface IPlayer extends GoObject {
	
	Boolean getColor();
	void play();
	boolean addPlayListener(PlayListener actionListener);
	boolean removePlayListener(PlayListener playListener);
	public void removePlayListeners();
	List<PlayListener> getPlayerListeners();
	void setEnabled(boolean enable);
	public void setStrategy(IStrategy strategieGame);
	public void interrupt();
	public void setEvaluation(IEvaluation evaluation);
	public String getName();
}
