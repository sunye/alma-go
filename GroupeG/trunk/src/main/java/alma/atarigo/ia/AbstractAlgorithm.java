/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alma.atarigo.ia;

import alma.atarigo.Cell;
import java.util.List;

import alma.atarigo.ia.valuation.Valuation;
import alma.atarigo.rule.Rules;
import alma.atarigo.CellContent;
import alma.atarigo.CellEvent;
import alma.atarigo.CellPosition;
import alma.atarigo.CaptureException;
import alma.atarigo.GobanModel;
import alma.atarigo.Rule;
import alma.atarigo.RuleViolationException;

/**
 *
 * @author gass-mouy
 */
public abstract class AbstractAlgorithm implements Algorithm{
    
    protected GobanModel goban;
    protected int height;
    protected CellContent content;
    protected Valuation valuation;
    protected int UP_LIMIT = 10000;
    protected int DOWN_LIMIT = -10000;


    /**
     * Creation d'un goban artificiel en mémoire
     * @param parent : lien vers son parent
     * @param position : la position simulée
     * @param content : le content du simuleur
     * @return : une nouvelle instance en mémoire
     */
    static public ArtificialGoban makeArtificialGoban(GobanModel parent
                                                    ,CellPosition position
                                                    ,CellContent content){

        return new ArtificialGoban(parent,position,content);
    }

    /**
     * Creation d'une paire <valeur,position>
     * @param value : la valeur
     * @param position : la position
     * @return : la paire
     */
    static public Pair<Long,CellPosition> makePair(long value, CellPosition position){
        return new Pair<Long,CellPosition>(value,position);
    }

    public static void checkRules(final GobanModel goban) throws RuleViolationException,CaptureException{
        for(Rule rule : Rules.IA_RULES){
            rule.check(goban);
        }        
    }
    
    public static double factorielle(long n){
    	double res = 1;
    	for(long i=2;i<=n;++i){
    		res = res*i;
    	}
    	return res;
    }

    public long akira(long x, long depth){
        long akira=1;
        if(depth!=0){
            long nawak=1;
            for(long i=1 ; i<=depth ; ++i){
                for(long j=i ; j<=depth ; ++j){
                    nawak *= (x-j+i);
                }
                akira += nawak;
                nawak=1;
            }
        }
        return akira;
    }
    
    public static long analizeLength(int size,int depth){
    	long res = size;
    	for(int i = 0;i<depth-1;++i){
    		res *= (size - i);
    	}
    	return res;
    }
    
    public void setAll(GobanModel goban, CellContent content, int height){
        this.goban = goban;
        this.content = content;
        this.height = height;
    }


    public void setContent(CellContent content){
        this.content = content;
    }

    public void setDepth(int height){
        this.height = height;
    }

    public void setGoban(GobanModel goban){
        this.goban = goban;
    }

    public void setValuation(Valuation valuation){
        this.valuation = valuation;
    }

    public Valuation getValuation(){
    	return valuation;    
    }
    
}
