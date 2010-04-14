// File Game.java
// Last commited $Date$
// By $Author$
// Revision $Rev$
//
// Copyright (C) 2010 Clotilde Massot & Julien Durillon
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// as published by the Free Software Foundation; either version 2
// of the License, or (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
//
// The above copyright notice and this permission notice shall be
// included in all copies or substantial portions of this program.
package fr.alma.atarigo.utils;

import fr.alma.atarigo.utils.exceptions.BadCouleurException;
import fr.alma.atarigo.utils.exceptions.BadGobanStateException;
import fr.alma.atarigo.utils.exceptions.BadPlaceException;
import fr.alma.atarigo.utils.tree.Node;
import fr.alma.atarigo.utils.tree.Tree;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author judu
 */
public class Game {

    protected Goban goban;
    // History of the game : contains all the moves since the beginning.
    protected Tree<PlayMove> history;
    // Last move played (faster backtracking).
    protected Node<PlayMove> lastMove;
    protected  PionVal currentPlayer;
    protected Set<Stone> freePlaces;

    public Game() {
        this.goban = new Goban();

        history = new Tree<PlayMove>();
        lastMove = new Node<PlayMove>(new PlayMove());
        history.setRootElement(lastMove);
        currentPlayer = PionVal.NOIR;
        freePlaces = new HashSet<Stone>(Goban.getTaille() * Goban.getTaille());
        for (int line = 0; line < Goban.getTaille(); ++line) {
            for (int col = 0; col < Goban.getTaille(); ++col) {
                freePlaces.add(new Stone(PionVal.RIEN, line, col));
            }
        }
    }

    public Boolean playAt(int line, int column) {
        try {
            posePion(line, column, currentPlayer);

            changeCurrentPlayer();
            freePlaces.remove(new Stone(PionVal.RIEN, line, column));
            return true;
        } catch (BadPlaceException ex) {
            Logger.getAnonymousLogger().log(Level.WARNING, ex.getMessage());
            return false;
        }


    }

    /**
     * This method put a stone on the goban, checking whether it is actually <em>on</em> the goban,
     * and manages all the changes involved (such as stone taken, and groups making)
     *
     * @param line The line where to put the stone
     * @param column The column where to put the stone
     * @param color The color of the stone.
     * @throws BadPlaceException Threw if the coordinates do not fit in the goban
     */
    public void posePion(int line, int column, PionVal color) throws BadPlaceException {
        if (goban.bonneCoords(line, column)) {
            if (goban.getCase(line, column) == PionVal.RIEN) {
                if (!isSuicide(line, column, color)) {
                    Stone current = new Stone(color, line, column);

                    Modif modif = new Modif(line, column, goban.getCase(line, column), color);
                    PlayMove currentMove = new PlayMove();
                    currentMove.addModif(modif);
                    currentMove.setPutStone(modif);

                    startMove(currentMove);

                    //Let's modify the goban !
                    goban.setCase(line, column, color);
//                    freePlaces.remove(new Stone(PionVal.RIEN, line, column));

                    //Calculates the new groups
                    Set<Groupe> ennemies = calculateGroups(current);

                    //Update the liberties of surrounding ennemy groups
                    updateLiberties(ennemies);

                    //Removes taken stones, and update the currentMove with the Modifs.
                    processGroupeTaking(ennemies);

                } else {
                    throw new BadPlaceException("This move is a suicide, I cannot let you do that !");
                }
            } else {
                throw new BadPlaceException("Already a stone here");
            }
        } else {
            throw new BadPlaceException("It is actually better to play on the board");
        }
    }

    /**
     * Checks if the place is considered as a suicide.
     * If the played Stone fills the last liberty of a group, it is,
     * unless we kill another group.
     * @param line
     * @param column
     * @param pionVal
     * @return whether or not it is a suicide
     */
    protected boolean isSuicide(int line, int column, PionVal pionVal) {
        //TODO : correct this function
        if (goban.nbLibertes(new Stone(pionVal, line, column)) == 0) {
            // No liberty ? Let's check if we are killing a ennemy group.
            Set<Groupe> groupes = getSurroundingGroups(new Stone(pionVal, line, column));
            
            int i = 0;
            for (Groupe gr : groupes) {
                ++i;
                if (gr.getCouleur() != pionVal) {
                    // If ennemy, check if it has one liberty left
                    if (gr.getLiberties().size() == 1) {
                        return false;
                    }
                } else if (gr.getLiberties().size() > 1) {
                    // If allie and more than 1 liberty, it is not a suicide.
                    return false;
                }
            }
//            System.out.println(groupes);
//            System.out.println(goban);
            return true;
        } else {
            return false;
        }
    }

    private void startMove(PlayMove currentMove) {

        //prepare the group modifications
        currentMove.setGroups((ArrayList<Groupe>) getCurrentMove().getGroups().clone());

        Node<PlayMove> newMove = new Node<PlayMove>(currentMove);
        lastMove.addChild(newMove);
        lastMove = newMove;
    }

    private HashSet<Groupe> getGroupsFromPions(List<Stone> pions) {
        HashSet<Groupe> groups = new HashSet<Groupe>(4);
        for (Stone pi : pions) {
            Groupe containing = getCurrentMove().getGroupeContaining(pi);
            groups.add(containing);
        }
        return groups;
    }

    protected HashSet<Groupe> getSurroundingGroups(Stone pion) {
        // Get the (<= 4) neighbours groups of the current stone.
        List<Stone> pions = goban.getVoisins(pion);

        return getGroupsFromPions(pions);
    }

    /**
     * Calculates the new groups, after putting the last stone, then returns
     * the ennemy's groups surrounding the stone. It is not really
     * KISS, but useful, and a little faster than re-calculate the groups.
     * @param last The last stone played
     */
    protected Set<Groupe> calculateGroups(Stone last) {
        Groupe lastAdded = new Groupe(last.getCouleur());
        try {
            lastAdded.addStone(last);
        } catch (BadCouleurException e) {
        }

        List<Groupe> groupes = getCurrentMove().getGroups();

        Set<Groupe> groups = getSurroundingGroups(last);
        Set<Groupe> others = new HashSet<Groupe>(4);
        for (Groupe groupe : groups) {
            if (groupe.getCouleur() == last.getCouleur()) {
                lastAdded.addAll(groupe);
                groupes.remove(groupe);
            } else {
                others.add(groupe);
            }
        }
        groupes.add(lastAdded);
        lastAdded.setLiberties(getGroupLiberties(lastAdded));
        return others;
    }

    /**
     * Returns all the empty places surrounding a group.
     * @param groupe
     * @return
     */
    protected  Set<Stone> getGroupLiberties(Groupe groupe) {
        HashSet<Stone> libertes = new HashSet<Stone>(groupe.size() * 2 + 2);

        for (Stone pion : groupe.getStones()) {
            List<Stone> libz = goban.getLibertes(pion);
            libertes.addAll(libz);
        }
        return libertes;
    }

    /**
     * Remove each group without any liberty.
     * @param ennemies
     */
    private void processGroupeTaking(Set<Groupe> ennemies) {
        for (Groupe groupe : ennemies) {
            if (groupe.getLiberties().size() == 0) {
                // Dans ce cas là, on a bouché la dernière libertée,
                //il faut donc supprimer les pierres du goban, et le groupe du pm.
                removeGroupe(groupe);
                getCurrentMove().setEnd(true);
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Fin du jeu !");
            }
        }
    }

    /**
     * Removes <em>groupe</em> from the current PlayMove, each of its stones from the goban,
     * then update the liberties of the surrounding groups.
     * @param groupe
     */
    private void removeGroupe(Groupe groupe) {

        for (Stone pion : groupe.getStones()) {
            // Don't forget to register the modification.
            getCurrentMove().addModif(new Modif(pion.getLine(), pion.getColumn(), pion.getCouleur(), PionVal.RIEN));
            goban.setCase(pion.getLine(), pion.getColumn(), PionVal.RIEN);
            freePlaces.add(new Stone(PionVal.RIEN, pion.getLine(), pion.getColumn()));
        }

        Set<Groupe> surroundings = new HashSet<Groupe>();
        for (Stone pion : groupe.getStones()) {
            surroundings.addAll(getSurroundingGroups(pion));
        }
        updateLiberties(surroundings);

        getCurrentMove().getGroups().remove(groupe);
    }

    /**
     * Calculate the liberties of each Groupe surrounding the <em>current</em> Stone
     * @param current
     */
    private void calculateLiberties(Stone current) {
        Set<Groupe> surroundings = getSurroundingGroups(current);
        for (Groupe groupe : surroundings) {
            if (groupe.getCouleur() != current.getCouleur()) {
                groupe.setLiberties(getGroupLiberties(groupe));
            }
        }
    }

    /**
     * Calculates and Updates the liberties of each group of the Set.
     * @param ennemies
     */
    protected void updateLiberties(Set<Groupe> ennemies) {
        for (Groupe groupe : ennemies) {
            groupe.setLiberties(getGroupLiberties(groupe));
        }
    }

    /**
     * Returns the current PlayMove
     * @return
     */
    public PlayMove getCurrentMove() {
        return lastMove.getData();
    }

    /**
     * Get the Stone in goban[line][column].
     * @param line
     * @param column
     * @return
     * @throws BadPlaceException
     */
    public Stone getStone(int line, int column) throws BadPlaceException {
        if (goban.bonneCoords(line, column)) {
            return new Stone(goban.getCase(line, column), line, column);
        } else {
            throw new BadPlaceException("Coordinates out of the board");
        }
    }

    /**
     * Reverts the game to the last Move (the father of the currentMove).
     */
    public Boolean revert() {

        PlayMove lastPm = getCurrentMove();
        try {
            lastPm.revert(this.goban);

            for (Modif mod : lastPm.getDiff()) {
                if (mod.getBefore() == PionVal.RIEN) {
                    this.freePlaces.add(new Stone(PionVal.RIEN, mod.getLine(), mod.getColumn()));
                } else {
                    this.freePlaces.remove(new Stone(PionVal.RIEN, mod.getLine(), mod.getColumn()));
                }
            }

            lastMove = lastMove.getFather();
            this.changeCurrentPlayer();
            return Boolean.TRUE;
        } catch (BadGobanStateException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            return Boolean.FALSE;
        }
    }

    public Boolean apply(int numChild) {
        Node<PlayMove> child = lastMove.getChildAt(numChild);
        if (child != null) {
            if (child.getData().getPutStone().getAfter() != currentPlayer) {
                return false;
            }

            PlayMove newPM = child.getData();
            try {
                newPM.apply(goban);
            } catch (BadGobanStateException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }

            // Update the freeplaces
            for (Modif mod : newPM.getDiff()) {
                if (mod.getAfter() == PionVal.RIEN) {
                    this.freePlaces.add(new Stone(PionVal.RIEN, mod.getLine(), mod.getColumn()));
                } else {
                    this.freePlaces.remove(new Stone(PionVal.RIEN, mod.getLine(), mod.getColumn()));
                }
            }

            lastMove = child;
            this.changeCurrentPlayer();
            return true;
        } else {
            Logger.getAnonymousLogger().log(Level.WARNING, "NOT applied, null child");
            return false;
        }
    }

    public boolean isEnd() {
        return getCurrentMove().isEnd();
    }

    /**
     * Calculates an PionVal.RIEN Stone Groupe.
     * @param pion
     * @return
     * @throws Exception
     */
    public Groupe getEmptyGroupContaining(Stone pion) throws Exception {
        if (pion.getCouleur() == PionVal.RIEN) {
            if (goban.bonneCoords(pion.getLine(), pion.getColumn())) {
                // Here, we have to get all the surroundings empty spots.
                // Let's use a classic breadth-first search algorithm for the empty spots

                Groupe curGroup = new Groupe(PionVal.RIEN);
                LinkedList<Stone> processingList = new LinkedList<Stone>();
                processingList.addAll(goban.getLibertes(pion));
                while (!processingList.isEmpty()) {

                    Stone curPion = processingList.poll();
                    List<Stone> neighboursLib = goban.getLibertes(curPion);
                    neighboursLib.removeAll(curGroup.getStones());

                    processingList.addAll(neighboursLib);
                    curGroup.addAll(neighboursLib);
                }
                return curGroup;
            } else {
                throw new BadPlaceException("Please try with a spot in the range");
            }
        } else {
            throw new Exception("This is not an empty spot");
        }
    }

    public List<Groupe> getEmptySpotSurroundingGroups(Groupe emptiesGroup) {
        HashSet<Groupe> surrounders = new HashSet<Groupe>();
        for (Stone spot : emptiesGroup.getStones()) {
            surrounders.addAll(getSurroundingGroups(spot));
        }
        return new ArrayList<Groupe>(surrounders);
    }

    public List<Groupe> getGroupSurroundingGroups(Groupe groupe) {
        HashSet<Groupe> surrounding = new HashSet<Groupe>();
        for (Stone stone : groupe.getStones()) {
            List<Stone> neighbours = goban.getVoisins(stone);
            for (Stone neighbour : neighbours) {
                if (neighbour.getCouleur() == stone.getCouleur()) {
                    neighbours.remove(neighbour);
                }
            }
            surrounding.addAll(getGroupsFromPions(neighbours));
        }

        return new ArrayList<Groupe>(surrounding);
    }

    public boolean bonnesCoords(int line, int column) {
        return goban.bonneCoords(line, column);
    }

    public Goban getGoban() {
        return this.goban;
    }

    public List<Node<PlayMove>> getChildrenMove() {
        return lastMove.getChildren();
    }

    public int getCurrentDepth() {
        return this.lastMove.getDepth();
    }

    public Set<Stone> getFreePlaces() {
        return freePlaces;
    }

    public Tree<PlayMove> getHistory() {
        return history;
    }

    public Node<PlayMove> getLastMove() {
        return lastMove;
    }

    public PionVal getCurrentPlayer() {
        return currentPlayer;
    }

    private void changeCurrentPlayer() {
        if (currentPlayer == PionVal.NOIR) {
            currentPlayer = PionVal.BLANC;
        } else {
            currentPlayer = PionVal.NOIR;
        }
    }
}
