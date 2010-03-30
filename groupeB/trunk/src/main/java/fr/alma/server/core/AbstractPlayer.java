/*
 * IA Project ATARI-GO
 * UNIVERSITY OF NANTES
 * MASTER ALMA 1
 * 2009 - 2010
 * Version 1.0
 * @author Romain Gournay & Bruno Belin
 * 
 * Copyright 2010 Romain Gournay & Bruno Belin, All rights reserved.
 * Use is subject to license terms.
 */
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
	
	@Override
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

	public void removePlayListeners() {
		getPlayerListeners().clear();
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
			if (! listener.actionPerformed(event)) {
				retour = false;
			}
		}
		return retour;
	};
	
	public String toString() {
		return name;
	}
}
