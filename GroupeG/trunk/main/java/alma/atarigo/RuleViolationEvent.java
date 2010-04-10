/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alma.atarigo;

/**
 *
 * @author steg
 */
public interface RuleViolationEvent {

    /**
     * Recupere le nom de la regle
     * @return : le nom de la regle
     */
    public String getRuleName();

    /**
     * Recupere les coordonnees de la cellule
     * @return : la position
     */
    public CellPosition getPosition();

    /**
     * Retourne l'état de la cellule
     * @return : l'état de la cellule
     */
    public CellContent getContent();

}
