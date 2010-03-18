package fr.alma.atarigo;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.ListIterator;

import javax.swing.ImageIcon;
import javax.swing.JPanel;


/**
 * classe representant le plateau de jeu.
 */
public class Plateau {

 /**
  * grille de pions
  */
 public Pion[][] grille;
 
/**
 * constructeur logique
 */
 public Plateau(int lignes, int colonnes) {
	this.grille = new Pion[lignes][colonnes];
	nouvellePartie();
 }
 
 public Plateau(Plateau p){
	 grille = new Pion[9][9];
	 for(int i=0;i<9;i++)
		 for(int j=0;j<9;j++)
			 grille[i][j]=p.grille[i][j];
 }

 /**
  * supprime tous les pions du plateau en vue d'une nouvelle partie.
  */
 public void nouvellePartie() {
	for (int i = 0; i < lireLignes(); i ++) {
	    for (int j = 0; j < lireColonnes(); j ++) {
		grille[i][j] = Pion.VIDE;
	    }
	}
 }

/**
 * accesseur en lecture
 */
 public int lireLignes() {
	return grille.length;
 }

 /**
  * accesseur en lecture
  */
 public int lireColonnes() {
	return grille[0].length;
 }

 /**
  * accesseur en lecture
  */
 public Pion lireCase(Position position) {
	return grille[position.lireLigne()][position.lireColonne()];
 }

 /**
  * accesseur en écriture.
  */
 public void ecrireCase(Position position, Pion pion) {
	grille[position.lireLigne()][position.lireColonne()] = pion;
 }
 
 /**
  * @deprecated
  * transforme la case en une case vide
  * @param position
  */
 public void viderCase(Position position) {
		grille[position.lireLigne()][position.lireColonne()] = Pion.VIDE;
	 }

 /**
  * indique si la position fournie en argument appartient au plateau.
  * @param position position testée
  */
 public boolean estValide(Position position) {
	//test si rentre dans le plateau
	final int LIGNE = position.lireLigne();
	final int COLONNE = position.lireColonne();
	return LIGNE >= 0 && LIGNE < lireLignes() &
	    COLONNE >= 0 && COLONNE < lireColonnes();
 }

 /**
  * vérifie si le pion est pacé dans une case entourée par des pions opposés
  * forme basique de suicide
  * @return si suicide ou pas
  */
 public boolean estSuicide(Position position, Pion joueur){
	boolean estValide;
	//on test d'abord si la position est bonne
	if(estValide(position)){
		//test si ne se suicide pas
		int count = 0; // nombre de non-libertes
		for (Direction maDirection : Direction.values()) {
			//maPosition = position.voisine(maDirection);
			if(estValide(position.voisine(maDirection))){
				//System.err.println(lireCase(position.voisine(maDirection)).toString());
				if (lireCase(position.voisine(maDirection)) == joueur.oppose()){
					count++;
				}
			}else{
				count++;
			}
		}
		if(count==4){estValide=false;} // suicide
		else{estValide=true;} //pas de suicide
	}
	else{estValide=false;} // la position n'est pas bonne
	return estValide;
 }
 
 /**
  * test si le groupe entré en paramètre est capturé ou non.
  * @param groupe groupe à tester
  * @return capturé ou non
  */
	public boolean estPris(Groupe groupe){
		Position positionEnCours;
		int libertes = 0;
		ListIterator itr = groupe.linkedPions.listIterator();
 		while(itr.hasNext()){
 			positionEnCours = (Position)itr.next();
 			libertes += liberte(positionEnCours);
 		}
		if(libertes==0){
			return true;
		}else{
			return false;
		}
	}

 /**
  * indique le nombre de libertés que possède la position
  */
 public int lireLibertes(Position position){
	 int count = 0;
	 //Position maPosition;
	 for (Direction maDirection : Direction.values()) {
		 //maPosition = position.voisine(maDirection);
		 if(estValide(position.voisine(maDirection))){
			 //System.err.println(lireCase(position.voisine(maDirection)).toString());
			 if (lireCase(position.voisine(maDirection)) == Pion.VIDE){
				 count++;
			 }
		 }
	 }
	 
	 return count;
 }
 
 /**
  * retourne si le coup est gagnant ou non
  * @param position position testée
  * @param lg liste de groupes testée
  * @return si le coup est gagnant ou non
  */
 public ListeGroupes estGagnant(Position position,ListeGroupes lg){
	 ListeGroupes listeres= new ListeGroupes();
	 for (Direction maDirection : Direction.values()) {
		 if(estValide(position.voisine(maDirection))){
			 if (lireCase(position.voisine(maDirection)) == lireCase(position).oppose()){
				 if(estPris(lg.getGroupe(position.voisine(maDirection)))){
					 //estGagnant = true;
					 listeres.listeG.add(lg.getGroupe(position.voisine(maDirection)));
				 }
			 }
		 }
	 }
	 
	 return listeres;
 }
 
 /**
  * surcharge
  */
 public String toString() {
	final int LIGNES = lireLignes();
	final int COLONNES = lireColonnes();
	String plateau = "";
	for (int i = LIGNES - 1; i > -1; i --) {
	    plateau += i + " |\t";
	    for (int j = 0; j < COLONNES; j ++) {
		plateau += grille[i][j].toString() + '\t';
	    }
	    plateau += '\n';
	}
	plateau += '\t';
	for (int i = 0; i < COLONNES; i ++) {
	    plateau += "_\t";
	}
     plateau += "\n\t";
	for (int i = 0; i < COLONNES; i ++) {
	    plateau += i + "\t";
	}
	plateau += '\n';
	return plateau;
 }

public void actionPerformed(ActionEvent arg0) {
	// TODO Auto-generated method stub
	
}

LinkedList<Position> casesVides(){
	 LinkedList<Position> listeCasesVides = new LinkedList<Position>();
	 
	 for(int i=0;i<9;i++){
		 for(int j=0;j<9;j++){
			 if(grille[i][j].equals(Pion.VIDE))
				 listeCasesVides.add(new Position(i,j));
		 }
	 }
	 return listeCasesVides;
}

public LinkedList<Position> getCases(Pion pion){
	 LinkedList<Position> listeCases = new LinkedList<Position>();
	 
	 for(int i=0;i<9;i++){
		 for(int j=0;j<9;j++){
			 if(grille[i][j].equals(pion))
				 listeCases.add(new Position(i,j));
		 }
	 }
	 return listeCases;
}

public LinkedList<Plateau> genererCoups(Pion pion){
	 LinkedList<Plateau> listePlateaux = new LinkedList<Plateau>();
	 
	 for(Position position : getCases(Pion.VIDE)){
		 Plateau nouveauPlateau = new Plateau(this);
		 nouveauPlateau.ecrireCase(position, pion);
		 listePlateaux.add(nouveauPlateau);
	 }
	 return listePlateaux;	 
}

public int liberte(Position pos){
	 int cmpt=0;
	 if(estValide(pos.voisine(Direction.NORD)))
		 if(lireCase(pos.voisine(Direction.NORD)).equals(Pion.VIDE))
			 cmpt++;
	 if(estValide(pos.voisine(Direction.SUD)))
		 if(lireCase(pos.voisine(Direction.SUD)).equals(Pion.VIDE))
			 cmpt++;
	 if(estValide(pos.voisine(Direction.EST)))
		 if(lireCase(pos.voisine(Direction.EST)).equals(Pion.VIDE))
			 cmpt++;
	 if(estValide(pos.voisine(Direction.OUEST)))
		 if(lireCase(pos.voisine(Direction.OUEST)).equals(Pion.VIDE))
			 cmpt++;
			 
	 return cmpt;
}

}