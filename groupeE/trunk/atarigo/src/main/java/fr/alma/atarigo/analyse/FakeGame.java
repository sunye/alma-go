/**
 * File {FakeGame.java}
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
import fr.alma.atarigo.utils.Modif;
import fr.alma.atarigo.utils.PionVal;
import fr.alma.atarigo.utils.PlayMove;
import fr.alma.atarigo.utils.Stone;
import fr.alma.atarigo.utils.exceptions.BadCouleurException;
import fr.alma.atarigo.utils.exceptions.BadGobanStateException;
import fr.alma.atarigo.utils.exceptions.BadPlaceException;
import fr.alma.atarigo.utils.tree.Node;
import fr.alma.atarigo.utils.tree.Tree;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author clotildemassot
 */
public final class FakeGame extends Game {

    private Node<PlayMove> fakeLastMove;
    private int depth;

    public FakeGame(Game game) {
        this.goban = (Goban) game.getGoban().clone();
        this.history = new Tree<PlayMove>(new Node<PlayMove>(game.getCurrentMove()));
        this.lastMove = history.getRootElement();
        this.depth = game.getCurrentDepth();
        this.freePlaces = new HashSet<Stone>(game.getFreePlaces());
        this.currentPlayer = game.getCurrentPlayer();
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public void fakePosePion(int line, int column, PionVal color) throws BadPlaceException {
        if (goban.bonneCoords(line, column)) {
            if (goban.getCase(line, column) == PionVal.RIEN) {
                if (!isSuicide(line, column, color)) {

                    Stone current = new Stone(color, line, column);

                    Modif modif = new Modif(line, column, goban.getCase(line, column), color);
                    PlayMove currentMove = new PlayMove();
                    currentMove.addModif(modif);
                    currentMove.setPutStone(modif);

                    fakeStartMove(currentMove);

                    //Let's modify the goban !
                    goban.setCase(line, column, color);

                    //Calculates the new groups
                    Set<Groupe> ennemies = fakeCalculateGroups(current);

                    //Update the liberties of surrounding ennemy groups
                    updateLiberties(ennemies);

                    //Removes taken stones, and update the currentMove with the Modifs.
                    fakeProcessGroupeTaking(ennemies);

                    goban.setCase(line, column, PionVal.RIEN);

                } else {
                    throw new BadPlaceException("(" + line + ", " + column + ", " + color + ") This move is a suicide, I cannot let you do that !");
                }
            } else {
                throw new BadPlaceException("Already a stone here (" + line + "," + column + "," + color.toString() + ")");
            }
        } else {
            throw new BadPlaceException("It is actually better to play on the board");
        }
    }

    private void fakeStartMove(PlayMove currPM) {

        //prepare the group modifications
        currPM.setGroups((ArrayList<Groupe>) getCurrentMove().getGroups().clone());

        Node<PlayMove> newMove = new Node<PlayMove>(currPM);
        lastMove.addChild(newMove);
        fakeLastMove = newMove;
    }

    private void fakeProcessGroupeTaking(Set<Groupe> ennemies) {
        for (Groupe groupe : ennemies) {
            if (groupe.getLiberties().size() == 0) {
                // Dans ce cas là, on a bouché la dernière libertée,
                //il faut donc supprimer les pierres du goban, et le groupe du pm.
                fakeRemoveGroupe(groupe);
                getFakeCurrentMove().setEnd(true);
//                Logger.getLogger(FakeGame.class.getName()).log(Level.INFO, "Fin du jeu détectée − coup joué : "+getFakeCurrentMove().getPutStone().toString());
            }
        }
    }

    private void fakeRemoveGroupe(Groupe groupe) {

        PlayMove fakePM = new PlayMove();

        // Removing eah Stone
        for (Stone pion : groupe.getStones()) {
            // Don't forget to register the modification.
            Modif mod = new Modif(pion.getLine(), pion.getColumn(), pion.getCouleur(), PionVal.RIEN);
            getFakeCurrentMove().addModif(mod);
            fakePM.addModif(mod);
            goban.setCase(pion.getLine(), pion.getColumn(), PionVal.RIEN);
        }

        // Calculating the liberties of surrounding groups
        Set<Groupe> surroundings = new HashSet<Groupe>();
        for (Stone pion : groupe.getStones()) {
            surroundings.addAll(getSurroundingGroups(pion));
        }
        surroundings.remove(null);
        updateLiberties(surroundings);

        getFakeCurrentMove().getGroups().remove(groupe);

        try {
            // Now, let's revert the removal, to keep the same goban.
            fakePM.revert(goban);
        } catch (BadGobanStateException ex) {
            Logger.getLogger(FakeGame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Set<Groupe> fakeGetGroupsFromPions(List<Stone> pions) {
        HashSet<Groupe> groups = new HashSet<Groupe>(4);
        for (Stone pi : pions) {
            Groupe containing = getFakeCurrentMove().getGroupeContaining(pi);
            if (containing == null) {
//                Logger.getAnonymousLogger().log(Level.SEVERE, "Containing null, pion : " + pi);
//                Logger.getAnonymousLogger().log(Level.SEVERE,goban.toString());
            } else {
                groups.add(containing);
            }
        }
        return groups;
    }

    public Set<Groupe> fakeGetSurroundingGroups(Stone pion) {
        // Get the (<= 4) neighbours groups of the current stone.
        List<Stone> neighbourList = goban.getVoisins(pion);
        if (neighbourList.isEmpty()) {
//            Logger.getAnonymousLogger().log(Level.INFO, "Empty list");
            return null;
        } else {
            return fakeGetGroupsFromPions(neighbourList);
        }
    }

    protected Set<Groupe> fakeCalculateGroups(Stone last) {

        Groupe lastAdded = new Groupe(last.getCouleur());
        try {
            lastAdded.addStone(last);
        } catch (BadCouleurException e) {
        }

        List<Groupe> currentGroups = getFakeCurrentMove().getGroups();

        Set<Groupe> surroundingGroups = fakeGetSurroundingGroups(last);
        Set<Groupe> ennemyGroups = new HashSet<Groupe>(4);

        if (surroundingGroups != null) {
            for (Groupe groupe : surroundingGroups) {
                if (groupe.getCouleur() == last.getCouleur()) {
                    lastAdded.addAll(groupe);
                    currentGroups.remove(groupe);
                } else {
                    ennemyGroups.add(groupe);
                }
            }
        }

        currentGroups.add(lastAdded);
        lastAdded.setLiberties(getGroupLiberties(lastAdded));
        return ennemyGroups;
    }

    private PlayMove getFakeCurrentMove() {
        return this.fakeLastMove.getData();
    }

    public boolean isInLeaf() {
        return this.lastMove.isLeaf();
    }

    @Override
    public Boolean apply(int numChild) {
        Boolean ok = super.apply(numChild);
        if (ok) {
            ++depth;
            this.fakeLastMove = null;
        }
        return ok;
    }

    @Override
    public Boolean revert() {
        Boolean ok = super.revert();
        if (ok) {
            --depth;
        }
        return ok;
    }

    public void keepThisBranch() {
        if (lastMove.getFather() != null) {
            lastMove.getFather().keepChild(lastMove);
        }
    }

    void keepChildAt(int i) {
        lastMove.keepChildAt(i);
    }
}
