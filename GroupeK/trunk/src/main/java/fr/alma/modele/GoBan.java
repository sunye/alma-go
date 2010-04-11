package fr.alma.modele;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class GoBan {

	private Pion[][] goban;
	private int nbBlanc;
	private int nbNoir;
	private int num=0;
	private CouleurPion gagnant=CouleurPion.EMPTY;
	private HashSet<Groupe> group;
	private Groupe groupeVide;
	private static int[] modifier={1,-1};
	public static int TAILLE_GO_BAN=9;

	public GoBan() {
		this.goban = new Pion[9][9];
		this.group= new HashSet<Groupe>();
		this.groupeVide=new Groupe(CouleurPion.EMPTY);
		this.remiseZero();
	}
	
	public Pion[][] getGoban() {
		return goban;
	}
	
	public int getNbBlanc() {
		return nbBlanc;
	}
	public int getNbNoir() {
		return nbNoir;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public void setGoban(Pion[][] goban) {
		this.goban = goban;
	}
	public void setNbBlanc(int nbBlanc) {
		this.nbBlanc = nbBlanc;
	}
	public void setNbNoir(int nbNoir) {
		this.nbNoir = nbNoir;
	}
	
	public CouleurPion getGagnant() {
		return gagnant;
	}
	public void setGagnant(CouleurPion gagnant) {
		this.gagnant = gagnant;
	}
	
	public boolean addPion(int position, int positiony){
		
		
		//FIXME gestion de la prise et du vainqueur
		CouleurPion coul=num%2!=0?CouleurPion.BLANC:CouleurPion.NOIR;
		Coordonnee temp=new Coordonnee(position, positiony);
		if (estLegal(temp, coul)!=TypeCoup.NONVALID){
			this.ajouterPionleretour(temp, coul);
			
		}
		
		
		return true;
	}
	
	public boolean addPion(int position, int positiony, CouleurPion cc){
		
		
		
		this.ajouterPionleretour(new Coordonnee(position, positiony), cc);
		return true;
	}
	
	
	public boolean retirerPion(int position, int positiony, CouleurPion coul){
		
		removePion(goban[position][positiony]);
		
		
		return true;
	}


	public TypeCoup estLegal(Coordonnee cood, CouleurPion coul) {
				
		if( !cood.isValid(GoBan.TAILLE_GO_BAN)){
			return TypeCoup.NONVALID;
		}
			
		Pion pi=goban[cood.getX()][cood.getY()];
		
		if (pi.getCouleur()!=CouleurPion.EMPTY){
			return TypeCoup.NONVALID;
		}
		
		List<Pion> voisin= getVoisin(pi);

		for (Pion v:voisin){
			if( v.getCouleur()==CouleurPion.oppose(coul)){
				if (v.getGroupe().liberty()==1){
					return TypeCoup.PRISE;
				}
			} else if (v.getCouleur()==coul){
				if( v.getGroupe().liberty()!=1){
					return TypeCoup.VALID;
				}
			}else if (v.getCouleur()==CouleurPion.EMPTY){
				return TypeCoup.VALID;
			} 
		}
		return TypeCoup.NONVALID;
	}
	
	public void ajouterPionleretour(Coordonnee coord, CouleurPion coul){
	
		Pion pi=goban[coord.getX()][coord.getY()];
		groupeVide.removePion(pi);
		
		pi.setCouleur(coul);
		pi.setGroupe(new Groupe(coul));
			
		List<Pion> voisin= getVoisin(pi);
		
		
		
		for (Pion v:voisin){
			if( v.getCouleur()== CouleurPion.oppose(pi.getCouleur())){
				v.getGroupe().removeLiberty(pi.getPosition());
			}else if(v.getCouleur()== pi.getCouleur()){
				pi.getGroupe().fusionGroup(v.getGroupe());
				group.remove(v.getGroupe());
			}else{
				pi.getGroupe().addLiberty(v.getPosition());
			}
		}
		
	
		
		group.add(pi.getGroupe());
		num++;
	}
	

	public void removePion(Pion pi){
		int x=pi.getPosition().getX();
		int y=pi.getPosition().getY();
		
		Pion temp=goban[x][y];
		
		goban[x][y] = new Pion(x,y,CouleurPion.EMPTY, 0);
		goban[x][y].setNumero(-1);
		goban[x][y].setGroupe(groupeVide);
		groupeVide.addPion(goban[x][y]);
		
		
		List<Pion> voisin= getVoisin(pi);
		for (Pion v:voisin){
			if( v.getCouleur()== CouleurPion.oppose(pi.getCouleur())){
				v.getGroupe().addLiberty(pi.getPosition());
			}else if(v.getCouleur()== pi.getCouleur()){
				Groupe newGroup= new Groupe(v.getCouleur());
				group.add(newGroup);
				reformeGroup(newGroup, v);
			}else{
				
			}
		}
		
	
		
		if (temp.getGroupe().nbPions()==1){
			//finaliser ?
		}else{
			temp.getGroupe().removePion(temp);
		}
			temp.setGroupe(null);
	}
	
	public List<Coordonnee> calculPionLiberte(Pion pi){
		LinkedList<Coordonnee> result= new LinkedList<Coordonnee>();
				
		List<Pion> voisin= getVoisin(pi);
		
		for (Pion v:voisin){
			if( v.getCouleur()==CouleurPion.EMPTY){
				result.add(v.getPosition());
			}
		}

		return result;
	}

	
	public List<Pion> getVoisin(Pion pi){
		LinkedList<Pion> listVoisin= new LinkedList<Pion>();
		int x=pi.getPosition().getX();
		int y=pi.getPosition().getY();
				
		for (int i=0; i<modifier.length;i++){
			Coordonnee coordtemp=new Coordonnee(x+modifier[i], y);
			if (coordtemp.isValid(GoBan.TAILLE_GO_BAN)){
			listVoisin.add(goban[coordtemp.getX()][coordtemp.getY()]);
			}
		}
		for (int i=0; i<modifier.length;i++){
			Coordonnee coordtemp=new Coordonnee(x, y+modifier[i]);
			if (coordtemp.isValid(GoBan.TAILLE_GO_BAN)){
				listVoisin.add(goban[coordtemp.getX()][coordtemp.getY()]);
			}
		}
		
		return listVoisin;
		
	}
	
	

	public void remiseZero(){
		this.group.clear();
		groupeVide= new Groupe(CouleurPion.EMPTY);
		for (int i=0; i<9; i++){
			for (int k=0; k<9; k++) {
				goban[i][k] = new Pion(i,k,CouleurPion.EMPTY, 0);
				goban[i][k].setNumero(-1);
				goban[i][k].setGroupe(groupeVide);
			}
		}
		this.setNbNoir(0);
		this.setNbBlanc(0);
		this.setNum(0);
		this.setGagnant(CouleurPion.EMPTY);
	}

	
	private void reformeGroup(Groupe grop, Pion pi){
		if( pi.getCouleur()==grop.getCoul()){
			pi.setGroupe(grop);
			grop.addPion(pi);
			grop.addLibertys(calculPionLiberte(pi));
			
			int x=pi.getPosition().getX();
			int y=pi.getPosition().getY();
			Pion avisiter=null;
			
			for (int i=0; i<modifier.length;i++){
					Coordonnee coordtemp=new Coordonnee(x+modifier[i], y);
					if (coordtemp.isValid(GoBan.TAILLE_GO_BAN)){
						avisiter=goban[coordtemp.getX()][coordtemp.getY()];
						reformationGroup(grop, avisiter, modifier[i]*-1, 0);
					}
				
			}
			for (int i=0; i<modifier.length;i++){
				Coordonnee coordtemp=new Coordonnee(x, y+modifier[i]);
				if (coordtemp.isValid(GoBan.TAILLE_GO_BAN)){
					avisiter=goban[coordtemp.getX()][coordtemp.getY()];
					reformationGroup(grop, avisiter, 0, modifier[i]*-1);
				}
			}
		}
	}
		
	
	
	
	
	private void reformationGroup(Groupe grop, Pion pi, int intmodifierx, int intmodifiery){
		
		if( pi.getCouleur()==grop.getCoul()){
			grop.addPion(pi);
			grop.addLibertys(calculPionLiberte(pi));
			
			int x=pi.getPosition().getX();
			int y=pi.getPosition().getY();
			Pion avisiter=null;
			
			for (int i=0; i<modifier.length;i++){
				if(intmodifierx!= modifier[i]){
					Coordonnee coordtemp=new Coordonnee(x+modifier[i], y);
					if (coordtemp.isValid(GoBan.TAILLE_GO_BAN)){
						avisiter=goban[coordtemp.getX()][coordtemp.getY()];
						reformationGroup(grop, avisiter, modifier[i]*-1, 0);
					}
				}
			}
			for (int i=0; i<modifier.length;i++){
				if(intmodifiery!= modifier[i]){
					Coordonnee coordtemp=new Coordonnee(x, y+modifier[i]);
					if (coordtemp.isValid(GoBan.TAILLE_GO_BAN)){
						avisiter=goban[coordtemp.getX()][coordtemp.getY()];
						reformationGroup(grop, avisiter, 0, modifier[i]*-1);
					}
				}
			}
		}
	}
}
