package fr.alma;

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

public class ColorTest extends TestCase{
	
	
	public void testOposeEmpty(){
		StoneColor empty=StoneColor.EMPTY;
		assertEquals(StoneColor.oppose(empty), empty);
		
		
	}
	
	
	public void testOposeBlackWhite(){
		StoneColor White=StoneColor.WHITE;
		StoneColor Black=StoneColor.BLACK;
		
		assertEquals(StoneColor.oppose(White), Black);
		
		assertEquals(StoneColor.oppose(Black), White);
		
		
	}
	
}
