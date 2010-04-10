/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alma.atarigo.ia;

import java.util.ArrayList;
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
import alma.atarigo.RuleViolationException;
import alma.atarigo.model.CellPositionImpl;
import alma.atarigo.rule.Capture;
import alma.atarigo.rule.Suicide;

/**
 *
 * @author gass-mouy
 */
public class AlphaBeta extends AbstractAlgorithm implements Algorithm {
    
	private AlgoTask finder = null;
	private final static long R_INF = Integer.MAX_VALUE;
	private final static long L_INF = Integer.MIN_VALUE;
	
	/* (non-Javadoc)
	 * @see alma.atarigo.ia.Algorithm#abort()
	 */
	@Override
	public void abort() {
		if(finder!=null){
			finder.cancel();			
		}
	}

	@Override
	public List<CellPosition> run(IProgressMonitor progressMonitor) {
		if(finder==null){
			finder = new AlgoTask();
			List<CellPosition> result = null;
			try{
    			finder.setProgressMonitor(progressMonitor);
				result = finder.get();
			} catch (InterruptedException e) {
				Logger.getLogger(getClass().getName()).log(Level.SEVERE,null,e);
			}finally{
				finder=null;
				if(progressMonitor!=null){
					progressMonitor.close();
				}
			}
//			System.out.println("<---------------------------------------------------->");
			return result;
		}
		return null;
	}

	private List<CellPosition> start(GobanModel model,CellContent content,IProgressMonitor monitor){
            
		List<CellPosition> choices = getChildrenFor(model,content);
		List<CellPosition> results = new ArrayList<CellPosition>();
		
		if(monitor!=null){
			long min = 1;
			long max = akira(choices.size(), height);
			monitor.setMinimum(min);
			monitor.setMaximum(max);
		}
		
		int index = 0;
		Long alpha = L_INF;
		Long beta = R_INF;
		Long best = L_INF;
		for(CellPosition pos : choices){
			if(monitor!=null){
				if(monitor.isCancelled()){
					break;
				}else{
					monitor.setNextValue();
				}
			}
			
			Long val = R_INF;
			boolean badSituation = false;
			boolean goodSituation = false;
			
			//on teste si on peut perdre au prochain coup
			CellEvent captureEvent = makeEvent(pos,content.getEnemy());
			try{
				Capture.RULE.check(model,captureEvent);
			}catch(CaptureException e){
				badSituation = true;
			}catch(Throwable e){					
			}
			
			//on teste si on gagne au prochain coup
			captureEvent = makeEvent(pos,content);
			try{
				Capture.RULE.check(model,captureEvent);
			}catch(CaptureException e){
				goodSituation = true;
			}catch(Throwable e){					
			}
							
//			System.out.println(String.format("AlphaBeta::MAIN[%d][cell=%s,best=%d,alpha=%d,beta=%d,depth=%d]",index,pos,best,alpha,beta,0));
			
			if(!badSituation && !goodSituation){
				GobanModel childState = makeArtificialGoban(model,pos,content);	
				val = iter(1,content.getEnemy(),childState,alpha,beta,monitor);
			}
			
//			System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			
			if(val>best){
				results.clear();
				results.add(pos);
				best = val;
				if(best>alpha){
					alpha = best;
//					if(alpha>beta){
//						break;
//					}
				}
			}

			else if(best==val){
//				System.out.println(String.format("best[%s],val[%s]", best,val));
				results.add(pos);
			}
			
			index++;
		}
		
		return results;
	}
	
	private List<CellPosition> getChildrenFor(GobanModel model,final CellContent content){
		List<CellPosition> result;
		if(content.isKuro()){
			result = model.getPositionsFor(CellContent.Empty,CellContent.ShiroSuicide);
		}else if(content.isShiro()){
			result = model.getPositionsFor(CellContent.Empty,CellContent.KuroSuicide);
		}else{
			return Collections.emptyList();
		}

		List<CellPosition> fakes = new ArrayList<CellPosition>();
		for(CellPosition cell : result){
			CellEvent event = makeEvent(cell,content);
			try{
				Suicide.RULE.check(model,event);
			}catch(RuleViolationException e){	
				fakes.add(cell);
			} catch (CaptureException e) {
			}
		}
		
		result.removeAll(fakes);
		Collections.shuffle(result);
		return result;
	}
	
	private long iter(
			int depth,
			final CellContent content,
			//List<CellPosition> children,
			GobanModel state,
			Long alpha,
			Long beta,
			IProgressMonitor monitor)
	{
		if(monitor!=null){
			monitor.setNextValue();
		}
		
		if(depth>=height){
			long result = valuation.run(state);
//			System.out.println(String.format("AlphaBeta::LEAF[depth=%d, value=%d]",depth,result));
			return result;
		}else{
			List<CellPosition> children = getChildrenFor(state,content);
			long best = L_INF;
			int index = 0;
			for(final CellPosition pos : children){
				
//				System.out.println(String.format("AlphaBeta::ITER[%d][cell=%s,best=%d,alpha=%d,beta=%d,depth=%d]",index,pos,best,alpha,beta,depth));
				GobanModel childState = makeArtificialGoban(state,pos,content);
				long val = (depth%2==0?-1:1)*valuation.run(childState);
				
				try{
					CellEvent event = makeEvent(pos,content);
					//Suicide.RULE.check(state,event);
					Capture.RULE.check(state,event);
					if(!finder.isCancelled() 
							&& (monitor==null 
									|| !monitor.isCancelled()))
					{
						val = -iter(depth+1,content.getEnemy(),childState,-beta,-alpha,monitor);
					}
				}catch(CaptureException e){
				}catch(Throwable e){						
				}
				
				if(val>best){
					best = val;
					if(val>alpha){
						alpha = best;
						if(alpha>beta){
							return best;
						}
					}
				}
				index++;
			}
			return best;
		}
	}
	
	class AlgoTask extends AlgorithmWorker{

		@Override
		protected List<CellPosition> doInBackground(IProgressMonitor progressMonitor) {
			return start(goban,content,progressMonitor);
		}		
		
	}
	
	private static CellEvent makeEvent(final CellPosition p,final CellContent c){
		return new CellEvent() {			
			@Override
			public CellPosition getPosition() { return p; }
			
			@Override
			public CellContent getContent() { return c; }
		};
	}
	
	private static CellEvent makeEvent(int row,int col,CellContent c){
		return makeEvent(new CellPositionImpl(row,col),c);
	}

}
