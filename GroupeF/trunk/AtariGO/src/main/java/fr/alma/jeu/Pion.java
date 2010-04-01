package fr.alma.jeu;

import java.awt.Point;

public class Pion {

	public enum Couleur {NOIR,BLANC,NULL};
	
	public Couleur couleur;
	public Point position;
	
	public Pion(Couleur couleur, Point position){
		
		this.couleur = couleur; 
		this.position = position; 
	}

	public Pion(Point point) {
		this.couleur = Couleur.NULL;
		this.position = point;
	}

	public boolean equal(Pion pion) {
		return this.couleur.equals(pion.couleur)&&(this.position.x==pion.position.x)&&(this.position.y==pion.position.y);
	}
}
