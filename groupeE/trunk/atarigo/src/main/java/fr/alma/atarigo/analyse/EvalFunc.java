/**
 * File {EvalFunc.java}
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
import fr.alma.atarigo.utils.Groupe;
import fr.alma.atarigo.utils.Stone;
import fr.alma.atarigo.utils.PionVal;
import fr.alma.atarigo.utils.PlayMove;
import fr.alma.atarigo.utils.exceptions.BadPlaceException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Provides static evaluation functions for an atari go game.
 * @author judu
 */
public final class EvalFunc {

   /**
    * Strong value for a winning case.
    */
   public static final int WIN = 1000;
   /**
    * Strong value for a loosing case.
    */
   public static final int PWND = -1000;
   /**
    * Very very important case : 5 * Very important.
    */
   public static final int VERYVERYIMPORTANT = 200;
   /**
    * Very important case : 4 * important.
    */
   public static final int VERYIMPORTANT = 40;
   /**
    * Important case : 2 * mean.
    */
   public static final int IMPORTANT = 10;
   /**
    * Mean case : ~ 2 * tiny.
    */
   public static final int MEAN = 5;
   /**
    * The less meaningful score.
    */
   public static final int TINY = 2;

   /**
    * Useless default constructor.
    */
   private EvalFunc() {
   }

   /**
    * Evaluates the game.
    *
    * Calls evaluateBeginning of evaluateFollowing
    * according to <em>beginning</em>
    * @param game The tested game.
    * @param color The AI color.
    * @param beginning true if we have not played enough moves.
    * @return the score calculated by the evaluation
    */
   public static int evaluate(final FakeGame game, final PionVal color,
           final boolean beginning) {
      final int coef = 6;
      if (game.isEnd()) {
         if (game.getCurrentMove().getPutStone().getAfter() == color) {
            return coef * WIN;
         } else {
            return coef * PWND;
         }

      }

      if (beginning) {
         if (game.getCurrentMove().getPutStone().getAfter() == color) {
            return evaluateBeginning(game);
         } else {
            return -evaluateBeginning(game);
         }
      } else {
         if (game.getCurrentMove().getPutStone().getAfter() == color) {
            return evaluateFollowing(game);
         } else {
            return -evaluateFollowing(game);
         }
      }
   }

   /**
    * Tests a game when it has not been played too much.
    * @param game the game we test
    * @return the score.
    */
   public static int evaluateBeginning(final Game game) {
      int score = 0;
      PlayMove pm = game.getCurrentMove();

      Stone putStone = pm.getPutStone().getNewStone();



      score += checkStonePlaces(game, IMPORTANT);

      score += evaluateGroupsLiberties(game, MEAN);

      Groupe containing = pm.getGroupeContaining(
              new Stone(putStone.getCouleur(),
              putStone.getLine(),
              putStone.getColumn()));

      if (containing.getLiberties().size() >= containing.size() * 2) {
         score += MEAN;
      } else if (containing.getLiberties().size() <= containing.size()) {
         score -= IMPORTANT;
      }

      if (nbKeima(game, putStone) > 0) {
         score += VERYVERYIMPORTANT;
      }
      return score;
   }

   /**
    * Evaluates the game when it has been played a sufficient time.
    * @param game The game to evaluate
    * @return The score.
    */
   public static int evaluateFollowing(final Game game) {
      int score = 0;
      Stone putStone = game.getCurrentMove().getPutStone().getNewStone();

      score += evaluateGroupsLiberties(game, VERYIMPORTANT);
      score += checkStonePlaces(game, IMPORTANT);
      if (nbKeima(game, putStone) > 0) {
         score += VERYVERYIMPORTANT;
      }
//        calculateEyes(game);
      score += checkEyes(game);
      return score;
   }

   /**
    * Check if the current group is in a Shicho position.
    * More information at : http://en.wikipedia.org/wiki/Shicho
    * @param game the current tested game.
    * @param groupe the suspected group.
    * @return A local score.
    */
   private static int checkShicho(final Game game, final Groupe groupe) {
      int score = 0;
      int diff;

      // First, a Shicho appends when you only have one liberty.
      if (groupe.getLiberties().size() == 1) {
         Stone[] lib = new Stone[1];
         groupe.getLiberties().toArray(lib);
         List<Stone> libslibs = game.getGoban().getLibertes(lib[0]);

         if (libslibs.size() == 2) {
            // Check if the liberties form a keima.
            Stone[] libsoflib = new Stone[2];
            libslibs.toArray(libsoflib);
            diff = Math.abs(
                    Math.abs(libsoflib[0].getColumn()
                    - libsoflib[1].getColumn())
                    - Math.abs(libsoflib[0].getLine()
                    - libsoflib[1].getLine()));

            if (diff == 0) {
               score += VERYVERYIMPORTANT;
            }
         }
      }

      return score;
   }

   /**
    * Checks the positions of the Stones.
    *
    * It is better to not play on the sides.
    *
    * @param game the tested game.
    * @param importance the importance of this test.
    * @return a local score.
    */
   private static int checkStonePlaces(final Game game, final int importance) {

      final int minBonnePlace = 2;
      final int maxBonnePlace = Goban.getTaille() - minBonnePlace;
      final PionVal curColor = game.getCurrentMove().getPutStone().
              getNewStone().getCouleur();

      int score = 0;

      for (Groupe groupe : game.getCurrentMove().getGroups()) {
         for (Stone stone : groupe.getStones()) {
            if (stone.getLine() >= minBonnePlace
                    && stone.getLine() <= maxBonnePlace
                    && stone.getColumn() >= minBonnePlace
                    && stone.getColumn() <= maxBonnePlace) {
               if (stone.getCouleur() == curColor) {
                  score += importance;
               } else {
                  score -= importance;
               }

            } else {
               if (stone.getCouleur() == curColor) {
                  score -= importance;
               } else {
                  score += importance;
               }
            }
         }
      }

      return score;
   }

   /**
    * Evaluates the liberties of the groups.
    *
    * Here, we detect if we are in danger or not.
    * @param game the tested game.
    * @param importance the importance of the test.
    * @return a local score.
    */
   private static int evaluateGroupsLiberties(final Game game,
           final int importance) {

      final int coef = 4;
      int score = 0;

      Groupe minLibMySide = null;
      Groupe minLibOtherSide = null;

      PionVal curColor = game.getCurrentMove().getPutStone().
              getNewStone().getCouleur();

      // First, get each side's group with a minimum of liberties
      for (Groupe groupe : game.getCurrentMove().getGroups()) {

         // Calculate in both side the group with the least liberties
         if (groupe.getCouleur() == curColor) {
            if (minLibMySide == null || minLibMySide.getLiberties().size()
                    > groupe.getLiberties().size()) {
               minLibMySide = groupe;
            }
         } else {
            if (minLibOtherSide == null || minLibOtherSide.getLiberties().size()
                    > groupe.getLiberties().size()) {
               minLibOtherSide = groupe;
            }
         }
      }

      int myLibs;
      int otherLibs;

      if (minLibMySide == null) {
         myLibs = 0;
         Logger.getAnonymousLogger().log(Level.WARNING, "groupe null "
                 + game.getCurrentMove().getGroups().toString());
      } else {
         myLibs = minLibMySide.getLiberties().size();
      }

      if (minLibOtherSide == null) {
         otherLibs = 0;
         Logger.getAnonymousLogger().log(Level.WARNING, "groupe null "
                 + game.getCurrentMove().getGroups().toString());
      } else {
         otherLibs = minLibOtherSide.getLiberties().size();
      }


      // Check if only one liberty
      if (myLibs == 1) {
         score += coef * PWND;
         Stone[] lib = new Stone[1];
         minLibMySide.getLiberties().toArray(lib);

         if (game.getGoban().getLibertes(lib[0]).size() <= 1) {
            score += PWND;
         }

         score -= checkShicho(game, minLibMySide);
      }

      if (otherLibs == 1) {
         score += VERYIMPORTANT;
         Stone[] lib = new Stone[1];
         minLibOtherSide.getLiberties().toArray(lib);

         if (game.getGoban().getLibertes(lib[0]).size() <= 1) {
            score += VERYVERYIMPORTANT;
         }

         score += checkShicho(game, minLibOtherSide);
      }

      final short bigDiffLib = 3;
      final short divideImportance = 2;


      // Compare minimum of group liberties.
      if (myLibs > otherLibs + bigDiffLib) {
         score += importance;
      } else if (myLibs > otherLibs) {
         score += importance / divideImportance;
      } else if (myLibs < otherLibs - bigDiffLib) {
         score -= importance;
      } else if (myLibs < otherLibs) {
         score -= importance / divideImportance;
      }
      return score;
   }

   /**
    * Calculate the eyes of each Groupe.
    *
    * Note : this method is not used.
    * @param game the game to test
    */
   private static void calculateEyes(final Game game) {
      // TODO : make an accurate an fast algorithm.

      final short minSurSize = 3;

      for (int ligne = 0; ligne < Goban.getTaille(); ++ligne) {
         for (int col = 0; col < Goban.getTaille(); ++col) {
            try {
               Stone spot = game.getStone(ligne, col);
               if (spot.getCouleur() == PionVal.RIEN) {
                  // If empty spot, we need its "empty group"
                  Groupe emptyGroup = game.getEmptyGroupContaining(spot);
                  List<Groupe> surrounders =
                          game.getEmptySpotSurroundingGroups(emptyGroup);

                  if (surrounders.size() == 1
                          && surrounders.get(0).size() > minSurSize) {
                     // We have only one surrounding Groupe, big enough.
                     surrounders.get(0).addEye(emptyGroup);
                  }
               }
            } catch (Exception ex) {
               Logger.getLogger(EvalFunc.class.getName()).
                       log(Level.SEVERE, null, ex);
            }
         }
      }
   }

   /**
    * Check the eyes, calculated by <em>calculateEyes</em>.
    * @param game the tested game.
    * @return a local score.
    */
   private static int checkEyes(final Game game) {
      final int goodNbEyes = 2;
      int score = 0;
      Stone putStone = game.getCurrentMove().getPutStone().getNewStone();

      for (Groupe groupe : game.getCurrentMove().getGroups()) {
         if (groupe.getCouleur() == putStone.getCouleur()) {
            if (groupe.nbEyes() > goodNbEyes) {
               score += IMPORTANT;
            } else if (groupe.nbEyes() == 1) {
               for (Groupe eye : groupe.getEyes()) {
                  if (eye.size() == 1) {
                     if (groupe.getLiberties().size() == 1) {
                        // If it's the last liberty, we lose more than him,
                        // because, he just has to play.
                        score += (PWND + PWND);
                     } else if (groupe.getLiberties().size() > groupe.size()) {
                        // If we still have some liberties, it's better.
                        score -= MEAN;
                     } else {
                        // If we don't have enough liberties, it's really bad.
                        score -= VERYIMPORTANT;
                     }
                  }
               }
            }
         } else {
            // If this group is an ennemy
            if (groupe.nbEyes() > goodNbEyes) {
               score -= IMPORTANT;
            } else if (groupe.nbEyes() == 1) {
               for (Groupe eye : groupe.getEyes()) {
                  if (eye.size() == 1) {
                     if (groupe.getLiberties().size() == 1) {
                        // If it's the last liberty, we almost win...
                        score += VERYIMPORTANT;
                     } else if (groupe.getLiberties().size() > groupe.size()) {
                        // If we still have some liberties, it's better.
                        score += MEAN;
                     } else {
                        // If we don't have enough liberties, it's really bad.
                        score += IMPORTANT;
                     }
                  }
               }
            }
         }
      }
      return score;
   }

   /**
    * Check the keimas.
    *
    * More information on : http://en.wikipedia.org/wiki/Shape_%28Go%29
    * @param game the tested game
    * @param putStone the last put Stone.
    * @return a local score.
    */
   private static int nbKeima(final Game game, final Stone putStone) {
      int keimas = 0;
      try {
         // N-W
         if (game.bonnesCoords(putStone.getLine() - 1, putStone.getColumn() - 1)
                 && game.getStone(putStone.getLine() - 1,
                 putStone.getColumn() - 1).getCouleur()
                 == putStone.getCouleur()) {

            if (game.getStone(putStone.getLine() - 1,
                    putStone.getColumn()).getCouleur() == PionVal.RIEN
                    && game.getStone(putStone.getLine(),
                    putStone.getColumn() - 1).getCouleur() == PionVal.RIEN) {
               ++keimas;
            }
         }

         // N-E
         if (game.bonnesCoords(putStone.getLine() - 1, putStone.getColumn() + 1)
                 && game.getStone(putStone.getLine() - 1,
                 putStone.getColumn() + 1).getCouleur()
                 == putStone.getCouleur()) {
            if (game.getStone(putStone.getLine() - 1,
                    putStone.getColumn()).getCouleur() == PionVal.RIEN
                    && game.getStone(putStone.getLine(),
                    putStone.getColumn() + 1).getCouleur() == PionVal.RIEN) {
               ++keimas;
            }
         }

         // S-W
         if (game.bonnesCoords(putStone.getLine() + 1, putStone.getColumn() - 1)
                 && game.getStone(putStone.getLine() + 1,
                 putStone.getColumn() - 1).getCouleur()
                 == putStone.getCouleur()) {
            //If same color, check the liberties
            if (game.getStone(putStone.getLine() + 1,
                    putStone.getColumn()).getCouleur() == PionVal.RIEN
                    && game.getStone(putStone.getLine(),
                    putStone.getColumn() - 1).getCouleur() == PionVal.RIEN) {
               ++keimas;
            }
         }

         // S-W
         if (game.bonnesCoords(putStone.getLine() + 1, putStone.getColumn() + 1)
                 && game.getStone(putStone.getLine() + 1,
                 putStone.getColumn() + 1).getCouleur()
                 == putStone.getCouleur()) {
            if (game.getStone(putStone.getLine() + 1,
                    putStone.getColumn()).getCouleur() == PionVal.RIEN
                    && game.getStone(putStone.getLine(),
                    putStone.getColumn() + 1).getCouleur() == PionVal.RIEN) {
               ++keimas;
            }
         }
      } catch (BadPlaceException ex) {
         return 0;
      }

      return keimas;
   }
}
