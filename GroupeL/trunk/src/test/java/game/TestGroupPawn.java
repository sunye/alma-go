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
