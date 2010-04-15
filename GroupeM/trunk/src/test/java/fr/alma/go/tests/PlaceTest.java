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
