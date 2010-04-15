package fr.alma.go.tests;

import fr.alma.go.goban.Place;
import fr.alma.go.heuristics.GameNode;
import junit.framework.TestCase;

public class GameNodeTest extends TestCase {

	public void testSetCoords(){
		int abs=(int)(Math.random()*9);
		int ord=(int)(Math.random()*9);
		GameNode node=new GameNode();
		node.setCoords(abs,ord);
		assertEquals(node.getPlace().getAbs(),abs);
		assertEquals(node.getPlace().getOrd(),ord);
	}

	public void testSetPlace(){
		int abs=(int)(Math.random()*9);
		int ord=(int)(Math.random()*9);
		GameNode node=new GameNode();
		node.setPlace(new Place(abs,ord));
		assertEquals(node.getPlace().getAbs(),abs);
		assertEquals(node.getPlace().getOrd(),ord);
	}
	
	public void testSetNote(){
		GameNode node=new GameNode();
		node.setNote(0);
		assertEquals(node.getNote(),0);
	}

}
