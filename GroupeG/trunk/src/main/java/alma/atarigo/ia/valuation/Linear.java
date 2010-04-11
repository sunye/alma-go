/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alma.atarigo.ia.valuation;

import java.util.ArrayList;
import java.util.List;

import alma.atarigo.CellContent;
import alma.atarigo.GobanModel;

/**
 * Valuation utilisee par Intelligent
 * Linear fait une combinaison lineaire de plusieurs fonctions d'evaluations
 * @author gass-mouy
 */
public class Linear extends AbstractValuation implements Valuation {
	
	/**
	 * Une liste de valuations
	 */
	private List<Valuation> valuations = new ArrayList<Valuation>();

	/**
	 * Une liste de coeficients reels
	 */
	private List<Double> coefs = new ArrayList<Double>();

    /**
     * Constructeur de la classe avec le content de l'IA
     * @param content Le content de l'IA
     */
    public Linear(CellContent content){
    	super("Linear",content);    	
    	initValuations(content);
    }

    public long run(GobanModel goban) {
    	
    	//mise a jour du status de jeu
    	updateGameStatus(goban);
    	
    	//le resultat initialise a 0
    	double result=0;
    	double total = 0;
    	
    	//on parcours les fonction d'evaluations utilisees
    	for(int i =0;i<valuations.size();++i){
    		Valuation valuation = valuations.get(i);
    		double coef = coefs.get(i);    	
    		
    		//System.out.println("Valuation: "+valuation.getName());
    		
    		//recuperation de la note emise par la valuation
        	long val=getVal(goban,valuation);
        	
        	//cas d'arret premature: goban sur/sous note
        	if(returnNow(val)){
        		return val;
        	}
        	
        	//on ajoute le resultat dans la variable de retour
        	result += coef*val;
        	total += val;
    	}
    	
    	return (long)(result/Math.max(total, 1));
    }
    
    /**
     * Obtenir la note de l'evaluation
     * @param goban Le goban de jeu
     * @param valuation La valuation
     * @return La note du goban
     */
    private long getVal(GobanModel goban,Valuation valuation){
    	return valuation.run(goban);
    }

    public String toString(){
    	return getName();
    }
    
    /**
     * Cas d'arret premature
     * @param val La note d'une evaluation
     * @return Oui ou Non
     */
    private boolean returnNow(long val){
    	return (val<=DOWN_LIMIT || val>=UP_LIMIT);
    }

    
    /**
     * Creation et stockage des valuations
     * @param content Le content de l'IA
     * @return La liste des valuation remplie
     */
    private void initValuations(CellContent content){
    	valuations.add(new Border(content));
    	coefs.add(0.40);

    	valuations.add(new LibertyVal(content));
    	coefs.add(0.45);

    	valuations.add(new TerritoryVal(content));
    	coefs.add(0.10);

    	valuations.add(new Random(-2000, 2000));
    	coefs.add(0.05);
    
    }

    
}
