/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alma.atarigo.ia;

import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import alma.atarigo.CellContent;
import alma.atarigo.CellPosition;
import alma.atarigo.CaptureException;
import alma.atarigo.GobanModel;
import alma.atarigo.IProgressMonitor;
import alma.atarigo.RuleViolationException;

/**
 *
 * @author gass-mouy
 */
public class MinMax extends AbstractAlgorithm{

    private AlgoTask finder = null;
    private int cancelLimit = 10;

    public MinMax(){
    }

    public MinMax(GobanModel goban, CellContent content, int height){
        this.goban = goban;
        this.content = content;
        this.height = height;
    }

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
    	int progress;
    	
        @Override
        protected List<CellPosition> doInBackground(IProgressMonitor monitor){

        	//La liste des possibilites
        	List<CellPosition> empties = goban.getEmptyCells();
            Collections.shuffle(empties);

            //Le moniteur de progress
        	monitor.setMinimum(0);
        	
//        	double num = factorielle(empties.size());
//        	double denom = factorielle(empties.size() - height);
//        	monitor.setMaximum((long)(num/denom));
        	
        	monitor.setMaximum(akira(empties.size(),height));
            
            return Collections.singletonList(minMax(goban,0,empties,monitor).getSecond());
        }

        public Pair<Long,CellPosition> minMax(GobanModel goban, int depth,List<CellPosition> empties,IProgressMonitor progressMonitor){
            if(depth < height){
                if(depth % 2 == 0){
//                    if(((ArtificialGoban)goban).getValue() < UP_LIMIT)
                        return max(goban,depth,empties,progressMonitor);
                }else{
//                    if(((ArtificialGoban)goban).getValue() > DOWN_LIMIT){
                        return min(goban,depth,empties,progressMonitor);
                    }
                }
            return makePair(valuation.run(goban), null);
        }

        public Pair<Long,CellPosition> min(GobanModel goban, int depth,List<CellPosition> empties,IProgressMonitor progressMonitor){
            int size = empties.size();

            Pair<Long,CellPosition> pair = null;
            
            for(int i=0 ; i<size  && (!progressMonitor.isCancelled() || size-i<=cancelLimit); ++i){

                long value = Long.MAX_VALUE;

                CellPosition position = empties.get(i);
                GobanModel childGoban = makeArtificialGoban(goban,position,content.getEnemy());

                try{
                	checkRules(childGoban);
                	if(progressMonitor.isCancelled()){
                            value = valuation.run(childGoban);
                	}else{
                		value = minMax(childGoban,depth+1,new PartialList<CellPosition>(empties,i),progressMonitor).getFirst();
                	}
                }catch(RuleViolationException e){
                    value = Long.MAX_VALUE;
                }catch(CaptureException e){
                    value = valuation.run(childGoban);
                }

                if(pair==null){
                    pair = makePair(value,position);
                }else if(value < pair.getFirst()){
                    pair.setFirst(value);
                    pair.setSecond(position);
                }

                progressMonitor.setProgress(progress++);
            }

            return pair;
        }

        public Pair<Long,CellPosition> max(GobanModel goban, int depth,List<CellPosition> empties,IProgressMonitor progressMonitor){
            int size = empties.size();
            
            Pair<Long,CellPosition> pair = null;
            
            for(int i=0 ; i<size && (!progressMonitor.isCancelled() || size-i<cancelLimit); ++i){

                long value = Long.MIN_VALUE;

                CellPosition position = empties.get(i);
                GobanModel childGoban = makeArtificialGoban(goban,position,content.getEnemy());

                try{
                	checkRules(childGoban);
                	if(progressMonitor.isCancelled()){
                            value = valuation.run(childGoban);
                	}else{
                		value = minMax(childGoban,depth+1,new PartialList<CellPosition>(empties,i),progressMonitor).getFirst();
                	}
                }catch(RuleViolationException e){
                    value = Long.MIN_VALUE;
                }catch(CaptureException e){
                    value = valuation.run(childGoban);
                }

                if(pair==null){
                    pair = makePair(value,position);
                }else if(value > pair.getFirst()){
                    pair.setFirst(value);
                    pair.setSecond(position);
                }
                
                progressMonitor.setProgress(progress++);
            }

            return pair;
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
