package fr.alma;

import fr.alma.modele.Coordinate;
import fr.alma.modele.GoBan;
import fr.alma.modele.MoveType;
import fr.alma.modele.StoneColor;
import junit.framework.TestCase;
/*$Author: manoel.fortun $ 
 * $Date: 2010-04-14 19:18:29 +0200 (mer., 14 avr. 2010) $ 
 * $Revision: 430 $ 
 * 
 *Copyright (C) 2010  Fortun Manoël & Caillaud Anthony
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
public class GoBanTest extends TestCase{

	
	public void testAddPion(){
		GoBan goban= new GoBan();
		Coordinate joue= new Coordinate(1, 1);
		
		assertEquals(goban.getGoban()[1][1].getColor(), StoneColor.EMPTY);
		
		StoneColor col= goban.getTurn();
		goban.addPion(joue);
		
		assertEquals(goban.getGoban()[1][1].getColor(), col);
	}
	
	
	public void testRemovePion(){
		
		GoBan goban= new GoBan();
		Coordinate joue= new Coordinate(1, 1);
			
		StoneColor col= goban.getTurn();
		goban.addPion(joue);
		
		
		
		goban.removeStone(joue, col);
		
		assertEquals(goban.getGoban()[1][1].getColor(), StoneColor.EMPTY);
		
	}

	
	public void testNONVALIDMove(){
		
		GoBan goban= new GoBan();
		Coordinate joue= new Coordinate(1, 1);
			
		StoneColor col= goban.getTurn();
		goban.addPion(joue);
		
		assertEquals(goban.isMoveAllowed(joue, col), MoveType.NONVALID);
	
	
	}
	
	
	public void testPrise(){
		
		GoBan goban= new GoBan();
		Coordinate joue1= new Coordinate(0, 1);
		Coordinate joue2= new Coordinate(1, 0);
		Coordinate joue3= new Coordinate(1, 2);
		Coordinate joue4= new Coordinate(2, 1);
		Coordinate jouePrise= new Coordinate(1, 1);
			

		goban.addPion(joue1, StoneColor.BLACK);
		goban.addPion(joue2, StoneColor.BLACK);
		goban.addPion(joue3, StoneColor.BLACK);
		goban.addPion(joue4, StoneColor.BLACK);
		
		assertEquals(goban.isMoveAllowed(jouePrise, StoneColor.WHITE), MoveType.NONVALID);
	
		
		
		
	}
	
	
	
	
}
