package main.java.alexanddam.logic;

import java.util.Iterator;
import java.util.LinkedList;

public class Noeud_LA extends Noeud {

	public LinkedList<Noeud_LA> liste_fils;
	public Noeud_LA pere;
	public int hauteur;
	public boolean valeurPresent;
	//n'a pas besoin ni de la valeur etiquette ni de position	
	
	public Noeud_LA(int tour, int x, int y, int[] voProp, int[] vaProp) {
		
		int i = 0;
		this.liste_fils = new LinkedList<Noeud_LA>();
		
		switch(tour) {
		
		case 0:
			    // le vecteur voProp est null => le pere est la racine 
				if(voProp.length == 0) {
					this.vo = new int[2];
					this.va = new int[0];
					
					this.vo[0] = x;
					this.vo[1] = y;
				}
				else {
									
					this.vo = new int[voProp.length + 2];  // c'est le tour de l'ordinateur
					this.va = new int[vaProp.length];   
				
					for(i = 0; i < voProp.length ; i++)
						this.vo[i] = voProp[i];
					
					this.vo[i++] = x;  this.vo[i] = y;  
					
					for(i = 0; i < vaProp.length ; i++)
						this.va[i] = vaProp[i];
				}
				
			break; 
			
		case 1:  // tour de l'adversaire
			
				if(vaProp.length == 0) { // c'est le premiere coup de l'adversaire dans le devellopement de l'arbre
				
					this.va = new int[2];
					this.va[0] = x; this.va[1] = y;
					
					this.vo = new int[2];
					
					this.vo[0] = voProp[0];
					this.vo[1] = voProp[1];					
				}
				else {
					
					this.va = new int[vaProp.length + 2];  // c'est le tour de l'adversaire
					this.vo = new int[voProp.length];   
				
					for(i = 0; i < vaProp.length ; i++)
						this.va[i] = vaProp[i];
					
					this.va[i++] = x;  this.va[i] = y;  
					
					for(i = 0; i < voProp.length ; i++)
						this.vo[i] = voProp[i];				
				}
			break;
			
		default:
			System.out.println(">> erreur dans l'etablissement de tour");
			break;
		}		
		
	}	
	
	public Noeud_LA(int val){
		this.valeur = val;
		this.liste_fils = new LinkedList<Noeud_LA>();
		this.hauteur = 1;
		
		this.vo = new int[0];
		this.va = new int[0];
	}
	
	public Noeud_LA(int val,int arite){
		
		this.valeur = val;
		this.liste_fils = new LinkedList<Noeud_LA>();

		this.vo = new int[0];
		this.va = new int[0];
	}
	
	public Noeud_LA node(int e){
		
		if(e==valeur){// on se trouve dans le bon noeud
			
			return this;
			
		}
		else{//sinon on recherche au noeud suivant...
			
			Iterator<Noeud_LA> it = liste_fils.iterator(); 
			
			while (it.hasNext()) {
				
  				Noeud_LA fils = it.next();				
   				if(fils.node(e)!=null)return fils.node(e);
   				
			}	
  		}
		return null;
	}
	
	@Override
	public boolean equals(Object o) {
				
		if(o instanceof Noeud_LA) {
			
			Noeud_LA n = (Noeud_LA) o;
					
			return this == n;
		}
		else {
			
			return false;
		}
			
	}
	
	// ajoute le noeud f en tant que fils, ne marche que si la liste n'est pas pleine
	public boolean ajouterFils(Noeud f){
		
		((Noeud_LA) f).pere = this;
		liste_fils.add(((Noeud_LA)f));
		return true;		
	}
	
	//supprimer le noeud et donc son arborescence
	public void supprimer()
	{
		this.pere.liste_fils.remove(this.position());
		this.pere = null;
	}
	
	//position du noeud parmi ses freres
	public int position()
	{
		return this.pere.liste_fils.indexOf(this);
	}

	// accede au i-ieme fils du noeud
	public Noeud ieme(int i){
		try{
			return liste_fils.get(i);
		}
		catch(IndexOutOfBoundsException e){
			System.out.println("ne possede pas le fils en question");
			return null;
		}
		
	}
	
	public void affichage_la(){
		String ch = new String();
		Iterator<Noeud_LA> it = liste_fils.iterator(); // On paramètre Iterator par le type des éléments de la collection qui sera parcourue
		while (it.hasNext()) {
  			ch = ch + it.next().valeur+" , ";
		}
		System.out.println(valeur+" : "+ch);
		
		it = liste_fils.iterator();
		while (it.hasNext()) {
			it.next().affichage_la();
		}
	}
	
	public void affichage_na(){
		String ch = new String();
		Iterator<Noeud_LA> it = liste_fils.iterator(); // On paramètre Iterator par le type des éléments de la collection qui sera parcourue
		while (it.hasNext()) {
  			ch = ch + "{"+valeur+","+it.next().valeur+"}"+" , ";
		}
		if(pere!=null) ch = ch + "{"+valeur+","+pere.valeur+"}";
		System.out.println(valeur+" : "+ch);
		
		it = liste_fils.iterator();
		while (it.hasNext()) {
			it.next().affichage_na();
		}
	}	
	
}
