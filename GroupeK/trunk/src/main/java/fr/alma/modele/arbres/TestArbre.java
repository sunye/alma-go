package fr.alma.modele.arbres;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;


@SuppressWarnings("unchecked")
public class TestArbre {

	
	
	

	public static void testEquilibre( Arbre a){
		//parcour de l'arbre
		//choper les feuilles
		//chopper les profondeurs des feuilles et voir
		int profondeur=0;
		boolean testHauteur=true;
		LinkedList<Node> feuilles= new LinkedList<Node>();
		
		Queue<Node> file =new LinkedList<Node>();
		
		Node n=null;
		Node temp=null;
		file.offer(a.racine());
		while (!file.isEmpty()){
		n=file.remove();
		if (a.estFeuille(n)){
			feuilles.add(n);
		}
		if (n!=null){
		for (int i=0 ; i<a.getArrite();i++){
			try {
				temp=a.iemeFils(n, i);
			} catch (ConflitArriteException e) {}
			if (temp!=null){
				file.offer(temp);
			}
		}
		}
		}
		
		if (feuilles.size()!=0){
		Iterator<Node> itefeuille=feuilles.iterator();
		
		Node tempnod=itefeuille.next();
		 profondeur=a.profondeur(tempnod);
	
		
		while (itefeuille.hasNext())
		{
			tempnod=itefeuille.next();
			if (profondeur!=a.profondeur(tempnod)){
				testHauteur=false;
			}
		}
	}
		if (testHauteur){
			System.out.println("l'arbre est équilibré avec une profondeur des feuilles de " + profondeur);
		}else{
			System.out.println("l'arbre n'est pas équilibré");
		}
	}
	
	
	public static void testCompletude( Arbre a){
		//parcours de l'arbre 
		//et pour chaque noeud on regarde si ya le compte de noeud de l'arrité
	
		Queue<Node> file =new LinkedList<Node>();
		boolean complet=true;
		Node n=null;
		Node temp=null;
		file.offer(a.racine());
		while (!file.isEmpty()){
		n=file.remove();
		
		if (n!=null){
		for (int i=0 ; i<a.getArrite();i++){
			try {
				temp=a.iemeFils(n, i);
			} catch (ConflitArriteException e) {}
			if (temp!=null){
				file.offer(temp);
			}
			else if (!a.estFeuille(n)){
				complet=false;
			}
		}
			
			
		}
		}
		
		if (complet){
			System.out.println("l'arbre est complet");
		}else{
			System.out.println("l'arbre n'est pas complet");
		}
		
	}
	

	public static void testKArite(Arbre a){
		
	}
	
	
	public static void calculHauteur(Arbre a){
		Queue<Node> file =new LinkedList<Node>();
		int profondeur=0;
		int tempprofond=0;
		Node n=null;
		Node temp=null;
		file.offer(a.racine());
		while (!file.isEmpty()){
		n=file.remove();
		tempprofond=a.profondeur(n);
		if (tempprofond>profondeur){
			profondeur=tempprofond;
		}
		
		if (n!=null){
		for (int i=0 ; i<a.getArrite();i++){
			try {
				temp=a.iemeFils(n, i);
			} catch (ConflitArriteException e) {}
			if (temp!=null){
				file.offer(temp);
			}
		}
			
			
		}
		}
		System.out.println("La hauteur de l'arbre est: "+profondeur);
		
		
		
	}
	
	public static void calculArrite(Arbre a){
		System.out.println("Arrité de l'arbre: " + a.getArrite());
	}
	
	
	
	
	
	
	
	public static Arbre generation(){
		return null;
	}
	
}
