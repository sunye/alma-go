package plateau;

import java.util.ArrayList;

import analyse.PierreGroupe;

public class Goban {
	
	private final static int taille = 9;
	public static enum  PionVal{rien, noir, blanc};
	
	// 0 = rien, noir = 1, blanc = 2
	Goban.PionVal goban[][];

	public Goban() {
		super();
		goban = new Goban.PionVal[Goban.taille][Goban.taille];
		
		for(int i = 0; i < Goban.taille; ++i){
			for (int j = 0; j < Goban.taille; ++j){
				goban[i][j] = Goban.PionVal.rien;
			}
		}
	}
	
	
	public void setCase(int ligne, int colonne, Goban.PionVal pion) throws Exception{
		if(ligne < Goban.taille && colonne < Goban.taille){
			if(goban[ligne][colonne] != Goban.PionVal.rien && (pion == Goban.PionVal.blanc || pion == Goban.PionVal.noir)){
				throw new Exception("Vous ne pouvez pas jouer là, c'est déjà pris");
			} else {
				goban[ligne][colonne] = pion;
			}
		} else {
			throw new Exception("Case en dehors du goban");
		}
	}
	
	public Goban.PionVal getCase(int ligne, int colonne) throws Exception {
		if(ligne < Goban.taille && colonne < Goban.taille){
			return goban[ligne][colonne];
		} else {
			throw new Exception("Case en dehors du goban");
		}
	}
	
	public ArrayList<PierreGroupe> getGroupes(){
		return null;
	}
}
//test
