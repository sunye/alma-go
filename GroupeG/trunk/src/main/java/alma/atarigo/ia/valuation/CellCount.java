/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alma.atarigo.ia.valuation;

import alma.atarigo.CellContent;
import alma.atarigo.GobanModel;

/**
 *
 * @author gassMouy()
 */
public class CellCount extends AbstractValuation implements Valuation {

    public CellCount(CellContent content){
    	super("CellCount",content);
    }

    public long run(GobanModel goban) {

        int countIA = goban.getPositionsFor(content).size();
        int countEnemy = goban.getPositionsFor(content).size();

        return countIA - countEnemy;

    }

}
