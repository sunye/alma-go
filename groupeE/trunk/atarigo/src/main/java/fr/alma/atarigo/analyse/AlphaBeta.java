/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.alma.atarigo.analyse;

import fr.alma.atarigo.utils.Game;
import fr.alma.atarigo.utils.tree.Node;
import fr.alma.atarigo.utils.tree.Tree;

/**
 *
 * @author clotildemassot
 */
public class AlphaBeta {

    private final static int profondeurMax = 6;
    private final static int INFINI = 10000;

    public CoupAJouer init(Game jeu) {
        Tree<CoupAJouer> arbreJeu = new Tree(new Node(new CoupAJouer(jeu)));
        return alphaBeta(arbreJeu, 0, -INFINI, +INFINI);
    }

    public CoupAJouer alphaBeta(Tree<CoupAJouer> arbreJeu, int prof, int alpha, int beta) {
        if (prof < profondeurMax) {
            //Construire fils !
        }
        if (prof == profondeurMax || arbreJeu.isLeaf()) {
            CoupAJouer coup = arbreJeu.getRootElement().getData();
            coup.setEvaluation(EvalFunc.evaluateFollowing(coup.getJeu())); ////////////??????
            return coup;
        } else {
            if (prof % 2 == 0) {//profondeur paire = ˆ l'ordinateur de jouer
                return max(arbreJeu, prof + 1, alpha, beta);
            } else {//profondeur impaire = au joueur de jouer
                return min(arbreJeu, prof + 1, alpha, beta);
            }
        }
    }

    private CoupAJouer max(Tree<CoupAJouer> arbreJeu, int prof, int alpha, int beta) {
        CoupAJouer coupMax = new CoupAJouer(-INFINI);
        CoupAJouer valFils;
        for (int i = 0; i < arbreJeu.getRootElement().getChildren().size(); i++) {
            valFils = alphaBeta(new Tree(arbreJeu.getRootElement().getChildAt(i)), prof + 1, alpha, beta); // new Tree(ayant pour racine le fils courant)
            if (valFils.getEvaluation() > coupMax.getEvaluation()) {
                coupMax = valFils;
            }
            if (beta < coupMax.getEvaluation()) {
                return coupMax; // coupure beta
            }
            if (alpha < coupMax.getEvaluation()) {
                alpha = coupMax.getEvaluation();
            }
        }
        return coupMax;
    }

    private CoupAJouer min(Tree<CoupAJouer> arbreJeu, int prof, int alpha, int beta) {
        CoupAJouer coupMin = new CoupAJouer(INFINI);
        CoupAJouer valFils;
        for (int i = 0; i < arbreJeu.getRootElement().getChildren().size(); i++) {
            valFils = alphaBeta(new Tree(arbreJeu.getRootElement().getChildAt(i)), prof + 1, alpha, beta); // new Tree(ayant pour racine le fils courant)
            if (valFils.getEvaluation() < coupMin.getEvaluation()) {
                coupMin = valFils;
            }
            if (alpha > coupMin.getEvaluation()) {
                return coupMin; // coupure alpha
            }
            if (beta > coupMin.getEvaluation()) {
                beta = coupMin.getEvaluation();
            }
        }
        return coupMin;
    }
}
