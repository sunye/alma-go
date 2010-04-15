// File Game.java
// Last commited $Date$
// By $Author$
// Revision $Rev$
//
// Copyright (C) 2010 Clotilde Massot & Julien Durillon
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// as published by the Free Software Foundation; either version 2
// of the License, or (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program;
//
// The above copyright notice and this permission notice shall be
// included in all copies or substantial portions of this program.
package fr.alma.atarigo.utils;

import fr.alma.atarigo.utils.exceptions.BadCouleurException;
import fr.alma.atarigo.utils.exceptions.BadGobanStateException;
import fr.alma.atarigo.utils.exceptions.BadPlaceException;
import fr.alma.atarigo.utils.tree.Node;
import fr.alma.atarigo.utils.tree.Tree;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Describes a game, and all its moves history.
 * @author judu
 */
public class Game {

   /**
    * Maximum of Stone neighbours.
    */
   protected static final short MAX_NEIGHBOURS = 4;

   /**
    * Board used for the game.
    */
   protected Goban goban;
   /**
    * History of the game : contains all the moves since the beginning.
    */
   protected Tree<PlayMove> history;
   /**
    * Last move played (faster backtracking).
    */
   protected Node<PlayMove> lastMove;
   /**
    * Next Color that will be played.
    */
   protected StoneVal currentPlayer;
   /**
    * Set of empty stones.
    */
   protected Set<Stone> freePlaces;

   /**
    * Constructor.
    */
   public Game() {
      this.goban = new Goban();

      history = new Tree<PlayMove>();
      lastMove = new Node<PlayMove>(new PlayMove());
      history.setRootElement(lastMove);
      currentPlayer = StoneVal.BLACK;
      freePlaces = new HashSet<Stone>(Goban.getTaille() * Goban.getTaille());
      for (int line = 0; line < Goban.getTaille(); ++line) {
         for (int col = 0; col < Goban.getTaille(); ++col) {
            freePlaces.add(new Stone(StoneVal.EMPTY, line, col));
         }
      }
   }

   /**
    * Tries to play at (<em>line</em>, <em>column</em>).
    *
    * Calls posePion with currentPlayer color.
    * @param line the line to play at.
    * @param column the column  to play at.
    * @return if the move succeed.
    */
   public Boolean playAt(final int line, final int column) {
      try {
         posePion(line, column, currentPlayer);

         changeCurrentPlayer();
         return true;
      } catch (BadPlaceException ex) {
         Logger.getAnonymousLogger().log(Level.WARNING, ex.getMessage());
         return false;
      }


   }

   /**
    * This method put a stone on the goban, if possible.
    * Checks whether it is actually <em>on</em> the goban,
    * and manages all the changes involved
    * (such as stone taken, and groups making)
    *
    * @param line The line where to put the stone
    * @param column The column where to put the stone
    * @param color The color of the stone.
    * @throws BadPlaceException Threw if the coordinates do not fit in the goban
    */
   public void posePion(final int line, final int column, final StoneVal color)
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

               startMove(currentMove);

               // Let's modify the goban !
               goban.setCase(line, column, color);
               freePlaces.remove(new Stone(StoneVal.EMPTY, line, column));

               //Calculates the new groups
               Set<Group> ennemies = calculateGroups(current);

               // Update the liberties of surrounding ennemy groups
               updateLiberties(ennemies);

               // Removes taken stones,
               // and update the currentMove with the Modifs.
               processGroupeTaking(ennemies);

            } else {
               throw new BadPlaceException("This move is a suicide, "
                       + "I cannot let you do that !");
            }
         } else {
            throw new BadPlaceException("Already a stone here");
         }
      } else {
         throw new BadPlaceException("It is actually better to play "
                 + "on the board");
      }
   }

   /**
    * Checks if the place is considered as a suicide.
    * If the played Stone fills the last liberty of a group, it is,
    * unless we kill another group.
    * @param line
    * @param column
    * @param pionVal
    * @return whether or not it is a suicide
    */
   protected boolean isSuicide(final int line, final int column,
           final StoneVal pionVal) {
      //TODO : correct this function
      if (goban.nbLibertes(new Stone(pionVal, line, column)) == 0) {
         // No liberty ? Let's check if we are killing a ennemy group.
         Set<Group> groupes = getSurroundingGroups(
                 new Stone(pionVal, line, column));

         int i = 0;
         for (Group gr : groupes) {
            ++i;
            if (gr.getCouleur() != pionVal) {
               // If ennemy, check if it has one liberty left
               if (gr.getLiberties().size() == 1) {
                  return false;
               }
            } else if (gr.getLiberties().size() > 1) {
               // If allie and more than 1 liberty, it is not a suicide.
               return false;
            }
         }
//            System.out.println(groupes);
//            System.out.println(goban);
         return true;
      } else {
         return false;
      }
   }

   /**
    * Creates a new Node with the given PlayMove.
    * @param currentMove the new PlayMove.
    */
   private void startMove(final PlayMove currentMove) {

      //prepare the group modifications
      currentMove.setGroups(
              (ArrayList<Group>) getCurrentMove().getGroups().clone());

      Node<PlayMove> newMove = new Node<PlayMove>(currentMove);
      lastMove.addChild(newMove);
      lastMove = newMove;
   }

   /**
    * Returns a Set of groups containing at least one of the given Stones.
    * @param pions The Stones we want the groups.
    * @return à Set<Group> containing the given stones.
    */
   private HashSet<Group> getGroupsFromPions(final List<Stone> pions) {
      HashSet<Group> groups = new HashSet<Group>(MAX_NEIGHBOURS);
      for (Stone pi : pions) {
         Group containing = getCurrentMove().getGroupeContaining(pi);
         groups.add(containing);
      }
      return groups;
   }

   /**
    * Finds and returns the groups containing the stones surrounding the given.
    * @param pion the tested Stone.
    * @return a Set<Group>.
    */
   protected HashSet<Group> getSurroundingGroups(final Stone pion) {
      // Get the (<= MAX_NEIGHBOURS) neighbours groups of the current stone.
      List<Stone> pions = goban.getVoisins(pion);

      return getGroupsFromPions(pions);
   }

   /**
    * Calculates the new groups, after putting the last stone, then returns
    * the ennemy's groups surrounding the stone. It is not really
    * KISS, but useful, and a little faster than re-calculate the groups.
    * @param last The last stone played
    */
   protected Set<Group> calculateGroups(final Stone last) {
      Group lastAdded = new Group(last.getCouleur());
      try {
         lastAdded.addStone(last);
      } catch (BadCouleurException e) {
      }

      List<Group> groupes = getCurrentMove().getGroups();

      Set<Group> groups = getSurroundingGroups(last);
      Set<Group> others = new HashSet<Group>(MAX_NEIGHBOURS);
      for (Group groupe : groups) {
         if (groupe.getCouleur() == last.getCouleur()) {
            lastAdded.addAll(groupe);
            groupes.remove(groupe);
         } else {
            others.add(groupe);
         }
      }
      groupes.add(lastAdded);
      lastAdded.setLiberties(getGroupLiberties(lastAdded));
      return others;
   }

   /**
    * Returns all the empty places surrounding a group.
    * @param groupe the group to test.
    * @return the surrounding groups.
    */
   protected final Set<Stone> getGroupLiberties(final Group groupe) {
      HashSet<Stone> libertes = new HashSet<Stone>(groupe.size() * 2 + 2);

      for (Stone pion : groupe.getStones()) {
         List<Stone> libz = goban.getLibertes(pion);
         libertes.addAll(libz);
      }
      return libertes;
   }

   /**
    * Remove each group without any liberty.
    * @param ennemies the groups we have to test.
    */
   private void processGroupeTaking(final Set<Group> ennemies) {
      for (Group groupe : ennemies) {
         if (groupe.getLiberties().size() == 0) {
            // Dans ce cas là, on a bouché la dernière libertée,
            //il faut donc supprimer les pierres du goban, et le groupe du pm.
            removeGroup(groupe);
            getCurrentMove().setEnd(true);
            Logger.getLogger(this.getClass().getName()).log(Level.INFO,
                    "Fin du jeu !");
         }
      }
   }

   /**
    * Removes <em>groupe</em> from the current PlayMove.
    * Also removes each of its stones from the goban,
    * then update the liberties of the surrounding groups.
    * @param groupe the group we want to remove.
    */
   private void removeGroup(final Group groupe) {

      for (Stone pion : groupe.getStones()) {
         // Don't forget to register the modification.
         getCurrentMove().addModif(new Modif(pion.getLine(), pion.getColumn(),
                 pion.getCouleur(), StoneVal.EMPTY));
         goban.setCase(pion.getLine(), pion.getColumn(), StoneVal.EMPTY);
         freePlaces.add(new Stone(StoneVal.EMPTY, pion.getLine(),
                 pion.getColumn()));
      }

      Set<Group> surroundings = new HashSet<Group>();
      for (Stone pion : groupe.getStones()) {
         surroundings.addAll(getSurroundingGroups(pion));
      }
      updateLiberties(surroundings);

      getCurrentMove().getGroups().remove(groupe);
   }

   /**
    * Calculate the liberties of each Group surrounding the given Stone
    * @param current The last put Stone.
    */
   private void calculateLiberties(final Stone current) {
      Set<Group> surroundings = getSurroundingGroups(current);
      for (Group groupe : surroundings) {
         if (groupe.getCouleur() != current.getCouleur()) {
            groupe.setLiberties(getGroupLiberties(groupe));
         }
      }
   }

   /**
    * Calculates and Updates the liberties of each group of the Set.
    * @param ennemies the groups we update.
    */
   protected final void updateLiberties(final Set<Group> ennemies) {
      for (Group groupe : ennemies) {
         groupe.setLiberties(getGroupLiberties(groupe));
      }
   }

   /**
    * Returns the current PlayMove
    * @return current PlayMove
    */
   public final PlayMove getCurrentMove() {
      return lastMove.getData();
   }

   /**
    * Get the Stone in goban[line][column].
    * @param line y coordinate.
    * @param column x coordinate.
    * @return the Stone.
    * @throws BadPlaceException if out of the goban.
    */
   public final Stone getStone(final int line, final int column)
           throws BadPlaceException {
      if (goban.bonneCoords(line, column)) {
         return new Stone(goban.getCase(line, column), line, column);
      } else {
         throw new BadPlaceException("Coordinates out of the board");
      }
   }

   /**
    * Reverts the game to the father of the currentMove.
    */
   public Boolean revert() {

      PlayMove lastPm = getCurrentMove();
      try {
         lastPm.revert(this.goban);

         for (Modif mod : lastPm.getDiff()) {
            if (mod.getBefore() == StoneVal.EMPTY) {
               this.freePlaces.add(new Stone(StoneVal.EMPTY, mod.getLine(),
                       mod.getColumn()));
            } else {
               this.freePlaces.remove(new Stone(StoneVal.EMPTY, mod.getLine(),
                       mod.getColumn()));
            }
         }

         lastMove = lastMove.getFather();
         this.changeCurrentPlayer();
         return Boolean.TRUE;
      } catch (BadGobanStateException ex) {
         Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
         return Boolean.FALSE;
      }
   }

   /**
    * Moves the game to the state described by the given numChild th child.
    * @param numChild the position of the child we want.
    * @return If the appliance was successful.
    */
   public Boolean apply(final int numChild) {
      Node<PlayMove> child = lastMove.getChildAt(numChild);
      if (child != null) {
         if (child.getData().getPutStone().getAfter() != currentPlayer) {
            return false;
         }

         PlayMove newPM = child.getData();
         try {
            newPM.apply(goban);
         } catch (BadGobanStateException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            return false;
         }

         // Update the freeplaces
         for (Modif mod : newPM.getDiff()) {
            if (mod.getAfter() == StoneVal.EMPTY) {
               this.freePlaces.add(new Stone(StoneVal.EMPTY, mod.getLine(),
                       mod.getColumn()));
            } else {
               this.freePlaces.remove(new Stone(StoneVal.EMPTY, mod.getLine(),
                       mod.getColumn()));
            }
         }

         lastMove = child;
         this.changeCurrentPlayer();
         return true;
      } else {
         Logger.getAnonymousLogger().log(Level.WARNING, "NOT applied, "
                 + "null child");
         return false;
      }
   }

   /**
    * Return true if the game has ended.
    * @return endgame ?
    */
   public boolean isEnd() {
      return getCurrentMove().isEnd();
   }

   /**
    * Calculates an StoneVal.EMPTY Stone Group.
    * @param pion the EMPTY Stone
    * @return the group containing the stone.
    * @throws BadPlaceException if out of the goban.
    * @throws BadCouleurException if not EMPTY Stone.
    */
   public Group getEmptyGroupContaining(final Stone pion)
           throws BadPlaceException, BadCouleurException {
      if (pion.getCouleur() == StoneVal.EMPTY) {
         if (goban.bonneCoords(pion.getLine(), pion.getColumn())) {
            // Here, we have to get all the surroundings empty spots.
            // Let's use a classic breadth-first search algorithm
            // for the empty spots

            Group curGroup = new Group(StoneVal.EMPTY);
            LinkedList<Stone> processingList = new LinkedList<Stone>();
            processingList.addAll(goban.getLibertes(pion));
            while (!processingList.isEmpty()) {

               Stone curPion = processingList.poll();
               List<Stone> neighboursLib = goban.getLibertes(curPion);
               neighboursLib.removeAll(curGroup.getStones());

               processingList.addAll(neighboursLib);
               curGroup.addAll(neighboursLib);
            }
            return curGroup;
         } else {
            throw new BadPlaceException("Please try with a spot in the range");
         }
      } else {
         throw new BadCouleurException("This is not an empty spot");
      }
   }

   /**
    * Calculates the groups surrounding a Group of EMPTY Stones.
    * @param emptiesGroup thes groups.
    * @return the surrounding groups.
    */
   public List<Group> getEmptySpotSurroundingGroups(final Group emptiesGroup) {
      HashSet<Group> surrounders = new HashSet<Group>();
      for (Stone spot : emptiesGroup.getStones()) {
         surrounders.addAll(getSurroundingGroups(spot));
      }
      return new ArrayList<Group>(surrounders);
   }

   /**
    * Calculates the groups surrounding a given Group
    * @param groupe the surrounded group.
    * @return the surrounding groups.
    */
   public List<Group> getGroupSurroundingGroups(final Group groupe) {
      HashSet<Group> surrounding = new HashSet<Group>();
      for (Stone stone : groupe.getStones()) {
         List<Stone> neighbours = goban.getVoisins(stone);
         for (Stone neighbour : neighbours) {
            if (neighbour.getCouleur() == stone.getCouleur()) {
               neighbours.remove(neighbour);
            }
         }
         surrounding.addAll(getGroupsFromPions(neighbours));
      }

      return new ArrayList<Group>(surrounding);
   }

   /**
    * Checks if (<em>line</em>, <em>column</em>) are on the goban.
    * @param line y value.
    * @param column x value.
    * @return if we are on the goban.
    */
   public boolean bonnesCoords(final int line, final int column) {
      return goban.bonneCoords(line, column);
   }

   /**
    * Get the goban.
    * @return the goban.
    */
   public Goban getGoban() {
      return this.goban;
   }

   /**
    * Get the children of the current Node.
    * @return the children.
    */
   public List<Node<PlayMove>> getChildrenMove() {
      return lastMove.getChildren();
   }

   /**
    * get the current depth.
    * @return
    */
   public int getCurrentDepth() {
      return this.lastMove.getDepth();
   }

   /**
    * get freePlaces.
    * @return the current empty spot of the goban.
    */
   public Set<Stone> getFreePlaces() {
      return freePlaces;
   }

   /**
    * Get the game history
    * @return the game history.
    */
   public Tree<PlayMove> getHistory() {
      return history;
   }

   /**
    * get the lastMove Node.
    * @return last Move.
    */
   public Node<PlayMove> getLastMove() {
      return lastMove;
   }

   /**
    * Get the next playing color.
    * @return
    */
   public StoneVal getCurrentPlayer() {
      return currentPlayer;
   }

   /**
    * invert the current player : B->W, W->B
    */
   private void changeCurrentPlayer() {
      if (currentPlayer == StoneVal.BLACK) {
         currentPlayer = StoneVal.WHITE;
      } else {
         currentPlayer = StoneVal.BLACK;
      }
   }
}
