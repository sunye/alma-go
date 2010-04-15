/*****************************************************************************
**
** This program is free software; you can redistribute it and/or modify
** it under the terms of the GNU General Public License as published by
** the Free Software Foundation; either version 2 of the License, or
** (at your option) any later version.
** 
** This program is distributed in the hope that it will be useful,
** but WITHOUT ANY WARRANTY; without even the implied warranty of
** MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
** GNU General Public License for more details.
** 
** You should have received a copy of the GNU General Public License
** along with this program; if not, write to the Free Software
** Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
*****************************************************************************/


package go;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.LinkedHashSet;

/**
 * State.java
 * @author FREDERIC DUMONT FREDERIC DUMONCEAUX
 * @version 1.0
 * 
 * revision $Revision$
 */
public class State implements Cloneable, Constants {

     private final transient Position[][] goban;
     private final transient LinkedHashSet<StoneGroup> blackStonesGroups;
     private final transient LinkedHashSet<StoneGroup> whiteStonesGroups;

     private final transient Main frame;

     /**
      * Create a new instance of the initial state game
      * @throws ExceptionGlobal 
      */
     public State(Main frame) {

          goban = new Position[GOBAN_SIZE][GOBAN_SIZE];

          for(byte i=0; i <= GOBAN_SIZE-1 ; ++i){
               for (byte j=0; j <= GOBAN_SIZE-1 ; ++j){
                    try {
                         goban[i][j] = new Position(i,j,EMPTY,this);
                    } catch (ExceptionGlobal e) {
                         e.writeMessage();
                    }
               }
          }
          this.frame = frame; 
          blackStonesGroups = new LinkedHashSet<StoneGroup>();
          whiteStonesGroups = new LinkedHashSet<StoneGroup>();
     }


     /**
      * makes a deep-copy from a state
      * @param state the state to duplicate
      * @throws ExceptionGlobal 
      */
     public State(final State state) throws ExceptionGlobal {

          goban = new Position[GOBAN_SIZE][GOBAN_SIZE];

          blackStonesGroups = new LinkedHashSet<StoneGroup>();
          whiteStonesGroups = new LinkedHashSet<StoneGroup>();
          frame = state.frame; 

          // goban initialization
          for(byte i=0; i <= GOBAN_SIZE-1 ; ++i){
               for (byte j=0; j <= GOBAN_SIZE-1 ; ++j){
                    try {
                         goban[i][j] = new Position(i,j,EMPTY,this);
                    } catch (ExceptionGlobal e) {
                         e.writeMessage();
                    }
               }
          }


          // building from whole groups
          final Iterator<StoneGroup> itrBsg = state.blackStonesGroups.iterator();

          while (itrBsg.hasNext()) {

               final StoneGroup oneGroup = itrBsg.next();
               StoneGroup newGroup = null;

               final Iterator<Position> itrSto = oneGroup.getStones().iterator();
               final Iterator<Position> itrLib = oneGroup.getLiberties().iterator();
               final Iterator<Position> itrFro = oneGroup.getFrontiers().iterator();
               final Iterator<Position> itrEye = oneGroup.getEyes().iterator();

               newGroup = new StoneGroup(BLACK, this);
               while (itrSto.hasNext()) {

                    final Position position = itrSto.next();

                    // update of groups windows
                    final Coord bottomL = newGroup.getBottomLeft();
                    final Coord topR = newGroup.getTopRight();

                    final byte byteX = position.getX(), byteY = position.getY();

                    if (bottomL.coordX < byteX+1){
                         bottomL.coordX = (byte) (byteX+1 <= GOBAN_SIZE-1 ? byteX+1 : GOBAN_SIZE-1);
                    }

                    if (topR.coordX > byteX-1){
                         topR.coordX = (byte) (byteX-1 >= 0 ? byteX-1 : 0);
                    }

                    if (bottomL.coordY > byteY-1){
                         bottomL.coordY = (byte) (byteY-1 >= 0 ? byteY-1 : 0);
                    }

                    if (topR.coordY < byteY+1){
                         topR.coordY = (byte) (byteY+1 <= GOBAN_SIZE-1 ? byteY+1 : GOBAN_SIZE-1);
                    }

                    // color copy out
                    goban[position.getX()][position.getY()].setStone(position.getOwnState());

                    // add to the group
                    newGroup.getStones().add(goban[position.getX()][position.getY()]);

                    // assignment to the group
                    goban[position.getX()][position.getY()].setGroup(newGroup);

                    // friends groups copy out
                    final LinkedHashSet<Position> list = goban[position.getX()][position.getY()].get4cLiberties();

                    final Iterator<Position> iterator = list.iterator();
                    while (iterator.hasNext()){
                         iterator.next().addFriendGroup(newGroup);
                    }
               }

               newGroup.getLiberties().clear();
               while (itrLib.hasNext()) {
                    final Position position = itrLib.next();
                    newGroup.getLiberties().add(goban[position.getX()][position.getY()]);
               }

               while (itrFro.hasNext()) {
                    final Position position = itrFro.next();
                    newGroup.getFrontiers().add(goban[position.getX()][position.getY()]);
               }

               while (itrEye.hasNext()) {
                    final Position position = itrEye.next();
                    newGroup.getFrontiers().add(goban[position.getX()][position.getY()]);
               }

               blackStonesGroups.add(newGroup);
          }


          final Iterator<StoneGroup> itrWsg = state.whiteStonesGroups.iterator();
          while (itrWsg.hasNext())
          {
               final StoneGroup oneGroup = itrWsg.next();
               StoneGroup newGroup = null;

               final Iterator<Position> itrSto = oneGroup.getStones().iterator();
               final Iterator<Position> itrLib = oneGroup.getLiberties().iterator();
               final Iterator<Position> itrFro = oneGroup.getFrontiers().iterator();
               final Iterator<Position> itrEye = oneGroup.getEyes().iterator();

               newGroup = new StoneGroup(WHITE,this);
               while (itrSto.hasNext()) {
                    final Position position = itrSto.next();

                    // update of groups windows
                    final Coord bottomL = newGroup.getBottomLeft();
                    final Coord topR = newGroup.getTopRight();

                    final byte coordx = position.getX(), coordy = position.getY();

                    if (bottomL.coordX < coordx+1){
                         bottomL.coordX = (byte) (coordx+1 <= GOBAN_SIZE-1 ? coordx+1 : GOBAN_SIZE-1);
                    }

                    if (topR.coordX > coordx-1){
                         topR.coordX = (byte) (coordx-1 >= 0 ? coordx-1 : 0);
                    }

                    if (bottomL.coordY > coordy-1){
                         bottomL.coordY = (byte) (coordy-1 >= 0 ? coordy-1 : 0);
                    }

                    if (topR.coordY < coordy+1){
                         topR.coordY = (byte) (coordy+1 <= GOBAN_SIZE-1 ? coordy+1 : GOBAN_SIZE-1);
                    }

                    // color copy out
                    goban[position.getX()][position.getY()].setStone(position.getOwnState());

                    // add to the group
                    newGroup.getStones().add(goban[position.getX()][position.getY()]);

                    // assignment to the group
                    goban[position.getX()][position.getY()].setGroup(newGroup);

                    // friends groups copy out
                    final LinkedHashSet<Position> list = goban[position.getX()][position.getY()].get4cLiberties();

                    final Iterator<Position> iterator = list.iterator();
                    while (iterator.hasNext()){
                         iterator.next().addFriendGroup(newGroup);
                    }
               }

               newGroup.getLiberties().clear();
               while (itrLib.hasNext()) {
                    final Position position = itrLib.next();
                    newGroup.getLiberties().add(goban[position.getX()][position.getY()]);
               }

               while (itrFro.hasNext()) {
                    final Position position = itrFro.next();
                    newGroup.getFrontiers().add(goban[position.getX()][position.getY()]);
               }

               while (itrEye.hasNext()) {
                    final Position position = itrEye.next();
                    newGroup.getFrontiers().add(goban[position.getX()][position.getY()]);
               }

               whiteStonesGroups.add(newGroup);
          }
     }


     /**
      * @param coordx abscissa position
      * @param coordy ordinate position
      * @return the position associated on goban by its coordinate
      */
     public Position getPosition(final int coordx, final int coordy) {
          return goban[coordx][coordy];
     }


     /**
      * @param coordx abscissa position
      * @param coordy ordinate position
      * @return count liberties from a position (4-connection based)
      */
     public int countLiberties4Connexe(final int coordx, final int coordy) {

          byte count = 0;

          if (coordx < GOBAN_SIZE-1){
               count += goban[coordx+1][coordy].isPlayable() ? 1 : 0;
          }
          if (coordy < GOBAN_SIZE-1){
               count += goban[coordx][coordy+1].isPlayable() ? 1 : 0;
          }
          if (coordx > 0){
               count += goban[coordx-1][coordy].isPlayable() ? 1 : 0;
          }
          if (coordy > 0){
               count += goban[coordx][coordy-1].isPlayable() ? 1 : 0;
          }

          return count;
     }


     /**
      * @param coordx abscissa position
      * @param coordy ordinate position
      * @param stone color which is played
      * @return check if a position is available to put down a new stone of some color
      */
     public boolean isLegalMove(final int coordx, final int coordy, final int stone)
     {
          // already filled position
          if (goban[coordx][coordy].getOwnState() != EMPTY){ return false;}

          // not fully connected (whatever color of adjacent stones)
          if (countLiberties4Connexe(coordx, coordy) > 0){ return true;}

          // search for a direct catch
          LinkedHashSet<Position> list;
          if (stone == BLACK){
               list = goban[coordx][coordy].getNeighborhoodList(WHITE);
          }
          else {
               list = goban[coordx][coordy].getNeighborhoodList(BLACK);
          }

          Iterator<Position> itr = list.iterator();
          itr = list.iterator();

          // stone group which has remaining one only liberty is in atari, so it's caught
          while (itr.hasNext())
          {
               final Position lib = itr.next();

               if (lib.getGroup().countLiberties() == 1){
                    return true;
               }
          }


          // search for a group which can take place
          // e.g friend group which are one liberty remaining
          // lib(grp) + lib(st) = 1 - 1 = 0 (caught stone group)
          list = goban[coordx][coordy].getNeighborhoodList(stone);
          itr = list.iterator();


          // add to each friend position's stone group
          // By hypothesis, stones are compacted and those immediately adjacents, in a catch phase,
          // one only available liberty (on the new stone position)
          while(itr.hasNext())
          {
               if (itr.next().getGroup().countLiberties() > 1){
                    return true;
               }
          }

          return countLiberties4Connexe(coordx, coordy) > 0;
     }


     /**
      * @param coordx abscissa position
      * @param coordy ordinate position
      * @param stone color which is player
      * @return check if the go can make a catch of any enemy stone group
      */
     public boolean isTakingStones(final int coordx, final int coordy, final int stone)
     {     
          // search for a direct catch
          LinkedHashSet<Position> lst;
          if (stone == BLACK){
               lst = goban[coordx][coordy].getNeighborhoodList(WHITE);
          }
          else {
               lst = goban[coordx][coordy].getNeighborhoodList(BLACK);
          }

          Iterator<Position> itr = lst.iterator();
          itr = lst.iterator();

          // stone group which has remaining one only liberty is in atari, so it's caught
          while (itr.hasNext())
          {
               final Position lib = itr.next();

               if (lib.getGroup().countLiberties() == 1){
                    return true;
               }
          }
          return false;
     }


     /**
      * apply a play and handle changes on the goban (catch, grouping, etc.)
      * @param coordx abscissa position
      * @param coordy ordinate position
      * @param newState the new stone
      * @throws ExceptionGlobal 
      */
     public LinkedHashSet<Coord> applyMove(final int coordx, final int coordy, final byte newState) throws ExceptionGlobal
     {
          // check non-empty position
          if (goban[coordx][coordy].getOwnState() != EMPTY){
               throw new ExceptionGlobal("Impossible de placer une pierre sur un emplacement non-vide du goban !");
          }


          // update of the goban position
          goban[coordx][coordy].setStone(newState);


          // check if the position was an 'eye'
          // that can be possible on a catch after suicide play
          if (goban[coordx][coordy].getLibertiesGroups().size() == 1 && goban[coordx][coordy].get4cLiberties().size() == 0) {

               int index_i, index_j;

               if (coordx > 0 && coordx < GOBAN_SIZE - 1){
                    index_i = coordx + 1;
               }
               else {
                    index_i = (coordx > 0 ? coordx - 1 : coordx + 1);
               }

               if (coordy > 0 && coordy < GOBAN_SIZE - 1){
                    index_j = coordy + 1;
               }
               else {
                    index_j = (coordy > 0 ? coordy - 1 : coordy + 1);
               }

               if (goban[index_i][index_j].getGroup() != null) {
                    final LinkedHashSet<Position> eyesSet = goban[index_i][index_j].getGroup().getEyes();
                    eyesSet.remove(goban[coordx][coordy]);
               }
          }


          ///////////////////////////////////////////
          //////// UP GROUPS CURRENT PLAYER ////////
          ///////////////////////////////////////////


          // search for friends and add to their groups
          final LinkedHashSet<Position> lstFriend = goban[coordx][coordy].getNeighborhoodList(newState);
          Iterator<Position> itr = lstFriend.iterator();


          // for each friend, add to its group (if necessary, groups are merging with more than one stone friend)
          while(itr.hasNext()) {
               final Position oneFriend = itr.next();

               if (oneFriend.getGroup() == null){
                    throw new ExceptionGlobal("Une pierre amie n'est placée dans aucun groupe ??!! (applyMove sur \"EMPTY\"...)");
               }


               // discard friend stones which are no longer on group frontier
               if (oneFriend.get4cLiberties().size() == 0){
                    oneFriend.getGroup().getFrontiers().remove(oneFriend);
               }


               // not yet in a group
               if (goban[coordx][coordy].getGroup() == null) {
                    final StoneGroup grp = oneFriend.getGroup();
                    grp.addStone(goban[coordx][coordy]);
                    goban[coordx][coordy].setGroup(grp);
               }


               // we have to fusion the both two and discard one
               else if (goban[coordx][coordy].getGroup() != oneFriend.getGroup()) {
                    // to suppress the old reference to the stone group which change during fusion phase
                    final StoneGroup oldRef = goban[coordx][coordy].getGroup();

                    // group transfer
                    oneFriend.getGroup().mergeWith(goban[coordx][coordy].getGroup());


                    // suppress of a group which was merged
                    if (newState == BLACK){
                         blackStonesGroups.remove(oldRef);
                    }
                    else {
                         whiteStonesGroups.remove(oldRef);
                    }
               }
          }


          // not yet in a group (thus, the first)
          if (goban[coordx][coordy].getGroup() == null) {
               final StoneGroup grp = new StoneGroup(goban[coordx][coordy], this);
               goban[coordx][coordy].setGroup(grp);

               if (grp.getState() != goban[coordx][coordy].getState()){
                    throw new ExceptionGlobal("Ajout de pierres dans un groupe d'un autre goban (sans doute parent -> mauvais COW)");
               }

               // add to a singleton group
               if (newState == BLACK){
                    blackStonesGroups.add(grp);
               }
               else {
                    whiteStonesGroups.add(grp);
               }
          }


          //////////////////////////////////////////
          //////// UP GROUPS CURRENT ENEMY ////////
          //////////////////////////////////////////


          // suppression of caught groups and stones on the goban 
          Iterator<StoneGroup> itrLstGrp;
          if (newState == BLACK){
               itrLstGrp = whiteStonesGroups.iterator();
          }
          else {
               itrLstGrp = blackStonesGroups.iterator();
          }


          // search for enemies and compute again liberties
          LinkedHashSet<Position> lstEnemy;
          if (newState == BLACK){
               lstEnemy = goban[coordx][coordy].getNeighborhoodList(WHITE);
          }
          else {
               lstEnemy = goban[coordx][coordy].getNeighborhoodList(BLACK);
          }
          itr = lstEnemy.iterator();


          // for each group, compute remaining liberties
          while(itr.hasNext()) {
               final Position oneEnemy = itr.next();
               oneEnemy.getGroup().updateLiberties();
          }


          // if one, doesn't have at least one, it will be delete
          final LinkedHashSet<Coord> res = new LinkedHashSet<Coord>();
          boolean atLeastOneKill = false;
          while(itrLstGrp.hasNext()) {

               final StoneGroup oneGrp = itrLstGrp.next();

               if (oneGrp.countLiberties() == 0) {

                    final Iterator<Position> iter = oneGrp.getStones().iterator();

                    while (iter.hasNext()) {
                         final Position position = iter.next();
                         res.add(new Coord(position.getX(),position.getY()));
                    }

                    oneGrp.resetGroup();
                    itrLstGrp.remove();
                    atLeastOneKill = true;
               }
          }


          ////////////////////////////////////////////////////
          //////// UP GROUPS CURRENT PLAYER ON CATCH ////////
          ////////////////////////////////////////////////////


          // if one was caught, liberties must be compute again
          if (atLeastOneKill) {
               if (newState == BLACK){
                    itrLstGrp = blackStonesGroups.iterator();
               }
               else {
                    itrLstGrp = whiteStonesGroups.iterator();
               }

               while(itrLstGrp.hasNext()){
                    itrLstGrp.next().updateLiberties();
               }

          }

          // coordinate of taken stones from the goban on the play
          return res;
     }


     public float evaluate(final int color)     {
          
          LinkedHashSet<StoneGroup> friends, enemies;
          if (color == BLACK)     {
               friends = blackStonesGroups;
               enemies = whiteStonesGroups;
          }
          else {
               enemies = blackStonesGroups;
               friends = whiteStonesGroups;
          }

          byte enemyColor = (color == BLACK ? WHITE : BLACK);

          final int dangerFr = friends.size() - groupsWithRemainingLiberties(color,4).size()
                                   - 2 * groupsWithRemainingLiberties(color,3).size()
                                   - 4 * groupsWithRemainingLiberties(color,2).size();
          final int dangerEn = enemies.size() - groupsWithRemainingLiberties(enemyColor,4).size()
          - 2 * groupsWithRemainingLiberties(enemyColor,3).size()
          - 4 * groupsWithRemainingLiberties(enemyColor,2).size();

          //float conn = searchConnectionsAndFractions(color);
          return dangerFr + dangerEn;
     }


     /**
      * search groups which have the liberties number specified in argument
      * amongst those which are not invulnerable
      * @param sg point of view to follow
      * @param remainder number of liberties
      * @param black_subset a subset of black stone group or all
      * @param white_subset a subset of white stone group or all
      * @return finding groups
      */
     public LinkedHashSet<StoneGroup> groupsWithRemainingLiberties(final int color, final int remainder) {
          return groupsWithRemainingLiberties(color, remainder, blackStonesGroups, whiteStonesGroups);
     }     
     public LinkedHashSet<StoneGroup> groupsWithRemainingLiberties(final int color, final int remainder, final LinkedHashSet<StoneGroup> blackSubset, final LinkedHashSet<StoneGroup> whiteSubset) {
          final LinkedHashSet<StoneGroup> stoneGroup = new LinkedHashSet<StoneGroup>();
          Iterator<StoneGroup> iterator;

          if (color == BLACK){
               iterator = blackSubset.iterator();
          }
          else {
               iterator = whiteSubset.iterator();
          }

          while (iterator.hasNext()) {

               StoneGroup one = iterator.next();

               // invulnerable group, next one
               while (iterator.hasNext() && one.getEyes().size() >= 2){
                    one = iterator.next();
               }

               if (one != null && one.countLiberties() < remainder){
                    stoneGroup.add(one);
               }
          }

          return stoneGroup;
     }


     /**
      * search groups which have the liberties number specified in argument
      * amongst those which are not invulnerable
      * @param sg point of view to follow
      * @param remainder number of liberties
      * @param black_subset a subset of black stone group or all
      * @param white_subset a subset of white stone group or all
      * @return finding groups
      */
     public LinkedHashSet<StoneGroup> vacuity(final int color)     {
          final LinkedHashSet<StoneGroup> stoneGroup = new LinkedHashSet<StoneGroup>();
          Iterator<StoneGroup> iter;
          LinkedHashSet<StoneGroup> otherStoneGroup;

          if (color == BLACK) {
               iter = blackStonesGroups.iterator();
               otherStoneGroup = whiteStonesGroups;
          }
          else {
               iter = whiteStonesGroups.iterator();
               otherStoneGroup = blackStonesGroups;
          }

          while (iter.hasNext()) {

               StoneGroup one = iter.next();

               // invulnerable group, next one
               while (iter.hasNext() && one.getEyes().size() >= 2){
                    one = iter.next();
               }

               if (one != null && one.getEnemyTrait(otherStoneGroup).size() == 0){
                    stoneGroup.add(one);
               }
          }

          return stoneGroup;
     }


     /**
      * enumerate virtual eyes for each group
      * @param stoneGroup point of view to follow
      */
     public void searchEyes(final int stoneGroup) {
          searchEyes(stoneGroup, false);
     }
     public void searchEyes(final int stoneGroup, final boolean debug) {

          LinkedHashSet<StoneGroup> ref1;

          if (stoneGroup == BLACK){
               ref1 = blackStonesGroups;
          }
          else {
               ref1 = whiteStonesGroups;
          }

          final Iterator<StoneGroup> iter = ref1.iterator();

          // merge all liberties
          final LinkedHashSet<Position> allLib = new LinkedHashSet<Position>();
          while (iter.hasNext()) {
               final StoneGroup one = iter.next();
               allLib.addAll(one.getLiberties());
          }

          final Iterator<Position> itrLib = allLib.iterator();

          // for each liberty
          while(itrLib.hasNext()) {

               final Position oneLib = itrLib.next();

               // if it's in a corner or on goban side
               // we have to change decision for identification
               int offset = 0;
               final int coordx = oneLib.getX();
               final int coordy = oneLib.getY();
               int onEdge;

               offset += (coordx == 0 || coordx == GOBAN_SIZE - 1 ? 1 : 0);
               offset += (coordy == 0 || coordy == GOBAN_SIZE - 1 ? 1 : 0);
               onEdge = (offset > 0 ? 1 : 0);


               // one eye can be created if and only if
               // the color on the liberties is the same
               // and the number of remainings 8c-liberties is less or equal than 2
               // minus eventually a position if on a corner
               // (no more liberty, thus, it's already one)
               // (>) all 4c-liberties taken and more than one available diagonal to finish the shape
               // minus one penalty for each position on a side (simple side = 1, corner = 2)
               // (*) or else

               final int lib4 = oneLib.get4cLiberties().size();
               final int libDiag = oneLib.get4cDiagLiberties().size();
               final int lib8 = lib4 + libDiag;

               if (oneLib.hasSameColorLibertiesGroup()
                         && oneLib.colorLibertiesGroup() == stoneGroup
                         && lib8 > 1 - onEdge
                         && lib8 < 3 - onEdge) {

                    if (lib4 == 0 && libDiag > 1 && libDiag < 3 - offset && debug) { 
                         System.out.println("Oeil virtuel (>) " + " sur "+ oneLib);
                    }
                    else {
                         if (debug) {
                         System.out.println("Oeil virtuel (*) " + " sur "+ oneLib);
                         }
                    }
               }     
          }
     }





     /**
      * enumerate virtuals connections and fractions between each group of the goban
      * @param stoneGroup point of view to follow
      * @param black_subset a subset of black stone group or all
      * @param white_subset a subset of white stone group or all
      */
     public float searchConnectFrac(final int stoneGroup) {

          LinkedHashSet<StoneGroup> ref1;
          LinkedHashSet<StoneGroup> ref2;

          boolean debug = false;

          if (stoneGroup == BLACK) {
               ref1 = blackStonesGroups;
               ref2 = whiteStonesGroups;
          } else {
               ref1 = whiteStonesGroups;
               ref2 = blackStonesGroups;
          }


          Iterator<StoneGroup> iter = ref1.iterator();


          // connectors set
          LinkedHashSet<StoneGroup> connTrue = new LinkedHashSet<StoneGroup>();
          LinkedHashSet<StoneGroup> connMay = new LinkedHashSet<StoneGroup>();


          // fractions set
          LinkedHashSet<StoneGroup> fracTrue = new LinkedHashSet<StoneGroup>();
          LinkedHashSet<StoneGroup> fracMay = new LinkedHashSet<StoneGroup>();


          int integer = 0;

          float evaluate = 0;

          // each groups couple of same color is associated
          while (iter.hasNext()) {
               final StoneGroup one = iter.next();

               while (iter.hasNext()) {

                    final StoneGroup other = iter.next();

                    // points in common for both if there is intersection
                    LinkedHashSet<Position> inter4c;
                    LinkedHashSet<Position> inter8c;


                    // intersections number
                    int nbInter4c = 0;
                    int nbInter8c = 0;


                    // asshole on the heap...
                    int diff = 0;


                    // is there an intersection ?
                    if (one.hasIntersection(other)) {
                         inter4c = one.get4cConnection(other);
                         inter8c = one.get8cConnection(other);

                         // intersections number
                         nbInter4c = inter4c.size();
                         nbInter8c = inter8c.size();

                         // asshole on the heap...
                         diff = one.getEnemyTraitGroup(other, ref2).size();
                    } else { 
                         // useless compute so ...
                         inter4c = new LinkedHashSet<Position>();
                         inter8c = new LinkedHashSet<Position>();
                    }


                    // connector (>)
                    if (nbInter4c > 1 && nbInter4c > diff) {

                         evaluate += 10;

                         // same intersection
                         if (connTrue.size() > 0) {
                              if (debug){
                                   System.out.println("Groupe-connecté (>) "+ " par : "+ connTrue);
                              }
                              connTrue = new LinkedHashSet<StoneGroup>();
                         }     

                         connTrue.add(one); connTrue.add(other);

                         if (connMay.size() > 0) {
                              if (debug) {
                                   System.out.println("Groupe-connecté (*) "+ " par : "+ connMay);
                              }
                              connMay = new LinkedHashSet<StoneGroup>();
                         }
                    }
                    // connector unsure (*)
                    else if (nbInter4c > 0 && nbInter4c >= diff) {

                         evaluate += 20;

                         if (connMay.size() > 0) {
                              if (debug){
                                   System.out.println("Groupe-connecté (*) "+ " par : "+ connMay);
                              }
                              connMay = new LinkedHashSet<StoneGroup>();
                         }

                         connMay.add(one); connMay.add(other);
                    }


                    // connector (>)
                    if (nbInter8c > 1 && nbInter8c > diff) {

                         evaluate += 15;

                         if (fracMay.size() > 0) {
                              if (debug) {
                                   System.out.println("Fraction (*) "+ (stoneGroup == BLACK ? "blanche " : "noire ") + " par : "+ fracMay);
                              }
                              fracMay = new LinkedHashSet<StoneGroup>();
                         }

                         if (fracTrue.size() > 0) {
                              if (debug) {
                                   System.out.println("Fraction (>) "+ (stoneGroup == BLACK ? "blanche " : "noire ") + " par : "+fracTrue);
                              }
                              fracTrue = new LinkedHashSet<StoneGroup>();
                         }

                         fracTrue.add(one); fracTrue.add(other);
                    }
                    // connector unsure (*)
                    else if (nbInter8c > 0 && nbInter8c >= diff) {

                         evaluate += 30;

                         if (fracTrue.size() > 0) {
                              if (debug) {
                                   System.out.println("Fraction (>) "+ (stoneGroup == BLACK ? "blanche " : "noire ")+ " par : "+fracTrue);
                              }
                              fracTrue = new LinkedHashSet<StoneGroup>();
                         }

                         if (fracMay.size() > 0) {
                              if (debug) {
                                   System.out.println("Fraction (*) "+ (stoneGroup == BLACK ? "blanche " : "noire ")+ " par : "+ fracMay);
                              }
                              fracMay = new LinkedHashSet<StoneGroup>();
                         }

                         fracMay.add(one); fracMay.add(other);
                    }
               }

               ++integer;
               iter = ref1.iterator();

               // suppress of the already tested member
               for (int j=0; j<integer; ++j) {
                    iter.next();
               }
          }


          // last merged connectors are printed
          if ((connTrue.size() > 0) && debug ) {
                    System.out.println("Groupe-connecté (>) "+ " par : "+ connTrue);
          }

          if ((connMay.size() > 0) && debug) {
                    System.out.println("Groupe-connecté (*) "+ " par : "+ connMay);
          }


          // last merged fractions are printed
          if ((fracTrue.size() > 0) && debug) {
               System.out.println("Fraction (>) "+ (stoneGroup == BLACK ? "blanche " : "noire ")+ " par : "+ fracTrue);
          }

          if ((fracMay.size() > 0) && debug){
               System.out.println("Fraction (*) "+ (stoneGroup == BLACK ? "blanche " : "noire ")+ " par : "+ fracMay);
          }

          return evaluate;
     }


     /* (non-Javadoc)
      * @see java.lang.Object#toString()
      */
     @Override
     public String toString() {
          String res = "State\n"; 
          res += "   0  1  2  3  4  5  6  7  8 \n\n";

          for(int i=0; i <= GOBAN_SIZE-1 ; ++i) {
               res += i + "  ";
               for (int j=0; j <= GOBAN_SIZE-1 ; ++j) {
                    res += goban[i][j].getOwnState() == EMPTY ? goban[i][j].getLibertiesGroups().size() /*"_"*/ : goban[i][j].getOwnState() == BLACK ? "B" : "W";
                    res += "  ";
               }
               res += "\n\n";
          }

          res += "==========--GROUPES--==========\n";
          res += blackStonesGroups+"\n";
          res += whiteStonesGroups;
          res += "\n===========================================================\n";
          res += "========================= END MOVE ========================\n";
          res += "===========================================================\n";

          /*System.out.println(" =============== BLACK ===============");
          computeDist(BLACK);
          System.out.println(" =============== WHITE ===============");
          computeDist(WHITE);
          System.out.println();*/

          System.out.println(" =============== EN DANGER ===============");
          System.out.println(" =============== BLACK ===============");
          System.out.println(groupsWithRemainingLiberties(BLACK, 4));
          System.out.println(" =============== WHITE ===============");
          System.out.println(groupsWithRemainingLiberties(WHITE, 4));
          System.out.println();

          System.out.println(" =============== DUALITE BLACK-WHITE ===============");
          System.out.println(" =============== BLACK ===============");
          searchConnectFrac(BLACK);
          searchEyes(BLACK,true);
          System.out.println(" =============== WHITE ===============");
          searchConnectFrac(WHITE);
          searchEyes(WHITE,true);
          System.out.println();

          return res;
     }

     public Move computeNext(final int coordx, final int coordy, final int stone) {

          int integerI, integerJ;

          final ArrayDeque<Node> queue = new ArrayDeque<Node>(); // la file des noeuds a traiter sur une certaine profondeur
          final Tree tree = new Tree(new Move(coordx, coordy, (byte)stone), this);

          final Node root = tree.root;

          System.out.println("racine "  + root.move);

          // root node
          queue.add(root);

          // au niveau 1
          byte switch_col = (byte) stone;
          int cpt = 0;


          for (integerI = 2; integerI <= 4; ++integerI) // profondeur 3 max
          {
               integerJ = 0;

               boolean breakSearch = false;

               final int number = queue.size();
               System.out.println("profondeur : " + (integerI - 1));
               System.out.println("nb : " + number);

               float best = 0;
               float worst = 0;
               Node worstNode = new Node(null, null);

               int inc = 0;
               while (integerJ < number)
               {
                    final Node currentRoot = queue.pollFirst();
                    final State currentState = currentRoot.state;

                    /*     System.out.println(" is root "+(new_root == root));*/
                    //     current_state = new_root.state;
                    
                    for(byte posX=0; posX <= GOBAN_SIZE-1 && !breakSearch; ++posX){
                         for (byte posY=0; posY <= GOBAN_SIZE-1 && !breakSearch; ++posY, ++inc) {
                              if (inc == number * 81 / 100)
                              {
                                   inc = 0;
                                   frame.modProgress(1);
                              }
                              // on teste chaque coup possible
                              if (currentState.isLegalMove(posX,posY,switch_col))
                              {
                                   try {
                                        final State newState = new State(currentState);

                                        // on l'applique
                                        newState.applyMove(posX, posY, switch_col);

                                        final float eval = newState.evaluate(switch_col);

                                        // pas encore de fils
                                        if (currentRoot.listeFils.size() == 0)
                                        {
                                             best = worst = eval;

                                             final Node newChild = new Node(new Move(posX, posY, switch_col), newState);

                                             worstNode = newChild;

                                             tree.ajouterFils(newChild, currentRoot);

                                             if (integerI == 4) // max depth
                                             {
                                                  // remonte vers le haut les val.
                                                  breakSearch = raiseChild(newChild, integerI % 2);
                                                  if (breakSearch)
                                                  {
                                                       newChild.pere.listeFils.clear();
                                                       newChild.pere.listeFils.add(newChild);
                                                  }
                                             }
                                        }
                                        else
                                        {
                                             // création du noeud
                                             final Node newChild = new Node(new Move(posX, posY, switch_col), newState);

                                             newChild.move.evaluation = eval;

                                             if (currentRoot.listeFils.size() >= 10)
                                             {
                                                  if (eval >= best)
                                                  {
                                                       best = eval;

                                                       currentRoot.listeFils.remove(worstNode);
                                                       currentRoot.listeFils.remove(newChild);

                                                       worst = eval;
                                                       worstNode = newChild;
                                                       for (Node n : currentRoot.listeFils)
                                                       {
                                                            if (n.move.evaluation <= worst)
                                                            {
                                                                 worst = (int) n.move.evaluation;
                                                                 worstNode = n;
                                                            }
                                                       }
                                                  }
                                                  else if (eval >= worst)
                                                  {
                                                       currentRoot.listeFils.remove(worstNode);
                                                       currentRoot.listeFils.remove(newChild);

                                                       worst = eval;
                                                       worstNode = newChild;
                                                       for (Node n : currentRoot.listeFils)
                                                       {
                                                            if (n.move.evaluation <= worst)
                                                            {
                                                                 worst = (int) n.move.evaluation;
                                                                 worstNode = n;
                                                            }
                                                       }
                                                  }

                                             }
                                             else if (currentRoot.listeFils.size() < 10 && eval >= best)
                                             {
                                                  best = eval;
                                                  currentRoot.listeFils.add(newChild);
                                                  newChild.pere = currentRoot;
                                             }     
                                             else if (currentRoot.listeFils.size() < 10 && eval <= worst)
                                             {
                                                  worst = eval;
                                                  worstNode = newChild;
                                                  currentRoot.listeFils.add(newChild);
                                                  newChild.pere = currentRoot;
                                             }
                                        }

                                        ++cpt;

                                   } catch (ExceptionGlobal e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                   }
                              }
                         }
                    }
                    
                    queue.addAll(currentRoot.listeFils);
                    ++integerJ;
               }
               frame.resetProgress();



               System.out.println("compteur :"+cpt);

               // on interverti les couleurs
               switch_col = switch_col == BLACK ? WHITE : BLACK;
          }

          System.out.println("nb : " + queue.size());
          System.out.println("root "+ tree.root.move);

          return tree.root.move;
     }

     public boolean raiseChild(final Node node, final int integer) {

          node.move.evaluate = true;

          switch(integer) {
          case 0: // ordinateur -> coup max
               if(node.pere.pere != null) {
                         if(node.pere.pere.move.evaluate && node.pere.pere.move.evaluation < node.move.evaluation) {
                         return true;    // raccourci alpha-beta
                     }
                    if(!node.pere.move.evaluate || node.pere.move.evaluation < node.move.evaluation) {
                         node.pere.move.evaluation = node.move.evaluation;
                         if(!node.pere.move.evaluate) { node.pere.move.evaluate = true; }

                         return raiseChild(node.pere, 1);     // le pere sera un coup min
                    }
               }
               else { // le pere est la racine
                    if(!node.pere.move.evaluate || node.pere.move.evaluation < node.move.evaluation){
                         node.pere.move = node.move;
                    }

                    if(!node.pere.move.evaluate) {
                         node.pere.move.evaluate = true;
                    }
               }
               break;
          case 1: // adversair -> coup min
                    if(node.pere.pere.move.evaluate && node.pere.pere.move.evaluation > node.move.evaluation){
                     return true;    // raccourci alpha-beta
                 }
               if(!node.pere.move.evaluate || node.pere.move.evaluation > node.move.evaluation) {
                    node.pere.move.evaluation = node.move.evaluation;
                    if(!node.pere.move.evaluate) { node.pere.move.evaluate = true; }

                    return raiseChild(node.pere, 0);     // le pere sera un coup max
               }
               break;
          default: break;
          }

          return false;
     }
}


