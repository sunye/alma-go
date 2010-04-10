/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alma.atarigo.ia.valuation;

import alma.atarigo.CellContent;
import alma.atarigo.GobanModel;

/**
 *
 * @author gass-mouy
 */
public interface Valuation {

    /**
     * Evaluation de l'état de jeu
     * @param goban : l'état de jeu
     * @return : l'entier correspondant à l'évaluation
     */
    public long run(GobanModel goban);
    
    /**
     * Met a jour le content
     * @param content Le content
     */
    public void setCellContent(CellContent content);
    
    /**
     * Obtenir le nom de la fonction d'evaluation
     * @return Le nom de l'evaluation
     */
    public String getName();
}
