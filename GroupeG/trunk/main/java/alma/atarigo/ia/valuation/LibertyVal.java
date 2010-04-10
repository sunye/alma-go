/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alma.atarigo.ia.valuation;

import alma.atarigo.CellContent;
import alma.atarigo.GobanModel;
import alma.atarigo.Territory;
import java.util.List;

/**
 * Evaluation des libertes du goban de jeu
 * @author gassMouy()
 */
public class LibertyVal extends AbstractValuation implements Valuation {

	/**
	 * Nombre minimum de libertes pour un singleton
	 */
	private static final int MIN_LIBERTY_SINGLETON=3;
	
	/**
	 * Nombre minimum de libertes pour un groupe
	 */
	private static final int MIN_LIBERTY_GROUP=5;

    /**
     * Constructeur de la classe avec le content de l'IA
     * @param content Le content de l'IA
     */
    public LibertyVal(CellContent content){
        this.content = content;
        this.name="LibertyVal";
    }
    
    public LibertyVal(){
        this.name="LibertyVal";
    }

    public long run(GobanModel goban) {

    	//comme pour Border,on recupere un coeficient d'erreur
    	//mais cette fois ci sur tous les territoires trouves
    	int coefIA = setCoef(goban,content)
    		,coefEnemy = setCoef(goban,content.getEnemy());

        if(coefIA==1){
        	if(coefEnemy==1){
        		//tous les territoires de l'IA et de son enemi sont valides
        		//une note a mettre?
        		return 0;
        	}
        	//IA ok, enemi non: le goban est bon
        	//multiplication par le coeficient d'erreur
        	return UP_LIMIT*coefEnemy;
        }
        //il y a un coeficient d'erreur sur l'IA
        //en plus l'enemi est ok: note merdik
        if(coefEnemy==1){
        	//multiplication par le coeficient d'erreur
        	return DOWN_LIMIT*coefIA;
        }
        //sinon mini algebre
        return coefEnemy-coefIA;
//        return (libsIA*coefIA - libsEnemy*coefEnemy);
    }

	/**
	 * Incrematation d'un coeficient a chaque territoire non valides
	 * @param goban Le goban de jeu
	 * @param content Le content des territoires
	 * @return Le coeficient incremente
	 */
    private int setCoef(GobanModel goban, CellContent content){
        int coef=1;
        List<Territory> territories = goban.getTerritories(content);
        for(Territory territory : territories){
        	boolean check = check(goban,territory);
            if(!check){
                ++coef;
            }
        }
        return coef;
    }

    public String toString(){
    	String[] yep = getClass().getName().split("\\.");
    	return yep[yep.length - 1];
    }

    /**
     * Algebre checkant un territoire dans le goban en fonction du nombre de ses libertes
     * @param goban Le goban
     * @param territory Le territoire
     * @return Un boolean
     */
    public static boolean check(GobanModel goban,Territory territory){
    	
    	int sizeLibs=goban.getLibertiesFor(territory).size();
    	
    	if(territory.isSingleton()){
    		return (sizeLibs < MIN_LIBERTY_SINGLETON)?false:true;
    	}
    	return (sizeLibs < MIN_LIBERTY_GROUP)?false:true;
    }
    
    //=================================================
    //methodes non utilisees
    //mais peu etre que l'on peut travailler avec!!
    
    /**
     * Obtenir le nombre minimum de libertes pour un territoire d'une certaine taille
     * @param size La taille du territoire entoure par CellContent.Empty
     * @return Le nombre minimum de ses libertes
     */
    private int getMinLibs(int size){
    	return size+4;
    }
    
    /**
     * Obtenir le nombre maximum de libertes pour un territoire d'une certaine taille
     * @param size La taille du territoire entoure par CellContent.Empty
     * @return Le nombre maximums de ses libertes
     */    
    private int getMaxLibs(int size){
    	return getMinLibs(size)+(size-2);
    }
    
    /**
     * Obtenir le nombre possible des libertes d'un territoire d'une certaine taille
     * @param size La taille du territoire entoure par CellContent.Empty
     * @return Le nombre possible de ses libertes
     */
    private int getLibertyCount(int size){
    	return getMaxLibs(size) - getMinLibs(size) + 1;
    }
    
}
