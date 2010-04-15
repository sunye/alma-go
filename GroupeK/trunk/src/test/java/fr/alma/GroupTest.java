package fr.alma;

import fr.alma.modele.Group;
import fr.alma.modele.Stone;
import fr.alma.modele.StoneColor;
import junit.framework.TestCase;
/*$Author: manoel.fortun $ 
 * $Date: 2010-04-14 19:18:29 +0200 (mer., 14 avr. 2010) $ 
 * $Revision: 430 $ 
 * 
 *Copyright (C) 2010  Fortun Mano�l & Caillaud Anthony
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
public class GroupTest extends TestCase{
	
	public void testFusion(){
		
		Stone st1=  new Stone(0, 0, StoneColor.EMPTY);
		
		Stone st2=  new Stone(1, 0, StoneColor.EMPTY);
		
		Stone st3=  new Stone(0, 0, StoneColor.EMPTY);
		
		Group g1= new Group(StoneColor.EMPTY);
		
		Group g2= new Group(StoneColor.BLACK);
		
		g1.addPion(st1);
		
		g2.addPion(st2);
		
		g2.addPion(st3);
		
		g1.fusionGroup(g2);
		
	
		assertEquals(g2.getStoneNumber(),3);
		

	}

}
