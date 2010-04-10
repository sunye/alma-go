package fr.alma.modele;

public class CoordonneeIA extends Coordonnee {

	private boolean termine; 
	
	public CoordonneeIA(Integer x, Integer y) {
		super(x, y);
		setTermine(false);
	}

	public void setTermine(boolean termine) {
		this.termine = termine;
	}

	public boolean isTermine() {
		return termine;
	}
	
	public void setCoordinate(Coordonnee coord){
		this.setX(coord.getX());
		this.setY(coord.getY());
		
		
	}
	
	
	
	
}
