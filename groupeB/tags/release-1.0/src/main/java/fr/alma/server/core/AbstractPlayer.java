/*
 *   
 *   IA Project ATARI-GO
 *   UNIVERSITY OF NANTES
 *   MASTER ALMA 1
 *   2009 - 2010
 * 	 Version 1.0
 *   
 *   $Date$
 *   $Author$
 * 	 $Revision$
 * 
 *   Copyright (C) 2010 Romain Gournay & Bruno Belin, All rights reserved.
 * 
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
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
	protected List<PlayListener> playerListeners = null;
	
	
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
		if (playerListeners == null) {
			playerListeners = new Vector<PlayListener>();
		}
		return playerListeners;
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
