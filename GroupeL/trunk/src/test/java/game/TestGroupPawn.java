package game;

import junit.framework.TestCase;

public class TestGroupPawn extends TestCase {
	
	public void testDivideGroup(){
		
		/*	  12345
		 * 	1| 0
		 * 	2| 0
		 * 	3| X00
		 * 	4| 0
		 * 	5| 0
		 */
		GroupPawns groupTest = new GroupPawns(new Coordinates(2, 1),4,Color.WHITE);
		groupTest.getPawns().add(new Coordinates(2, 2));
		//groupTest.getPawns().add(new Coordinates(2, 3));
		groupTest.getPawns().add(new Coordinates(2, 4));
		groupTest.getPawns().add(new Coordinates(2, 5));
		groupTest.getPawns().add(new Coordinates(3, 3));
		groupTest.getPawns().add(new Coordinates(4, 3));
		
		assertEquals( groupTest.divideGroup().size() , 3 );
	}
	
	public void testTestPosition(){
		
		GroupPawns groupTest = new GroupPawns(new Coordinates(2, 1),4,Color.WHITE);
		groupTest.getPawns().add(new Coordinates(2, 2));
		groupTest.getPawns().add(new Coordinates(2, 3));
		groupTest.getPawns().add(new Coordinates(2, 4));
		groupTest.getPawns().add(new Coordinates(2, 5));
		groupTest.getPawns().add(new Coordinates(3, 3));
		groupTest.getPawns().add(new Coordinates(4, 3));
		
		Coordinates coordTest = new Coordinates(2, 3);
		
		assertTrue( groupTest.testPosition(coordTest)  );
	}	
}
