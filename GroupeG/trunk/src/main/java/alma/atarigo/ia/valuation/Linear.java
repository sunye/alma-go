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
        this.content = content;
        valuations = setValuations(content);
        coefs = setCoefs(extendCoefs());
        this.name="Linear";
    }

    public long run(GobanModel goban) {
    	
    	//mise a jour du status de jeu
    	GAME_STATUS = setGameStatus(goban);
    	
    	//le resultat initialise a 0
    	long result=0;

    	//on parcours les fonction d'evaluations utilisees
    	for(Valuation valuation : valuations){
    		
    		//System.out.println("Valuation: "+valuation.getName());
    		
    		//recuperation de la note emise par la valuation
        	long val=getVal(goban,valuation);
        	
        	//cas d'arret premature: goban sur/sous note
        	if(returnNow(val)){
        		return val;
        	}
        	
        	//on ajoute le resultat dans la variable de retour
        	result += val;
    	}
    	
    	return result;
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
    	String[] yep = getClass().getName().split("\\.");
    	return yep[yep.length - 1];
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
     * Mise a jour des coeficients utilises pour ponderer les valuations
     * @param coefs Une liste vide
     * @return La liste pleine
     */
    private List<Double> setCoefs(List<Double> coefs){
        coefs.add(3.);coefs.add(2.5);coefs.add(2.);
        return coefs;
    }
    
    /**
     * Extention de la liste des coeficients s'il n'y en a pas assez
     * @return La meme liste mise a jour
     */
    private List<Double> extendCoefs(){
        while(coefs.size() < valuations.size()){
            coefs.add(1.);
        }
        return coefs;
    }
    
    /**
     * Creation et stockage des valuations
     * @param content Le content de l'IA
     * @return La liste des valuation remplie
     */
    private List<Valuation> setValuations(CellContent content){
    	valuations.add(new Border(content));
    	valuations.add(new LibertyVal(content));
    	valuations.add(new TerritoryVal(content));
    	
    	return valuations;
    }

    
}
