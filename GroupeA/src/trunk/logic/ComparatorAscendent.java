package trunk.logic;

import java.util.Comparator;

// comparateur ascendent
public class ComparatorAscendent implements Comparator<Noeud_LA> {
	@Override
	public int compare(Noeud_LA n1, Noeud_LA n2) {
		if(n1.valeur == n2.valeur) { 
			return 0;
		} else {
			return (n1.valeur < n2.valeur) ? -1 : 1;
		}
		
	}
}

// comparateur descendent 
class ComparatorDescendent implements Comparator<Noeud_LA> {	
	@Override
	public int compare(Noeud_LA n1, Noeud_LA n2) {
		if(n1.valeur == n2.valeur) { 
			return 0; 
		} else {
			return (n1.valeur < n2.valeur) ? 1 : -1;
		}
		
	}
}


