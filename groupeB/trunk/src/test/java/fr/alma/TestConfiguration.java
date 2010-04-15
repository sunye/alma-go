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
package fr.alma;

import static org.junit.Assert.*;

import org.junit.Test;
import fr.alma.server.rule.Configuration;


/**
 * Verifies the coherence of the configuration
 */
public class TestConfiguration {

	
	@Test
	public void testFirstColor() {
		assertTrue("not good start color", Configuration.getColorFirstPlayer() == Configuration.BLACK);
		assertTrue("the level of search depth in the tree is incorrect", Configuration.getMaxDeepLevel(6) == 6);
		assertTrue("the level of search depth in the tree is incorrect", Configuration.getMaxDeepLevel(10) <=  5);
	}

}
