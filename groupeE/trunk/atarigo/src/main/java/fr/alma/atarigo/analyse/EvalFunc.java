package fr.alma.atarigo.analyse;

import fr.alma.atarigo.utils.Game;
import fr.alma.atarigo.utils.Goban;
import fr.alma.atarigo.utils.Groupe;
import fr.alma.atarigo.utils.Modif;
import fr.alma.atarigo.utils.Pion;
import fr.alma.atarigo.utils.PionVal;
import fr.alma.atarigo.utils.PlayMove;
import java.util.HashSet;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EvalFunc {

    public void calculeYeux(Game game) {
        for (int ligne = 0; ligne < Goban.getTaille(); ++ligne) {
            for (int col = 0; col < Goban.getTaille(); ++col) {
                try {
                    if (game.getPion(ligne, col).getCouleur() == PionVal.RIEN) {
                        // Si c'est une case vide
                        if (game.nbLibPion(ligne, col) == 0) {
                            // Si cette case n'a qu'une liberté
                            //TODO: gérer un groupe de cases vides.
                            Pion oeil = new Pion(PionVal.RIEN, ligne, col);
                            HashSet<Groupe> groupes = game.getSurroundingGroups(oeil);
                            if (groupes.size() == 1) {
                                Iterator<Groupe> groupeIt = groupes.iterator();
                                while (groupeIt.hasNext()) {
                                    Groupe gr = groupeIt.next();
                                    gr.addOeil(oeil);
                                }
                            }
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

        Groupe containing = pm.getGroupeContaining(new Pion(putPion.getAfter(),putPion.getLine(),putPion.getColumn()));
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
        //TODO: détecter le nombre de hoshi
        return 0;
    }
}
