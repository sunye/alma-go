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

/**
 * Define a basic player
 */
public abstract class AbstractPlayer implements IPlayer {
	
	private boolean color;
	private String name;
	protected List<PlayListener> listPlayerActionListener = null;
	
	
	public AbstractPlayer(String name, boolean color) {
		this.name = name;
		this.color = color;
	}
	
	/*
	 * (non-Javadoc)
	 * @see fr.alma.server.core.IPlayer#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/*
	 * (non-Javadoc)
	 * @see fr.alma.server.core.IPlayer#getColor()
	 */
	@Override
	public Boolean getColor() {
		return color;
	}

	/*
	 * (non-Javadoc)
	 * @see fr.alma.server.core.IPlayer#addPlayListener(fr.alma.server.core.PlayListener)
	 */
	@Override
	public boolean addPlayListener(PlayListener playListener) {
		return getPlayerListeners().add(playListener);
	}

	/*
	 * (non-Javadoc)
	 * @see fr.alma.server.core.IPlayer#removePlayListener(fr.alma.server.core.PlayListener)
	 */
	@Override
	public boolean removePlayListener(PlayListener playListener) {
		return getPlayerListeners().remove(playListener);
	}
	
	/*
	 * (non-Javadoc)
	 * @see fr.alma.server.core.IPlayer#removePlayListeners()
	 */
	@Override
	public void removePlayListeners() {
		getPlayerListeners().clear();
	}
	/*
	 * (non-Javadoc)
	 * @see fr.alma.server.core.IPlayer#getPlayerListeners()
	 */
	@Override
	public List<PlayListener> getPlayerListeners() {
		if (listPlayerActionListener == null) {
			listPlayerActionListener = new Vector<PlayListener>();
		}
		return listPlayerActionListener;
	}

	/**
	 * Events spread toward listeners (bind with the observer pattern)
	 * @param event
	 * @return false if a listener have interrupted the current action
	 */
	protected boolean raiseEvent(PlayEvent event) {
		boolean result = true;
		for (PlayListener listener : getPlayerListeners()) {
			if (! listener.actionPerformed(event)) {
				result = false;
			}
		}
		return result;
	};
	
	public String toString() {
		return name;
	}
}
