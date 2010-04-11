/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alma.atarigo.ia;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import alma.atarigo.CaptureException;
import alma.atarigo.CellContent;
import alma.atarigo.CellEvent;
import alma.atarigo.CellPosition;
import alma.atarigo.GobanModel;
import alma.atarigo.IProgressMonitor;
import alma.atarigo.RuleViolationException;
import alma.atarigo.rule.Capture;

/**
 *
 * @author gass-mouy
 */
public class AlphaBeta extends AbstractAlgorithm{

    private AlgoTask finder = null;

    public AlphaBeta(){
    }

    public AlphaBeta(GobanModel goban, CellContent content, int height){
        this.goban = goban;
        this.content = content;
        this.height = height;
    }

    /* (non-Javadoc)
     * @see alma.atarigo.ia.Algorithm#run(alma.atarigo.IProgressMonitor)
     */
    public List<CellPosition> run(IProgressMonitor monitor) {
    	try{
    		finder = new AlgoTask();
        	List<CellPosition> result = null;
    		try {
    			finder.setProgressMonitor(monitor);
    			result = finder.get();
    		} catch (InterruptedException e) {
    			Logger.getLogger(getClass().getName()).log(Level.SEVERE,null,e);
    		}
        	finder = null;
        	return result;
    	}finally{
    		if(monitor!=null){
    			monitor.close();
    		}
    	}
    }

    class AlgoTask extends AlgorithmWorker{
    	IProgressMonitor monitor;
    	long alpha = Long.MIN_VALUE;
    	long beta = Long.MAX_VALUE;
    	
    	static final long INF = Long.MAX_VALUE - 10;
    	static final long _INF = Long.MIN_VALUE + 10;
    	
    	private boolean couldContinue(){
    		return !monitor.isCancelled() && !isCancelled();
    	}
    	
        @Override
        protected List<CellPosition> doInBackground(IProgressMonitor monitor){
        	this.monitor = monitor;
        	//Le resultat
        	List<CellPosition> result = new ArrayList<CellPosition>();
        	
        	//La liste des possibilites
        	List<CellPosition> choices = getChildrenFor(goban, content);

            //Le moniteur de progress
        	monitor.setMinimum(1);        	
        	monitor.setMaximum(akira(choices.size(),height));

        	Long best = INF;
        	
        	int size = choices.size();
            for(int i=0; i<size; ++i){
                CellPosition position = choices.get(i);
                long value = _INF;
                boolean forbidden = false;

                try{
                    //on test si on gagne directement
                	Capture.RULE.check(goban,makeEvent(position,content));
                	//on teste la defense immédiate
                	Capture.RULE.check(goban,makeEvent(position,content.getEnemy()));                	
                    GobanModel newState = makeArtificialGoban(goban,position,content);
                    value = alphabeta(newState,1);
                }catch(CaptureException e){
                	value = Long.MAX_VALUE;
                }catch(RuleViolationException e){
                	forbidden = true;
                	value = Long.MIN_VALUE;
                }
                
                
                if(result.isEmpty()){
                	best = value;
                }
                
                if(!forbidden && value>best){
                	best = value;
                	result.clear();
                }
                
                if(!forbidden && value==best){
                	result.add(position);
                }
                
                /**
                 * Coupure alpha             
                 */
                alpha = Math.max(alpha, best);
                
                monitor.nextValue();
            }
        	
        	return result;
        }

        public long alphabeta(GobanModel goban, int depth){
        	if(depth<height && couldContinue()){
        		if(depth%2 == 0){
        			return max(goban,depth);
        		}else{
        			return min(goban,depth);
        		}
        	}
        	
        	monitor.nextValue();
        	return valuation.run(goban);
        }

        public long max(GobanModel state, int depth){

        	//La liste des possibilites
        	long result = _INF;
        	List<CellPosition> choices = getChildrenFor(state, content);
        	int size = choices.size();
        	
            for(int i=0; i<size; ++i){
                long value = _INF;
                boolean forbidden = false;

                CellPosition position = choices.get(i);
                CellEvent evWin = makeEvent(position,content);
                CellEvent evLoose = makeEvent(position,content.getEnemy());
                try{
                    //on test si on gagne directement
                	Capture.RULE.check(goban,evWin);
                	//on teste la defense immédiate
                	Capture.RULE.check(goban,evLoose);                	
                    GobanModel newState = makeArtificialGoban(goban,position,content);
                    value = alphabeta(newState,depth+1);
                }catch(CaptureException e){
                	value = Long.MAX_VALUE;
                }catch(RuleViolationException e){
                	forbidden = true;
                	value = Long.MIN_VALUE;
                }

                if(!forbidden){
                	result = Math.max(result, value);
                }
                
                /**
                 * Coupure beta
                 */
                if(result>=beta){
                	return result;
                }
                
                alpha = Math.max(alpha, result);
                
                monitor.nextValue();
            }

            return result;
        }

        public long min(GobanModel state, int depth){

        	//La liste des possibilites
        	long result = INF;
        	List<CellPosition> choices = getChildrenFor(state, content.getEnemy());
        	int size = choices.size();
        	
            for(int i=0; i<size; ++i){
                long value = INF;
                boolean forbidden = false;

                CellPosition position = choices.get(i);

                CellEvent evWin = makeEvent(position,content.getEnemy());
                CellEvent evLoose = makeEvent(position,content);
                try{
                    //on test si on gagne directement
                	Capture.RULE.check(goban,evWin);
                	//on teste la defense immédiate
                	Capture.RULE.check(goban,evLoose);                	
                    GobanModel newState = makeArtificialGoban(goban,position,content.getEnemy());
                    value = alphabeta(newState,depth+1);
                }catch(CaptureException e){
                	value = Long.MAX_VALUE;
                }catch(RuleViolationException e){
                	forbidden = true;
                	value = Long.MAX_VALUE;
                }

                if(!forbidden){
                	result = Math.min(result, value);
                }

                /**
                 * Coupure alpha
                 */
                if(result<=alpha){
                	return result;
                }
                
                beta = Math.min(beta, result);
                
                monitor.nextValue();
            }

            return result;
        }

        
    }

    @Override
    public void abort() {
        if(finder!=null){
        	finder.cancel();
        	
        	try{
        		Thread.sleep(250);       
        	}catch(Throwable ex){        		
        	}
        	
        	try{
        		finder.kill();
        	}catch(Throwable ex){        		
        	}
        }
    }
    
    public void kill(){
    	if(finder!=null){
    		finder.kill();
    	}
    }


}
