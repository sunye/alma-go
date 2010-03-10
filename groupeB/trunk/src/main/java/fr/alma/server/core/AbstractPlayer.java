package fr.alma.server.core;

import java.util.List;
import java.util.Vector;

public abstract class AbstractPlayer implements IPlayer {
	
	private boolean color;
	private String name;
	protected List<PlayListener> listPlayerActionListener = null;
	
	public AbstractPlayer(String name, boolean color) {
		this.name = name;
		this.color = color;
	}
	
	public String getName() {
		return name;
	}

	public Boolean getColor() {
		return color;
	}

	public boolean addPlayListener(PlayListener playListener) {
		return getPlayerListeners().add(playListener);
	}

	public boolean removePlayListener(PlayListener playListener) {
		return getPlayerListeners().remove(playListener);
	}

	public List<PlayListener> getPlayerListeners() {
		if (listPlayerActionListener == null) {
			listPlayerActionListener = new Vector<PlayListener>();
		}
		return listPlayerActionListener;
	}

	protected boolean raiseEvent(PlayEvent event) {
		boolean retour = true;
		for (PlayListener listener : getPlayerListeners()) {
			if (!listener.actionPerformed(event)) {
				retour = false;
			}
		}
		return retour;
	};
}
