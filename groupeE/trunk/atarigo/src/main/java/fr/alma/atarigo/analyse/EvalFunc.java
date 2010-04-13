/**
 * File {EvalFunc.java}
 * Last commited $Date$
 * By $Author$
 * Revision $Rev$
 *
 * Copyright (C) 2010 Clotilde Massot & Julien Durillon
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of this program.
 */
         
package fr.alma.atarigo.analyse;

import fr.alma.atarigo.utils.Game;
import fr.alma.atarigo.utils.Goban;
import fr.alma.atarigo.utils.Groupe;
import fr.alma.atarigo.utils.Stone;
import fr.alma.atarigo.utils.PionVal;
import fr.alma.atarigo.utils.PlayMove;
import fr.alma.atarigo.utils.exceptions.BadPlaceException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Provides static evaluation functions for an atari go game.
 * @author judu
 */
public class EvalFunc {

    static public final int GAGNE = 1000;
    static public final int PERDU = -1000;
    static public final int TRESTRESIMPORTANT = 200;
    static public final int TRESIMPORTANT = 40;
    static public final int IMPORTANT = 10;
    static public final int MOYEN = 5;
    static public final int FAIBLE = 2;

    static int evaluate(FakeGame tests, PionVal couleur, boolean beginning) {
        if (tests.isEnd()) {
            if (tests.getCurrentMove().getPutStone().getAfter() == couleur) {
                return 6*GAGNE;
            } else {
                return 6*PERDU;
            }

        }

        if (beginning) {
            if (tests.getCurrentMove().getPutStone().getAfter() == couleur) {
                return evaluateBeginning(tests);
            } else {
                return -evaluateBeginning(tests);
            }
        } else {
            if (tests.getCurrentMove().getPutStone().getAfter() == couleur) {
                return evaluateFollowing(tests);
            } else {
                return -evaluateFollowing(tests);
            }
        }
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
            score += TRESTRESIMPORTANT;
        }
        return score;
    }

    static public int evaluateFollowing(Game game) {
        int score = 0;
        Stone putStone = game.getCurrentMove().getPutStone().getNewStone();

        score += evaluateGroupsLiberties(game, TRESIMPORTANT);
        score += checkStonePlaces(game, IMPORTANT);
        if (nbKeima(game, putStone) > 0) {
            score += TRESTRESIMPORTANT;
        }
        calculateEyes(game);
        score += checkEyes(game);
        return score;
    }

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


    /**
     * Check if the current group is in a Shicho position :
     * http://en.wikipedia.org/wiki/Shicho
     * @param game
     * @param groupe
     * @return
     */
    static private int checkShicho(final Game game, final Groupe groupe){
        int score = 0;
        int diff;

        // First, a Shicho appends when you only have one liberty.
        if(groupe.getLiberties().size() == 1){
            Stone[] lib = new Stone[1];
            groupe.getLiberties().toArray(lib);
            List<Stone> libslibs = game.getGoban().getLibertes(lib[0]);
            
            if (libslibs.size() == 2) {
                // Check if the liberties form a keima.
                Stone[] libsoflib = new Stone[2];
                libslibs.toArray(libsoflib);
                diff = Math.abs(Math.abs(libsoflib[0].getColumn() - libsoflib[1].getColumn()) - Math.abs(libsoflib[0].getLine() - libsoflib[1].getLine()));

                if(diff == 0){
                    score += TRESTRESIMPORTANT;
                }
            }
        }

        return score;
    }



    static private int checkStonePlaces(Game game, int importance) {

        final int minBonnePlace = 2;
        final int maxBonnePlace = Goban.getTaille() - minBonnePlace;
        final PionVal curColor = game.getCurrentMove().getPutStone().getNewStone().getCouleur();
        int score = 0;

        for (Groupe groupe : game.getCurrentMove().getGroups()) {
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

        // First, get each side's group with a minimum of liberties
        for (Groupe groupe : game.getCurrentMove().getGroups()) {

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
            Logger.getAnonymousLogger().log(Level.WARNING, "groupe null " + game.getCurrentMove().getGroups().toString());
        } else {
            myLibs = minLibMySide.getLiberties().size();
        }

        if (minLibOtherSide == null) {
            otherLibs = 0;
            Logger.getAnonymousLogger().log(Level.WARNING, "groupe null " + game.getCurrentMove().getGroups().toString());
        } else {
            otherLibs = minLibOtherSide.getLiberties().size();
        }


        // Check if only one liberty
        if (myLibs == 1) {
            score += 4*PERDU;
            Stone[] lib = new Stone[1];
            minLibMySide.getLiberties().toArray(lib);

            if (game.getGoban().getLibertes(lib[0]).size() <= 1) {
                score += PERDU;
            }

            score -= checkShicho(game, minLibMySide);
        }

        if (otherLibs == 1) {
            score += TRESIMPORTANT;
            Stone[] lib = new Stone[1];
            minLibOtherSide.getLiberties().toArray(lib);

            if (game.getGoban().getLibertes(lib[0]).size() <= 1) {
                score += TRESTRESIMPORTANT;
            }

            score += checkShicho(game, minLibOtherSide);
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

    static private int checkEyes(Game game) {
        int score = 0;
        Stone putStone = game.getCurrentMove().getPutStone().getNewStone();

        for (Groupe groupe : game.getCurrentMove().getGroups()) {
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
