package fr.alma;

import fr.alma.modele.Coordinate;
import fr.alma.modele.intelligence.CoordinateAI;
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
public class CoordinateTest extends TestCase {

	
	public void testConversionFromAi(){
		Coordinate ref=new Coordinate(0, 0);
		
		CoordinateAI teste= new CoordinateAI(0, 0);
		
		assertEquals(teste.convert(), ref);
		
		
	}
	
	
	public void testConvertionToAi(){
		Coordinate ref=new Coordinate(0, 0);
		CoordinateAI teste= new CoordinateAI(0, 0);
		CoordinateAI refAi=new CoordinateAI(null, null);
		refAi.setCoordinate(ref);
		assertEquals(teste, refAi);
	}
	
	public void testValidyEmpty(){
		Coordinate ref=new Coordinate(0, 8);
		assertTrue(ref.isValid(9));
		
	}
	
}
