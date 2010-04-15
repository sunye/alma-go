package fr.alma;

import fr.alma.modele.Coordinate;
import fr.alma.modele.GoBan;
import fr.alma.modele.MoveType;
import fr.alma.modele.StoneColor;
import junit.framework.TestCase;

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
