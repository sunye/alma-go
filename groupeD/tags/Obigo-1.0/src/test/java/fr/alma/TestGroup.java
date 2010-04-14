package fr.alma.test;

import java.util.ArrayList;
import java.util.LinkedList;

import fr.alma.atarigo.Group;
import fr.alma.atarigo.Stone;
import fr.alma.atarigo.Position;
import junit.framework.TestCase;

public class TestGroup extends TestCase {

        Group Group;
        ArrayList<Position> linkedStones;
        Stone Stone;
        
        public void setUp(){
                linkedStones = new ArrayList();
                linkedStones.add(new Position(0,1));
                linkedStones.add(new Position(0,2));
                linkedStones.add(new Position(0,0));
                Stone = Stone.WHITE;
                Group = new Group(linkedStones,Stone);
        }
        
        /*
        public void testClone(){
                assertEquals(Group.linkedStones.get(0).getLine(),Group.clone().linkedStones.get(0).getColumn());
                assertEquals(Group.linkedStones.get(0).getColumn(),Group.clone().linkedStones.get(0).getColumn());
        }*/
        
        public void testHasPos(){
                assertTrue(Group.hasPos(new Position(0,1)));
                assertFalse(Group.hasPos(new Position(2,2)));
        }
        
        public void testAfficher(){
                Group.print();
        }
        
        public void testAjouter(){
                Group.add(new Position(0,3));
                assertTrue(Group.hasPos(new Position(0,3)));
                
        }
        
}
