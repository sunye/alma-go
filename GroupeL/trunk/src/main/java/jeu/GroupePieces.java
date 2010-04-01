/**
 * @author Dejean Charles - Pottier Vincent
 * 
 * Classe permettant de gere un groupe de pieces adjacentes (dans un jeu de go). 
 * 
 */

package jeu;

import java.util.LinkedList;
import java.util.List;

public class GroupePieces {

	private List<Coordonnees> pieces;
	private Integer liberte;
	private Couleur couleur; 
	
	/* Constructeur */
	public GroupePieces() {
		super();
		pieces = new LinkedList<Coordonnees>();
		liberte = 0;
		couleur = Couleur.none;
	}
	
	public GroupePieces(Coordonnees coord, Integer liberte, Couleur coul) {
		super();
		pieces = new LinkedList<Coordonnees>();
		pieces.add(coord);
		this.liberte = liberte;
		couleur = coul;
	}
	
	/* Getters - Setters */
	public List<Coordonnees> getPieces() {
		return pieces;
	}
	public void setPieces(List<Coordonnees> pieces) {
		this.pieces = pieces;
	}
	public Integer getLiberte() {
		return liberte;
	}
	public void setLiberte(Integer liberte) {
		this.liberte = liberte;
	}
	public void setCouleur(Couleur couleur) {
		this.couleur = couleur;
	}
	public Couleur getCouleur() {
		return couleur;
	}
	
	
	public LinkedList<GroupePieces> separeGroupe() {
				
		LinkedList<GroupePieces> groupAdj = new LinkedList<GroupePieces>();
		
		while(pieces.size() != 0)
		{
			/* On prend la premiere piece */
			/* On la retire de gc */
			GroupePieces gcTmp = new GroupePieces(pieces.get(0),0,couleur);
			pieces.remove(0);
			
			int i = 0;
			/* on parcour la liste des pieces adjacentes */
			while(i < gcTmp.getPieces().size()) {
				
				Coordonnees coordAdj = gcTmp.getPieces().get(i);
				int j = 0;
				
				while(j < pieces.size() ){
					Coordonnees coordTest = pieces.get(j);
					/* si elle sont adjacente on la recupere */
					if(coordAdj.estAdjacent(coordTest)){
						gcTmp.getPieces().add(coordTest);
						pieces.remove(coordTest);
					}else{
						// sinon on passe a la suivante
						j++;
					}
				}
				i++;
			}
			
			groupAdj.add(gcTmp);
		}
		
		return groupAdj;
	}
	
	
	/**
	 * @param coord : coordonnee de la position a tester;
	 * @return vrai si la position est occupee par le groupe, faux sinon
	 * 
	 */	
	public boolean testPosition(Coordonnees coord){
		for(Coordonnees c : pieces){						
			if( c.estEgal(coord) ){
				return true;
			}
		}
		return false;
	}
		
	
}
