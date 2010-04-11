/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alma.atarigo.ia.valuation;

import alma.atarigo.CellContent;
import alma.atarigo.GobanModel;

/**
 * Valuation principale utilisant une valuation interne cachee
 * @author gassMouy()
 */
public class Intelligent extends AbstractValuation implements Valuation {

	/**
	 * Une fonction intermediaire pour cacher l'implementation: une linearite d'evaluations
	 */
    private Linear linear = null;

    /**
     * Constructeur de la classe avec le content de l'IA
     * @param content Le content de l'IA
     */
    public Intelligent(CellContent content){
    	super("Intelligent",content);
        linear = new Linear(content);
    }    

    public long run(GobanModel goban){
    	return linear.run(goban);
    }
    
}
