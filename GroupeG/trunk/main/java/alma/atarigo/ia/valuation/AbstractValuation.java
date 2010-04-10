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
public abstract class AbstractValuation implements Valuation {

    protected CellContent content;
    protected static final int DOWN_LIMIT = -1000;
    protected static final int UP_LIMIT = 1000;
    protected String name;
    
    public void setCellContent(CellContent content){
    	this.content = content;
    }
    
	/**
	 * Recuperation de la situation de jeu selon un mini algebre:
	 * Diviser la taille du goban par le nombre de pions joues
	 */
	protected static Status GAME_STATUS;
	
	protected Status setGameStatus(GobanModel goban){
		float status =
			(float)
				(Math.pow(goban.getSize(),2.) 
					/ goban.getPositionsFor(content,content.getEnemy()).size());
		
		if(status > 3){
			return Status.Start;
		}
		if(status > 1.5 && status <=3){
			return Status.Middle;
		}
		if(status <= 1.5){
			return Status.End;
		}
		return null;
	}
	
	public String getName(){
		return name;
	}

}
