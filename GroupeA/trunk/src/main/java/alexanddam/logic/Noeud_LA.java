package main.java.alexanddam.logic;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/** The class Noeud_LA is the concrete implementation of the abstract class AbstractNoeud.
* @author Alexandru Schiaucu
* @version 1.0
* @since  15 april 2010
* @see main.java.alexanddam.logic.AbstractNoeud
*/
public class Noeud_LA extends AbstractNoeud {

	public List<Noeud_LA> liste_fils;
	public Noeud_LA pere;
	public int hauteur;
	public boolean valeurPresent;
	//n'a pas besoin ni de la valeur etiquette ni de position	
	
	/**
	 * Constructor setting fields
	 */
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
	/**
	 * Constructor setting its value
	 * 
	 * @param val Value of the node
	 */
	public Noeud_LA(int val){
		this.valeur = val;
		this.liste_fils = new LinkedList<Noeud_LA>();
		this.hauteur = 1;
		
		this.vOrdi = new int[0];
		this.vAdver = new int[0];
	}
	
	/**
	 * Get a node from its value
	 * 
	 * @param val Value of the node to find
	 * @return The found node
	 */
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
	
	/**
	 * Override of the equals function
	 */
	@Override
	public boolean equals(Object obj) {
				
		if(obj instanceof Noeud_LA) {
			Noeud_LA noeud = (Noeud_LA) obj;
			return this == noeud;
		} else {
			return false;
		}
			
	}
	
	/**
	 * Add the node f as son if the list is not full
	 * 
	 * @param fils Node we want to add
	 * @return true if added, otherwise, false 
	 */
	public boolean ajouterFils(AbstractNoeud fils){
		((Noeud_LA) fils).pere = this;
		liste_fils.add(((Noeud_LA)fils));
		return true;		
	}
	
	/**
	 * Delete the node and its arborescence
	 */
	public void supprimer() {
		this.pere.liste_fils.remove(this.position());
		this.pere = null;
	}
	
	/**
	 * Get the position of the node between his brothers
	 * @return position the position of the node between his brothers
	 */
	public int position() {
		return this.pere.liste_fils.indexOf(this);
	}

	/**
	 * Get the nth son of the node
	 * 
	 * @param ieme nth son as integer 
	 * @return nth son as Node
	 */
	public AbstractNoeud ieme(int ieme){
		try{
			return liste_fils.get(ieme);
		} catch(IndexOutOfBoundsException e) {
			System.out.println("ne possede pas le fils en question");
			return null;
		}
		
	}
	
	/**
	 * Display the tree
	 */
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
	
	/**
	 * Display the tree
	 */
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
