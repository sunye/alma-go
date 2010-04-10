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
     * @throws CaptureException : exception levée en cas de fin de partie
     */
    public void check(GobanModel goban,CellEvent event) throws RuleViolationException,CaptureException;

    /**
     * Teste la règle sur le goban
     * @param goban : le plateau de jeu entier
     * @throws RuleViolationException : exception levee en cas d'échec
     * @throws CaptureException : exception levée en cas de fin de partie
     */
    public void check(GobanModel goban) throws RuleViolationException,CaptureException;
    

}
