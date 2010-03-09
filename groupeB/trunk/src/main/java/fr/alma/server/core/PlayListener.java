package fr.alma.server.core;

import java.util.EventListener;

public interface PlayListener extends EventListener {

	/**
	 * Invoked when an action occurs.
	 */
	public boolean actionPerformed(PlayEvent e);
}