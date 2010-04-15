package fr.alma.test;

import java.lang.reflect.Field;
import java.util.ArrayList;

import fr.alma.atarigo.Goban;
import fr.alma.atarigo.Position;
import fr.alma.atarigo.Stone;
import fr.alma.ia.Tree;
import junit.framework.TestCase;

public class TestTree extends TestCase {

	@SuppressWarnings("unchecked")
	public void testAddChild() {
		Goban goban = new Goban(1,1);
		Tree tree = new Tree(goban);
		Tree child = new Tree(goban);
		
		tree.addChild(child);
		
		Field field;
		try{
			field = tree.getClass().getDeclaredField("children_");
			//L'attribut étant privé, on le rend lisible
			field.setAccessible(true);
			//On récupère la pile devant être modifiée durant l'exécution
			ArrayList<Tree> list = (ArrayList<Tree>) field.get(tree);
			
			assertEquals(list.size(),1);
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	public void testIsLeaf() {
		Goban goban = new Goban(1,1);
		Tree tree = new Tree(goban);
		Tree child = new Tree(goban);
		
		tree.addChild(child);
		
		assertTrue(child.isLeaf());
		assertFalse(tree.isLeaf());
	}
	
	public void testGetParent() {
		Goban goban = new Goban(1,1);
		Tree tree = new Tree(goban);
		Tree child = new Tree(goban);
		
		assertEquals(tree.getParent(),null);
		tree.addChild(child);
		assertNotNull(child.getParent());
	}

	public void testGetChildren() {
		Goban goban = new Goban(1,1);
		Tree tree = new Tree(goban);
		assertNotNull(tree.getGoban());
	}

	public void testGenerateChildren() {
		Goban goban = new Goban(4,4);
		Tree tree = new Tree(goban);
		tree.generateChildren(null, Stone.WHITE, Stone.BLACK);
		assertEquals(tree.getChildren().size(),16);
	}
	
	public void testGetDepth(){
		Goban goban = new Goban(4,4);
		Tree tree = new Tree(goban);
		Tree tree2 = new Tree(null);
		assertEquals(tree.getDepht(),0);
		tree.addChild(tree2);
		assertEquals(tree2.getDepht(),1);
	}
	
	public void testGetMove(){
		Goban goban = new Goban(4,4);
		goban.writeCell(null, new Position(1,1), Stone.WHITE, true);
		Tree tree = new Tree(null);
		Tree tree2 = new Tree(goban);
		Tree tree3 = new Tree(null);
		tree.addChild(tree2);
		tree2.addChild(tree3);
		assertEquals(tree3.getMove(),goban);
		assertEquals(tree2.getMove(),goban);
	}
}
