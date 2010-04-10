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
        return alphaBeta(tests, 0, -INFINI, +INFINI);
    }

    public AlphaBeta(int profondeurMax, PionVal couleur) {
        this.profondeurMax = profondeurMax;
        this.couleur = couleur;
    }

    public PlayMove alphaBeta(FakeGame tests, int prof, int alpha, int beta) {
        Logger.getAnonymousLogger().log(Level.INFO, "Profondeur = "+prof);
        if (prof < profondeurMax) {
            this.generateMoves(tests);
        }
        if (prof >= profondeurMax || tests.isInLeaf()) {
                tests.getCurrentMove().setEval(EvalFunc.evaluate(tests, couleur, tests.getDepth() <= BEGINLIMIT));
                return tests.getCurrentMove();
        } else {
            if (prof % 2 == 0) {//profondeur paire = a l'ordinateur de jouer
                return max(tests, prof + 1, alpha, beta);
            } else {//profondeur impaire = au joueur de jouer
                return min(tests, prof + 1, alpha, beta);
            }
        }
    }

    private PlayMove max(FakeGame tests, int prof, int alpha, int beta) {
        PlayMove coupMax = new PlayMove();
        int indexMax = 0;
        coupMax.setEval(-INFINI);
        for (int i = 0; i < tests.getChildrenMove().size(); ++i) {
            tests.apply(i);
            PlayMove valFils = alphaBeta(tests, prof + 1, alpha, beta);
            tests.revert();
            if (valFils.getEval() > coupMax.getEval()) {
                coupMax = valFils;
                indexMax = i;
            }
            if (beta <= coupMax.getEval()) {
                // TODO : garder coupMax.
                tests.keepChildAt(i);
                return coupMax; // coupure beta
            }
            if (alpha < coupMax.getEval()) {
                alpha = coupMax.getEval();
            }
        }
        tests.keepChildAt(indexMax);
        return coupMax;
    }

    private PlayMove min(FakeGame tests, int prof, int alpha, int beta) {
        PlayMove coupMin = new PlayMove();
        int indexMin = 0;
        coupMin.setEval(INFINI);
        for (int i = 0; i < tests.getChildrenMove().size(); ++i) {
            tests.apply(i);
            PlayMove valFils = alphaBeta(tests, prof + 1, alpha, beta);
            tests.revert();
            if (valFils.getEval() < coupMin.getEval()) {
                coupMin = valFils;
                indexMin = i;
            }
            if (alpha <= coupMin.getEval()) {
                tests.keepChildAt(i);
                return coupMin; // coupure alpha
            }
            if (beta > coupMin.getEval()) {
                beta = coupMin.getEval();
            }
        }
        tests.keepChildAt(indexMin);
        return coupMin;
    }

    private void generateMoves(FakeGame tests) {
        Set<Stone> freeToTry = tests.getFreePlaces();
        if (tests.getDepth() <= BEGINLIMIT) {
            freeToTry.retainAll(generateFreePlaces(2, 2, 6, 6));
        }
        for (Stone stone : freeToTry) {
            try {
                tests.fakePosePion(stone.getLine(), stone.getColumn(), tests.getCurrentPlayer());
            } catch (BadPlaceException ex) {
                System.out.println(ex.getMessage());
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
