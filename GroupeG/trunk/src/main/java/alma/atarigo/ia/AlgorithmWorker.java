package alma.atarigo.ia;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import alma.atarigo.CellPosition;
import alma.atarigo.IProgressMonitor;

public abstract class AlgorithmWorker implements Runnable{

	private static Map<AlgorithmWorker,Boolean> states 
		= Collections.synchronizedMap(new HashMap<AlgorithmWorker, Boolean>());
	private IProgressMonitor monitor = null;
	
	private final Thread task = new Thread(this);
	private List<CellPosition> positions = new ArrayList<CellPosition>();

	protected AlgorithmWorker(){
		states.put(this, false);
	}
	
	protected void finalize() throws Throwable{
		super.finalize();
		states.remove(this);
	}
	
	protected Boolean isCancelled(){
		return states.get(this);
	}

	public void kill(){
		task.interrupt();
	}
	
	public void cancel(){
		states.put(this, true);
		try {
			task.join(1000);
		} catch (InterruptedException e) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE,null,e);
		}
	}

	
	public void run(){
		positions = doInBackground(monitor);
	}
	
	public void setProgressMonitor(IProgressMonitor monitor){
		this.monitor = monitor;
	}
	
	public List<CellPosition> get() throws InterruptedException{
		states.put(this, false);
		positions = null;
		task.start();
		task.join();		
		return positions;
	}

	protected abstract List<CellPosition> doInBackground(IProgressMonitor progressMonitor);
	
}
