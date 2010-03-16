/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alma.atarigo;

/**
 *
 * @author steg
 */
public interface Rule {

    /**
     * Teste la règle sur l'évenement effectué sur la cellule
     * @param goban : le plateau de jeu entier
     * @param event : la cellule en question
     * @throws RuleViolationException : exception levee en cas d'échec
     * @throws EndOfGameException : exception levée en cas de fin de partie
     */
    public void check(Goban goban,CellEvent event) throws RuleViolationException,EndOfGameException;

}
