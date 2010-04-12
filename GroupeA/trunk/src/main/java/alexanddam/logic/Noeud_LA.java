package main.java.alexanddam.logic;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Noeud_LA extends AbstractNoeud {

	public List<Noeud_LA> liste_fils;
	public Noeud_LA pere;
	public int hauteur;
	public boolean valeurPresent;
	//n'a pas besoin ni de la valeur etiquette ni de position	
	
	public Noeud_LA(int tour, int coordX, int coordY, int[] voProp, int[] vaProp) {
		
		int iVect = 0;
		this.liste_fils = new LinkedList<Noeud_LA>();
		
		switch(tour) {
		
		case 0:
			    // le vecteur voProp est null => le pere est la racine 
				if(voProp.length == 0) {
					this.vOrdi = new int[2];
					this.vAdver = new int[0];
					
					this.vOrdi[0] = coordX;
					this.vOrdi[1] = coordY;
				} else {
									
					this.vOrdi = new int[voProp.length + 2];  // c'est le tour de l'ordinateur
					this.vAdver = new int[vaProp.length];   
				
					for(iVect = 0; iVect < voProp.length ; iVect++){
						this.vOrdi[iVect] = voProp[iVect];
					}
					
					this.vOrdi[iVect++] = coordX;  this.vOrdi[iVect] = coordY;  
					
					for(iVect = 0; iVect < vaProp.length ; iVect++){
						this.vAdver[iVect] = vaProp[iVect];
					}
				}
				
			break; 
			
		case 1:  // tour de l'adversaire
			
				if(vaProp.length == 0) { // c'est le premiere coup de l'adversaire dans le devellopement de l'arbre
				
					this.vAdver = new int[2];
					this.vAdver[0] = coordX; this.vAdver[1] = coordY;
					
					this.vOrdi = new int[2];
					
					this.vOrdi[0] = voProp[0];
					this.vOrdi[1] = voProp[1];
					
				} else {
					
					this.vAdver = new int[vaProp.length + 2];  // c'est le tour de l'adversaire
					this.vOrdi = new int[voProp.length];   
				
					for(iVect = 0; iVect < vaProp.length ; iVect++){
						this.vAdver[iVect] = vaProp[iVect];
					}
					
					this.vAdver[iVect++] = coordX;  this.vAdver[iVect] = coordY;  
					
					for(iVect = 0; iVect < voProp.length ; iVect++){
						this.vOrdi[iVect] = voProp[iVect];
					}
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
		
		this.vOrdi = new int[0];
		this.vAdver = new int[0];
	}
	
	public Noeud_LA node(int val){
		
		if(val==valeur){// on se trouve dans le bon noeud
			return this;			
		} else{//sinon on recherche au noeud suivant...
			
			Iterator<Noeud_LA> iter = liste_fils.iterator(); 
			
			while (iter.hasNext()) {
  				Noeud_LA fils = iter.next();				
   				if(fils.node(val)!=null) { 
   					return fils.node(val);
   				}
			}	
  		}
		return null;
	}
	
	@Override
	public boolean equals(Object obj) {
				
		if(obj instanceof Noeud_LA) {
			Noeud_LA noeud = (Noeud_LA) obj;
			return this == noeud;
		} else {
			return false;
		}
			
	}
	
	// ajoute le noeud f en tant que fils, ne marche que si la liste n'est pas pleine
	public boolean ajouterFils(AbstractNoeud fils){
		((Noeud_LA) fils).pere = this;
		liste_fils.add(((Noeud_LA)fils));
		return true;		
	}
	
	//supprimer le noeud et donc son arborescence
	public void supprimer() {
		this.pere.liste_fils.remove(this.position());
		this.pere = null;
	}
	
	//position du noeud parmi ses freres
	public int position() {
		return this.pere.liste_fils.indexOf(this);
	}

	// accede au i-ieme fils du noeud
	public AbstractNoeud ieme(int ieme){
		try{
			return liste_fils.get(ieme);
		} catch(IndexOutOfBoundsException e) {
			System.out.println("ne possede pas le fils en question");
			return null;
		}
		
	}
	
	public void affichageLA(){
		String str = new String();
		Iterator<Noeud_LA> iter = liste_fils.iterator(); // On paramètre Iterator par le type des éléments de la collection qui sera parcourue
		while (iter.hasNext()) {
  			str = str + iter.next().valeur+" , ";
		}
		System.out.println(valeur+" : "+str);
		
		iter = liste_fils.iterator();
		while (iter.hasNext()) {
			iter.next().affichageLA();
		}
	}
	
	public void affichageNA(){
		String str = new String();
		Iterator<Noeud_LA> iter = liste_fils.iterator(); // On paramètre Iterator par le type des éléments de la collection qui sera parcourue
		while (iter.hasNext()) {
  			str = str + "{"+valeur+","+iter.next().valeur+"}"+" , ";
		}
		if(pere!=null){
			str = str + "{"+valeur+","+pere.valeur+"}";
		}
		System.out.println(valeur+" : "+str);
		
		iter = liste_fils.iterator();
		while (iter.hasNext()) {
			iter.next().affichageNA();
		}
	}	
	
}
