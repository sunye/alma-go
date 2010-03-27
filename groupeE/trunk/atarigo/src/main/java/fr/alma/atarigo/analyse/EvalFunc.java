package fr.alma.atarigo.analyse;

import fr.alma.atarigo.utils.Game;
import fr.alma.atarigo.utils.Goban;
import fr.alma.atarigo.utils.Groupe;
import fr.alma.atarigo.utils.Modif;
import fr.alma.atarigo.utils.Stone;
import fr.alma.atarigo.utils.PionVal;
import fr.alma.atarigo.utils.PlayMove;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EvalFunc {


    static public void calculateEyes(Game game) {
        for (int ligne = 0; ligne < Goban.getTaille(); ++ligne) {
            for (int col = 0; col < Goban.getTaille(); ++col) {
                try {
                    Stone spot = game.getStone(ligne, col);
                    if (spot.getCouleur() == PionVal.RIEN) {
                        // If empty spot, we need its "empty group"
                        Groupe emptyGroup = game.getEmptyGroupContaining(spot);
                        List<Groupe> surrounders = game.getEmptySpotGroupSurrounders(emptyGroup);
                        if(surrounders.size() == 1 && surrounders.get(0).size() > 3){
                            // We have only one surrounding Groupe, big enough.
                            surrounders.get(0).addEye(emptyGroup);
                        }
                    }
                } catch (Exception ex) {
                    Logger.getLogger(EvalFunc.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public int evaluateBeginning(Game game) {
        int score = 0;
        PlayMove pm = game.getCurrentMove();

        Modif putPion = pm.getPutPion();
        int minBonnePlace = 2;
        int maxBonnePlace = Goban.getTaille() - minBonnePlace;

        if (putPion.getLine() >= minBonnePlace && putPion.getLine() <= maxBonnePlace && putPion.getColumn() >= minBonnePlace && putPion.getColumn() <= maxBonnePlace) {
            score += 10;
        } else {
            score -= 10;
        }

        Groupe containing = pm.getGroupeContaining(new Stone(putPion.getAfter(),putPion.getLine(),putPion.getColumn()));
        if(containing.getLibertes() >= containing.size()*2){
            score += 5;
        } else if(containing.getLibertes() <= containing.size()){
            score -= 5;
        }

        if(nbKeima(putPion) > 0){

        }
        return score;
    }

    private int nbKeima(Modif putPion) {
        //TODO: détecter le nombre de keima
        return 0;
    }
}
