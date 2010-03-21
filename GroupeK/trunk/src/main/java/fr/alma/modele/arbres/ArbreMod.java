package fr.alma.modele.arbres;
import java.util.ArrayList;

public class ArbreMod<E> extends ArbreAbstract<E>{


	private	ArrayList<NodeMod<E>> tabMod;
	
	//les fils d'un noeud sont en (k*i)+1 a k(i+1);
	// ou la position max est (k*i)+arrité
	//i position du noeud
	//k arrité
	
	
	public ArbreMod(int arrite, String etiq, E valeur){
		super(arrite);
		this.tabMod=new ArrayList<NodeMod<E>>();
		//ajout de null pour permettre d'initialiser correctement l'arbre
		// et qu'on puisse rajouter la racine simplement.
		
		tabMod.add(new NodeMod<E>( 0,etiq,valeur, null));
		for (int i=0; i<arrite ; i++){
			tabMod.add(null);
		}
	
		
	}


	@Override
	public Node<E> FrerePrec(Node<E> n) throws ConflitArriteException {
		
		NodeMod<E> temp = (NodeMod<E>) n;
		 
		int perepos= temp.getPere().getPosition();
		int pos= temp.getPosition();
		int numFils=pos-(perepos*getArrite());
		if (numFils==1){
			throw new ConflitArriteException();
		}else {
			return this.tabMod.get(pos-1);
		}
		
		
	}


	@Override
	public Node<E> FrereSuiv(Node<E> n) throws ConflitArriteException {
		NodeMod<E> temp = (NodeMod<E>) n;
		 
		int perepos= temp.getPere().getPosition();
		int pos= temp.getPosition();
		int numFils=pos-(perepos*getArrite());
		if (numFils>=getArrite()){
			throw new ConflitArriteException();
		}else {
			return this.tabMod.get(pos+1);
		}
			
	}


	@Override
	public Node<E> Rechercher(String etiq) {
		
		boolean trouve=false;
		int i=0;
		NodeMod<E> temp=null;
		
		while (!trouve  || i <=tabMod.size()){
		temp=this.tabMod.get(i) ;
		if ( temp.getEtiquette().equals(etiq)){
		trouve=true;	
		}
			i++;
		}
		if (trouve) return temp;   
		
		return null;
	}


	@Override
	public void ajouterFils(Node<E> pere, Node<E> fils) throws ConflitFilsException  {
	
		NodeMod<E> pereMod=(NodeMod<E>) pere;  	
		NodeMod<E> filsMod=(NodeMod<E>) fils;
		
		int posfils=(getArrite()*pereMod.getPosition())+1;
		filsMod.setPosition(posfils);
//si position du fils trop loin on rajoute des nulls
		while (this.tabMod.size()-1<(posfils)){
			tabMod.add(null);
	}
		
		if (this.tabMod.get(posfils)!=null){
			throw new ConflitFilsException();
		}else
		{
		this.tabMod.set(posfils, filsMod);
		filsMod.setPere(pereMod);
		//FIXME REVOIR AGRANDISSEMENT DU TABLEAU
		
		}
	}


	@Override
	public void ajouterFils(Node<E> pere, Node<E> fils, int ieme) throws  ConflitFilsException, ConflitArriteException {
		if (ieme<=this.getArrite() && ieme>0){
				
			NodeMod<E> pereMod=(NodeMod<E>) pere;  	
			NodeMod<E> filsMod=(NodeMod<E>) fils;
			int pospourfils= (pereMod.getPosition()*this.getArrite())+ieme;
			
			filsMod.setPosition(pospourfils);
			
			
			//si position du fils trop loin on rajoute des nulls
			while (this.tabMod.size()-1<(pospourfils)){
					tabMod.add(null);
			}
			
			
			if (this.tabMod.get(pospourfils)!=null){
				throw new ConflitFilsException();
			}else{
				
				
				
			this.tabMod.set(pospourfils, filsMod);
					
			filsMod.setPere(filsMod);
			
			
			
			
			}
		}else {
			throw new ConflitArriteException();
		}
		
		
		
		
	}


	@Override
	public Node<E> iemeFils(Node<E> n, int ieme) throws ConflitArriteException {
		if (ieme<=this.getArrite() && ieme>0 ){
			NodeMod<E> pere=(NodeMod<E>) n;
			int posfils= (pere.getPosition()*getArrite())+ieme;
			
			return this.tabMod.get(posfils);
		}else {
			throw new ConflitArriteException();
		}
	}


	@Override
	public Node<E> pere(Node<E> n) {
		
		return((NodeMod<E>) n).getPere();
	}


	@Override
	public int positionFratrie(Node<E> n) {
		NodeMod<E> temp = (NodeMod<E>) n;
		 
		int perepos= temp.getPere().getPosition();
		int pos= temp.getPosition();
		return pos-(perepos*getArrite());
		
	}


	@Override
	public Node<E> racine() {
		return this.tabMod.get(0);
	}


	@Override
	public void supprimer() {
		this.tabMod.clear();
		
	}
	
	// on laisse dans la signature le mod, mais à chaque utilisation on convertira en fonction de ce qu'on a
	//le typage du type de node se fera a voir au fur et à mesure si ça va pas ête compliqué et pour le respect des types et complexité
	
	public Node<E> getNewNode(){
		return new NodeMod<E>();
	}
	
	
	
	public boolean estFeuille(Node<E> n){
		NodeMod<E> temp = (NodeMod<E>) n;
		boolean result=false;
		if(n!=null){
		int pospremierfils=(temp.getPosition()*getArrite())+1;
		int posdernierfils=(temp.getPosition()*getArrite())+getArrite();
		
		for (int i=pospremierfils; i<=posdernierfils; i++ ){
			if (this.tabMod.get(i)!=null){
				result=true;
			}
		}
		}

		
		return !result;
	}


	@Override
	public void supprimerIeme(Node<E> pere, int ieme) throws ConflitArriteException {
		NodeMod<E> temp = (NodeMod<E>) pere;
		
		if (this.iemeFils(temp, ieme)!=null ){
			if (!estFeuille(this.iemeFils(pere, ieme))){
			for (int i=1; i<=this.getArrite();i++){
				supprimerIeme(this.iemeFils(pere, ieme), i);
			}
			}
			int pospourfils= (temp.getPosition()*this.getArrite())+ieme;
			this.tabMod.set(pospourfils, null);	
	}
		
	}
	

	public boolean est_sous_feuille(Node<E> pere) {
		
		boolean result=true;
		
		for (int i=1; i<=getArrite();i++){
			try {
				result=result&&estFeuille(this.iemeFils(pere, i));
			} catch (ConflitArriteException e) {
			}
		}
		
		return result;
	}
	
	
}
