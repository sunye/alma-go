package fr.alma.test;

import java.util.ArrayList;
import java.util.LinkedList;

import fr.alma.atarigo.AtariGo;
import fr.alma.atarigo.Group;
import fr.alma.atarigo.GroupsList;
import fr.alma.atarigo.Stone;
import fr.alma.atarigo.Goban;
import fr.alma.atarigo.Position;
import junit.framework.TestCase;

public class TestGroupList extends TestCase {

        GroupsList GroupsList;
        Group g1;
        Group g2;
        ArrayList<Position> linkedStones1;
        ArrayList<Position> linkedStones2;
        Goban Goban;
        AtariGo atariGo;
       
        public void setUp(){
                Goban = new Goban(9,9);
                Goban.writeCell(atariGo, new Position(0,0), Stone.WHITE, true);
                Goban.writeCell(atariGo,new Position(1,0), Stone.WHITE, true);
                Goban.writeCell(atariGo,new Position(2,0), Stone.WHITE, true);
                linkedStones1 = new ArrayList();
                linkedStones1.add(new Position(0,0));
                linkedStones1.add(new Position(1,0));
                linkedStones1.add(new Position(2,0));
                g1 = new Group(linkedStones1,Stone.WHITE);
                Goban.writeCell(atariGo,new Position(0,1), Stone.BLACK, true);
                Goban.writeCell(atariGo,new Position(0,2), Stone.BLACK, true);
                Goban.writeCell(atariGo,new Position(0,3), Stone.BLACK, true);
                linkedStones2 = new ArrayList();
                linkedStones2.add(new Position(0,1));
                linkedStones2.add(new Position(0,2));
                linkedStones2.add(new Position(0,3));
                g2 = new Group(linkedStones2,Stone.BLACK);        
                GroupsList = new GroupsList();
                GroupsList.gList.add(g1);
                GroupsList.gList.add(g2);
        }

       
        public void testGetGroup(){
                assertEquals(g1,GroupsList.getGroup(new Position(0,0)));
                assertNull(GroupsList.getGroup(new Position(0,5)));
        }
       
        public void testAfficher(){
                GroupsList.print();
        }
       
        //TODO tests pas finis
        public void testCaculerGroups(){
                Goban.writeCell(atariGo,new Position(1,1), Stone.WHITE,true);
                GroupsList nouvListe = GroupsList.updateGroups(Goban,new Position(1,1), Stone.WHITE);
               
        }
       
       
}

