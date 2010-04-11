/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alma.atarigo.ia;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import alma.atarigo.CaptureException;
import alma.atarigo.CellContent;
import alma.atarigo.CellEvent;
import alma.atarigo.CellPosition;
import alma.atarigo.GobanModel;
import alma.atarigo.Rule;
import alma.atarigo.RuleViolationException;
import alma.atarigo.ia.valuation.Valuation;
import alma.atarigo.rule.Rules;
import alma.atarigo.rule.Suicide;

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

    /**
     * Test de violation de règle
     * @param goban
     * @throws RuleViolationException
     * @throws CaptureException
     */
    public static void checkRules(final GobanModel goban) throws RuleViolationException,CaptureException{
        for(Rule rule : Rules.IA_RULES){
            rule.check(goban);
        }        
    }
    
    /**
     * Calcul du nombre de noeud à traverser par l'algorithme
     * s'il parcourait tout l'arbre de recherche
     * @param x Le nombre de possibilité initial
     * @param depth La profondeur de l'arbre de recherche
     * @return
     */
    public static long akira(long x, long depth){
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

	/**
	 * Calculer la liste des possibilités de jeu pour un contenu à partir d'un état de jeu
	 * @param model L'état de jeu 
	 * @param content Le contenu
	 * @return une liste de <code> CellPosition </code>
	 */
	public static List<CellPosition> getChildrenFor(GobanModel model,final CellContent content){
		List<CellPosition> result;
		if(content.isKuro()){
			result = model.getPositionsFor(CellContent.Empty,CellContent.ShiroSuicide);
		}else if(content.isShiro()){
			result = model.getPositionsFor(CellContent.Empty,CellContent.KuroSuicide);
		}else{
			return Collections.emptyList();
		}

		List<CellPosition> fakes = new ArrayList<CellPosition>();
		for(CellPosition cell : result){
			CellEvent event = makeEvent(cell,content);
			try{
				Suicide.RULE.check(model,event);
			}catch(RuleViolationException e){	
				fakes.add(cell);
			} catch (CaptureException e) {
			}
		}
		
		result.removeAll(fakes);
		Collections.shuffle(result);
		return result;
	}

	/**
	 * Construire un evenement
	 * @param p
	 * @param c
	 * @return
	 */
	public static CellEvent makeEvent(final CellPosition p,final CellContent c){
		return new CellEvent() {			
			@Override
			public CellPosition getPosition() { return p; }
			
			@Override
			public CellContent getContent() { return c; }
		};
	}
	
    /* (non-Javadoc)
     * @see alma.atarigo.ia.Algorithm#setAll(alma.atarigo.GobanModel, alma.atarigo.CellContent, int)
     */
    public void setAll(GobanModel goban, CellContent content, int height){
        this.goban = goban;
        this.content = content;
        this.height = height;
    }


    /* (non-Javadoc)
     * @see alma.atarigo.ia.Algorithm#setContent(alma.atarigo.CellContent)
     */
    public void setContent(CellContent content){
        this.content = content;
    }

    /* (non-Javadoc)
     * @see alma.atarigo.ia.Algorithm#setDepth(int)
     */
    public void setDepth(int height){
        this.height = height;
    }

    /* (non-Javadoc)
     * @see alma.atarigo.ia.Algorithm#setGoban(alma.atarigo.GobanModel)
     */
    public void setGoban(GobanModel goban){
        this.goban = goban;
    }

    /* (non-Javadoc)
     * @see alma.atarigo.ia.Algorithm#setValuation(alma.atarigo.ia.valuation.Valuation)
     */
    public void setValuation(Valuation valuation){
        this.valuation = valuation;
    }

    /* (non-Javadoc)
     * @see alma.atarigo.ia.Algorithm#getValuation()
     */
    public Valuation getValuation(){
    	return valuation;    
    }
    
}
