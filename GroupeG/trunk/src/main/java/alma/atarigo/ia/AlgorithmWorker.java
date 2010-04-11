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

/**
 * Permet au algorithme de travailler en arrière plan
 * dans un thread separé du thread principal
 * @author steg
 *
 */
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

	/**
	 * Arreter la tache encour 
	 */
	public void kill(){
		task.interrupt();
	}
	
	/**
	 * Annuler la tache encour. Ce qui signifie 
	 * notifier au processus l'arret imminent et attendre 
	 * qu'il se termine.
	 */
	public void cancel(){
		states.put(this, true);
		try {
			task.join(1000);
		} catch (InterruptedException e) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE,null,e);
		}
	}

	/**
	 * Lancement de la tache
	 */
	public void run(){
		positions = doInBackground(monitor);
	}
	
	/**
	 * Mettre à jour le moniteur de progrès associé à la tache
	 * @param monitor La nouvelle valeur du moniteur de progrès
	 */
	public void setProgressMonitor(IProgressMonitor monitor){
		this.monitor = monitor;
	}
	
	/**
	 * Lancer l'algorithme, attendre la fin de son exécution et retourner
	 * son résultat
	 * @return La liste de proposition de l'algorithme.
	 * @throws InterruptedException
	 */
	public List<CellPosition> get() throws InterruptedException{
		states.put(this, false);
		positions = null;
		task.start();
		task.join();		
		return positions;
	}

	/**
	 * A implémenter dans la sous hiérarchie
	 * @param progressMonitor
	 * @return
	 */
	protected abstract List<CellPosition> doInBackground(IProgressMonitor progressMonitor);
	
}
