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
		assertTrue("not good start color", Configuration.isColorFirstPlayer() == Configuration.BLACK);
		assertTrue("the level of search depth in the tree is incorrect", Configuration.getMaxDeepLevel(6) == 6);
		assertTrue("the level of search depth in the tree is incorrect", Configuration.getMaxDeepLevel(10) <=  5);
	}

}
