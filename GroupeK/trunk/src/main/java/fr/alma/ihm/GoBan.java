package fr.alma.ihm;

import java.util.Enumeration;
import java.util.Vector;


public class GoBan {

	private Pion[][] goban;
	private int nbBlanc;
	private int nbNoir;
	private Integer num=0;

	private int[] modifier={1,-1};
	public static int TAILLE_GO_BAN=9;

	public GoBan() {
		this.goban = new Pion[9][9];
		this.nbBlanc =0;
		this.nbNoir = 0;
	}


	public boolean ajouterPion(int position, int positiony){
		int nbLib;
		//int prise;
		//if (goban[position][positiony].getCouleur()==CouleurPion.EMPTY){
		//Si le nombre est pair alors c'est au blanc de jouer
		if (num%2==0){
			//Vérification que le pion joué n'est pas en suicide
			//nbLib=liberte(position,positiony,CouleurPion.NOIR);
			//System.out.println(nbLib);
			//if (nbLib!=0){
			if (estLegal(position, positiony, CouleurPion.BLANC)){
				goban[position][positiony]= new Pion(CouleurPion.BLANC,num);
				apresJoue(position, positiony, CouleurPion.BLANC);
				//Vérification si ce pion réalise une prise d'un pion noir
				//prise(position,positiony,CouleurPion.NOIR);
				nbBlanc++;
				num++;}/*
				}else{
					System.out.println("Suicidaire!");
				}*/

		}else{
			//Vérification que le pion joué n'est pas en suicide
			//nbLib=liberte(position,positiony,CouleurPion.BLANC);
			//System.out.println(nbLib);
			//if (nbLib!=0){
			if (estLegal(position, positiony, CouleurPion.NOIR)){
				goban[position][positiony]= new Pion(CouleurPion.NOIR,num);
				apresJoue(position, positiony, CouleurPion.NOIR);
				//Vérification si ce pion réalise une prise d'un pion blanc
				//prise(position,positiony,CouleurPion.BLANC);
				nbNoir++;
				num++;}/*
				}else{
					System.out.println("Suicidaire!");
				}*/
		}/*
		}else{
			System.out.println("T'as pas vu qu'il y avait déjà un pion connard!");
		}*/
		return true;
	}


	/*public int liberte(int positionx, int positiony, CouleurPion cool){
		int result=0;
		int modifierTemp=0;

		for (int i=0; i<this.modifier.length;i++){
			modifierTemp=this.modifier[i];
			result+=calculLiberte(positionx+modifierTemp, positiony, modifierTemp*-1, 0, cool);

		}
		for (int i=0; i<this.modifier.length;i++){
			modifierTemp=this.modifier[i];
			result+=calculLiberte(positionx, positiony+modifierTemp, 0, modifierTemp*-1, cool);

		}
		return result;


	}

	private int calculLiberte(int positionx, int positiony, int xinterdit, int yinterdit,CouleurPion cool){
		if (positionx<TAILLE_GO_BAN&&positionx>=0&& positiony>=0&&positiony<TAILLE_GO_BAN){
			Pion actuel=goban[positionx][positiony];
			if(actuel==null){
				return 1;
			}else if (actuel.getCouleur()!=cool){
				return 0;
			} else {
				int result=0;
				int modifierTemp=0;
				//on fait deux boucle sur le modifier
				//on fait attention a ne pas retourner sur nos pas
				for (int i=0; i<this.modifier.length;i++){
					modifierTemp=+this.modifier[i];
					if (modifierTemp!=xinterdit){
						result+=calculLiberte(positionx+modifierTemp, positiony, modifierTemp*-1, 0, cool);
					}
				}
				for (int i=0; i<this.modifier.length;i++){
					modifierTemp=+this.modifier[i];
					if (modifierTemp!=yinterdit){
						result+=calculLiberte(positionx, positiony+modifierTemp, 0, modifierTemp*-1, cool);
					}
				}
				System.out.println("result :"+result);
				return result;
			}
		}
		return 0;
	}
	 */

	public boolean estLegal(int position, int positiony, CouleurPion coul) {
		int x, y;
		CouleurPion couleur;

		x = position;
		y = positiony;
		couleur = coul;

		if (goban[x][y].getCouleur() != CouleurPion.EMPTY){
			System.out.println("T'as pas vu qu'il y avait déjà un pion connard!");
			return false;
		}

		// vérifie qu'il reste au moins une liberté au pion joué
		if (countLiberties(x, y) > 0){
			System.out.println("Nombre de libertés supérieures à 0");
			return true;
		}

		Vector<Groupe> listVoisinsAmis = new Vector<Groupe>();
		Vector<Groupe> listVoisinsEnnemis = new Vector<Groupe>();

		// on remplit les 2 vecteurs avec les voisins amis et ennemis
		System.out.println("Remplissage des listes...");
		visitVoisinsPion(position, positiony, coul, listVoisinsAmis, listVoisinsEnnemis);

		// maj du groupe des amis
		//System.out.println("maj du groupe...");
		//majListeAmis(position, positiony, coul, listVoisinsAmis);

		/*// check if we would capture anything
		Enumeration<Pion> ennemis = listVoisinsEnnemis.elements();
		for (;ennemis.hasMoreElements();) {
			if ((ennemis.nextElement()).getListeLibertes().size() <= 1)
				return true;
		}*/

		// vérification du suicide
		System.out.println("Vérif suicide");
		int libertesGroupe = 0;
		System.out.println(listVoisinsAmis.size());
		Enumeration<Groupe> amis = listVoisinsAmis.elements();
		for (;amis.hasMoreElements();) {
				libertesGroupe += amis.nextElement().getLibertes().size();
				libertesGroupe -= 1;
				System.out.println(libertesGroupe);
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
			goban[position][positiony].getListeLibertes().add(goban[currentX][currentY]);
			goban[currentX][currentY].getListeLibertes().remove(goban[position][positiony]);
		}

		// si le voisin est un ami, on l'ajoute à la liste d'amis
		else if (goban[currentX][currentY].getCouleur() == coul){
			System.out.println("Ajout liste d'amis");
			listVoisinsAmis.add(goban[currentX][currentY].getGroupe());
			System.out.println("Libertés du voisin ami:"+goban[currentX][currentY].getGroupe().getLibertes().size());
		}
		// si le voisin est un ennemi, on l'ajoute à la liste d'ennemis
		else{
			System.out.println("Ajout liste d'ennemis");
			listVoisinsEnnemis.add(goban[currentX][currentY].getGroupe());
			System.out.println("Libertés du voisin ennemi:"+goban[currentX][currentY].getGroupe().getLibertes().size());
		}
	}

	// mise à jour du groupe des amis
	public void majListeAmis(int position, int positiony, CouleurPion coul, Vector<Groupe> listVoisinsAmis){

		// si aucun ami autour -> création d'un nouveau groupe
		if (listVoisinsAmis.size() == 0){
			System.out.println("Création d'un nouveau groupe");
			Groupe newGroupe = new Groupe(goban[position][positiony]);
			goban[position][positiony].setGroupe(newGroupe);
			System.out.println(newGroupe.getLibertes().size());
		}

		//ajout du pion joué à un groupe
		else if (listVoisinsAmis.size() == 1){
			System.out.println("Ajout du pion à un groupe existant");
			listVoisinsAmis.firstElement().getPions().add(goban[position][positiony]);
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

		premierGroupe.getPions().add(goban[position][positiony]);
		// définition du groupe du pion
		goban[position][positiony].setGroupe(premierGroupe);
		//les libertés du pions sont ajoutées à celles du groupe
		premierGroupe.getLibertes().addAll(goban[position][positiony].getListeLibertes());
	}

	// actions une fois le pion joué
	public void apresJoue(int position, int positiony, CouleurPion coul) {
		System.out.println("Apres joué");
		// on refait les listes d'amis et d'ennemis
		Vector<Groupe> listOfNeighborFriends = new Vector<Groupe>();

		Vector<Groupe> listOfNeighborEnemies = new Vector<Groupe>();

		visitVoisinsPion(position, positiony, coul,
				listOfNeighborFriends,
				listOfNeighborEnemies);

		//le pioon joué n'est plus une liberté pour les groupes amis et ennemis
		enleveLibertesVoisins(position, positiony, coul,
				listOfNeighborFriends,
				listOfNeighborEnemies);

		// update the situation of the friend groups (listOfNeighborFriends) after the last move (stone)
		majListeAmis(position, positiony, coul, listOfNeighborFriends);

		// take care of captured stones
		/*updateStonesEnemyGroups(position, positiony, coul,
				listOfNeighborEnemies,
				changedLocations);*/
	}

	public void enleveLibertesVoisins(int position, int positiony, CouleurPion coul,
			Vector<Groupe> listOfNeighborFriends,
			Vector<Groupe> listOfNeighborEnemies) {

		Enumeration<Groupe> e = listOfNeighborFriends.elements();
		for (;e.hasMoreElements();) {
			Groupe currentGroup = (Groupe)e.nextElement();
			System.out.println("enleve liberte d'un groupe ami");
			System.out.println("avt :"+currentGroup.getLibertes().size());
			currentGroup.getLibertes().remove(goban[position][positiony]);
			System.out.println("apr :"+currentGroup.getLibertes().size());
		}


		e = listOfNeighborEnemies.elements();
		System.out.println("nbre d'ennemis:"+listOfNeighborEnemies.size());
		for (;e.hasMoreElements();) {
			Groupe currentGroup = (Groupe)e.nextElement();
			System.out.println("enleve liberte d'un groupe ennemi");
			System.out.println("avt :"+currentGroup.getLibertes().size());
			currentGroup.getLibertes().remove(goban[position][positiony]);
			System.out.println("apr :"+currentGroup.getLibertes().size());
		}
	}













	// update the situation of the enemy groups (listOfNeighborEnemies) after the last move (stone)
	/*  private void updateStonesEnemyGroups(int position, int positiony, CouleurPion coul,
			  Vector<Groupe> listOfNeighborEnemies,
			  Vector<Groupe> changedLocations) {

	   Enumeration<Groupe> e = listOfNeighborEnemies.elements());
	   for(;e.hasMoreElements();) {
	     Groupe currentGroup = (Groupe)e.nextElement();
	     if (currentGroup.hasNoLiberty()) {
	       // update the captured stones info
	       if (currentGroup.getCoul() == CouleurPion.NOIR){
	    	   nrBlackPrisoners += currentGroup.nrStones(); }
	       else { nrWhitePrisoners += currentGroup.nrStones(); }

	       // add the stones of this group to the list of changed locations
	       changedLocations.appendList(currentGroup.stones);

	       // iterate for all the stones in the "to be captured" enemy group
	       Enumeration stonesList = currentGroup.stones.getElements();
	       for(;stonesList.hasMoreElements();) {
	         GobanLocation currentStone = (GobanLocation)stonesList.nextElement();

	         // update the liberties after removing currentStone
	         Enumeration enemyList = (currentStone.getNeighborEnemies()).getElements();
	         for (;enemyList.hasMoreElements();) {
	           Group currentEnemy = (Group)enemyList.nextElement();
	           currentEnemy.liberties.add(currentStone);
	         }

	         // we actually remove the currentStone here
	         currentStone.state = EMPTY;

	         if (currentGroup.color == BLACK)
	           blackGroups.deleteElement(currentGroup);
	         else
	         if (currentGroup.color == WHITE)
	           whiteGroups.deleteElement(currentGroup);
	       } // for
	     } // if
	   } // for
	  }*/

	public Pion[][] getGoban() {
		return goban;
	}


	public void setGoban(Pion[][] goban) {
		this.goban = goban;
	}


	public int getNbBlanc() {
		return nbBlanc;
	}


	public void setNbBlanc(int nbBlanc) {
		this.nbBlanc = nbBlanc;
	}


	public int getNbNoir() {
		return nbNoir;
	}


	public void setNbNoir(int nbNoir) {
		this.nbNoir = nbNoir;
	}


	public void init(){

		for (int i=0; i<9; i++)
			for (int k=0; k<9; k++) {
				goban[i][k] = new Pion(CouleurPion.EMPTY, 0);
			}
	}

}
