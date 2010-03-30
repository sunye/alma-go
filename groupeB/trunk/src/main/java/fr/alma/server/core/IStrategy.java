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

import fr.alma.server.ia.IEvaluation;

public interface IStrategy {
	public IEmplacement getEmplacementMax(IEvaluation evaluation, boolean trace);
	public IStateGame getStateGame();
	public void interrupt();
}
