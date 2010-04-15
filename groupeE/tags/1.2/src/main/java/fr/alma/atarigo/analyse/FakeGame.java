/**
 * File {FakeGame.java}
 * Last commited $Date$
 * By $Author$
 * Revision $Rev$
 *
 * Copyright (C) 2010 Clotilde Massot & Julien Durillon
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program;
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of this program.
 */
package fr.alma.atarigo.analyse;

import fr.alma.atarigo.utils.Game;
import fr.alma.atarigo.utils.Goban;
import fr.alma.atarigo.utils.Group;
import fr.alma.atarigo.utils.Modif;
import fr.alma.atarigo.utils.StoneVal;
import fr.alma.atarigo.utils.PlayMove;
import fr.alma.atarigo.utils.Stone;
import fr.alma.atarigo.utils.exceptions.BadCouleurException;
import fr.alma.atarigo.utils.exceptions.BadGobanStateException;
import fr.alma.atarigo.utils.exceptions.BadPlaceException;
import fr.alma.atarigo.utils.tree.Node;
import fr.alma.atarigo.utils.tree.Tree;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Describes a fake game. Need it to test operations without wasting the game.
 * @author clotildemassot
 */
public final class FakeGame extends Game {

   /**
    * The current processing child of Game.lastMove. Used for fakePutStone
    */
   private Node<PlayMove> fakeLastMove;
   /**
    * The current depth of the game.
    */
   private int depth;

   /**
    * Constructor.
    * Clones the given game, and choose the current Move as root for history.
    *
    * @param game the game to fake.
    */
   public FakeGame(final Game game) {
      this.goban = (Goban) game.getGoban().clone();
      this.history = new Tree<PlayMove>(
              new Node<PlayMove>(game.getCurrentMove()));
      this.lastMove = history.getRootElement();
      this.depth = game.getCurrentDepth();
      this.freePlaces = new HashSet<Stone>(game.getFreePlaces());
      this.currentPlayer = game.getCurrentPlayer();
   }

   /**
    * Gepth getter.
    * @return the current depth.
    */
   public int getDepth() {
      return depth;
   }

   /**
    * Depth setter.
    * @param dep the depth to set.
    */
   public void setDepth(final int dep) {
      this.depth = dep;
   }

   /**
    * Simulate PutStone from Game.
    *
    * We only create a new Node, child of lastMove.
    * @param line the line to play
    * @param column the column to play
    * @param color the color of the put Stone.
    * @throws BadPlaceException if out of goban, or suicide, or non empty spot.
    */
   public void fakePutStone(final int line, final int column,
           final StoneVal color)
           throws BadPlaceException {
      if (goban.bonneCoords(line, column)) {
         if (goban.getCase(line, column) == StoneVal.EMPTY) {
            if (!isSuicide(line, column, color)) {

               Stone current = new Stone(color, line, column);

               Modif modif = new Modif(line, column,
                       goban.getCase(line, column), color);
               PlayMove currentMove = new PlayMove();
               currentMove.addModif(modif);
               currentMove.setPutStone(modif);

               fakeStartMove(currentMove);

               //Let's modify the goban !
               goban.setCase(line, column, color);

               //Calculates the new groups
               Set<Group> ennemies = fakeCalculateGroups(current);

               //Update the liberties of surrounding ennemy groups
               updateLiberties(ennemies);

               //Removes taken stones, and update currentMove with the Modifs.
               fakeProcessGroupTaking(ennemies);

               goban.setCase(line, column, StoneVal.EMPTY);

            } else {
               throw new BadPlaceException("(" + line + ", " + column + ", "
                       + color
                       + ") This move is a suicide,"
                       + "I cannot let you do that !");
            }
         } else {
            throw new BadPlaceException("Already a stone here ("
                    + line + "," + column + "," + color.toString() + ")");
         }
      } else {
         throw new BadPlaceException("It is actually better"
                 + "to play on the board");
      }
   }

   /**
    * Initialize a new Node for the current processing move.
    * @param currPM the current PlayMove.
    */
   private void fakeStartMove(final PlayMove currPM) {

      // Prepare the group modifications
      currPM.setGroups((ArrayList<Group>) getCurrentMove().
              getGroups().clone());

      Node<PlayMove> newMove = new Node<PlayMove>(currPM);
      lastMove.addChild(newMove);
      fakeLastMove = newMove;
   }

   /**
    * Checks if some of the given groups have 0 libs, and remove them.
    *
    *
    * @param ennemies the groups to test.
    */
   private void fakeProcessGroupTaking(final Set<Group> ennemies) {
      for (Group groupe : ennemies) {
         if (groupe.getLiberties().size() == 0) {
            // In this case, no more liberty. Let's eat them !!
            fakeRemoveGroupe(groupe);
            // If someone is taken, we have an endgame.
            getFakeCurrentMove().setEnd(true);
         }
      }
   }

   /**
    * Removes the given group.
    *
    * Here, we have to remove it from the current Group set, and each Stone from
    * the goban. But, as it is a fake game, we keep the stones on the board.
    * @param groupe the group to remove.
    */
   private void fakeRemoveGroupe(final Group groupe) {

      PlayMove fakePM = new PlayMove();

      // Removing eah Stone
      for (Stone pion : groupe.getStones()) {
         // Don't forget to register the modification.
         Modif mod = new Modif(pion.getLine(), pion.getColumn(),
                 pion.getCouleur(), StoneVal.EMPTY);
         getFakeCurrentMove().addModif(mod);
         fakePM.addModif(mod);
         goban.setCase(pion.getLine(), pion.getColumn(), StoneVal.EMPTY);
      }

      // Calculating the liberties of surrounding groups
      Set<Group> surroundings = new HashSet<Group>();
      for (Stone pion : groupe.getStones()) {
         surroundings.addAll(getSurroundingGroups(pion));
      }
      surroundings.remove(null);
      updateLiberties(surroundings);

      getFakeCurrentMove().getGroups().remove(groupe);

      try {
         // Now, let's revert the removal, to keep the same goban.
         fakePM.revert(goban);
      } catch (BadGobanStateException ex) {
         Logger.getLogger(FakeGame.class.getName()).log(Level.SEVERE, null, ex);
      }
   }

   /**
    * Returns a Set of groups containing at least one of the given Stones.
    * @param pions The Stones we want the groups.
    * @return à Set<Group> containing the given stones.
    */
   public Set<Group> fakeGetGroupsFromPions(final List<Stone> pions) {
      HashSet<Group> groups = new HashSet<Group>(MAX_NEIGHBOURS);
      for (Stone pi : pions) {
         Group containing = getFakeCurrentMove().getGroupeContaining(pi);
         if (containing != null) {
            groups.add(containing);
         }
      }
      return groups;
   }

   /**
    * Finds and returns the groups containing the stones surrounding the given.
    * @param pion the tested Stone.
    * @return a Set<Group>.
    */
   public Set<Group> fakeGetSurroundingGroups(final Stone pion) {
      // Get the (<= 4) neighbours groups of the current stone.
      List<Stone> neighbourList = goban.getVoisins(pion);
      if (neighbourList.isEmpty()) {
         return null;
      } else {
         return fakeGetGroupsFromPions(neighbourList);
      }
   }

   /**
    * Calculate the current groups, and put it in the fakeLastMove.
    *
    * @param last The last put Stone.
    * @return The surrounding ennemy groups.
    */
   protected Set<Group> fakeCalculateGroups(final Stone last) {

      Group lastAdded = new Group(last.getCouleur());
      try {
         lastAdded.addStone(last);
      } catch (BadCouleurException e) {
         Logger.getAnonymousLogger().log(Level.SEVERE, e.getMessage());
      }

      List<Group> currentGroups = getFakeCurrentMove().getGroups();

      Set<Group> surroundingGroups = fakeGetSurroundingGroups(last);
      Set<Group> ennemyGroups = new HashSet<Group>(MAX_NEIGHBOURS);

      if (surroundingGroups != null) {
         for (Group groupe : surroundingGroups) {
            if (groupe.getCouleur() == last.getCouleur()) {
               lastAdded.addAll(groupe);
               currentGroups.remove(groupe);
            } else {
               ennemyGroups.add(groupe);
            }
         }
      }

      currentGroups.add(lastAdded);
      lastAdded.setLiberties(getGroupLiberties(lastAdded));
      return ennemyGroups;
   }

   /**
    * Get the fake current PlayMove.
    *
    * @return The data of the current fakeLastMove.
    */
   private PlayMove getFakeCurrentMove() {
      return this.fakeLastMove.getData();
   }

   /**
    * Checks if the Node LastMove has registered children.
    * @return if the game has registered possibilities.
    */
   public boolean isInLeaf() {
      return this.lastMove.isLeaf();
   }

   /**
    * Moves the game to the state described by the given numChild th child.
    * @param numChild the position of the child we want.
    * @return If the appliance was successful.
    */
   @Override
   public Boolean apply(final int numChild) {
      Boolean ok = super.apply(numChild);
      if (ok) {
         ++depth;
         this.fakeLastMove = null;
      }
      return ok;
   }

   /**
    * Moves the game to the state described by the parent of lastMove.
    * @return If the revert was successful.
    */
   @Override
   public Boolean revert() {
      Boolean ok = super.revert();
      if (ok) {
         --depth;
      }
      return ok;
   }

   /**
    * Removes all children of lastMove's parent, while keeping this one.
    */
   public void keepThisBranch() {
      if (lastMove.getFather() != null) {
         lastMove.getFather().keepChild(lastMove);
      }
   }

   /**
    * Keeps the childNb'th child of lastMove, while removing the others.
    * @param childNb Number of the child we want to keep.
    */
   void keepChildAt(final int childNb) {
      lastMove.keepChildAt(childNb);
   }
}
