package main.java.alexanddam.logic;

import java.util.ArrayList;

public class NoeudsFils {

	private ArrayList<Noeud_LA> al;	
	private int arite;						// nombre maximal des elements
	boolean ascendent;
	
	NoeudsFils(int arite, boolean ascendent) {
		
		this.arite = arite;
		al = new ArrayList<Noeud_LA>();
		this.ascendent = ascendent;
	}
	
	public Noeud_LA get(int index) {
		
		if(index < 0 || index >= al.size()) {
				System.out.println("Index out of range in get");
			return null;
		}
		
		return al.get(index);
	}
	
	public Noeud_LA remove(int index) {
		if(index < 0 || index >= al.size()) {
			System.out.println("Index out of range in remove");
		return null;
	}
	
		return al.remove(index);		
	}
	
	public int size() {
		return this.al.size();
	}
	
	public boolean addFils(Noeud_LA nla) {
		boolean insere = false;
		int index = -1;
		
		System.out.println("valeur en addFils "+nla.valeur);
		
		index = getIndex(nla);
		System.out.println("index "+index);
		if(this.al.size() < this.arite) {
			insere = true;
			if(index == -1) {
				index = 0;
			}
			
		} else {
			if(index != -1) {
				insere = true;
				
				// il faut aussi supprimer le premier element
				this.al.remove(0);	
				index -= 1;				// l'index doit decrementer, suite a la suppression de l'element en position 0
			}
		}
		
		if(insere) {
			al.add(index, nla);
		}
		
		return insere;
	}
	
	private int getIndex(Noeud_LA nla) {
		int index = -1, i;
		
		if(this.al.size() == 0) {
				index = 0;
			return index;
		}
		
		if(ascendent) {
			if(nla.valeur > al.get(0).valeur) {
				for(i=1; i < al.size(); i++) {
					if(nla.valeur <= al.get(i).valeur) {
						break;
					}
				}
				index = i;
			}
		} else {
			if(nla.valeur < al.get(0).valeur) {
				for(i=1; i < al.size(); i++) {
					if(nla.valeur >= al.get(i).valeur) {
						break;
					}
				}
				index = i;
			}
		}
		
		return index;
	}
	
}
