package game;

import junit.framework.TestCase;

/*$Author$ 
 * $Date$ 
 * $Revision$ 
 * 
 *Copyright (C) 2010  Dejean Charles and Pottier Vincent
 *
 *This program is free software: you can redistribute it and/or modify
 *it under the terms of the GNU General Public License as published by
 *the Free Software Foundation, either version 3 of the License, or
 *(at your option) any later version.
 *
 *This program is distributed in the hope that it will be useful,
 *but WITHOUT ANY WARRANTY; without even the implied warranty of
 *MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *GNU General Public License for more details.
 *
 *You should have received a copy of the GNU General Public License
 *along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * */

public class TestGobanStructure extends TestCase {
	
	private GobanStructure initTest(){
		
		GobanStructure gobanTest = new GobanStructure();
		
		/*	  123456
		 * 	1| 0
		 * 	2| 0 0
		 * 	3| 00 0
		 * 	4| 0 0
		 * 	5| 0
		 */		
		gobanTest.addPawn(new Coordinates(2, 1),Color.WHITE);
		gobanTest.addPawn(new Coordinates(2, 2),Color.WHITE);
		gobanTest.addPawn(new Coordinates(2, 3),Color.WHITE);
		gobanTest.addPawn(new Coordinates(2, 4),Color.WHITE);
		gobanTest.addPawn(new Coordinates(2, 5),Color.WHITE);
		gobanTest.addPawn(new Coordinates(3, 3),Color.WHITE);
		gobanTest.addPawn(new Coordinates(4, 2),Color.WHITE);
		gobanTest.addPawn(new Coordinates(4, 4),Color.WHITE);
		gobanTest.addPawn(new Coordinates(5, 3),Color.WHITE);
		
		return gobanTest;
	}
	
	
	public void testAddPawn(){
		
		/*	  123456
		 * 	1| 0
		 * 	2| 0 0
		 * 	3| 0000
		 * 	4| 0 0
		 * 	5| 0
		 */			
		GobanStructure gobanTest = initTest();
		gobanTest.addPawn(new Coordinates(4, 3), Color.WHITE);
		
		assertEquals( gobanTest.getWhites().size() , 1 );				
	}
	
	public void testRemovePawn(){
		
		/*	  123456
		 * 	1| 0
		 * 	2| 0 0
		 * 	3|  0 0
		 * 	4| 0 0
		 * 	5| 0
		 */	
		GobanStructure gobanTest = initTest();
		gobanTest.removePawn(new Coordinates(2, 3));
		
		assertEquals( gobanTest.getWhites().size() , 6 );	
	}

	public void testMoveValid(){
		
		/*	  123456
		 * 	1| 0
		 * 	2| 0 0
		 * 	3| 00X0
		 * 	4| 0 0
		 * 	5| 0
		 */	
		GobanStructure gobanTest = initTest();
		assertFalse(gobanTest.moveValid(new Coordinates(4, 3), Color.BLACK));		
	}
}
