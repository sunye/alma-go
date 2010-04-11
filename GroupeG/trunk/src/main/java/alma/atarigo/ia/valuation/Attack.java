/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alma.atarigo.ia.valuation;

import java.util.List;

import alma.atarigo.CellContent;
import alma.atarigo.GobanModel;
import alma.atarigo.Territory;

/**
 *
 * @author gass-mouy
 */
public class Attack extends AbstractValuation implements Valuation{


    public Attack(CellContent content){
    	super("Attack",content);
    }

    public long run(GobanModel goban) {
        
    	//=====================
    	//A DEFINIR
    	
        int libs=0;
        List<Territory> terEnemy = goban.getTerritories(content.getEnemy());
        for(Territory ter : terEnemy){
        	libs += goban.getLibertiesFor(ter).size();
        }
        
        return libs*DOWN_LIMIT;
//        return 1;
    }

    public String toString(){
    	String[] yep = getClass().getName().split("\\.");
    	return yep[yep.length - 1];
    }

}

