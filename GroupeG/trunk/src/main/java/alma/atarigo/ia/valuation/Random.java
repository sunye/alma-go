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
public class Random extends AbstractValuation implements Valuation {

    private int lower;
    private int upper;

    public Random(CellContent content){
    	this(Integer.MIN_VALUE,Integer.MAX_VALUE);
        this.name="Random";
    }
    
    public Random(int lower, int upper){
    	super("Random",null);
        this.lower = lower;
        this.upper = upper;
    }

    public long run(GobanModel goban) {
        return randomInt(lower,upper);
    }

    public int randomInt(int lower,int upper){
        if(lower==upper){
            return lower;
        }
        return (int) (lower-1 + Math.random() * (upper - lower + 1));
    }

}
