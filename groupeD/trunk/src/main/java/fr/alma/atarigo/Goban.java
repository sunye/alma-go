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
public class Goban {

 /**
  * grille de pions
  */
 public Stone[][] matrice;
 
/**
 * constructeur logique
 */
 public Goban(int line, int column) {
	this.matrice = new Stone[line][column];
	newGame();
 }
 
 public Goban(Goban p){
	 matrice = new Stone[p.getLines()][p.getColumns()];
	 for(int i=0;i<p.getLines();i++)
		 for(int j=0;j<p.getColumns();j++)
			 matrice[i][j]=p.matrice[i][j];
 }

 /**
  * supprime tous les pions du plateau en vue d'une nouvelle partie.
  */
 public void newGame() {
	for (int i = 0; i < getLines(); i ++) {
	    for (int j = 0; j < getColumns(); j ++) {
		matrice[i][j] = Stone.EMPTY;
	    }
	}
 }

/**
 * accesseur en lecture
 */
 public int getLines() {
	return matrice.length;
 }

 /**
  * accesseur en lecture
  */
 public int getColumns() {
	return matrice[0].length;
 }

 /**
  * accesseur en lecture
  */
 public Stone readCell(Position position) {
	return matrice[position.getLine()][position.getColumn()];
 }

 /**
  * accesseur en écriture.
  */
 public void writeCell(Position position, Stone stone) {
	matrice[position.getLine()][position.getColumn()] = stone;
 }
 
 /**
  * @deprecated
  * transforme la case en une case vide
  * @param position
  */
 public void emptyCell(Position position) {
		matrice[position.getLine()][position.getColumn()] = Stone.EMPTY;
	 }

 /**
  * indique si la position fournie en argument appartient au plateau.
  * @param position position testée
  */
 public boolean isValid(Position position) {
	//test si rentre dans le plateau
	final int LINE = position.getLine();
	final int COLUMN = position.getColumn();
	return LINE >= 0 && LINE < getLines() &
	    COLUMN >= 0 && COLUMN < getColumns();
 }

 /**
  * vérifie si le pion est pacé dans une case entourée par des pions opposés
  * forme basique de suicide
  * @return si suicide ou pas
  */
 public boolean isSuicidal(Position position, Stone player){
	boolean isValid;
	//on test d'abord si la position est bonne
	if(isValid(position)){
		//test si ne se suicide pas
		int count = 0; // nombre de non-libertes
		for (Direction myDirection : Direction.values()) {
			//maPosition = position.voisine(maDirection);
			if(isValid(position.neighbor(myDirection))){
				//System.err.println(lireCase(position.voisine(maDirection)).toString());
				if (readCell(position.neighbor(myDirection)) == player.opponent()){
					count++;
				}
			}else{
				count++;
			}
		}
		if(count==4){isValid=false;} // suicide
		else{isValid=true;} //pas de suicide
	}
	else{isValid=false;} // la position n'est pas bonne
	return isValid;
 }
 
 /**
  * test si le groupe entré en paramètre est capturé ou non.
  * @param goup groupe à tester
  * @return capturé ou non
  */
	public boolean isCaught(Group goup){
		Position currentPosition;
		int liberties = 0;
		ListIterator itr = goup.linkedStones.listIterator();
 		while(itr.hasNext()){
 			currentPosition = (Position)itr.next();
 			liberties += liberty(currentPosition);
 		}
		if(liberties==0){
			return true;
		}else{
			return false;
		}
	}

 /**
  * indique le nombre de libertés que possède la position
  */
 public int getLiberty(Position position){
	 int count = 0;
	 //Position maPosition;
	 for (Direction myDirection : Direction.values()) {
		 //maPosition = position.voisine(maDirection);
		 if(isValid(position.neighbor(myDirection))){
			 //System.err.println(lireCase(position.voisine(maDirection)).toString());
			 if (readCell(position.neighbor(myDirection)) == Stone.EMPTY){
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
 public GroupsList hasCaught(Position position,GroupsList lg){
	 GroupsList result= new GroupsList();
	 for (Direction myDirection : Direction.values()) {
		 if(isValid(position.neighbor(myDirection))){
			 if (readCell(position.neighbor(myDirection)) == readCell(position).opponent()){
				 if(isCaught(lg.getGroup(position.neighbor(myDirection)))){
					 //estGagnant = true;
					 result.gList.add(lg.getGroup(position.neighbor(myDirection)));
				 }
			 }
		 }
	 }
	 
	 return result;
 }
 
 /**
  * surcharge
  */
 public String toString() {
	final int LINES = getLines();
	final int COLUMNS = getColumns();
	String goban = "";
	for (int i = LINES - 1; i > -1; i --) {
	    goban += i + " |\t";
	    for (int j = 0; j < COLUMNS; j ++) {
		goban += matrice[i][j].toString() + '\t';
	    }
	    goban += '\n';
	}
	goban += '\t';
	for (int i = 0; i < COLUMNS; i ++) {
	    goban += "_\t";
	}
     goban += "\n\t";
	for (int i = 0; i < COLUMNS; i ++) {
	    goban += i + "\t";
	}
	goban += '\n';
	return goban;
 }

public void actionPerformed(ActionEvent arg0) {
	// TODO Auto-generated method stub
	
}

public LinkedList<Position> emptyCells(){
	 LinkedList<Position> emptyCellsList = new LinkedList<Position>();
	 
	 for(int i=0;i<getLines();i++){
		 for(int j=0;j<getColumns();j++){
			 if(matrice[i][j].equals(Stone.EMPTY))
				 emptyCellsList.add(new Position(i,j));
		 }
	 }
	 return emptyCellsList;
}

public LinkedList<Position> getCells(Stone stone){
	 LinkedList<Position> cellsList = new LinkedList<Position>();
	 
	 for(int i=0;i<getLines();i++){
		 for(int j=0;j<getColumns();j++){
			 if(matrice[i][j].equals(stone))
				 cellsList.add(new Position(i,j));
		 }
	 }
	 return cellsList;
}

public LinkedList<Goban> computeMoves(Stone stone){
	 LinkedList<Goban> gobanList = new LinkedList<Goban>();
	 
	 for(Position position : getCells(Stone.EMPTY)){
		 Goban newGoban = new Goban(this);
		 newGoban.writeCell(position, stone);
		 gobanList.add(newGoban);
	 }
	 return gobanList;	 
}

public int liberty(Position pos){
	 int cmpt=0;
	 if(isValid(pos.neighbor(Direction.NORTH)))
		 if(readCell(pos.neighbor(Direction.NORTH)).equals(Stone.EMPTY))
			 cmpt++;
	 if(isValid(pos.neighbor(Direction.SOUTH)))
		 if(readCell(pos.neighbor(Direction.SOUTH)).equals(Stone.EMPTY))
			 cmpt++;
	 if(isValid(pos.neighbor(Direction.EAST)))
		 if(readCell(pos.neighbor(Direction.EAST)).equals(Stone.EMPTY))
			 cmpt++;
	 if(isValid(pos.neighbor(Direction.WEST)))
		 if(readCell(pos.neighbor(Direction.WEST)).equals(Stone.EMPTY))
			 cmpt++;
			 
	 return cmpt;	 
}

public Position getDifference(Goban goban){
	 for(int i=0;i<getLines();i++){
		 for(int j=0;j<getColumns();j++){
			 if(matrice[i][j]!=goban.matrice[i][j])
				 return new Position(i,j);
		 }
	 }
	 return new Position(-1,-1);
}

}