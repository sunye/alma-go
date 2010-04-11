/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.alma.atarigo.analyse;

import fr.alma.atarigo.utils.Game;
import fr.alma.atarigo.utils.PionVal;
import fr.alma.atarigo.utils.PlayMove;
import fr.alma.atarigo.utils.Stone;
import fr.alma.atarigo.utils.exceptions.BadPlaceException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author clotildemassot
 */
public class AlphaBeta {

    private int profondeurMax;
    private final static int INFINI = Integer.MAX_VALUE;
    private final static int BEGINLIMIT = 6;
    private PionVal couleur;

    public PlayMove init(Game jeu) {
        FakeGame tests = new FakeGame(jeu);
        PlayMove aJouer = alphaBeta(tests, 0, -INFINI, +INFINI);
        Logger.getAnonymousLogger().log(Level.INFO, "Fini ! coup renvoyé : "+aJouer);
        return aJouer;
    }

    public AlphaBeta(int profondeurMax, PionVal couleur) {
        this.profondeurMax = profondeurMax;
        this.couleur = couleur;
    }

    public PlayMove alphaBeta(FakeGame tests, int prof, int alpha, int beta) {
        if (prof < profondeurMax && !tests.isEnd()) {
            this.generateMoves(tests);
        }
        if (prof == profondeurMax || tests.isInLeaf()) {
            tests.getCurrentMove().setEval(EvalFunc.evaluate(tests, couleur, tests.getDepth() <= BEGINLIMIT));
            return tests.getCurrentMove();
        } else {
            if (prof % 2 == 0) {//profondeur paire = a l'ordinateur de jouer
                PlayMove maxPM = max(tests, prof + 1, alpha, beta);
                tests.getCurrentMove().setEval(maxPM.getEval());
                if(prof == 0){
                    return maxPM;
                } else {
                    return tests.getCurrentMove();
                }
                
            } else {//profondeur impaire = au joueur de jouer
                PlayMove minPM = min(tests, prof + 1, alpha, beta);
                tests.getCurrentMove().setEval(minPM.getEval());
                if(prof == 0){
                    return minPM;
                } else {
                    return tests.getCurrentMove();
                }
            }
        }
    }

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
            if (alphadep.getEval() >= beta)  {
                tests.keepChildAt(i);
                return alphadep; // coupure beta
            }
        }
        tests.keepChildAt(indexMax);
        return alphadep;
    }

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

    private void generateMoves(FakeGame tests) {
        Set<Stone> freeToTry = new HashSet<Stone>(tests.getFreePlaces());
        if (tests.getDepth() <= BEGINLIMIT) {
            freeToTry.retainAll(generateFreePlaces(2, 2, 6, 6));
        }
        for (Stone stone : freeToTry) {
            try {
                tests.fakePosePion(stone.getLine(), stone.getColumn(), tests.getCurrentPlayer());
            } catch (BadPlaceException ex) {
                Logger.getAnonymousLogger().log(Level.SEVERE, ex.getMessage());
            }
        }
    }

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
