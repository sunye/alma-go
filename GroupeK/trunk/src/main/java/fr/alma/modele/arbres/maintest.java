package fr.alma.modele.arbres;

public class maintest {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		
		ArbreAdj<Integer> arbre=new ArbreAdj<Integer>(2, "test", 15);
		
		Node<Integer> t1=arbre.getNewNode();
		t1.setEtiquette("test1");
		t1.setValeur(0);
		
		Node<Integer> t2=arbre.getNewNode();
		t2.setEtiquette("test2");
		t2.setValeur(0);
		
		arbre.ajouterFils(arbre.racine(),t1, 2);
		arbre.ajouterFils(arbre.racine(),t2, 1);
		
		
	System.out.println(arbre.FrerePrec(arbre.iemeFils(arbre.racine(), 2)).getEtiquette());
		
		
	}

}
