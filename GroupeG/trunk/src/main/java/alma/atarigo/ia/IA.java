/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alma.atarigo.ia;

import java.util.List;
import java.util.Properties;

import alma.atarigo.CellContent;
import alma.atarigo.CellPosition;
import alma.atarigo.Controller;
import alma.atarigo.GobanModel;
import alma.atarigo.IProgressMonitor;
import alma.atarigo.Level;
import alma.atarigo.ia.valuation.Intelligent;
import alma.atarigo.ia.valuation.Valuation;

/**
 *
 * @author gass-mouy
 */
public class IA {

    private Controller game;
    private Level level = null;
    private Algorithm algorithm = null;

    public IA(){    	
    }
    
    public IA(Controller game){
        this.game = game;
        
        algorithm = new MinMax();
        //mise a jour de la fonction d'eval
        algorithm.setValuation(
        			new Intelligent(
        				game.isKuroTurn()?CellContent.Kuro:CellContent.Shiro)
        			);
    }

    public void setController(Controller game){
    	this.game = game;
    }
    
    public CellPosition run(IProgressMonitor progressMonitor) {
    	List<CellPosition> choices = runAll(progressMonitor);
    	if(choices!=null && !choices.isEmpty()){
    		return choices.get((int) (Math.random()*choices.size()));
    	}
    	return null;
    }

    public List<CellPosition> runAll(IProgressMonitor progressMonitor) {
        Level level = this.level!=null?this.level:game.getLevel();
        GobanModel goban = game.getGoban();
        CellContent playing = game.isKuroTurn()?CellContent.Kuro:CellContent.Shiro;
        int depth = level.getAnalyzeDepth();
        algorithm.setAll(goban, playing,depth);
        return algorithm.run(progressMonitor);
    }
    
    /**
     *@return La fonction d'evaluation utilise par l'IA 
     */
    public Valuation getValuation(){
    	return algorithm.getValuation();
    }
    
    /**
     * 
     * @param valuation La nouvelle fonction d'evaluation que va utiliser l'IA
     */
    public void setValuation(Valuation valuation){
    	algorithm.setValuation(valuation);
    }
    
    /**
     * 
     * @param level  Modifier le niveau de reflexion de l'ia
     */
    public void setLevel(Level level){
    	this.level = level;
    }
    
    /**
     * 
     * @return Le niveau de reflexion de l'IA
     */
    public Level getLevel(){
    	return level;
    }
    
    /**
     * Changer l'algorithm a utiliser par l'ia
     * @param algorithm Le nouvelle algo a utilise
     */
    public void setAlgorithm(Algorithm algorithm){
    	this.algorithm = algorithm;
    }
    
    public Algorithm getAlgorithm(){
    	return algorithm;
    }

    public void saveInto(Properties metadata){    	
    	if(algorithm instanceof MinMax){
    		metadata.setProperty("algorithm", "alma.atarigo.ia.MinMax");
    	}
    	else{
    		metadata.setProperty("algorithm", "alma.atarigo.ia.AlphaBeta");
    	}    	
    	metadata.setProperty("level", level.name());    	
    	metadata.setProperty("valuation", "alma.atarigo.ia.valuation.Intelligent");
    }
    
    public static IA loadFrom(Properties metadata,CellContent content) throws Throwable{
    	IA ia = new IA();
    	Algorithm algo = 
    		(Algorithm)ClassLoader
    		.getSystemClassLoader()
    		.loadClass(metadata.getProperty("algorithm"))
    		.newInstance();
    	Valuation valuation = 
    		(Valuation)ClassLoader
    		.getSystemClassLoader()
    		.loadClass(metadata.getProperty("valuation"))
    		.getConstructor(CellContent.class)
    		.newInstance(content);
    	Level level = Level.valueOf(metadata.getProperty("level"));
    	
    	algo.setValuation(valuation);
    	ia.setAlgorithm(algo);
    	ia.setLevel(level);
    	
    	return ia;
    }
    
}
