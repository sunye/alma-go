/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.alma.atarigo.analyse;

import fr.alma.atarigo.utils.Game;

/**
 *
 * @author clotildemassot
 */
public class CoupAJouer {

    private Game jeu;
    private int evaluation;

    public CoupAJouer() {
        this.jeu = new Game();
        this.evaluation = 0;
    }

    public CoupAJouer(Game jeu, int evaluation) {
        this.jeu = jeu;
        this.evaluation = evaluation;
    }

    public CoupAJouer(int evaluation) {
        this.evaluation = evaluation;
    }

    public CoupAJouer(Game jeu) {
        this.jeu = jeu;
    }

    public void setEvaluation(int evaluation) {
        this.evaluation = evaluation;
    }

    public void setJeu(Game jeu) {
        this.jeu = jeu;
    }

    public int getEvaluation() {
        return evaluation;
    }

    public Game getJeu() {
        return jeu;
    }


}
