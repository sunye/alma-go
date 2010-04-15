/**
 * File {AlphaBeta.java}
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
import fr.alma.atarigo.utils.PionVal;
import fr.alma.atarigo.utils.PlayMove;
import fr.alma.atarigo.utils.Stone;
import fr.alma.atarigo.utils.exceptions.BadPlaceException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class describes the alpha-beta algorithm.
 * see http://en.wikipedia.org/wiki/Alpha-beta_pruning
 * Here, we cut the algorithm in 3 functions :
 * aphabeta, min and max.
 *
 * @author clotildemassot
 */
public final class AlphaBeta {

   /**
    * Defines where starts the middle of the board.
    */
   private static final int MIDDLE = 2;
   /**
    * Set the value for the infinite.
    */
   private static final int INFINI = Integer.MAX_VALUE;
   /**
    * The limit between evaluateBeginning and evaluateFollowing.
    */
   private static final int BEGINLIMIT = 8;
   /**
    * Max depth of the searching tree. Set when we initialize the game.
    */
   private int maxDepth;
   /**
    * Color played by the AI.
    */
   private PionVal aiColor;
   /**
    * Last best move calculated.
    */
   private final PlayMove lastBest;
   /**
    * In milliseconds, the time we have to play.
    */
   private int timer;
   /**
    * in milliseconds, the moment we start the research.
    */
   private long beginTime;

   /**
    * Constructor.
    * @param profondeurMax : Maximum depth to attain
    * @param couleur : color of the stone starting AlphaBeta
    */
   public AlphaBeta(final int profondeurMax, final PionVal couleur) {
      this.maxDepth = profondeurMax;
      this.aiColor = couleur;
      this.lastBest = new PlayMove();
   }

   /**
    * Initialization of AlphaBeta from a game.
    * @param jeu : State of the game
    * @param time : in millisec, the time we have to search.
    * @return the best move to play
    */
   public PlayMove init(final Game jeu, final int time) {
      this.timer = time;
      this.beginTime = System.currentTimeMillis();
      FakeGame tests = new FakeGame(jeu);
      PlayMove aJouer = alphaBeta(tests, 0, -INFINI, +INFINI);
      updateLastBest(aJouer);
      Logger.getAnonymousLogger().log(Level.INFO,
              "Fini ! coup renvoyé : " + lastBest);
      return lastBest;
   }

   /**
    * Start of AlphaBeta.
    * @param tests : Copy (false) of the game starting
    * @param prof : Starting depth
    * @param alpha Alpha(p), where p is the current node.
    * @param beta Beta(p), where p is the current node.
    * @return the best move to play
    */
   public PlayMove alphaBeta(final FakeGame tests, final int prof,
           final int alpha, final int beta) {

      if (prof < maxDepth && !tests.isEnd()) {
         this.generateMoves(tests);
      }
      if (tests.isInLeaf() || prof == maxDepth) {
         int coef = (maxDepth - prof) + (((maxDepth - prof) % 2) + 1);

         tests.getCurrentMove().setEval((coef) * EvalFunc.evaluate(tests,
                 aiColor,
                 tests.getDepth() <= BEGINLIMIT));

         return tests.getCurrentMove();
      } else {
         if (prof % 2 == 0) {
            //even depth : the computer plays.
            PlayMove maxPM = max(tests, prof + 1, alpha, beta);
            tests.getCurrentMove().setEval(maxPM.getEval());
            if (prof == 0) {
               return maxPM;
            } else {
               return tests.getCurrentMove();
            }

         } else {
            //odd depth : the gamer plays.
            PlayMove minPM = min(tests, prof + 1, alpha, beta);
            tests.getCurrentMove().setEval(minPM.getEval());
            if (prof == 0) {
               return minPM;
            } else {
               return tests.getCurrentMove();
            }
         }
      }
   }

   /**
    * Sets the fields of lastBest with those from currAlpha if they are better.
    * @param currAlpha the possible current best PlayMove.
    */
   private void updateLastBest(final PlayMove currAlpha) {
      synchronized (lastBest) {
         if (currAlpha.getEval() > lastBest.getEval()) {
            lastBest.setEnd(currAlpha.isEnd());
            lastBest.setGroups(currAlpha.getGroups());
            lastBest.setEval(currAlpha.getEval());
            lastBest.setDiff(currAlpha.getDiff());
            lastBest.setPutStone(currAlpha.getPutStone());
            lastBest.notify();
         }
      }
   }

   /**
    * Returns lastBest if it is actually describing a Move.
    * @return Last saved best move.
    */
   public PlayMove getLastBest() {
      synchronized (lastBest) {
         while (lastBest.getPutStone() == null) {
            try {
               lastBest.wait();
            } catch (InterruptedException ex) {
               Logger.getLogger(AlphaBeta.class.getName()).
                       log(Level.SEVERE, null, ex);

            }
         }
         return lastBest;
      }


   }

   /**
    * Process a max node.
    * @param tests : Copy (false) of the game
    * @param prof : depth from beginning
    * @param alpha current maximum.
    * @param beta current Beta(parent)
    * @return the max move to play
    */
   private PlayMove max(final FakeGame tests, final int prof,
           final int alpha, final int beta) {

      PlayMove alphadep = new PlayMove();
      int indexMax = 0;
      alphadep.setEval(-INFINI); // équivaut à Alpha(p)
      for (int i = 0; i < tests.getChildrenMove().size(); ++i) {
         tests.apply(i);
         PlayMove valFils = alphaBeta(tests, prof,
                 Math.max(alpha, alphadep.getEval()), beta);
         tests.revert();
         if (valFils.isEnd()) {
            tests.keepChildAt(i);
            return valFils;
         }

         if (valFils.getEval() > alphadep.getEval()) {
            alphadep = valFils;
            indexMax = i;
         }
         if (alphadep.getEval() >= beta) {
            tests.keepChildAt(i);
            // If we are testing a first move, put the alphadep in lastBest
            if (prof == 1) {
               updateLastBest(alphadep);
            }
            return alphadep; // coupure beta
         }

         if (System.currentTimeMillis() - this.beginTime > this.timer) {
            return alphadep;
         }
      }
      tests.keepChildAt(indexMax);
      if (prof == 1) {
         updateLastBest(alphadep);
      }
      return alphadep;
   }

   /**
    * Process a min Node.
    * @param tests : Copy (false) of the game
    * @param prof : depth from beginning
    * @param alpha Alpha(parent).
    * @param beta current minimum.
    * @return the min move to play
    */
   private PlayMove min(final FakeGame tests,
           final int prof, final int alpha, final int beta) {

      if (prof == 2 && lastBest.getPutStone() == null) {
         updateLastBest(tests.getCurrentMove());
      }

      PlayMove betadep = new PlayMove();
      int indexMin = 0;
      betadep.setEval(INFINI); // équivaut à Beta(p)
      for (int i = 0; i < tests.getChildrenMove().size(); ++i) {
         tests.apply(i);
         PlayMove valFils = alphaBeta(tests, prof, alpha,
                 Math.min(beta, betadep.getEval()));
         tests.revert();
         if (valFils.isEnd()) {
            tests.keepChildAt(i);
            return valFils;
         }

         if (valFils.getEval() < betadep.getEval()) {
            betadep = valFils;
            indexMin = i;
         }
         if (alpha >= betadep.getEval()) {
            tests.keepChildAt(i);
            return betadep; // coupure alpha
         }
         if (System.currentTimeMillis() - this.beginTime > this.timer) {
            return betadep;
         }
      }
      tests.keepChildAt(indexMin);
      return betadep;
   }

   /**
    * Generate all possible moves from the current state of the game.
    * @param tests : State of the game
    */
   private void generateMoves(final FakeGame tests) {
      Set<Stone> freeToTry = new HashSet<Stone>(tests.getFreePlaces());
      List<Stone> listToTry = null;


      /*
       * Two cases :
       * we have not played BEGINLIMIT moves;
       * we have.
       */
      if (tests.getDepth() <= BEGINLIMIT) {
         // In the first case, we only play in the middle of the goban.
         freeToTry.retainAll(generateFreePlaces(MIDDLE, MIDDLE,
                 Goban.getTaille() - MIDDLE,
                 Goban.getTaille() - MIDDLE));
         listToTry = new LinkedList<Stone>(freeToTry);
      } else {
         /*
          * In the second case, we only play in a area containing
          * all the played Stone + 2 lines and columns around,
          * starting just around the last played Stone.
          */
         Set<Stone> firstFreeToTry = new HashSet<Stone>(freeToTry);

         Stone lastPut = new Stone(PionVal.RIEN,
                 tests.getCurrentMove().getPutStone().getLine(),
                 tests.getCurrentMove().getPutStone().getColumn());

         Set<Stone> autour = generateFreePlaces(lastPut.getLine() - 1,
                 lastPut.getColumn() - 1,
                 lastPut.getLine() + 1,
                 lastPut.getColumn() + 1);
         autour.remove(lastPut);

         firstFreeToTry.retainAll(autour);

         // Here we calculate the rectangle where it is better to play
         // find the first and last lines, and same for the column.
         int firstLine = Goban.getTaille() - 1;
         int firstCol = firstLine;
         int lastLine = 0;
         int lastCol = 0;

         for (Groupe groupe : tests.getCurrentMove().getGroups()) {
            for (Stone stone : groupe.getStones()) {
               firstLine = Math.min(firstLine, stone.getLine());
               firstCol = Math.min(firstCol, stone.getColumn());
               lastLine = Math.max(lastLine, stone.getLine());
               lastCol = Math.max(lastCol, stone.getColumn());
            }
         }

         // The rectangle is from
         // (firstLine-2,firstCol-2), to (lastLine+2,lastCol+2)
         freeToTry.retainAll(generateFreePlaces(firstLine - 2, firstCol - 2,
                 lastLine + 2,
                 lastCol + 2));
         freeToTry.removeAll(firstFreeToTry);
         listToTry = new LinkedList<Stone>(firstFreeToTry);
         listToTry.addAll(freeToTry);
      }

      for (Stone stone : listToTry) {
         try {
            tests.fakePosePion(stone.getLine(), stone.getColumn(),
                    tests.getCurrentPlayer());
         } catch (BadPlaceException ex) {
            Logger.getAnonymousLogger().log(Level.SEVERE, ex.getMessage());
            Logger.getAnonymousLogger().log(Level.INFO,
                    tests.getGoban().toString());
         }
      }
   }

   /**
    * Search free places in the Goban.
    * @param linBeg : Line beginning
    * @param colBeg : Column beginning
    * @param linEnd : End line
    * @param colEnd : End column
    * @return free moves
    */
   private Set<Stone> generateFreePlaces(final int linBeg, final int colBeg,
           final int linEnd, final int colEnd) {

      Set<Stone> retour = new HashSet<Stone>();
      for (int l = linBeg; l <= linEnd; ++l) {
         for (int c = colBeg; c <= colEnd; ++c) {
            retour.add(new Stone(PionVal.RIEN, l, c));
         }
      }
      return retour;
   }
}
