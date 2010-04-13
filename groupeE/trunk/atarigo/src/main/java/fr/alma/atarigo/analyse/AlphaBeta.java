/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
 * @author clotildemassot
 */
public class AlphaBeta {

    private static int MILIEU = 2;
    private int profondeurMax;
    private final static int INFINI = Integer.MAX_VALUE;
    private final static int BEGINLIMIT = 8;
    private PionVal couleur;
    private final PlayMove lastBest;

    /**
     * Constructor
     * @param profondeurMax : Maximum depth to attain
     * @param couleur : color of the stone starting AlphaBeta
     */
    public AlphaBeta(int profondeurMax, PionVal couleur) {
        this.profondeurMax = profondeurMax;
        this.couleur = couleur;
        this.lastBest = new PlayMove();
    }

    /**
     * Initialization of AlphaBeta from a game
     * @param jeu : State of the game
     * @return the best move to play
     */
    public PlayMove init(Game jeu) {
        FakeGame tests = new FakeGame(jeu);
        PlayMove aJouer = alphaBeta(tests, 0, -INFINI, +INFINI);
        Logger.getAnonymousLogger().log(Level.INFO, "Fini ! coup renvoyé : " + aJouer);
        return aJouer;
    }

    /**
     * Start of AlphaBeta
     * @param tests : Copy (false) of the game starting
     * @param prof : Starting depth
     * @param alpha
     * @param beta
     * @return the best move to play
     */
    public PlayMove alphaBeta(FakeGame tests, int prof, int alpha, int beta) {
        if (prof < profondeurMax && !tests.isEnd()) {
            this.generateMoves(tests);
        }
        if (prof == profondeurMax || tests.isInLeaf()) {
            int coef = (profondeurMax - prof) + (((profondeurMax - prof) % 2) + 1);

            tests.getCurrentMove().setEval((coef) * EvalFunc.evaluate(tests, couleur, tests.getDepth() <= BEGINLIMIT));

            return tests.getCurrentMove();
        } else {
            if (prof % 2 == 0) {//profondeur paire = a l'ordinateur de jouer
                PlayMove maxPM = max(tests, prof + 1, alpha, beta);
                tests.getCurrentMove().setEval(maxPM.getEval());
                if (prof == 0) {
                    return maxPM;
                } else {
                    return tests.getCurrentMove();
                }

            } else {//profondeur impaire = au joueur de jouer
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

    private void updateLastBest(PlayMove currAlpha) {
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

    public PlayMove getLastBest() {
        synchronized (lastBest) {
            while (lastBest.getPutStone() == null) {
                try {
                    lastBest.wait(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(AlphaBeta.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return lastBest;
        }


    }

    /**
     * Calculation of the max move
     * @param tests : Copy (false) of the game
     * @param prof : depth from beginning
     * @param alpha
     * @param beta
     * @return the max move to play
     */
    private PlayMove max(FakeGame tests, int prof, int alpha, int beta) {
        PlayMove alphadep = new PlayMove();
        int indexMax = 0;
        alphadep.setEval(-INFINI); // équivaut à Alpha(p)
        for (int i = 0; i < tests.getChildrenMove().size(); ++i) {
            tests.apply(i);
            PlayMove valFils = alphaBeta(tests, prof, Math.max(alpha, alphadep.getEval()), beta);
            tests.revert();
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
        }
        tests.keepChildAt(indexMax);
        if (prof == 1) {
            updateLastBest(alphadep);
        }
        return alphadep;
    }

    /**
     * Calculation of the min move
     * @param tests : Copy (false) of the game
     * @param prof : depth from beginning
     * @param alpha
     * @param beta
     * @return the min move to play
     */
    private PlayMove min(FakeGame tests, int prof, int alpha, int beta) {
        PlayMove betadep = new PlayMove();
        int indexMin = 0;
        betadep.setEval(INFINI); // équivaut à Beta(p)
        for (int i = 0; i < tests.getChildrenMove().size(); ++i) {
            tests.apply(i);
            PlayMove valFils = alphaBeta(tests, prof, alpha, Math.min(beta, betadep.getEval()));
            tests.revert();
            if (valFils.getEval() < betadep.getEval()) {
                betadep = valFils;
                indexMin = i;
            }
            if (alpha >= betadep.getEval()) {
                tests.keepChildAt(i);
                return betadep; // coupure alpha
            }
        }
        tests.keepChildAt(indexMin);
        return betadep;
    }

    /**
     * Generate all possible moves from a state gaming
     * @param tests : State of the game
     */
    private void generateMoves(FakeGame tests) {
        Set<Stone> freeToTry = new HashSet<Stone>(tests.getFreePlaces());
        List<Stone> listToTry = null;

        if (tests.getDepth() <= BEGINLIMIT) {
            freeToTry.retainAll(generateFreePlaces(MILIEU, MILIEU, Goban.getTaille() - MILIEU, Goban.getTaille() - MILIEU));
            listToTry = new LinkedList<Stone>(freeToTry);
        } else {

            Set<Stone> firstFreeToTry = new HashSet<Stone>(freeToTry);

            Stone lastPut = new Stone(PionVal.RIEN, tests.getCurrentMove().getPutStone().getLine(), tests.getCurrentMove().getPutStone().getColumn());

            Set<Stone> autour = generateFreePlaces(lastPut.getLine() - 1, lastPut.getColumn() - 1, lastPut.getLine() + 1, lastPut.getColumn() + 1);
            autour.remove(lastPut);

            firstFreeToTry.retainAll(autour);

            // Here we calculate the rectangle where it is better to play
            // find the firs and last line, and same for the column.
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

            //The rectangle is from (firstLine-2,firstCol-2), to (lastLine+2,lastCol+2)
            freeToTry.retainAll(generateFreePlaces(firstLine - 2, firstCol - 2, lastLine + 2, lastCol + 2));
            freeToTry.removeAll(firstFreeToTry);
            listToTry = new LinkedList<Stone>(firstFreeToTry);
            listToTry.addAll(freeToTry);
        }
//        Logger.getAnonymousLogger().log(Level.INFO, new StringBuilder("Dispos : ").append(freeToTry.toString()).toString());

        for (Stone stone : listToTry) {
            try {
                tests.fakePosePion(stone.getLine(), stone.getColumn(), tests.getCurrentPlayer());
            } catch (BadPlaceException ex) {
                Logger.getAnonymousLogger().log(Level.SEVERE, ex.getMessage());
                Logger.getAnonymousLogger().log(Level.INFO, tests.getGoban().toString());
            }
        }
    }

    /**
     * Search free places in the Goban
     * @param linBeg : Line beginning
     * @param colBeg : Column beginning
     * @param linEnd : End line
     * @param colEnd : End column
     * @return free moves
     */
    private Set<Stone> generateFreePlaces(int linBeg, int colBeg, int linEnd, int colEnd) {
        Set<Stone> retour = new HashSet<Stone>();
        for (int l = linBeg; l <= linEnd; ++l) {
            for (int c = colBeg; c <= colEnd; ++c) {
                retour.add(new Stone(PionVal.RIEN, l, c));
            }
        }
        return retour;
    }
}
