/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.alma.atarigo.utils;

import fr.alma.atarigo.utils.exceptions.BadCouleurException;
import fr.alma.atarigo.utils.exceptions.BadPlaceException;
import fr.alma.atarigo.utils.tree.Node;
import fr.alma.atarigo.utils.tree.Tree;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author judu
 */
public class Game {

    Goban goban;
    // History of the game : contains all the moves since the beginning.
    private Tree<PlayMove> history;
    // Last move played (faster backtracking).
    private Node<PlayMove> lastMove;

    public Game() {
        this.goban = new Goban();

        history = new Tree<PlayMove>();
        lastMove = new Node<PlayMove>(new PlayMove());
        history.setRootElement(lastMove);
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
                    /*TODO: check stones taken + add PlayMove Node;
                     * + Find how to manage group history (Maybe in PlayMove) spacy, but faster.
                     */

                    Pion current = new Pion(color, line, column);

                    Modif modif = new Modif(line, column, goban.getCase(line, column), color);
                    PlayMove currentMove = new PlayMove();
                    currentMove.addModif(modif);

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

    private boolean isSuicide(int line, int column, PionVal pionVal) {
        //TODO: Check if the stone fills the last liberty of its group (=> suicide), except if it also fills the last liberty of a ennemy group.
        if(goban.libertesPion(line, column) == 0){
            // No liberty ? Let's check if we are killing a ennemy group.
            Set<Groupe> groupes = getSurroundingGroups(new Pion(pionVal, line, column));
            for(Groupe gr:groupes){
                if(gr.getCouleur() != pionVal){
                    // If ennemy, check if it has one liberty left
                    if (gr.getLibertes() == 1){
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

    private HashSet<Groupe> getGroupsFromPions(List<Pion> pions) {
        HashSet<Groupe> groups = new HashSet<Groupe>(4);
        for (Pion pi : pions) {
            groups.add(getCurrentMove().getGroupeContaining(pi));
        }
        return groups;
    }

    private HashSet<Groupe> getSurroundingGroups(Pion pion) {
        // Get the <4 neighbours of the current stone.
        List<Pion> pions = goban.getVoisins(pion);

        return getGroupsFromPions(pions);
    }

    /**
     * Calculates the new groups, after putting the last stone, then returns
     * the ennemy's groups surrounding the stone. It is not really
     * KISS, but useful, and a little faster than re-calculate the groups.
     * @param last The last stone played
     */
    private Set<Groupe> calculateGroups(Pion last) {
        Groupe lastAdded = new Groupe(last.getCouleur());
        try {
            lastAdded.addPion(last);
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
    private Set<Pion> getGroupLiberties(Groupe groupe) {
        //TODO:Create Set of the free places surrounding the group.

        HashSet<Pion> libertes = new HashSet<Pion>(groupe.size() * 2 + 2);

        for (Pion pion : groupe.getPions()) {
            List<Pion> libz = goban.getLibertes(pion);
            libertes.addAll(libz);
        }
        return libertes;
    }

    private void processGroupeTaking(Set<Groupe> ennemies) {
        for (Groupe groupe : ennemies) {
            if (groupe.getLibertes() == 0) {
                // Dans ce cas là, on a bouché la dernière libertée,
                //il faut donc supprimer les pierres du goban, et le groupe du pm.
                removeGroupe(groupe);
            }
        }
    }

    private void removeGroupe(Groupe groupe) {

        for (Pion pion : groupe.getPions()) {
            // Don't forget to register the modification.
            getCurrentMove().addModif(new Modif(pion.getLigne(), pion.getColonne(), pion.getCouleur(), PionVal.RIEN));
            goban.setCase(pion.getLigne(), pion.getColonne(), PionVal.RIEN);
        }

        lastMove.getData().getGroupes().remove(groupe);
    }

    private void calculateLiberties(Pion current) {
        Set<Groupe> surroundings = getSurroundingGroups(current);
        for (Groupe groupe : surroundings) {
            if (groupe.getCouleur() != current.getCouleur()) {
                groupe.setLibertes(getGroupLiberties(groupe).size());
            }
        }
    }

    private void updateLiberties(Set<Groupe> ennemies) {
        for (Groupe groupe : ennemies) {
            groupe.setLibertes(getGroupLiberties(groupe).size());
        }
    }

    private PlayMove getCurrentMove(){
        return lastMove.getData();
    }

    public Pion getPion(int line, int column) throws BadPlaceException {
        if(goban.bonneCoords(line, column)){
            return new Pion(goban.getCase(line, column),line,column);
        } else {
            throw new BadPlaceException("Coordinates out of the board");
        }
    }
}
