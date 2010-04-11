/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
    private PionVal currentPlayer;
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

        } catch (BadPlaceException ex) {
            Logger.getAnonymousLogger().log(Level.WARNING, ex.getMessage());
            return false;
        }

        freePlaces.remove(new Stone(PionVal.RIEN, line, column));
        return true;
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

    public boolean isSuicide(int line, int column, PionVal pionVal) {
        //TODO : correct this function
        if (goban.libertesPion(new Stone(pionVal, line, column)) == 0) {
            // No liberty ? Let's check if we are killing a ennemy group.
            Set<Groupe> groupes = getSurroundingGroups(new Stone(pionVal, line, column));
            int i = 0;
            for (Groupe gr : groupes) {
                ++i;


                if (gr.getCouleur() != pionVal) {
                    // If ennemy, check if it has one liberty left
                    if (gr.getLibertes() == 1) {

                        return false;
                    }
                } else if (gr.getLibertes() > 1) {
                    // If one of the surrounding groups has more than 1 liberty left, it is no suicide.
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    private void startMove(PlayMove currentMove) {

        //prepare the group modifications
        currentMove.setGroupes((ArrayList<Groupe>) getCurrentMove().getGroupes().clone());

        Node<PlayMove> newMove = new Node<PlayMove>(currentMove);
        lastMove.addChild(newMove);
        lastMove = newMove;
    }

    public HashSet<Groupe> getGroupsFromPions(List<Stone> pions) {
        HashSet<Groupe> groups = new HashSet<Groupe>(4);
        for (Stone pi : pions) {
            Groupe containing = getCurrentMove().getGroupeContaining(pi);
            groups.add(containing);
        }
        return groups;
    }

    public HashSet<Groupe> getSurroundingGroups(Stone pion) {
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

        List<Groupe> groupes = getCurrentMove().getGroupes();

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
        lastAdded.setLibertes(getGroupLiberties(lastAdded).size());
        return others;
    }

    /**
     * Returns all the empty places surrounding a group.
     * @param groupe
     * @return
     */
    protected Set<Stone> getGroupLiberties(Groupe groupe) {
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
            if (groupe.getLibertes() == 0) {
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
        }

        Set<Groupe> surroundings = new HashSet<Groupe>();
        for (Stone pion : groupe.getStones()) {
            surroundings.addAll(getSurroundingGroups(pion));
        }
        updateLiberties(surroundings);

        getCurrentMove().getGroupes().remove(groupe);
    }

    /**
     * Calculate the liberties of each Groupe surrounding the <em>current</em> Stone
     * @param current
     */
    private void calculateLiberties(Stone current) {
        Set<Groupe> surroundings = getSurroundingGroups(current);
        for (Groupe groupe : surroundings) {
            if (groupe.getCouleur() != current.getCouleur()) {
                groupe.setLibertes(getGroupLiberties(groupe).size());
            }
        }
    }

    /**
     * Calculates and Updates the liberties of each group of the Set.
     * @param ennemies
     */
    protected void updateLiberties(Set<Groupe> ennemies) {
        for (Groupe groupe : ennemies) {
            groupe.setLibertes(getGroupLiberties(groupe).size());
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

            for(Modif mod :lastPm.getDiff()){
                if(mod.getBefore() == PionVal.RIEN){
                    this.freePlaces.add(new Stone(PionVal.RIEN,mod.getLine(),mod.getColumn()));
                } else {
                    this.freePlaces.remove(new Stone(PionVal.RIEN,mod.getLine(),mod.getColumn()));
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
            for(Modif mod : newPM.getDiff()){
                if(mod.getAfter() == PionVal.RIEN){
                    this.freePlaces.add(new Stone(PionVal.RIEN,mod.getLine(),mod.getColumn()));
                } else {
                    this.freePlaces.remove(new Stone(PionVal.RIEN,mod.getLine(),mod.getColumn()));
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

    /**
     * Wrapper for Goban <em>libertesPion</em> method.
     * @param pion
     * @return
     */
    public int nbLibPion(Stone pion) {
        return goban.libertesPion(pion);
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

    public boolean isFree(int line, int column) {
        return freePlaces.contains(new Stone(PionVal.RIEN, line, column));
    }

    private void changeCurrentPlayer() {
        if (currentPlayer == PionVal.NOIR) {
            currentPlayer = PionVal.BLANC;
        } else {
            currentPlayer = PionVal.NOIR;
        }
    }
}
