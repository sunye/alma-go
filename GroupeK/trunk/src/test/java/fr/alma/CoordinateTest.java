package fr.alma;

import fr.alma.modele.Coordinate;
import fr.alma.modele.intelligence.CoordinateAI;
import junit.framework.TestCase;

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
