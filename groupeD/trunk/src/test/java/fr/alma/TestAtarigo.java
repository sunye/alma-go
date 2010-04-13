package fr.alma.test;

import fr.alma.atarigo.AtariGo;
import fr.alma.atarigo.Player;
import fr.alma.atarigo.HumanPlayer;
import fr.alma.atarigo.Stone;
import fr.alma.atarigo.Position;
import fr.alma.atarigo.AtariGo.Move;
import junit.framework.TestCase;

public class TestAtarigo extends TestCase {

        private AtariGo atarigo;
        private Player j1;
        private Player j2;
        
    protected void setUp() {
        atarigo = new AtariGo(9,9);
        j1 = new HumanPlayer(Stone.WHITE, "bob");
        j2 = new HumanPlayer(Stone.BLACK, "bill");
        
    }
    
    public void testplayMove(){
        Move MoveNeutre = atarigo.playMove(j1.color, new Position(0,0));
        Move MoveHorsPlateau = atarigo.playMove(j2.color, new Position(10,10));
        Move MoveSurMove = atarigo.playMove(j1.color, new Position(0,0));
        assertEquals(Move.NEUTRAL,Move.NEUTRAL);
        assertEquals(MoveHorsPlateau,Move.INVALID);
        assertEquals(MoveSurMove,Move.INVALID);
        //mise en place des cas de prise
        atarigo.playMove(j2.color, new Position(0,1));
        Move WIN1 = atarigo.playMove(j2.color, new Position(1,0));
        assertEquals(WIN1,Move.WIN);
        //on reinit pour tester d'autres Moves
        setUp();
        //mise en place de Stones WHITE pour test contre le suicide
        atarigo.playMove(j2.color, new Position(0,1));
        atarigo.playMove(j2.color, new Position(1,0));
        Move MoveSuicide = atarigo.playMove(j1.color, new Position(0,0));
        assertEquals(MoveSuicide,Move.INVALID);
        //mise en place pour le test contre le suicide en groupe
        atarigo.playMove(j2.color, new Position(2,1));
        atarigo.playMove(j2.color, new Position(0,2));
        atarigo.playMove(j2.color, new Position(2,2));
        atarigo.playMove(j2.color, new Position(1,3));
        atarigo.playMove(j1.color, new Position(1,1));
        Move MoveSuicideGroupe = atarigo.playMove(j1.color, new Position(1,2));
        assertEquals(MoveSuicideGroupe,Move.INVALID); 
        //mise en place du suicide pour gagner
        setUp();
        atarigo.playMove(j2.color, new Position(0,1));
        atarigo.playMove(j2.color, new Position(1,0));
        atarigo.playMove(j1.color, new Position(2,0));
        atarigo.playMove(j1.color, new Position(1,1));
        Move MoveSuicideWin = atarigo.playMove(j1.color, new Position(0,0));
        //TODO Ancienne version ou le suicide pour gagner n'etait pas encore implemente
        //assertEquals(MoveSuicideWin,Move.WIN); 
    }

        public void testAtariGo(){
        j1 = new HumanPlayer(Stone.WHITE, "bob");
        j2 = new HumanPlayer(Stone.BLACK, "bill");
                atarigo = new AtariGo(9,9,j1,j2);
        }
        
        public void testEstTermine(){
                atarigo.shutDown();
                assertTrue(atarigo.isOver());
        }
        
        public void testAccesseurs(){
                assertEquals(atarigo.getLines(),9);
                assertEquals(atarigo.getColumns(),9);
        }
        
        public void testNouvellePartie(){
                atarigo.newGame();
                assertFalse(atarigo.isOver());
        }
        
        
        
        
}
