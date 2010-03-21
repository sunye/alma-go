package fr.alma.modele.arbres;

public interface Node<E> {

		public String getEtiquette() ;

		public void setEtiquette(String etiq);
		
		public E getValeur();
		public void setValeur(E val);
		public Node<E> getPere();
		
		
		
		
}
