package fr.alma.modele;

import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JOptionPane;


public class GoBan {

	private Pion[][] goban;
	private int nbBlanc;
	private int nbNoir;
	private int num=0;
	CouleurPion gagnant=CouleurPion.EMPTY;

	//private int[] modifier={1,-1};
	public static int TAILLE_GO_BAN=9;

	public GoBan() {
		this.goban = new Pion[9][9];
		this.nbBlanc =0;
		this.nbNoir = 0;
	}
	public Pion[][] getGoban() {
		return goban;
	}
	
	public int getNbBlanc() {
		return nbBlanc;
	}
	public int getNbNoir() {
		return nbNoir;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public void setGoban(Pion[][] goban) {
		this.goban = goban;
	}
	public void setNbBlanc(int nbBlanc) {
		this.nbBlanc = nbBlanc;
	}
	public void setNbNoir(int nbNoir) {
		this.nbNoir = nbNoir;
	}
	
	public CouleurPion getGagnant() {
		return gagnant;
	}
	public void setGagnant(CouleurPion gagnant) {
		this.gagnant = gagnant;
	}
	
	public boolean ajouterPion(int position, int positiony){
		//Si le nombre est pair alors c'est au blanc de jouer
		if (num%2==0){
			//On vérifie si le coup joué est légal avant de placer le pion sur le goban
			if (estLegal(position, positiony, CouleurPion.BLANC)){
				goban[position][positiony].setCouleur(CouleurPion.BLANC);
				goban[position][positiony].setNumero(num);
				goban[position][positiony].setListeLibertes();
				apresJoue(position, positiony, CouleurPion.BLANC);
				nbBlanc++;
				num++;
			}

		}else{
			//On vérifie si le coup joué est légal avant de placer le pion sur le goban
			if (estLegal(position, positiony, CouleurPion.NOIR)){
				goban[position][positiony].setCouleur(CouleurPion.NOIR);
				goban[position][positiony].setNumero(num);
				goban[position][positiony].setListeLibertes();
				apresJoue(position, positiony, CouleurPion.NOIR);
				nbNoir++;
				num++;
			}
		}
		return true;
	}

	
	public boolean ajouterPion(int position, int positiony, CouleurPion coul){
		boolean result;
		if(result=estLegal(position, positiony, coul)){
			goban[position][positiony].setCouleur(coul);
			goban[position][positiony].setNumero(num);
			goban[position][positiony].setListeLibertes();
			apresJoueSansEnlevement(position, positiony, coul);
			num++;
			if( coul.equals(CouleurPion.NOIR)){
			nbNoir++;
			}else{
			nbBlanc++;
			}
		}
		return result;
	}
	
	
	public boolean retirerPion(int position, int positiony, CouleurPion coul){
		//liste de groupes amis et ennemis
		Vector<Groupe> listVoisinsAmis = new Vector<Groupe>();
		Vector<Groupe> listVoisinsEnnemis = new Vector<Groupe>();

		visitVoisinsPion(position, positiony, coul, listVoisinsAmis, listVoisinsEnnemis);
		//ajouter ce pion en liberté des groupes ennemis
		Enumeration<Groupe> e = listVoisinsEnnemis.elements();
		   for(;e.hasMoreElements();) {
			   Groupe groupeCourant = e.nextElement();
			   groupeCourant.getLibertes().addElement(goban[position][positiony]);
		   }
		//ajouter ce pion en liberté du groupe ami
		   goban[position][positiony].getGroupe().getLibertes().addElement(goban[position][positiony]);
		   goban[position][positiony].getGroupe().getPions().removeElement(goban[position][positiony]);
		//supprimer ce pion
		   goban[position][positiony].setCouleur(CouleurPion.EMPTY);
		   goban[position][positiony].setGroupe(null);
		   goban[position][positiony].setListeLibertes();
		return true;
	}



	public boolean estLegal(int position, int positiony, CouleurPion coul) {

		//Vérifie si un pion n'est pas déjà présent à cet endroit
		if (goban[position][positiony].getCouleur() != CouleurPion.EMPTY){
			System.out.println("T'as pas vu qu'il y avait déjà un pion connard!");
			return false;
		}

		// vérifie qu'il reste au moins une liberté au pion joué
		if (countLiberties(position, positiony) > 0){
			System.out.println("Nombre de libertés supérieures à 0");
			return true;
		}

		Vector<Groupe> listVoisinsAmis = new Vector<Groupe>();
		Vector<Groupe> listVoisinsEnnemis = new Vector<Groupe>();

		// on remplit les 2 vecteurs avec les voisins amis et ennemis
		System.out.println("Remplissage des listes...");
		visitVoisinsPion(position, positiony, coul, listVoisinsAmis, listVoisinsEnnemis);


		/*// vérification de capture
		System.out.println("Vérif capture");
		Enumeration<Groupe> ennemis = listVoisinsEnnemis.elements();
		for (;ennemis.hasMoreElements();) {
			if (ennemis.nextElement().getLibertes().size() <= 1)
				System.out.println("Capture!");
				return true;
		}*/


		// vérification du suicide
		System.out.println("Vérif suicide");
		int libertesGroupe = 0;
		Enumeration<Groupe> amis = listVoisinsAmis.elements();
		for (;amis.hasMoreElements();) {
				libertesGroupe += amis.nextElement().getLibertes().size();
				libertesGroupe -= 1;
		}

		if (libertesGroupe > 0){
			System.out.println("Pas de suicide");
			return true;
		}else{
			System.out.println("Suicide!");
			return false;
		}
	}

	public int countLiberties(int x, int y) {
		int count = 0;

		if (x > 0){
			if (goban[x-1][y].getCouleur() == CouleurPion.EMPTY){
				count++;
			}
		}

		if (x < TAILLE_GO_BAN - 1){
			if (goban[x+1][y].getCouleur() == CouleurPion.EMPTY){
				count++;
			}
		}

		if (y > 0){
			if (goban[x][y-1].getCouleur()== CouleurPion.EMPTY){
				count++;
			}
		}

		if (y < TAILLE_GO_BAN - 1){
			if (goban[x][y+1].getCouleur() == CouleurPion.EMPTY){
				count++;
			}
		}

		return count;
	}

	public void visitVoisinsPion(int position, int positiony, CouleurPion coul, Vector<Groupe> listVoisinsAmis, Vector<Groupe> listVoisinsEnnemis){

		if (position > 0) {
			int currentX = position - 1;
			int currentY = positiony;
			System.out.println("Analyse voisins 1");
			analyseVoisins(position, positiony, coul, currentX, currentY, listVoisinsAmis, listVoisinsEnnemis);
		}

		if (positiony > 0) {
			int currentX = position;
			int currentY = positiony - 1;
			System.out.println("Analyse voisins 2");
			analyseVoisins(position, positiony, coul, currentX, currentY, listVoisinsAmis, listVoisinsEnnemis);
		}

		if (position < TAILLE_GO_BAN - 1) {
			int currentX = position + 1;
			int currentY = positiony;
			System.out.println("Analyse voisins 3");
			analyseVoisins(position, positiony, coul, currentX, currentY, listVoisinsAmis, listVoisinsEnnemis);
		}

		if (positiony < TAILLE_GO_BAN - 1) {
			int currentX = position;
			int currentY = positiony + 1;
			System.out.println("Analyse voisins 4");
			analyseVoisins(position, positiony, coul, currentX, currentY, listVoisinsAmis, listVoisinsEnnemis);
		}
	}

	public void analyseVoisins(int position, int positiony, CouleurPion coul, int currentX, int currentY, Vector<Groupe> listVoisinsAmis, Vector<Groupe> listVoisinsEnnemis){

		// si le voisin est libre alors on l'ajoute à la liste des libertés du pion joué
		// et on enleve le pion joué des libertés du voisins
		System.out.println(goban[currentX][currentY].getCouleur());
		System.out.println(coul);
		if (goban[currentX][currentY].getCouleur() == CouleurPion.EMPTY){
			System.out.println("Voisin Libre");
			goban[position][positiony].getListeLibertes().addElement(goban[currentX][currentY]);
			goban[currentX][currentY].getListeLibertes().removeElement(goban[position][positiony]);
		}

		// si le voisin est un ami, on l'ajoute à la liste d'amis
		else if (goban[currentX][currentY].getCouleur() == coul){
			System.out.println("Ajout liste d'amis");
			listVoisinsAmis.addElement(goban[currentX][currentY].getGroupe());
		}
		// si le voisin est un ennemi, on l'ajoute à la liste d'ennemis
		else{
			System.out.println("Ajout liste d'ennemis");
			listVoisinsEnnemis.addElement(goban[currentX][currentY].getGroupe());
		}
	}

	// mise à jour des groupes d'amis
	public void majListeAmis(int position, int positiony, CouleurPion coul, Vector<Groupe> listVoisinsAmis){

		// si aucun ami autour -> création d'un nouveau groupe
		if (listVoisinsAmis.size() == 0){
			System.out.println("Création d'un nouveau groupe");
			Groupe nouveauGroupe = new Groupe(goban[position][positiony]);
			goban[position][positiony].setGroupe(nouveauGroupe);
		}

		//ajout du pion joué à un groupe
		else if (listVoisinsAmis.size() == 1){
			System.out.println("Ajout du pion à un groupe existant");
			listVoisinsAmis.firstElement().getPions().addElement(goban[position][positiony]);
			// définition du groupe du pion
			goban[position][positiony].setGroupe(listVoisinsAmis.firstElement());
			//les libertés du pions sont ajoutées à celles du groupe
			listVoisinsAmis.firstElement().getLibertes().addAll(goban[position][positiony].getListeLibertes());
		}
		//le pion joué relie plusieurs groupes
		else{
			System.out.println("Connexions entre groupes");
			connexionGroupes(position, positiony, coul, listVoisinsAmis);
		}
	}


	// Connexion entre plusieurs groupes grâce au pion joué
	public void connexionGroupes(int position, int positiony, CouleurPion coul, Vector<Groupe> listVoisinsAmis){

		// premier groupe
		Groupe premierGroupe = listVoisinsAmis.firstElement();

		// ajout des autres groupes au premier
		Enumeration<Groupe> e = listVoisinsAmis.elements();
		//saute le premier groupe
		e.nextElement();
		for (;e.hasMoreElements();){

			Groupe unAutreGroupe = e.nextElement();

			// changement de groupe pour ts les pions de unAutreGroupe
			Enumeration<Pion> enu = unAutreGroupe.getPions().elements();
			for (;enu.hasMoreElements();){
				enu.nextElement().setGroupe(premierGroupe);
			}

			// ajout des pions et des libertés dans le premier groupe
			premierGroupe.getLibertes().addAll(unAutreGroupe.getLibertes());
			premierGroupe.getPions().addAll(unAutreGroupe.getPions());

		}

		// ajout du pion joué à ce premier groupe

		premierGroupe.getPions().addElement(goban[position][positiony]);
		// définition du groupe du pion
		goban[position][positiony].setGroupe(premierGroupe);
		//les libertés du pions sont ajoutées à celles du groupe
		premierGroupe.getLibertes().addAll(goban[position][positiony].getListeLibertes());
	}

	// actions une fois le pion joué sur le goban
	public void apresJoue(int position, int positiony, CouleurPion coul) {
		System.out.println("Apres joué");
		// on fait les listes d'amis et d'ennemis
		Vector<Groupe> listVoisinsAmis = new Vector<Groupe>();
		Vector<Groupe> listVoisinsEnnemis = new Vector<Groupe>();


		visitVoisinsPion(position, positiony, coul, listVoisinsAmis, listVoisinsEnnemis);

		// une fois le pion joué, on le supprime des libertés des groupes voisins
		enleveLibertesVoisins(position, positiony, coul, listVoisinsAmis, listVoisinsEnnemis);

		// maj des groupes amis
		majListeAmis(position, positiony, coul, listVoisinsAmis);

		// maj des groupes ennemis (Prises)
		majListeEnnemis(position, positiony, coul, listVoisinsEnnemis);
	}
	
	public void apresJoueSansEnlevement(int position, int positiony, CouleurPion coul) {
		System.out.println("Apres joué");
		// on fait les listes d'amis et d'ennemis
		Vector<Groupe> listVoisinsAmis = new Vector<Groupe>();
		Vector<Groupe> listVoisinsEnnemis = new Vector<Groupe>();


		visitVoisinsPion(position, positiony, coul, listVoisinsAmis, listVoisinsEnnemis);

		// une fois le pion joué, on le supprime des libertés des groupes voisins
		enleveLibertesVoisins(position, positiony, coul, listVoisinsAmis, listVoisinsEnnemis);

		// maj des groupes amis
		majListeAmis(position, positiony, coul, listVoisinsAmis);

	}
	

	public void enleveLibertesVoisins(int position, int positiony, CouleurPion coul, Vector<Groupe> listVoisinsAmis, Vector<Groupe> listVoisinsEnnemis) {

		// Pour les amis
		Enumeration<Groupe> e = listVoisinsAmis.elements();
		for (;e.hasMoreElements();) {
			Groupe groupeCourant = e.nextElement();
			System.out.println("enleve liberte d'un groupe ami");
			groupeCourant.getLibertes().removeElement(goban[position][positiony]);
		}

		// Pour les ennemis
		e = listVoisinsEnnemis.elements();
		System.out.println("nbre d'ennemis:"+listVoisinsEnnemis.size());
		for (;e.hasMoreElements();) {
			Groupe groupeCourant = e.nextElement();
			System.out.println("enleve liberte d'un groupe ennemi");
			groupeCourant.getLibertes().removeElement(goban[position][positiony]);
		}
	}

	// mise à jour des groupes d'ennemis
	public void majListeEnnemis(int position, int positiony, CouleurPion coul, Vector<Groupe> listVoisinsEnnemis) {

	   Enumeration<Groupe> e = listVoisinsEnnemis.elements();
	   for(;e.hasMoreElements();) {
	     Groupe groupeCourant = e.nextElement();
	     // si un groupe n'a plus de libertés alors il est pris
	     if (groupeCourant.aucuneLibertes()) {
	    	 
	       // tous les pions du groupe pris passe donc dans la couleur EMPTY
	       Enumeration<Pion> listePions = groupeCourant.getPions().elements();
	       for(;listePions.hasMoreElements();) {
	         Pion pionCourant = listePions.nextElement();

	         pionCourant.setCouleur(CouleurPion.EMPTY);

	       }
	       this.gagnant=groupeCourant.coulOppose(groupeCourant.getCoul());
	     }
	   }
	}



	public void init(){

		for (int i=0; i<9; i++)
			for (int k=0; k<9; k++) {
				goban[i][k] = new Pion(CouleurPion.EMPTY, 0);
			}
	}
	
	public void remiseZero(){

		for (int i=0; i<9; i++)
			for (int k=0; k<9; k++) {
				goban[i][k] = new Pion(CouleurPion.EMPTY, 0);
				goban[i][k].setNumero(-1);
				goban[i][k].setGroupe(null);
				goban[i][k].setListeLibertes();
			}
		this.setNbNoir(0);
		this.setNbBlanc(0);
		this.setNum(0);
		this.setGagnant(CouleurPion.EMPTY);
	}

}
