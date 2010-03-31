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

import fr.alma.client.ihm.GoObject;
import fr.alma.server.rule.RuleManager;

/**
 * use to coordinate the game
 */
public interface ICoordinator extends GoObject{
	
	public abstract IPlayer getComputer();

	public abstract IPlayer getPlayer();

	public abstract RuleManager getRuleManager();

	public abstract IStateGame getStateGame();
	
	public void startGame();

}