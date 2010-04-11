package alma.atarigo;

public interface IProgressMonitor {

	/**
	 * Modifier la borne maximum 
	 * @param maximum La nouvelle valeur de la borne
	 */
	public void setMaximum(long maximum);
	
	/**
	 * Modifier la borne minimum
	 * @param minimum La nouvelle valeur de la borne
	 */
	public void setMinimum(long minimum);
	
	/**
	 * Modifier l'longervalle de progression
	 */
	public void setRange(long minimum,long maximum);
	
	/**
	 * Modifier la valeur courante de la progression
	 */
	public void setProgress(long progress);
	
	/**
	 * La valeur suivante
	 */
	public void setNextValue();
	
	/**
	 * @return true Si l'utilisateur a annuler la tache
	 */
	public boolean isCancelled();
	
	/**
	 * Fermer le moniteur
	 */
	public void close();
	
}
