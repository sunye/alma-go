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
 * @author gassMouy()
 */
public class TerritoryVal extends AbstractValuation implements Valuation {
	
	/**
	 * Nombre de territoires minimum
	 */
	private static final int MIN_TERRITORY=4;
	/**
	 * Nombre de territoires maximum
	 */
	private static final int MAX_TERRITORY=7;

    /**
     * Constructeur de la classe avec le content de l'IA
     * @param content Le content de l'IA
     */
    public TerritoryVal(CellContent content){
    	super("TerritoryVal",content);
    }
    
    public TerritoryVal(){
    	super("TerritoryVal",null);
    }

    public long run(GobanModel goban) {
    	updateGameStatus(goban);
    	
    	//recuperation des territoires
        List<Territory> ia = goban.getTerritories(content)
        				,enemy = goban.getTerritories(content.getEnemy());

        //stockage des tailles des listes
        int sizeIA=ia.size()
        	,sizeEnemy=enemy.size();

        //booleens de verification des attributs static final
        boolean iaHasMin=(sizeIA>=MIN_TERRITORY)?true:false
    			,iaHasMax=(sizeIA>=MAX_TERRITORY)?true:false
    			,enemyHasMin=(sizeEnemy>=MIN_TERRITORY)?true:false
    	    	,enemyHasMax=(sizeEnemy>=MAX_TERRITORY)?true:false;
    		
        //si l'IA n'a pas MIN territoires
   		if(!iaHasMin){
   			if(!enemyHasMin){
   				//pareil pour l'enemi
   				//on definit une algebre
   				return algebre(sizeIA,sizeEnemy);
   			}
   			//si l'enemi a deja tous ses territoires
   			if(enemyHasMax){
   				//et qu'en plus on est au debut de jeu
   				if(getGameStatus().isStart()){
   					//tres mauvaise note
   					return DOWN_LIMIT*sizeEnemy*2;
   				}
   				//mauvaise note quand meme
   				return DOWN_LIMIT*sizeEnemy;
   			}
   			//sinon l'enemi est entre MIN et MAX et l'IA sous le MIN
   			return DOWN_LIMIT/2;
   		}
   		//ici, le MIN est respecte pour l'IA
        //si le max pour l'IA est respecte
   		if(iaHasMax){
   			if(!enemyHasMax){
   				//MAX pour l'IA, pas pour l'enemi et debut de jeu
   	   			if(getGameStatus().isStart()){
   	   				//tres bonnee note
   	   				return UP_LIMIT*sizeIA*2;
   	   			}
   	   			//bonne note quand meme
   	   			return UP_LIMIT*sizeIA;
   			}
   			//sinon l'enemi est entre le MIN et le MAX et l'IA a le MAX
   			return UP_LIMIT/2;
   		}
        //sinon on est entre le MIN et le MAX pour l'IA
   	   	if(!enemyHasMin){
   			return UP_LIMIT/2;
   		}
   	   	if(enemyHasMax){
   	   		return DOWN_LIMIT/2;
   		}
   	   	//l'IA et son enemi sont entre MIN et MAX: algebre
   	   	return algebre(sizeIA,sizeEnemy);
    }
    
    /**
	 * Algebre pour definir une note via les tailles des territoires de chaque joueur
     * @param sizeIA Le nombre de territoires de l'IA
     * @param sizeEnemy Le nombre de territoires de l'enemi
     * @return Une note du jeu
     */
    private long algebre(int sizeIA,int sizeEnemy){
		return sizeIA - sizeEnemy;
    }
    

}
