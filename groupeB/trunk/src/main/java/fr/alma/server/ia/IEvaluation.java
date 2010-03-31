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
package fr.alma.server.ia;

import fr.alma.server.core.IStateGame;
import fr.alma.server.rule.StatusCheck;

public interface IEvaluation {
	int evaluate(IStateGame stateGame, StatusCheck status);
}
