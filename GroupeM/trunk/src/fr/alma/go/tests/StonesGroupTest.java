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
