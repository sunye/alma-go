/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
public class FakeGame extends Game {

    private Node<PlayMove> fakeLastMove;
    private int depth;



    public FakeGame(Game game) {
            this.goban = (Goban) game.getGoban().clone();
            this.history = new Tree<PlayMove>(new Node<PlayMove>(game.getCurrentMove()));
            this.depth = game.getCurrentDepth();
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
                    throw new BadPlaceException("This move is a suicide, I cannot let you do that !");
                }
            } else {
                throw new BadPlaceException("Already a stone here");
            }
        } else {
            throw new BadPlaceException("It is actually better to play on the board");
        }
    }

    private void fakeStartMove(PlayMove currentMove) {

        //prepare the group modifications
        currentMove.setGroupes((ArrayList<Groupe>) getCurrentMove().getGroupes().clone());

        Node<PlayMove> newMove = new Node<PlayMove>(currentMove);
        lastMove.addChild(newMove);
        fakeLastMove = newMove;
    }

    private void fakeProcessGroupeTaking(Set<Groupe> ennemies) {
        for (Groupe groupe : ennemies) {
            if (groupe.getLibertes() == 0) {
                // Dans ce cas là, on a bouché la dernière libertée,
                //il faut donc supprimer les pierres du goban, et le groupe du pm.
                fakeRemoveGroupe(groupe);
                this.end = true;
            }
        }
    }

    private void fakeRemoveGroupe(Groupe groupe) {

        for (Stone pion : groupe.getStones()) {
            // Don't forget to register the modification.
            getFakeCurrentMove().addModif(new Modif(pion.getLine(), pion.getColumn(), pion.getCouleur(), PionVal.RIEN));
        }

        Set<Groupe> surroundings = new HashSet<Groupe>();
        for (Stone pion : groupe.getStones()) {
            surroundings.addAll(getSurroundingGroups(pion));
        }
        updateLiberties(surroundings);

        getFakeCurrentMove().getGroupes().remove(groupe);
    }

    protected Set<Groupe> fakeCalculateGroups(Stone last) {
        Groupe lastAdded = new Groupe(last.getCouleur());
        try {
            lastAdded.addStone(last);
        } catch (BadCouleurException e) {
        }

        List<Groupe> groupes = getFakeCurrentMove().getGroupes();

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

    private PlayMove getFakeCurrentMove() {
        return this.fakeLastMove.getData();
    }


    public boolean isInLeaf(){
        return this.lastMove.isLeaf();
    }

    @Override
    public Boolean apply(int numChild){
        Boolean ok = super.apply(numChild);
        ++depth;
        return ok;
    }






}
