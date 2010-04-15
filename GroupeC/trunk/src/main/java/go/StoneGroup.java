package go;

import java.util.Iterator;
import java.util.LinkedHashSet;


/**
 * @author Frédéric Dumonceaux
 *
 */
public class StoneGroup implements Cloneable, Constants {
     
     private LinkedHashSet<Position> stones;
     private LinkedHashSet<Position> liberties;
     private LinkedHashSet<Position> frontiers;
     private byte owner;
     
     private State state;
     
     private Coord extern_bottomleft;
     private Coord extern_topright;

     private LinkedHashSet<Position> eyes;
     
     
     /**
      * create an empty group with a color
      * (useful for State duplication)
      * @param color the owner of the group
      * @throws ExceptionGlobal
      */
     public StoneGroup(byte color, State s) throws ExceptionGlobal {
          
          if (color == EMPTY)
               throw new ExceptionGlobal("La case doit être occupée avant l'ajout dans un groupe !");
          owner = color;
          
          stones = new LinkedHashSet<Position>();
          liberties = new LinkedHashSet<Position>();
          frontiers = new LinkedHashSet<Position>();
          eyes = new LinkedHashSet<Position>();

          state = s;

          extern_bottomleft = new Coord(Byte.MIN_VALUE, Byte.MAX_VALUE);
          extern_topright = new Coord(Byte.MAX_VALUE, Byte.MIN_VALUE);
          
     }
     
     
     /**
      * create a stone group after a play
      * which is distinct from the others already declared (non-connected)
      * @param o_position the first stone of the group
      * @throws ExceptionGlobal 
      */
     public StoneGroup(Position o_position, State s) throws ExceptionGlobal {
          
          if (o_position.getOwnState() == EMPTY)
               throw new ExceptionGlobal("La case doit être occupée avant l'ajout dans un groupe !");

          owner = o_position.getOwnState();
          
          stones = new LinkedHashSet<Position>();
          stones.add(o_position);
          liberties = o_position.get4cLiberties();
          
          Iterator<Position> it = liberties.iterator();
          while (it.hasNext())
          {
               Position lib = it.next();
               lib.addFriendGroup(this);
          }
          
          frontiers = new LinkedHashSet<Position>();
          frontiers.add(o_position);
          eyes = new LinkedHashSet<Position>();
          
          state = s;
          
          byte x = o_position.getX();
          byte y = o_position.getY();
          
          extern_bottomleft = new Coord((byte)(x+1 <= GOBAN_SIZE-1 ? x+1 : GOBAN_SIZE-1),
                    (byte)(y-1 >= 0 ?  y-1 : 0));
          
          extern_topright = new Coord((byte)(x-1 >= 0 ? x-1 : 0),
                    (byte)(y+1 <= GOBAN_SIZE-1 ? y+1 : GOBAN_SIZE-1));
     }
     
     
     /**
      * @return the count of stones
      */
     public int countStones()
     {
          return stones.size(); 
     }


     /**
      * @return the count of liberties
      */
     public int countLiberties()
     {
          return liberties.size(); 
     }


     /**
      * @return the stones list
      */
     public LinkedHashSet<Position> getStones() {
          return stones;
     }


     /**
      * @return the liberties list
      */
     public LinkedHashSet<Position> getLiberties() {
          return liberties;
     }


     /**
      * @return the frontiers list
      */
     public LinkedHashSet<Position> getFrontiers() {
          return frontiers;
     }


     /**
      * @return the eyes position list
      */
     public LinkedHashSet<Position> getEyes() {
          return eyes;
     }


     /**
      * @return the external bottom-left
      */
     public Coord getBottomLeft() {
          return extern_bottomleft;
     }


     /**
      * @return the external top-right
      */
     public Coord getTopRight() {
          return extern_topright;
     }


     /**
      * @return the owner color
      */
     public byte getOwner() {
          return owner;
     }


     /**
      * @return the state which owns the stone group
      */
     public State getState() {
          return state;
     }

     
     /**
      * @param s the state which owns the stone group
      */
     public void setState(State s) {
          state = s;
     }


     /**
      * add a stone to a group and update the liberties
      * then make a merge of several groups if it is adjacent to more than 1 group
      * @param new_stone the stone to add
      * @throws ExceptionGlobal 
      */
     public void addStone(Position new_stone) throws ExceptionGlobal
     {
          boolean res = false;
          Iterator<Position> itr = stones.iterator();
          while(itr.hasNext())
               if (itr.next().equals(new_stone)) res = true;
     
          int col_enemy = owner == BLACK ? WHITE : BLACK;
          if (!res)
          {
               res = stones.add(new_stone);
               
               // either there are some 4c-liberties, either we can catch (or else non-allowed play)
               // thus, it's a frontier stone
               if (new_stone.get4cLiberties().size() > 0 || new_stone.getNeighborhoodList(col_enemy).size() > 0)
                    frontiers.add(new_stone);
               
               byte x = new_stone.getX();
               byte y = new_stone.getY();
               
               if (extern_bottomleft.coordX < x+1)
                    extern_bottomleft.coordX = (byte) (x+1 <= GOBAN_SIZE-1 ? x+1 : GOBAN_SIZE-1);
               
               if (extern_topright.coordX > x-1)
                    extern_topright.coordX = (byte) (x-1 >= 0 ? x-1 : 0);
               
               if (extern_bottomleft.coordY > y-1)
                    extern_bottomleft.coordY = (byte) (y-1 >= 0 ? y-1 : 0);
     
               if (extern_topright.coordY < y+1)
                    extern_topright.coordY = (byte) (y+1 <= GOBAN_SIZE-1 ? y+1 : GOBAN_SIZE-1);
     
               // liberties list are merged
               LinkedHashSet<Position> new_lib = new_stone.get4cLiberties();
               
               Iterator<Position> itr_lib = new_lib.iterator();
               itr = stones.iterator();
               
               // the position which receive the stone is no longer a liberty
               liberties.remove(new_stone);
               new_stone.clearFriendGroup();
               
               // for each liberty
               while(itr_lib.hasNext())
               {
                    Position one_lib = itr_lib.next();
                    
                    if (!stones.contains(one_lib))
                    {
                         liberties.add(one_lib);
                         one_lib.addFriendGroup(this);
                    }
                    
                    // search for eyes
                    if (one_lib.getLibertiesGroups().size() == 1 && one_lib.get4cLiberties().size() == 0) {
                         Iterator<StoneGroup> it = one_lib.getLibertiesGroups().iterator();
                         
                         LinkedHashSet<Position> eyes_set = it.next().getEyes();

                         if (eyes_set != null && !eyes_set.contains(one_lib)) {
                              eyes_set.add(one_lib);
                         }
                    }
               }
          }
     }


     /**
      * merge two stone groups
      * @param other the second group (which will be delete)
      * @throws ExceptionGlobal 
      */
     public void mergeWith(StoneGroup other) throws ExceptionGlobal
     {
          if (owner != other.owner)
               throw new ExceptionGlobal("Tentative de fusionner deux groupes de couleurs différentes !!");
          
          if (this == other)
               throw new ExceptionGlobal("Tentative de fusionner deux groupes identiques !!");
          
          Iterator<Position> itr_sto = other.stones.iterator();
     
          int col_enemy = owner == BLACK ? WHITE : BLACK;
          
          
          // merge of stones
          while(itr_sto.hasNext())
          {
               Position one_sto = itr_sto.next();
               
               // switch group
               one_sto.setGroup(this);
               stones.add(one_sto);
               
               // either there is liberties
               // either we make a catch and this a frontier stone
               if (!one_sto.isInside() || one_sto.getNeighborhoodList(col_enemy).size() > 0)
                    frontiers.add(one_sto);
               
               byte x = one_sto.getX();
               byte y = one_sto.getY();
               
               if (extern_bottomleft.coordX < x+1)
                    extern_bottomleft.coordX = (byte) (x+1 <= GOBAN_SIZE-1 ? x+1 : GOBAN_SIZE-1);
               
               if (extern_topright.coordX > x-1)
                    extern_topright.coordX = (byte) (x-1 >= 0 ? x-1 : 0);
               
               if (extern_bottomleft.coordY > y-1)
                    extern_bottomleft.coordY = (byte) (y-1 >= 0 ? y-1 : 0);
               
               if (extern_topright.coordY < y+1)
                    extern_topright.coordY = (byte) (y+1 <= GOBAN_SIZE-1 ? y : GOBAN_SIZE-1);
          }
          
          Iterator<Position> itr_lib = other.liberties.iterator();
          
          // merge of liberties position
          while(itr_lib.hasNext())
          {
               Position one_lib = itr_lib.next();

               if (!liberties.contains(one_lib) && one_lib.isPlayable())
               {
                    liberties.add(one_lib);
                    
                    // this group was included with caller, thus it disappears
                    one_lib.removeFriendGroup(other);
                    // this is added because of the common liberty
                    one_lib.addFriendGroup(this);
               }
               else
                    one_lib.removeFriendGroup(other);
          }
          
          // suppress of old liberties, only considers in stone add in a group
          updateLiberties();
     
          
          Iterator<Position> itr_fro = other.frontiers.iterator();
          
          // frontiers group
          while(itr_fro.hasNext())
          {
               Position one_fro = itr_fro.next();
               
               if (!frontiers.contains(one_fro) && !one_fro.isInside())
                    frontiers.add(one_fro);
          }
          
          Iterator<Position> itr_eye = other.eyes.iterator();
          
          // eyes merge
          while(itr_eye.hasNext())
          {
               Position one_eye = itr_eye.next();
               
               if (!eyes.contains(one_eye) && !one_eye.isInside())
                    eyes.add(one_eye);
          }
     }


     /**
      * reset the group
      */
     public void resetGroup()
     {
          Iterator<Position> itr = stones.iterator();
          
          Position p,q;
          
          // each stone is deleted from the group
          while(itr.hasNext())
          {
               p = itr.next();
               p.reset();
          }
                    
          
          // positions are at new liberties, connected groups are computed
          itr = stones.iterator();
          while(itr.hasNext())
          {
               p = itr.next();
               
               int x = p.getX();
               int y = p.getY();
               
              if (x < GOBAN_SIZE-1) {
                   q = state.getPosition(x+1,y);
                   if (!q.isPlayable())
                        p.addFriendGroup(q.getGroup());
              }
              
              if (y < GOBAN_SIZE-1) {
                   q = state.getPosition(x,y+1);
                   if (!q.isPlayable())
                        p.addFriendGroup(q.getGroup());
              }
              
              if (x > 0) {
                   q = state.getPosition(x-1,y);
                   if (!q.isPlayable())
                        p.addFriendGroup(q.getGroup());
              }
              
              if (y > 0) {
                   q = state.getPosition(x,y-1);
                   if (!q.isPlayable())
                        p.addFriendGroup(q.getGroup());
              }
          }
     }


     /**
      * method which update liberties
      */
     public void updateLiberties() {
          Iterator<Position> itr_l = liberties.iterator();
          
          // suppress of elder liberties
          while(itr_l.hasNext()) {
               Position one_lib = itr_l.next();
               
               if (!one_lib.isPlayable()) {
                    liberties.remove(one_lib);
                    one_lib.clearFriendGroup();
                    
                    itr_l = liberties.iterator();
               }
          }
          
          // add of new liberties (if catch occurs then those taken are now liberties)
          Iterator<Position> itr_s = stones.iterator();
          
          // for each stone
          while(itr_s.hasNext()) {
               Position one_sto = itr_s.next();
               
               // its liberties list
               LinkedHashSet<Position> new_lib = one_sto.get4cLiberties();
               Iterator<Position> itr_lib = new_lib.iterator();
               
               // for each liberty
               while(itr_lib.hasNext()) {
                    Position one_lib = itr_lib.next();
     
                    if (!liberties.contains(itr_lib))
                         liberties.add(one_lib);
                    
                    // search for eyes
                    if (one_lib.getLibertiesGroups().size() == 1 && one_lib.get4cLiberties().size() == 0) {
                         Iterator<StoneGroup> it = one_lib.getLibertiesGroups().iterator();
                         
                         LinkedHashSet<Position> eyes_set = it.next().getEyes();

                         if (eyes_set != null && !eyes_set.contains(one_lib)) {
                              eyes_set.add(one_lib);
                         }
                    }
               }
          }
     }


     /**
      * check if one stone is at least in the window of two groups
      * then its group is included in
      * @param other the second group
      */
     public boolean isIncludeIn(StoneGroup one, StoneGroup other)
     {
          // frontiers are checked
          Iterator<Position> itr_sto = frontiers.iterator();
          while(itr_sto.hasNext()) {
               if (itr_sto.next().isInterior(one, other))
                    return true;
          }
          return false;
     }

     
     /**
      * check if two groups are adjacent by their window intersection
      * (NOT required but in that case, there is more chance that's the truth)
      * the method compute the constraint such as at least one corner of a group is included in the other window
      * is true (and vice-versa)
      * @param other the group with which search intersection
      */
     public boolean hasIntersection(StoneGroup other)
     {
          Coord tr = extern_topright, otr = other.extern_topright;
          Coord bl = extern_bottomleft, obl = other.extern_bottomleft;
          
          boolean top = otr.coordX >= tr.coordX && otr.coordX <= bl.coordX;
          boolean bottom = obl.coordX <= bl.coordX && obl.coordX >= tr.coordX;
          
          boolean _top = otr.coordX <= tr.coordX && obl.coordX >= tr.coordX;
          boolean _bottom = otr.coordX >= tr.coordX && otr.coordX <= bl.coordX;
          
          // initial condition : either top either bottom is included
          if (!top && !bottom && !_top && !_bottom)
               return false;
          else if (top || bottom)
          {
               boolean left = obl.coordY >= bl.coordY && obl.coordY <= tr.coordY;
               boolean right = otr.coordY <= tr.coordY && otr.coordY >= bl.coordY;
               
               return left || right;
          }
          else if (_top || _bottom)
          {
               boolean _left = obl.coordY <= bl.coordY && otr.coordY >= bl.coordY;
               boolean _right = otr.coordY >= tr.coordY && obl.coordY <= tr.coordY;
               return _left || _right;
          }
          
          return false;
     }
     
     
     /**
      * search an external connection (liberties, 4-connected) between two groups
      * @param other the second group
      * @return list of all possible connection
      */
     public LinkedHashSet<Position> get4cConnection(StoneGroup other)
     {
          // liberties selection
          
          LinkedHashSet<Position> lst_fr1 = liberties;
          LinkedHashSet<Position> lst_fr2 = other.liberties;
          
          Iterator<Position> itr_lib1;
          Iterator<Position> itr_lib2;
          
          // initial group with the least number of liberties
          itr_lib1 = (lst_fr1.size() < lst_fr2.size() ? lst_fr1.iterator() : lst_fr2.iterator());
          int i = 0;
          
          // for each liberty
          LinkedHashSet<Position> cpt = new LinkedHashSet<Position>();
          while (itr_lib1.hasNext())
          {
               Position one_lib = itr_lib1.next();
               
               // if a neighbor group -> liberty
               while(itr_lib1.hasNext() && one_lib.getLibertiesGroups().size() == 1)
                    one_lib = itr_lib1.next();
               
               itr_lib2 = (lst_fr1.size() < lst_fr2.size() ? lst_fr2.iterator() : lst_fr1.iterator());
               
               // if common liberty
               while (itr_lib2.hasNext())
               {
                    ++i;
                    if (itr_lib2.next().equals(one_lib))
                         cpt.add(one_lib);
               }
          }
          //System.out.println(i);
          /*System.out.println("Fr 1 " + lst_fr1);
          System.out.println("Fr 2 " + lst_fr2);
          System.out.println("Ext " + cpt);*/
          
          return cpt;
     }


     /**
      * search an external connection (8c-liberties from frontiers stones) between two same color groups 
      * in order to underline if two groups are <em><b>separators</b></em>
      * and returns the separations number
      * @param other the second group
      * @return list of all possible connection
      */
     public LinkedHashSet<Position> get8cConnection(StoneGroup other)
     {     
          LinkedHashSet<Position> cpt = new LinkedHashSet<Position>();
          
          LinkedHashSet<Position> ext_list = new LinkedHashSet<Position>();
          LinkedHashSet<Position> ext_list2 = new LinkedHashSet<Position>();
          
          int i = 0;
          
          // initial group 
          Iterator<Position> itr_sto1 = frontiers.iterator();
          Iterator<Position> itr_sto2 = other.frontiers.iterator();
          
          // we search 8c-liberties of frontier stones
          
          // for each one
          while (itr_sto1.hasNext())
          {
               ++i;
               Position one_stone = itr_sto1.next();
               
               // compute external diagonal neighbor [4c] of each
               ext_list.addAll(one_stone.get4cDiagLiberties());
          }
     
          
          // for each one
          while (itr_sto2.hasNext())
          {
               ++i;
               Position one_stone = itr_sto2.next();
               
               // compute external diagonal neighbor [4c] of each
               ext_list2.addAll(one_stone.get4cDiagLiberties());
          }
          
          ext_list.addAll(liberties);
          ext_list2.addAll(other.liberties);
          
          // initial group 
          itr_sto1 = (ext_list.size() < ext_list2.size() ? ext_list.iterator() : ext_list2.iterator());
          
          while (itr_sto1.hasNext())
          {
               Position lib = itr_sto1.next();
               
               itr_sto2 = (ext_list.size() < ext_list2.size() ? ext_list2.iterator() : ext_list.iterator());
               while (itr_sto2.hasNext())
               {
                    ++i;
                    Position other_lib = itr_sto2.next();
                         
                    // common liberty ?
                    if (lib.equals(other_lib))
                         cpt.add(other_lib);
               }
          }

          //System.out.println(i);
          /*System.out.println("Lib8 1 " + ext_list);
          System.out.println("Lib8 2 " + ext_list2);
          System.out.println("nb ext " + cpt);*/
          return cpt;
     }


     /**
      * search for enemy groups near from common liberties of two friend groups
      * @param other the second group
      * @param enemy_groups enemy groups
      * @return enemy groups which match
      */
     public LinkedHashSet<Position> getEnemyTraitGroup(StoneGroup other, LinkedHashSet<StoneGroup> enemy_groups)
     {
          
          Iterator<StoneGroup> enemy_sg = enemy_groups.iterator();
     
          LinkedHashSet<Position> lib = get4cConnection(other);
          LinkedHashSet<Position> cpt = new LinkedHashSet<Position>();
          
          while (enemy_sg.hasNext())
          {
               StoneGroup one_group = enemy_sg.next();
               
               Iterator<Position> itr = lib.iterator();
               while (itr.hasNext())
               {
                    Position one_lib = itr.next();
                    
                    // if a neighbor group -> liberty
                    while(itr.hasNext() && one_lib.getLibertiesGroups().size() == 1)
                         one_lib= itr.next();
                    
                    if (one_group.liberties.contains(one_lib))
                         cpt.add(one_lib);
               }
          }
          
          return cpt;
     }
     
     
     /**
      * search for enemy groups near from common liberties of a friend group
      * @param enemy_groups enemy groups
      * @return enemy groups which match
      */
     public LinkedHashSet<Position> getEnemyTrait(LinkedHashSet<StoneGroup> enemy_groups)
     {
          
          Iterator<StoneGroup> enemy_sg = enemy_groups.iterator();
     
          LinkedHashSet<Position> lib = liberties;
          LinkedHashSet<Position> cpt = new LinkedHashSet<Position>();
          
          while (enemy_sg.hasNext())
          {
               StoneGroup one_group = enemy_sg.next();
               
               Iterator<Position> itr = lib.iterator();
               while (itr.hasNext())
               {
                    Position one_lib = itr.next();
                    
                    // if a neighbor group -> liberty
                    while(itr.hasNext() && one_lib.getLibertiesGroups().size() == 1)
                         one_lib= itr.next();
                    
                    if (one_group.liberties.contains(one_lib))
                         cpt.add(one_lib);
               }
          }
          
          return cpt;
     }
     
     
     public int distanceTo(StoneGroup other) {
          
          Iterator<Position> grp_X = frontiers.iterator();
          Iterator<Position> grp_Y = other.frontiers.iterator();
          
          int max = Integer.MIN_VALUE;
          int min = Integer.MAX_VALUE;
          
          while (grp_X.hasNext()) {
               
               Position one_X = grp_X.next();
               
               while (grp_Y.hasNext()) {
                    Position one_Y = grp_Y.next();
                    
                    // TODO Manhattan
                    int dist = Math.abs(one_X.getX() - one_Y.getX()) + Math.abs(one_X.getY() - one_Y.getY());
                    System.out.println(">"+dist + " : " + min);
                    if (dist < min)
                         min = dist;
               }
               System.out.println("min " + min );
               if (min > max)
                    max = min;
          }
          
          return max;
     }


     /* (non-Javadoc)
      * @see java.lang.Object#toString()
      */
     @Override
     public String toString() {
          return "|"+ stones.size() +"| |"+ liberties.size() +"| |"+ frontiers.size() +"| <" 
          + extern_bottomleft.toString() + "," + extern_topright.toString() +">" 
          + " {" 
          + (owner == BLACK ? "BK" : owner == WHITE ? "WH" : "EMPTY") + ", " + stones + "}, {EYES, " + eyes + " }\n";
     }
}

