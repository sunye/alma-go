package utils;

import java.util.ArrayList;

import utils.exceptions.BadCouleurException;
import utils.exceptions.BadPlaceException;
import analyse.PierreGroupe;

public class Goban {
	
	private final static int taille = 9;
	//public static enum  PionVal{rien, noir, blanc};
	private static ArrayList<Groupe> groupes;
	

	// 0 = rien, noir = 1, blanc = 2
	PionVal goban[][];

	public static int getTaille() {
		return taille;
	}
	
	public Goban() {
		super();
		goban = new PionVal[Goban.taille][Goban.taille];
		
		for(int i = 0; i < Goban.taille; ++i){
			for (int j = 0; j < Goban.taille; ++j){
				goban[i][j] = PionVal.RIEN;
			}
		}
		
		groupes = new ArrayList<Groupe>();
	}
	
	
	private boolean bonneCoords(int ligne, int colonne){
		return ligne < Goban.getTaille() && colonne < Goban.getTaille();
	}
	
	public void posePion(int ligne, int colonne, PionVal couleur) throws BadPlaceException{
		int realLigne = ligne-1;
		int realCol = colonne-1;
		if(bonneCoords(realLigne, realCol)){
			if(goban[realLigne][realCol] == PionVal.RIEN){
				setCase(realLigne, realCol, couleur);
				
				Pion current = new Pion(couleur,realLigne,realCol);
				
				ArrayList<Pion> voisins = getVoisins(current);
				
				Groupe lastAdded = new Groupe(current.getCouleur());
				try {
					lastAdded.addPion(current);
				} catch (BadCouleurException e){}
				
				for(Pion pion:voisins){
					if(pion.getCouleur() == couleur){
						Groupe currentGroupe = getGroupeContenant(pion);
						lastAdded.addAll(currentGroupe);
						groupes.remove(currentGroupe);
					}
				}
				groupes.add(lastAdded);
				
			} else {
				throw new BadPlaceException("Il y a déjà une pierre ici");
			}
		} else {
			throw new BadPlaceException("Il est préférable de jouer sur le plateau");
		}
	}
	
	/**
	 * Retourne le premier (normalement le seul) groupe de pierres contenant la pierre passée en paramètre.
	 * @param pion
	 * @return
	 */
	public Groupe getGroupeContenant(Pion pion) {
		for(Groupe groupe:groupes){
			if(groupe.contains(pion)) {
				return groupe;
			}
		}
		return null;
	}

	/**
	 * Cette méthode permet de définir la valeur d'un pion. Elle est publique pour le cas où on doit ajouter rapidement des pions dans le goban.
	 * Attention toutefois, si on tente de modifier une case en dehors du plateau, rien n'est fait, mais on ne récupère aucune exception. 
	 * @param ligne
	 * @param colonne
	 * @param pion
	 */
	public void setCase(int ligne, int colonne, PionVal pion){
		if(bonneCoords(ligne, colonne)){
			goban[ligne][colonne] = pion;
		}
	}
	
	public PionVal getCase(int ligne, int colonne) throws Exception {
		if(bonneCoords(ligne, colonne)){
			return goban[ligne][colonne];
		} else {
			throw new Exception("Case en dehors du goban");
		}
	}
	
	public ArrayList<Pion> getVoisins(Pion pion){
		ArrayList<Pion> pions = new ArrayList<Pion>();
		if(bonneCoords(pion.getLigne()+1, pion.getColonne()) && (goban[pion.getLigne()+1][pion.getColonne()] != PionVal.RIEN)){
			pions.add(new Pion(goban[pion.getLigne()+1][pion.getColonne()], pion.getLigne()+1,pion.getColonne()));
		}
		
		if(bonneCoords(pion.getLigne()-1, pion.getColonne()) && (goban[pion.getLigne()-1][pion.getColonne()] != PionVal.RIEN)){
			pions.add(new Pion(goban[pion.getLigne()-1][pion.getColonne()], pion.getLigne()-1,pion.getColonne()));
		}
		
		if(bonneCoords(pion.getLigne(), pion.getColonne()+1) && (goban[pion.getLigne()][pion.getColonne()+1] != PionVal.RIEN)){
			pions.add(new Pion(goban[pion.getLigne()][pion.getColonne()+1], pion.getLigne(),pion.getColonne()+1));
		}
		
		if(bonneCoords(pion.getLigne(), pion.getColonne()-1) && (goban[pion.getLigne()][pion.getColonne()-1] != PionVal.RIEN)){
			pions.add(new Pion(goban[pion.getLigne()][pion.getColonne()-1], pion.getLigne(),pion.getColonne()-1));
		}
		
		return pions;
	}
	
	public ArrayList<PierreGroupe> getGroupes(){
		return null;
	}
}
