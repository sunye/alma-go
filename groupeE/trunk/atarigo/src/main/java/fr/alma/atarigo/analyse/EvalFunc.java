package fr.alma.atarigo.analyse;

import fr.alma.atarigo.utils.Game;
import fr.alma.atarigo.utils.Goban;
import fr.alma.atarigo.utils.Groupe;
import fr.alma.atarigo.utils.Modif;
import fr.alma.atarigo.utils.Stone;
import fr.alma.atarigo.utils.PionVal;
import fr.alma.atarigo.utils.PlayMove;
import fr.alma.atarigo.utils.exceptions.BadPlaceException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EvalFunc {

    static public final int TRESIMPORTANT = 20;
    static public final int IMPORTANT = 10;
    static public final int MOYEN = 5;
    static public final int FAIBLE = 2;

    /**
     * Calculate the eyes of each Groupe
     * @param game
     */
    static public void calculateEyes(Game game) {
        for (int ligne = 0; ligne < Goban.getTaille(); ++ligne) {
            for (int col = 0; col < Goban.getTaille(); ++col) {
                try {
                    Stone spot = game.getStone(ligne, col);
                    if (spot.getCouleur() == PionVal.RIEN) {
                        // If empty spot, we need its "empty group"
                        Groupe emptyGroup = game.getEmptyGroupContaining(spot);
                        List<Groupe> surrounders = game.getEmptySpotSurroundingGroups(emptyGroup);
                        if (surrounders.size() == 1 && surrounders.get(0).size() > 3) {
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

    static private int checkStonePlaces(Game game, int importance) {

        final int minBonnePlace = 2;
        final int maxBonnePlace = Goban.getTaille() - minBonnePlace;
        final PionVal curColor = game.getCurrentMove().getPutStone().getNewStone().getCouleur();
        int score = 0;

        for (Groupe groupe : game.getCurrentMove().getGroupes()) {
            for (Stone stone : groupe.getStones()) {
                if (stone.getLine() >= minBonnePlace && stone.getLine() <= maxBonnePlace && stone.getColumn() >= minBonnePlace && stone.getColumn() <= maxBonnePlace) {
                    score = (stone.getCouleur() == curColor) ? score + importance : score - importance;
                } else {
                    score = (stone.getCouleur() == curColor) ? score - importance : score + importance;
                }
            }
        }

        return score;

    }

    private static int evaluateGroupsLiberties(Game game, int importance) {
        int score = 0;

        Groupe minLibMySide = null;
        Groupe minLibOtherSide = null;

        PionVal curColor = game.getCurrentMove().getPutStone().getNewStone().getCouleur();

        for (Groupe groupe : game.getCurrentMove().getGroupes()) {

            // Calculate in both side the group with the least liberties
            if (groupe.getCouleur() == curColor) {
                if (minLibMySide == null || minLibMySide.getLibertes() > groupe.getLibertes()) {
                    minLibMySide = groupe;
                }
            } else {
                if (minLibOtherSide == null || minLibOtherSide.getLibertes() > groupe.getLibertes()) {
                    minLibOtherSide = groupe;
                }
            }
        }

        // Compare minimum of group liberties.
        if (minLibMySide.getLibertes() > minLibOtherSide.getLibertes() + 3) {
            score += importance;
        } else if (minLibMySide.getLibertes() > minLibOtherSide.getLibertes()) {
            score += importance/2;
        } else if (minLibMySide.getLibertes() < minLibOtherSide.getLibertes() - 3) {
            score -= importance;
        } else if (minLibMySide.getLibertes() < minLibOtherSide.getLibertes()) {
            score -= importance/2;
        }
        return score;
    }

    static public int evaluateBeginning(Game game) {
        int score = 0;
        PlayMove pm = game.getCurrentMove();

        Stone putStone = pm.getPutStone().getNewStone();



        score += checkStonePlaces(game, IMPORTANT);

        score += evaluateGroupsLiberties(game, MOYEN);

        Groupe containing = pm.getGroupeContaining(new Stone(putStone.getCouleur(), putStone.getLine(), putStone.getColumn()));
        if (containing.getLibertes() >= containing.size() * 2) {
            score += MOYEN;
        } else if (containing.getLibertes() <= containing.size()) {
            score -= IMPORTANT;
        }

        if (nbKeima(game, putStone) > 0) {
            score += MOYEN;
        }
        return score;
    }

    static public int evaluateFollowing(Game game) {
        int score = 0;

        score += evaluateGroupsLiberties(game, TRESIMPORTANT);
        score += checkStonePlaces(game, MOYEN);


        return score;
    }

    static private int nbKeima(Game game, Stone putStone) {
        int keimas = 0;
        try {
            // N-W
            if (game.bonnesCoords(putStone.getLine() - 1, putStone.getColumn() - 1)
                    && game.getStone(putStone.getLine() - 1, putStone.getColumn() - 1).getCouleur() == putStone.getCouleur()) {

                if (game.getStone(putStone.getLine() - 1, putStone.getColumn()).getCouleur() == PionVal.RIEN
                        && game.getStone(putStone.getLine(), putStone.getColumn() - 1).getCouleur() == PionVal.RIEN) {
                    ++keimas;
                }
            }

            // N-E
            if (game.bonnesCoords(putStone.getLine() - 1, putStone.getColumn() + 1)
                    && game.getStone(putStone.getLine() - 1, putStone.getColumn() + 1).getCouleur() == putStone.getCouleur()) {
                if (game.getStone(putStone.getLine() - 1, putStone.getColumn()).getCouleur() == PionVal.RIEN
                        && game.getStone(putStone.getLine(), putStone.getColumn() + 1).getCouleur() == PionVal.RIEN) {
                    ++keimas;
                }
            }

            // S-W
            if (game.bonnesCoords(putStone.getLine() + 1, putStone.getColumn() - 1)
                    && game.getStone(putStone.getLine() + 1, putStone.getColumn() - 1).getCouleur() == putStone.getCouleur()) {
                //If same color, check the liberties
                if (game.getStone(putStone.getLine() + 1, putStone.getColumn()).getCouleur() == PionVal.RIEN
                        && game.getStone(putStone.getLine(), putStone.getColumn() - 1).getCouleur() == PionVal.RIEN) {
                    ++keimas;
                }
            }

            // S-W
            if (game.bonnesCoords(putStone.getLine() + 1, putStone.getColumn() + 1)
                    && game.getStone(putStone.getLine() + 1, putStone.getColumn() + 1).getCouleur() == putStone.getCouleur()) {
                if (game.getStone(putStone.getLine() + 1, putStone.getColumn()).getCouleur() == PionVal.RIEN
                        && game.getStone(putStone.getLine(), putStone.getColumn() + 1).getCouleur() == PionVal.RIEN) {
                    ++keimas;
                }
            }
        } catch (BadPlaceException ex) {
            return 0;
        }

        return keimas;
    }
}
