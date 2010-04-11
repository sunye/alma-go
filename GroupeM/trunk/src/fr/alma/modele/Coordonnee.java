package fr.alma.modele;

public class Coordonnee {
	private Integer x;
	private Integer y;
	public Integer getX() {
		return x;
	}
	public void setX(Integer x) {
		this.x = x;
	}
	public Integer getY() {
		return y;
	}
	public void setY(Integer y) {
		this.y = y;
	}
	public Coordonnee(Integer x, Integer y) {
		this.x = x;
		this.y = y;
	}
	
	public boolean isValid(int max){
		return x>=0&&y>=0&&x<max&&y<max;
	}
	@Override
	public String toString() {
		
		return "x:"+x+" y:"+y;
	}
	
	
	
}
