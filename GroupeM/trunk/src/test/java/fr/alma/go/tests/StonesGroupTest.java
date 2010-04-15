// Copyright (C) 2010 Alexandre Garnier & Yann Treguer
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// as published by the Free Software Foundation; either version 2
// of the License, or (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
//
// The above copyright notice and this permission notice shall be
// included in all copies or substantial portions of this program.

package fr.alma.go.tests;

import java.util.ArrayList;

import fr.alma.go.goban.Stone;
import fr.alma.go.goban.StonesGroup;
import junit.framework.TestCase;

public class StonesGroupTest extends TestCase {

	public void testSize() {
		StonesGroup group = new StonesGroup();
		assertEquals(group.size(), 0);
	}

	public void testAdd() {
		StonesGroup group = new StonesGroup();
		group.add(new Stone());
		assertEquals(group.size(), 1);
	}

	public void testRemove() {
		StonesGroup group = new StonesGroup();
		Stone stone = new Stone();
		group.add(stone);
		assertEquals(group.size(), 1);
		group.remove(stone);
		assertEquals(group.size(), 0);
	}

	public void testGetGroup() {
		StonesGroup group = new StonesGroup();
		group.add(new Stone());
		ArrayList<Stone> list = group.getGroup();
		assertEquals(list.size(),1);
	}
	
	public void testContains(){
		StonesGroup group = new StonesGroup();
		Stone stone = new Stone();
		assertFalse(group.contains(stone));
		group.add(stone);
		assertTrue(group.contains(stone));
	}

}
