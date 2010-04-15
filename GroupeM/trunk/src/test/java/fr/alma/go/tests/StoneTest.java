package fr.alma.go.tests;

import fr.alma.go.goban.Stone;
import junit.framework.TestCase;

public class StoneTest extends TestCase {
	
	public void testIsUndefined(){
		Stone stone=new Stone();
		assertTrue(stone.isUndefined());
	}
	
	public void testGetColor(){
		Stone stone=new Stone();
		assertTrue(stone.isUndefined());
		stone=new Stone('w');
		assertEquals(stone.getColor(),'w');
		stone=new Stone('b');
		assertEquals(stone.getColor(),'b');
	}

	public void testSetColor() {
		Stone stone=new Stone();
		stone.setColor('w');
		assertEquals(stone.getColor(),'w');
		stone.setColor('b');
		assertEquals(stone.getColor(),'b');
		stone.setColor('u');
		assertTrue(stone.isUndefined());
	}

}
