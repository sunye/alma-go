package fr.alma.atarigo.analyse;

import fr.alma.atarigo.utils.Game;
import fr.alma.atarigo.utils.tree.Node;
import fr.alma.atarigo.utils.tree.Tree;

public class MinMax {

    private int profondeurMax;
    private final static int INFINI = 10000;

    public CoupAJouer init(Game jeu, int prof) {
        profondeurMax = prof;
        Tree<CoupAJouer> arbreJeu = new Tree(new Node(new CoupAJouer(jeu)));
        return calculMinMax(arbreJeu, 0);
    }

    public MinMax() {
        super();
    }


    public CoupAJouer calculMinMax(Tree<CoupAJouer> arbreJeu, int prof) {
        if (prof < profondeurMax) {
        //  crŽer les fils !
        }
        if (prof == profondeurMax || arbreJeu.isLeaf()) {
            //Evaluer la situation de la feuille
            CoupAJouer coup = arbreJeu.getRootElement().getData();
            coup.setEvaluation(EvalFunc.evaluateFollowing(coup.getJeu())); ////////////??????
            return coup;
        } else {
            if (prof % 2 == 0) {//profondeur paire = ˆ l'ordinateur de jouer
                return max(arbreJeu, prof + 1);
            } else {//profondeur impaire = au joueur de jouer
                return min(arbreJeu, prof + 1);
            }
        }
    }

    private CoupAJouer max(Tree<CoupAJouer> arbreJeu, int prof) {
        CoupAJouer coupMax = new CoupAJouer(-INFINI);
        CoupAJouer valFils;
        for (int i = 0; i < arbreJeu.getRootElement().getChildren().size(); i++) {
            valFils = calculMinMax(new Tree(arbreJeu.getRootElement().getChildAt(i)), prof + 1);
            if (valFils.getEvaluation() > coupMax.getEvaluation()) {
                coupMax = valFils;
            }
        }
        return coupMax;
    }

    private CoupAJouer min(Tree<CoupAJouer> arbreJeu, int prof) {
        CoupAJouer coupMin = new CoupAJouer(INFINI);
        CoupAJouer valFils;
        for (int i = 0; i < arbreJeu.getRootElement().getChildren().size(); i++) {
            valFils = calculMinMax(new Tree(arbreJeu.getRootElement().getChildAt(i)), prof + 1); 
            if (valFils.getEvaluation() < coupMin.getEvaluation()) {
                coupMin = valFils;
            }
        }
        return coupMin;
    }
}
