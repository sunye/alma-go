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
public class AlphaBeta extends AbstractAlgorithm{

    private AlgoTask finder = null;

    public AlphaBeta(){
    }

    public AlphaBeta(GobanModel goban, CellContent content, int height){
    	setAll(goban, content, height);
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
    	
    	private boolean couldContinue(){
    		return !monitor.isCancelled() && !isCancelled();
    	}
    	
        @Override
        protected List<CellPosition> doInBackground(IProgressMonitor monitor){
        	this.monitor = monitor;
        	return Collections.singletonList(launch(goban));
        }

        static final int MAX_TYPE = 1;
        static final int MIN_TYPE = 2;
        
        public CellPosition launch(GobanModel state){
        	CellPosition result = null;
        	long alpha = Long.MIN_VALUE;
        	long beta = Long.MAX_VALUE;
        	long val = Long.MIN_VALUE;
        	
        	List<CellPosition> children = getChildrenFor(state, content);
        	
        	monitor.setMinimum(0);
        	monitor.setMaximum(akira(children.size(),height));
        	
    		for(CellPosition pos : children){    			
    			if(immediateAction(state,pos,content)){
    				return pos;
    			}
    			
    			GobanModel childState = makeArtificialGoban(state, pos, content);
    			long note = alphabeta(childState,alpha,beta,MIN_TYPE,1);
    			
    			if(val<=note){
    				val = note;
    				result = pos;
    			}
    			
    			/* coupure beta */
    			if(val>=beta && result!=null){
    				return result;
    			}
    			alpha = Math.max(val, alpha);
    		}
        	return result;
        }
        
        public Long alphabeta(GobanModel state,long alpha,long beta,int nodetype,int depth){
        	Long val = null;
        	
        	if(monitor!=null){
        		monitor.nextValue();
        	}
        	
        	if(depth>=height || !couldContinue()){    		
        		val = valuation.run(state);
        	}
        	else {
        		switch(nodetype){
        		case MAX_TYPE:{
        			val = Long.MIN_VALUE;
        			for(CellPosition pos : getChildrenFor(state, content)){
        				if(immediateAction(state,pos,content)){
        					val = Long.MAX_VALUE;
        				}
        				else{
        					GobanModel childState = makeArtificialGoban(state, pos, content);
        					val = Math.max(val, alphabeta(childState,alpha,beta,MIN_TYPE,depth+1));
        				}
        				/* coupure beta */
        				if(val>=beta){
        					return val;
        				}
        				alpha = Math.max(val, alpha);
        			}
        		}break;
        		case MIN_TYPE:{
        			val = Long.MAX_VALUE;
        			for(CellPosition pos : getChildrenFor(state, content.getEnemy())){
        				if(immediateAction(state,pos,content.getEnemy())){
        					val = Long.MIN_VALUE;
        				}
        				else{
            				GobanModel childState = makeArtificialGoban(state, pos, content.getEnemy());
            				val = Math.min(val, alphabeta(childState,alpha,beta,MAX_TYPE,depth+1));
        				}
        				/* coupure alpha */
        				if(val<=alpha){
        					return val;
        				}        				
        				beta = Math.min(val, beta);
        			}
        		}break;
        		}
        	}
        	
        	return val;
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
