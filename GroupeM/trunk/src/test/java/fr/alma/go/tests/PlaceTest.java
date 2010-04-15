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

import fr.alma.go.goban.Place;
import junit.framework.TestCase;

public class PlaceTest extends TestCase {

	public void testGetters(){
		int abs=(int)(Math.random()*9);
		int ord=(int)(Math.random()*9);
		Place place=new Place(abs,ord);
		assertEquals(place.getAbs(),abs);
		assertEquals(place.getOrd(),ord);
	} // void testGetters()

	public void testEquals(){
		int abs=(int)(Math.random()*9);
		int ord=(int)(Math.random()*9);
		Place place1=new Place(abs,ord);
		Place place2=new Place(abs,ord);
		assertTrue(place1.equals(place2));
	} // void testGetters()

	public void testToString(){
		int abs=(int)(Math.random()*9);
		int ord=(int)(Math.random()*9);
		Place place=new Place(abs,ord);
		assertEquals(place.toString(),abs+","+ord);
	} // void testGetters()
	
}
