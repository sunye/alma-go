package alma.atarigo.ia.valuation;

/**
 * Enumeration sur l'etat d'avancement de la partie
 * @author gass-mouy
 *
 */
public enum Status {
	
	/**
	 * Nous sommes rendus au debut de la partie
	 */
	Start(1),

	/**
	 * Nous sommes rendus au milieu de la partie
	 */	
	Middle(2),

	/**
	 * Nous sommes rendus a la fin de la partie
	 */
	End(3);
	
	private Integer id;

	/**
	 * Constructeur implicite
	 */
	private Status(Integer id){
		this.id = id;
	}

	/**
	 * Est-ce le debut de partie?
	 * @return Oui ou Non
	 */
	public boolean isStart(){
		return id==Start.id;
	}

	/**
	 * Est-ce le milieu de partie?
	 * @return Oui ou Non
	 */
	public boolean isMiddle(){
		return id==Middle.id;
	}

	/**
	 * Est-ce la fin de partie?
	 * @return Oui ou Non
	 */
	public boolean isEnd(){
		return id==End.id;
	}

}
