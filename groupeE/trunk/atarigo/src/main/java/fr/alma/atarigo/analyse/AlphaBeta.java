/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.alma.atarigo.analyse;

import fr.alma.atarigo.utils.Game;
import fr.alma.atarigo.utils.PlayMove;
import java.util.Random;

/**
 *
 * @author clotildemassot
 */
public class AlphaBeta {

    private int profondeurMax;
    private final static int INFINI = Integer.MAX_VALUE;
    private final static int BEGINLIMIT = 6;

    public PlayMove init(Game jeu) {
        FakeGame tests = new FakeGame(jeu);
        return alphaBeta(tests, 0, -INFINI, +INFINI);
    }

    public AlphaBeta(int profondeurMax) {
        this.profondeurMax = profondeurMax;
    }

    public PlayMove alphaBeta(FakeGame tests, int prof, int alpha, int beta) {
        if (prof < profondeurMax) {
            this.generateMoves(tests);
        }
        if (prof == profondeurMax || tests.isInLeaf()) {
            if (tests.getDepth() <= BEGINLIMIT) {
                tests.getCurrentMove().setEval(EvalFunc.evaluateBeginning(tests));
                return tests.getCurrentMove();
            } else {
                tests.getCurrentMove().setEval(EvalFunc.evaluateFollowing(tests));
                return tests.getCurrentMove();
            }
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
        coupMax.setEval(-INFINI);
        for (int i = 0; i < tests.getChildrenMove().size(); ++i) {
            tests.apply(i);
            PlayMove valFils = alphaBeta(tests, prof + 1, alpha, beta);
            tests.revert();
            if (valFils.getEval() > coupMax.getEval()) {
                coupMax = valFils;
            }
            if (beta < coupMax.getEval()) {
                return coupMax; // coupure beta
            }
            if (alpha < coupMax.getEval()) {
                alpha = coupMax.getEval();
            }
        }
        return coupMax;
    }

    private PlayMove min(FakeGame tests, int prof, int alpha, int beta) {
        PlayMove coupMin = new PlayMove();
        coupMin.setEval(INFINI);
        for (int i = 0; i < tests.getChildrenMove().size(); ++i) {
            tests.apply(i);
            PlayMove valFils = alphaBeta(tests, prof + 1, alpha, beta);
            tests.revert();
            if (valFils.getEval() < coupMin.getEval()) {
                coupMin = valFils;
            }
            if (alpha < coupMin.getEval()) {
                return coupMin; // coupure alpha
            }
            if (beta > coupMin.getEval()) {
                beta = coupMin.getEval();
            }
        }
        return coupMin;
    }

    private void generateMoves(FakeGame tests) {
        if (tests.getDepth() <= BEGINLIMIT) {
            
        }
        if (tests.getDepth() <= BEGINLIMIT) {
            Random r = new Random();
            r.setSeed(System.currentTimeMillis());
            int line = r.nextInt(5)+2;
            int column = r.nextInt(5)+2;
            
        }
    }



}
