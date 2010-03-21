package fr.alma.modele.arbres;

import java.util.LinkedList;

public class ArbreAdj<E> extends ArbreAbstract<E> {

	
	private NodeAdj<E> racine;
	
	
	public ArbreAdj(int arrite, String etiq, E valeur ){
		super (arrite);
		this.racine=new NodeAdj<E>(arrite);
		this.racine.setEtiquette(etiq);
		this.racine.setValeur(valeur);
		
	}
	
	
	
	@Override
	public Node<E> FrerePrec(Node<E> n) {
		
		NodeAdj<E> fils=(NodeAdj<E>) n;
		NodeAdj<E> pere= fils.getPere();
		LinkedList<NodeAdj<E>> filss=pere.getFils();
		for(int i=1; i<filss.size();i++ ){
			if (fils.equals(filss.get(i))){
				return filss.get(i-1);
			}
				
			
		}
		
		return null;
	}

	@Override
	public Node<E> FrereSuiv(Node<E> n) {
		NodeAdj<E> fils=(NodeAdj<E>) n;
		
		NodeAdj<E> pere= fils.getPere();
		LinkedList<NodeAdj<E>> filss=pere.getFils();
		for(int i=0; i<filss.size();i++ ){
			if (fils.equals(filss.get(i))){
				if (i+1<=getArrite()){
				return filss.get(i+1);
				}else{
					return null;
				}
			}
				
			
		}
		return null;
	}

	@Override
	public Node<E> Rechercher(String etiq) {
		return racine.rechercher(etiq);
	}

	@Override
	public void ajouterFils(Node<E> pere, Node<E> fils) throws ConflitFilsException {
		NodeAdj<E> perenod=(NodeAdj<E>) pere;
		NodeAdj<E> filnod=(NodeAdj<E>) fils;
		
		
		
		if (perenod.getFils().get(0)!=null){
			throw new ConflitFilsException();
		}else{
			perenod.getFils().set(0, filnod);
			filnod.setPere(perenod);
		}
		
		
	}

	@Override
	public void ajouterFils(Node<E> pere, Node<E> fils, int ieme) throws ConflitArriteException, ConflitFilsException {
		
		if (ieme<=getArrite()){
		
		
		NodeAdj<E> perenod=(NodeAdj<E>) pere;
		NodeAdj<E> filnod=(NodeAdj<E>) fils;
		
		if (perenod.getFils().get(ieme-1)!=null){
		
			throw new ConflitFilsException();
		}else{
			if (filnod!=null){
			filnod.setPere(perenod);
			}
			perenod.getFils().set(ieme-1, filnod);
		}
				
		}else {
			throw new ConflitArriteException();
		}
		
		
	}

	@Override
	public Node<E> iemeFils(Node<E> n, int ieme) throws ConflitArriteException {
		NodeAdj<E> perenod=(NodeAdj<E>) n;
		
		
		if (ieme<=this.getArrite() && ieme>0 ){
			
			
			return perenod.getFils().get(ieme-1);
		}else {
			throw new ConflitArriteException();
		}
	}

	@Override
	public Node<E> pere(Node<E> n) {
		 NodeAdj<E> nodadj=(NodeAdj<E>)n;
		return nodadj.getPere();
	}

	@Override
	public int positionFratrie(Node<E> n) {
		NodeAdj<E> fils= (NodeAdj<E>) n;
		NodeAdj<E> pere= fils.getPere();
		int result=0;
		
		for (int i=0; i<getArrite(); i++){
			if (fils.equals(pere.getFils().get(i))){
				result=i;
			}
		}
		
		
		return result;
	}

	@Override
	public Node<E> racine() {
		return racine;
	}

	@Override
	public void supprimer() {
		racine=null;

	}

	
	public Node<E> getNewNode(){
		return new NodeAdj<E>(this.getArrite());
	}
	
	public boolean estFeuille(Node<E> n){
		NodeAdj<E> nod=(NodeAdj<E>) n;
		boolean result =false;
		if (n==null){
			result=true;
		}else{
			result=!nod.hasFils();
		}
		return result;
	}



	public void supprimerIeme(Node<E> pere, int ieme) throws ConflitArriteException {
		NodeAdj<E> nod=(NodeAdj<E>) pere;
		
		if (this.iemeFils(nod, ieme)!=null  ){
			if(!this.estFeuille(this.iemeFils(nod, ieme))){
			
			for (int i=1; i<=getArrite();i++){
				supprimerIeme( this.iemeFils(nod, ieme),  i);
			}}
			nod.getFils().set(ieme-1, null);
		}
		
	}



	@Override
	public boolean est_sous_feuille(Node<E> pere) {
	
		boolean result=true;
		
		
		for (int i=1; i<=getArrite();i++){
			try {
				result=result&&estFeuille(this.iemeFils(pere, i));
			} catch (ConflitArriteException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	

	
	
}
