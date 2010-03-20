package ihm;

import java.util.Enumeration;
import java.util.Vector;


public class GoBan {

	private Pion[][] goban;
	private int nbBlanc;
	private int nbNoir;
	private Integer num=0;

	//private int[] modifier={1,-1};
	public static int TAILLE_GO_BAN=9;

	public GoBan() {
		this.goban = new Pion[9][9];
		this.nbBlanc =0;
		this.nbNoir = 0;
	}


	public boolean ajouterPion(int position, int positiony){
		//int nbLib;
		//int prise;
		//if (goban[position][positiony].getCouleur()==CouleurPion.EMPTY){
		//Si le nombre est pair alors c'est au blanc de jouer
		if (num%2==0){
			//V�rification que le pion jou� n'est pas en suicide
			//nbLib=liberte(position,positiony,CouleurPion.NOIR);
			//System.out.println(nbLib);
			//if (nbLib!=0){
			if (estLegal(position, positiony, CouleurPion.BLANC)){
				goban[position][positiony]= new Pion(CouleurPion.BLANC,num);
				apresJoue(position, positiony, CouleurPion.BLANC);
				//V�rification si ce pion r�alise une prise d'un pion noir
				//prise(position,positiony,CouleurPion.NOIR);
				nbBlanc++;
				num++;}/*
				}else{
					System.out.println("Suicidaire!");
				}*/

		}else{
			//V�rification que le pion jou� n'est pas en suicide
			//nbLib=liberte(position,positiony,CouleurPion.BLANC);
			//System.out.println(nbLib);
			//if (nbLib!=0){
			if (estLegal(position, positiony, CouleurPion.NOIR)){
				goban[position][positiony]= new Pion(CouleurPion.NOIR,num);
				apresJoue(position, positiony, CouleurPion.NOIR);
				//V�rification si ce pion r�alise une prise d'un pion blanc
				//prise(position,positiony,CouleurPion.BLANC);
				nbNoir++;
				num++;}/*
				}else{
					System.out.println("Suicidaire!");
				}*/
		}/*
		}else{
			System.out.println("T'as pas vu qu'il y avait d�j� un pion connard!");
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


		if (goban[position][positiony].getCouleur() != CouleurPion.EMPTY){
			System.out.println("T'as pas vu qu'il y avait d�j� un pion connard!");
			return false;
		}

		// v�rifie qu'il reste au moins une libert� au pion jou�
		if (countLiberties(position, positiony) > 0){
			System.out.println("Nombre de libert�s sup�rieures � 0");
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

		// v�rification du suicide
		System.out.println("V�rif suicide");
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

		// si le voisin est libre alors on l'ajoute � la liste des libert�s du pion jou�
		// et on enleve le pion jou� des libert�s du voisins
		System.out.println(goban[currentX][currentY].getCouleur());
		System.out.println(coul);
		if (goban[currentX][currentY].getCouleur() == CouleurPion.EMPTY){
			System.out.println("Voisin Libre");
			goban[position][positiony].getListeLibertes().addElement(goban[currentX][currentY]);
			goban[currentX][currentY].getListeLibertes().removeElement(goban[position][positiony]);
		}

		// si le voisin est un ami, on l'ajoute � la liste d'amis
		else if (goban[currentX][currentY].getCouleur() == coul){
			System.out.println("Ajout liste d'amis");
			listVoisinsAmis.addElement(goban[currentX][currentY].getGroupe());
			System.out.println("Libert�s du groupe ami:"+goban[currentX][currentY].getGroupe().getLibertes().size());
		}
		// si le voisin est un ennemi, on l'ajoute � la liste d'ennemis
		else{
			System.out.println("Ajout liste d'ennemis");
			listVoisinsEnnemis.addElement(goban[currentX][currentY].getGroupe());
			System.out.println("Libert�s du groupe ennemi:"+goban[currentX][currentY].getGroupe().getLibertes().size());
			//regarde quelles sont les libert�s du voisin ennemi
			System.out.println("pion :");
			Enumeration<Pion> lib_pion = goban[currentX][currentY].getListeLibertes().elements();
			for (;lib_pion.hasMoreElements();) {
					Pion next=lib_pion.nextElement();
					if(next.equals(goban[currentX-1][currentY])){
						System.out.println("gauche");
					}else if(next.equals(goban[currentX][currentY+1])){
						System.out.println("haut");
					}else if(next.equals(goban[currentX+1][currentY])){
						System.out.println("droite");
					}else if(next.equals(goban[currentX][currentY-1])){
						System.out.println("bas");
					}else{
						System.out.println("autre");
					}
			}
			System.out.println("grpe :");
			Enumeration<Pion> lib_ennemi = goban[currentX][currentY].getGroupe().getLibertes().elements();
			for (;lib_ennemi.hasMoreElements();) {
					Pion next=lib_ennemi.nextElement();
					if(next.equals(goban[currentX-1][currentY])){
						System.out.println("gauche");
					}else if(next.equals(goban[currentX][currentY+1])){
						System.out.println("haut");
					}else if(next.equals(goban[currentX+1][currentY])){
						System.out.println("droite");
					}else if(next.equals(goban[currentX][currentY-1])){
						System.out.println("bas");
					}else{
						System.out.println("autre");
					}
			}
		}
	}

	// mise � jour du groupe des amis
	public void majListeAmis(int position, int positiony, CouleurPion coul, Vector<Groupe> listVoisinsAmis){

		// si aucun ami autour -> cr�ation d'un nouveau groupe
		if (listVoisinsAmis.size() == 0){
			System.out.println("Cr�ation d'un nouveau groupe");
			Groupe newGroupe = new Groupe(goban[position][positiony]);
			goban[position][positiony].setGroupe(newGroupe);
			System.out.println("lib pion :"+goban[position][positiony].getListeLibertes().size());
			System.out.println("lib grpe :"+newGroupe.getLibertes().size());
			Enumeration<Pion> lib_ennemi = newGroupe.getLibertes().elements();
			for (;lib_ennemi.hasMoreElements();) {
					Pion next=lib_ennemi.nextElement();
					if(next.equals(goban[position-1][positiony])){
						System.out.println("gauche");
					}else if(next.equals(goban[position][positiony+1])){
						System.out.println("haut");
					}else if(next.equals(goban[position+1][positiony])){
						System.out.println("droite");
					}else if(next.equals(goban[position][positiony-1])){
						System.out.println("bas");
					}else{
						System.out.println("autre");
					}
			}
		}

		//ajout du pion jou� � un groupe
		else if (listVoisinsAmis.size() == 1){
			System.out.println("Ajout du pion � un groupe existant");
			listVoisinsAmis.firstElement().getPions().addElement(goban[position][positiony]);
			// d�finition du groupe du pion
			goban[position][positiony].setGroupe(listVoisinsAmis.firstElement());
			//les libert�s du pions sont ajout�es � celles du groupe
			listVoisinsAmis.firstElement().getLibertes().addAll(goban[position][positiony].getListeLibertes());
		}
		//le pion jou� relie plusieurs groupes
		else{
			System.out.println("Connexions entre groupes");
			connexionGroupes(position, positiony, coul, listVoisinsAmis);
		}
	}

	// Connexion entre plusieurs groupes gr�ce au pion jou�
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

			// ajout des pions et des libert�s dans le premier groupe
			premierGroupe.getLibertes().addAll(unAutreGroupe.getLibertes());
			premierGroupe.getPions().addAll(unAutreGroupe.getPions());

		}

		// ajout du pion jou� � ce premier groupe

		premierGroupe.getPions().addElement(goban[position][positiony]);
		// d�finition du groupe du pion
		goban[position][positiony].setGroupe(premierGroupe);
		//les libert�s du pions sont ajout�es � celles du groupe
		premierGroupe.getLibertes().addAll(goban[position][positiony].getListeLibertes());
	}

	// actions une fois le pion jou�
	public void apresJoue(int position, int positiony, CouleurPion coul) {
		System.out.println("Apres jou�");
		// on fait les listes d'amis et d'ennemis
		Vector<Groupe> listOfNeighborFriends = new Vector<Groupe>();
		Vector<Groupe> listOfNeighborEnemies = new Vector<Groupe>();
		
		
		visitVoisinsPion(position, positiony, coul,
				listOfNeighborFriends,
				listOfNeighborEnemies);
		
		// une fois le pion jou�, on supprime l'endroit des libert�s des groupes voisins
		enleveLibertesVoisins(position, positiony, coul,
				listOfNeighborFriends,
				listOfNeighborEnemies);

		// maj des groupes amis
		majListeAmis(position, positiony, coul, listOfNeighborFriends);
		
	}

	public void enleveLibertesVoisins(int position, int positiony, CouleurPion coul,
			Vector<Groupe> listOfNeighborFriends,
			Vector<Groupe> listOfNeighborEnemies) {

		// Pour les amis
		Enumeration<Groupe> e = listOfNeighborFriends.elements();
		for (;e.hasMoreElements();) {
			Groupe currentGroup = (Groupe)e.nextElement();
			System.out.println("enleve liberte d'un groupe ami");
			currentGroup.getLibertes().removeElement(goban[position][positiony]);
		}

		// Pour les ennemis
		e = listOfNeighborEnemies.elements();
		System.out.println("nbre d'ennemis:"+listOfNeighborEnemies.size());
		for (;e.hasMoreElements();) {
			Groupe currentGroup = (Groupe)e.nextElement();
			System.out.println("enleve liberte d'un groupe ennemi");
			System.out.println("avt suppr ennemi :"+currentGroup.getLibertes().size());
/*			if(currentGroup.getLibertes().contains(goban[position][positiony])){
				System.out.println("il contient la position a suppr!");
			}else{
				System.out.println("il contient pas la position a suppr!");
			}*/
			currentGroup.getLibertes().removeElement(goban[position][positiony]);
			System.out.println("avpr suppr ennemi :"+currentGroup.getLibertes().size());
		}
	}
	
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
