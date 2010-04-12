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

    static public final int GAGNE = 1000;
    static public final int PERDU = -GAGNE;
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
                if (minLibMySide == null || minLibMySide.getLiberties().size() > groupe.getLiberties().size()) {
                    minLibMySide = groupe;
                }
            } else {
                if (minLibOtherSide == null || minLibOtherSide.getLiberties().size() > groupe.getLiberties().size()) {
                    minLibOtherSide = groupe;
                }
            }
        }

        int myLibs;
        int otherLibs;

        if (minLibMySide == null) {
            myLibs = 0;
            Logger.getAnonymousLogger().log(Level.WARNING, "groupe null " + game.getCurrentMove().getGroupes().toString());
        } else {
            myLibs = minLibMySide.getLiberties().size();
        }

        if (minLibOtherSide == null) {
            otherLibs = 0;
            Logger.getAnonymousLogger().log(Level.WARNING, "groupe null " + game.getCurrentMove().getGroupes().toString());
        } else {
            otherLibs = minLibOtherSide.getLiberties().size();
        }


        // Check if only one liberty
        if (myLibs == 1) {
            score += PERDU;
            Stone[] lib = new Stone[1];
            minLibMySide.getLiberties().toArray(lib);

            if (game.getGoban().getLibertes(lib[0]).size() <= 1) {
                score += PERDU;
            }
        }

        if (otherLibs == 1) {
            score += IMPORTANT;
            for (Stone lib : minLibOtherSide.getLiberties()) {
                if (game.getGoban().getLibertes(lib).size() <= 1) {
                    score += GAGNE;
                }
            }
        }

        // Compare minimum of group liberties.
        if (myLibs > otherLibs + 3) {
            score += importance;
        } else if (myLibs > otherLibs) {
            score += importance / 2;
        } else if (myLibs < otherLibs - 3) {
            score -= importance;
        } else if (myLibs < otherLibs) {
            score -= importance / 2;
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
        if (containing.getLiberties().size() >= containing.size() * 2) {
            score += MOYEN;
        } else if (containing.getLiberties().size() <= containing.size()) {
            score -= IMPORTANT;
        }

        if (nbKeima(game, putStone) > 0) {
            score += MOYEN;
        }
        return score;
    }

    static private int checkEyes(Game game) {
        int score = 0;
        Stone putStone = game.getCurrentMove().getPutStone().getNewStone();

        for (Groupe groupe : game.getCurrentMove().getGroupes()) {
            if (groupe.getCouleur() == putStone.getCouleur()) {
                if (groupe.nbEyes() > 2) {
                    score += IMPORTANT;
                } else if (groupe.nbEyes() == 1) {
                    for (Groupe eye : groupe.getEyes()) {
                        if (eye.size() == 1) {
                            if (groupe.getLiberties().size() == 1) {
                                // If it's the last liberty, we lose much than him, because, he just has to play.
                                score += (PERDU + PERDU);
                            } else if (groupe.getLiberties().size() > groupe.size()) {
                                // If we still have some liberties, it's better.
                                score -= MOYEN;
                            } else {
                                // If we don't have enough liberties, it's really bad.
                                score -= TRESIMPORTANT;
                            }
                        }
                    }
                }
            } else {
                // If this group is an ennemy
                if (groupe.nbEyes() > 2) {
                    score -= IMPORTANT;
                } else if (groupe.nbEyes() == 1) {
                    for (Groupe eye : groupe.getEyes()) {
                        if (eye.size() == 1) {
                            if (groupe.getLiberties().size() == 1) {
                                // If it's the last liberty, we almost win...
                                score += TRESIMPORTANT;
                            } else if (groupe.getLiberties().size() > groupe.size()) {
                                // If we still have some liberties, it's better.
                                score += MOYEN;
                            } else {
                                // If we don't have enough liberties, it's really bad.
                                score += IMPORTANT;
                            }
                        }
                    }
                }
            }
        }
        return score;
    }

    static public int evaluateFollowing(Game game) {
        int score = 0;
        Stone putStone = game.getCurrentMove().getPutStone().getNewStone();

        score += evaluateGroupsLiberties(game, TRESIMPORTANT);
        score += checkStonePlaces(game, MOYEN);
        calculateEyes(game);
        score += checkEyes(game);
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

    static int evaluate(FakeGame tests, PionVal pionVal, boolean beginning) {
        if (tests.isEnd()) {
            if (tests.getCurrentMove().getPutStone().getAfter() == pionVal) {
                return GAGNE + GAGNE + GAGNE;
            } else {
                return PERDU + PERDU + PERDU;
            }

        }

        if (beginning) {
            if (tests.getCurrentMove().getPutStone().getAfter() == pionVal) {
                return evaluateBeginning(tests);
            } else {
                return -evaluateBeginning(tests);
            }
        } else {
            if (tests.getCurrentMove().getPutStone().getAfter() == pionVal) {
                return evaluateFollowing(tests);
            } else {
                return -evaluateFollowing(tests);
            }
        }
    }
}
