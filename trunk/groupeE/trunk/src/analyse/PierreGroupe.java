package analyse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import plateau.Goban;
import plateau.Goban.PionVal;

public class PierreGroupe {
	protected class Coords {
		private int ligne;
		private int colonne;
		
		public Coords() {
			super();
		}
		public Coords(int ligne, int colonne) {
			super();
			this.ligne = ligne;
			this.colonne = colonne;
		}
		public int getLigne() {
			return ligne;
		}
		public void setLigne(int ligne) {
			this.ligne = ligne;
		}
		public int getColonne() {
			return colonne;
		}
		public void setColonne(int colonne) {
			this.colonne = colonne;
		}
		
	}

	private ArrayList<Coords> groupe;
	private Goban.PionVal couleur;
	
	
	private boolean checkCohesion(final ArrayList<Coords> gr){
		Iterator<Coords> it1 = gr.iterator();
		boolean coherent = true;
		
		while(it1.hasNext() && coherent){
			Coords c1 = it1.next();
			
			for(Coords c:gr){
				coherent &= ((c.getLigne() == c1.getLigne() && Math.abs(c.getColonne() - c1.getColonne()) == 1) || (c.getColonne() == c1.getColonne() && Math.abs(c.getLigne() - c1.getLigne()) == 1));
			}
		}
		return coherent;
	}
	
	public void addCoords(int ligne, int colonne) throws NoCohesionException{
		Coords coords = new Coords(ligne,colonne);
		Iterator<Coords> it = groupe.iterator();
		int position = 0;
		boolean place = false;
		
		while(!place && it.hasNext()){
			Coords curC = it.next();
			if((curC.getColonne() == coords.getColonne() && Math.abs(curC.getLigne() - coords.getLigne()) == 1) || (curC.getLigne() == coords.getLigne() && Math.abs(curC.getColonne() - coords.getColonne()) == 1)){
				groupe.add(position, coords);
				place = true;
			} else {
				++position;
			}
		}
		if(!place){
			throw new NoCohesionException("La pierre ne peut être ajoutée de façon cohérente.");
		}
	}
	
	public ArrayList<Coords> getGroupe() {
		return groupe;
	}
	public void setGroupe(ArrayList<Coords> groupe) throws NoCohesionException {
		if(this.checkCohesion(groupe)){
			this.groupe = groupe;
		} else {
			throw new NoCohesionException("Le groupe n'est pas cohérent.");
		}
		
	}
	public Goban.PionVal getCouleur() {
		return couleur;
	}
	public void setCouleur(Goban.PionVal couleur) {
		this.couleur = couleur;
	}
	
	
	
	/**
	 * Compte les libertés du groupe sur le goban donné.
	 * @param goban
	 * @return
	 * @throws Exception 
	 */
	public int compteLibertes(Goban goban) throws Exception{
		HashMap<Coords,Boolean> visites = new HashMap<Coords, Boolean>();
		
		for(Coords c : this.groupe){
			/*
			 * On teste pour chaque place autour de la pierre courante si elle est libre
			 */
			if(goban.getCase(c.getLigne()-1, c.getColonne()) == PionVal.rien){
				visites.put(new Coords(c.getLigne()-1, c.getColonne()), true);
			}
			
			if(goban.getCase(c.getLigne(), c.getColonne()-1) == PionVal.rien){
				visites.put(new Coords(c.getLigne()-1, c.getColonne()), true);
			}
			
			if(goban.getCase(c.getLigne()+1, c.getColonne()) == PionVal.rien){
				visites.put(new Coords(c.getLigne()-1, c.getColonne()), true);
			}
			
			if(goban.getCase(c.getLigne(), c.getColonne()+1) == PionVal.rien){
				visites.put(new Coords(c.getLigne()-1, c.getColonne()), true);
			}
		}
		
		return visites.size();
	}
	
	
}
