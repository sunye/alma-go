package analyse;

import java.util.ArrayList;

import plateau.Goban;

public class EvalFunc {
	
	private ArrayList<PierreGroupe> groups;
	
	
	public void addGroupe(PierreGroupe gr){
		this.groups.add(gr);
	}
	
	public int evaluate(Goban goban){
		return 0;
		
	}
}
