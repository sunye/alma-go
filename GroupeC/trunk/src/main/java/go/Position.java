package go;

import java.util.LinkedHashSet;


/**
 * @author Frédéric Dumonceaux
 *
 */
public class Position implements Constants {
     
     private byte x;
     private byte y;
     
     private byte own_state;
     private State state;
     
     private boolean inside;
     
     private StoneGroup group;
     
     private LinkedHashSet<StoneGroup> liberties_in_groups;
     private int liberties_black_groups = 0;
     private int liberties_white_groups = 0;
     

     /**
      * create an empty position (empty case)
      */
     public Position() {
          this.own_state = EMPTY;
          liberties_in_groups = new LinkedHashSet<StoneGroup>();
     }

     
     /**
      * @param x its abscissa
      * @param y its ordinate
      * @param o_state initial state during initialization phase
      * @param glob_state the goban which contains the position
      * @throws ExceptionGlobal 
      */
     public Position(byte x, byte y, byte o_state, State glob_state) throws ExceptionGlobal {
          if (glob_state == null)
               throw new ExceptionGlobal("Position don't rely to any state (null)");
          
          if (o_state < BLACK || o_state > WHITE)
               throw new ExceptionGlobal("Color could be only empty, black or white");
          
          if (x < 0 || y < 0)
               throw new ExceptionGlobal("Coordinate isn't an integer couple");

          this.x = x;
          this.y = y;
          this.own_state = o_state;
          this.state = glob_state;
          this.group = null;
          liberties_in_groups = new LinkedHashSet<StoneGroup>();
     }

     
     /**
      * @return abscissa of the position
      */
     public byte getX() {
          return x;
     }

     /**
      * @return ordonna of the position
      */
     public byte getY() {
          return y;
     }

     /**
 	 * SetX.
 	 * @param coordX coordX
 	 */
 	public void setX(byte coordX) {
 		this.x = coordX;
 	}
     
 	/**
	 * SetY.
	 * @param coordY coordY
	 */
	public void setY(byte coordY) {
		this.y = coordY;
	}
 	
     /**
      * @return its own state (or color)
      */
     public byte getOwnState() {
          return own_state;
     }


     /**
      * @return reference to the goban 
      */
     public State getState() {
          return state;
     }


     /**
      * @return reference to the group (not empty position)
      */
     public StoneGroup getGroup() {
          return group;
     }


     /**
      * @return the adjacent group of the empty position
      */
     public LinkedHashSet<StoneGroup> getLibertiesGroups() {
          return liberties_in_groups;
     }


     /**
      * @param state the state to set
      */
     public void setState(State state) {
          this.state = state;
     }


     /**
      * @param new_state color of the stone on the position
      * @throws ExceptionGlobal 
      */
     public void setStone(byte new_state) {
          own_state = new_state;
     }

     
     /**
      * @param the_group group which owns the stone (not empty position)
      * @throws ExceptionGlobal 
      */
     public void setGroup(StoneGroup the_group) {
          group = the_group;
     }

     
     /**
      * reset the stone in an empty position
      */
     public void reset() {
          setStone(EMPTY);
          setGroup(null);
          clearFriendGroup();
     }


     /**
      * @param s the stone group to add
      */
     public void addFriendGroup(StoneGroup s) {
          liberties_in_groups.add(s);
          
          if (s.getOwner() == BLACK)
               ++liberties_black_groups;
          else
               ++liberties_white_groups;
     }


     /**
      * @param s the stone group to remove
      */
     public void removeFriendGroup(StoneGroup s) {
          liberties_in_groups.remove(s);
          
          if (s.getOwner() == BLACK)
               --liberties_black_groups;
          else
               --liberties_white_groups;
     }


     /**
      * erase all references to friend stone groups
      */
     public void clearFriendGroup() {
          liberties_in_groups.clear();
          liberties_black_groups = 0;
          liberties_white_groups = 0;
     }


     /**
      * if one color group is adjacent, return it as color friend group
      */
     public int colorLibertiesGroup() {
          if (liberties_black_groups == 0)
               return WHITE;
          else
               return BLACK;
     }


     /**
      * @return check if there are only black or white friend stone groups
      */
     public boolean hasSameColorLibertiesGroup() {
          return liberties_black_groups == 0 ^ liberties_white_groups == 0;
     }


     /**
      * @return check if a stone group is friendly to the position
      */
     public boolean isFriendGroup(StoneGroup s) {
          return liberties_in_groups.contains(s);
     }
     
     
     /**
      * @return liberties set of a position
      */
     public LinkedHashSet<Position> get4cLiberties() {
         LinkedHashSet<Position> lst = new LinkedHashSet<Position>();
         Position p;
         
         if (x < GOBAN_SIZE-1)
         {
              p = state.getPosition(x+1,y);
              if (p.isPlayable())
                   lst.add(p);
         }
         
         if (y < GOBAN_SIZE-1)
         {
              p = state.getPosition(x,y+1);
              if (p.isPlayable())
                   lst.add(p);
         }
         
         if (x > 0)
         {
              p = state.getPosition(x-1,y);
              if (p.isPlayable())
                   lst.add(p);
         }
         
         if (y > 0)
         {
              p = state.getPosition(x,y-1);
              if (p.isPlayable())
                   lst.add(p);
         }
         
         if (lst.size() == 0)
              inside = true;
         else
              inside = false;
         return lst;
     }
     
     
     /**
      * @param color color to search
      * @return set of adjacent diagonal positions to it
      */
     public LinkedHashSet<Position> get4cDiagLiberties() {
         LinkedHashSet<Position> lst = new LinkedHashSet<Position>();
         
         // top-right corner
         if (x < GOBAN_SIZE-1 && y < GOBAN_SIZE-1 && state.getPosition(x+1,y+1).getOwnState() == EMPTY)
              lst.add(state.getPosition(x+1,y+1));
         
         // top-left corner
         if (x > 0 && y < GOBAN_SIZE-1 && state.getPosition(x-1,y+1).getOwnState() == EMPTY)
              lst.add(state.getPosition(x-1,y+1));
         
         // bottom-left corner
         if (x > 0 && y > 0 && state.getPosition(x-1,y-1).getOwnState() == EMPTY)
              lst.add(state.getPosition(x-1,y-1));
         
         // bottom-right corner
         if (x < GOBAN_SIZE-1 && y > 0 && state.getPosition(x+1,y-1).getOwnState() == EMPTY)
              lst.add(state.getPosition(x+1,y-1));
         
         return lst;
     }


     /**
      * @param color color to search
      * @return set of adjacent positions to it
      */
     public LinkedHashSet<Position> getNeighborhoodList(int color) {
         LinkedHashSet<Position> lst = new LinkedHashSet<Position>();
         
         if (x < GOBAN_SIZE-1 && state.getPosition(x+1,y).getOwnState() == color)
              lst.add(state.getPosition(x+1,y));
         if (y < GOBAN_SIZE-1 && state.getPosition(x,y+1).getOwnState() == color)
              lst.add(state.getPosition(x,y+1));
         if (x > 0 && state.getPosition(x-1,y).getOwnState() == color)
              lst.add(state.getPosition(x-1,y));
         if (y > 0 && state.getPosition(x,y-1).getOwnState() == color)
              lst.add(state.getPosition(x,y-1));
         
         return lst;
     }
     
     
     /**
      * @return check if a position isn't empty
      */
     public boolean isPlayable() {
          return own_state == EMPTY;
     }


     /**
      * @return check if a position has remaining liberties
      */
     public boolean isInside() {
          return inside;
     }



     /**
      * @return check if a position is included in the window composed by the two groups union
      */
     public boolean isInterior(StoneGroup one, StoneGroup other) {
          Coord bl = one.getBottomLeft().getMaxBottomLeft(other.getBottomLeft());
          Coord tp = one.getTopRight().getMaxTopRight(other.getTopRight());
          
          return (bl.coordX >= x && bl.coordY <= y && tp.coordX <= x && tp.coordY >= y);
     }


     /* (non-Javadoc)
      * @see java.lang.Object#equals(java.lang.Object)
      */
     @Override
     public boolean equals(Object obj) {
          Position other = (Position) obj;
          if (x != other.x)
               return false;
          if (y != other.y)
               return false;
          return true;
     }


     /* (non-Javadoc)
      * @see java.lang.Object#toString()
      */
     @Override
     public String toString() {
          return "[" + getX() + "," + getY()
          + ", " + (own_state == BLACK ? "BK" : own_state == WHITE ? "WH" : "EMPTY") + "]";
     }
}
