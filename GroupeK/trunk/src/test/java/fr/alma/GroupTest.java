package fr.alma;

import fr.alma.modele.Group;
import fr.alma.modele.Stone;
import fr.alma.modele.StoneColor;
import junit.framework.TestCase;

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
