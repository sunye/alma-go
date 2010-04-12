/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alma.atarigo.ia;

import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import alma.atarigo.CaptureException;
import alma.atarigo.CellContent;
import alma.atarigo.CellEvent;
import alma.atarigo.CellPosition;
import alma.atarigo.GobanModel;
import alma.atarigo.IProgressMonitor;
import alma.atarigo.rule.Capture;

/**
 *
 * @author gass-mouy
 */
public class MinMax extends AbstractAlgorithm{

    private AlgoTask finder = null;

    public MinMax(){
    }

    public MinMax(GobanModel goban, CellContent content, int height){
        this.goban = goban;
        this.content = content;
        this.height = height;
    }

    /* (non-Javadoc)
     * @see alma.atarigo.ia.Algorithm#run(alma.atarigo.IProgressMonitor)
     */
    public List<CellPosition> run(IProgressMonitor monitor) {
    	try{
//            System.out.println("//================================================");
//            System.out.println("//================================================");
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
    	
    	private boolean couldContinue(){
    		return !monitor.isCancelled() && !isCancelled();
    	}
    	
        @Override
        protected List<CellPosition> doInBackground(IProgressMonitor monitor){
        	return Collections.singletonList(depthZero(goban,monitor));
        }

        public CellPosition depthZero(GobanModel state,IProgressMonitor monitor){
        	this.monitor = monitor;
        	        	
        	CellPosition result = null;
        	long best = Long.MIN_VALUE;
        	
        	//La liste des possibilites
        	List<CellPosition> choices = getChildrenFor(state, content);
        	int size = choices.size();
        	
        	monitor.setMinimum(1);
        	monitor.setMaximum(akira(choices.size(),height));
        	
            for(int i=0; i<size; ++i){
                long value = Long.MIN_VALUE;
                CellPosition position = choices.get(i);
            	
                if(immediateAction(state,position,content)){
                	return position;
            	}
                else{
                    GobanModel newState = makeArtificialGoban(goban,position,content);
                    value = minmax(newState,1);
                }
                
                if(value>best || result==null){
                	result = position;
                	best = value;
                }
            }

            return result;
        }
        
        public long minmax(GobanModel goban, int depth){
        	monitor.nextValue();
        	if(depth<height && couldContinue()){
        		if(depth % 2 == 0){
        			return max(goban,depth);
        		}else{
        			return min(goban,depth);
        		}
        	}        	
        	return valuation.run(goban);
        }

        public long max(GobanModel state, int depth){

        	//La liste des possibilites
        	long result = Long.MIN_VALUE;
        	List<CellPosition> choices = getChildrenFor(state, content);
        	int size = choices.size();
        	
            for(int i=0; i<size; ++i){
                long value = Long.MIN_VALUE;
                CellPosition position = choices.get(i);
            	
                if(immediateAction(state,position,content)){
                	return Long.MAX_VALUE;
            	}
                else{
                    GobanModel newState = makeArtificialGoban(goban,position,content);
                    value = minmax(newState,depth+1);
                }
                
                result = Math.max(result, value);                
            }

            return result;
        }

        public long min(GobanModel state, int depth){

        	//La liste des possibilites
        	long result = Long.MAX_VALUE;
        	List<CellPosition> choices = getChildrenFor(state, content.getEnemy());
        	int size = choices.size();
        	
            for(int i=0; i<size; ++i){
                long value = Long.MIN_VALUE;
                CellPosition position = choices.get(i);
            	
                if(immediateAction(state,position,content)){
                	return Long.MIN_VALUE;
            	}
                else{
                    GobanModel newState = makeArtificialGoban(goban,position,content);
                    value = minmax(newState,depth+1);
                }
                
                result = Math.min(result, value);                
            }

            return result;
        }

        public boolean immediateAction(GobanModel state,CellPosition p,CellContent c){
			//on teste la attaque immediate ou la defense immediate
			boolean imAttack = false,imDefense = false;
			
			CellEvent evt = makeEvent(p,content);
			try{
				Capture.RULE.check(state,evt);
			}catch(CaptureException ex){
				imAttack = true;
			}catch(Throwable ex){    				
			}
			
			evt = makeEvent(p,content.getEnemy());
			try{
				Capture.RULE.check(state,evt);
			}catch(CaptureException ex){
				imDefense = true;
			}catch(Throwable ex){    				
			}
			
			return imAttack || imDefense;
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
