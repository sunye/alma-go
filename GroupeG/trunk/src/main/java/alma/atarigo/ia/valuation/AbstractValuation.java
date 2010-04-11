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
/**
 * @author steg
 *
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
	private Status gameStatus;
	
	protected AbstractValuation(String name,CellContent content){
		this.name = name;
		this.content = content;
	}
	
	protected void updateGameStatus(GobanModel goban){
		float status =
			(float)
				(Math.pow(goban.getSize(),2.) 
					/ goban.getPositionsFor(content,content.getEnemy()).size());
		
		if(status > 3){
			gameStatus = Status.Start;
		}
		if(status > 1.5 && status <=3){
			gameStatus = Status.Middle;
		}
		if(status <= 1.5){
			gameStatus = Status.End;
		}
		
	}
	
	/**
	 * 
	 * @return Le status du jeu
	 */
	public Status getGameStatus(){
		return gameStatus;
	}
	
	/* (non-Javadoc)
	 * @see alma.atarigo.ia.valuation.Valuation#getName()
	 */
	public String getName(){
		return name;
	}

	public String toString(){
		return name;
	}
}

