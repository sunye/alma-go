package fr.alma.atarigo;


import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ListIterator;

import fr.alma.atarigo.AtariGo.Move;


/**
 * Goban.java is a representation of the board game.
 */
public class Goban {

 /** matrice of stones */
 public Stone[][] matrice;
 /** the list of stone groups on the goban */
 public GroupsList groupsList;
 /** Amount of captured black stones*/
 public int caughtBlack;
 /** Amount of captured white stones*/
 public int caughtWhite;
 
 
 /**
  * Logic constructor
  * @param line number of lines of the goban.
  * @param column number of columns of the goban. 
  */
 public Goban(int line, int column) {
	this.matrice = new Stone[line][column];
	this.groupsList=new GroupsList();
	caughtWhite=0;
	caughtBlack=0;
	newGame();
 }
 
 /**
  * Copy constructor
  * @param p the goban we want to copy.
  */
 public Goban(Goban p){
	 matrice = new Stone[p.getLines()][p.getColumns()];
	 for(int i=0;i<p.getLines();i++)
		 for(int j=0;j<p.getColumns();j++)
			 matrice[i][j]=p.matrice[i][j];
	 groupsList = new GroupsList();
	 groupsList.gList.addAll(p.groupsList.gList);
	 caughtBlack=p.caughtBlack;
	 caughtWhite=p.caughtWhite;
 }

 /**
  * remove all the stones from the board for a new game.
  */
 public void newGame() {
	for (int i = 0; i < getLines(); i ++) {
	    for (int j = 0; j < getColumns(); j ++) {
		matrice[i][j] = Stone.EMPTY;
	    }
	}
	groupsList.gList.clear();
 }

/**
 * accessor for lines
 */
 public int getLines() {
	return matrice.length;
 }

 /**
  * accessor for columns
  */
 public int getColumns() {
	return matrice[0].length;
 }

 /**
  * accessor for stones
  */
 public Stone readCell(Position position) {
	return matrice[position.getLine()][position.getColumn()];
 }

 /**
  * Write on the goban if the move is not invalid and return the type of move.
  * @param atariGo the atarigo instance
  * @param position the position of the stone
  * @param stone the color of the stone (black or white)
  * @param finalWriting used by AI to not modify the goban
  * @return the type of move 
  */
 public Move writeCell(AtariGo atariGo,Position position, Stone stone, boolean finalWriting) {
	matrice[position.getLine()][position.getColumn()] = stone;
	
	GroupsList newGL = groupsList.updateGroups(this,position,stone);
	//test de la prise sur le nouveau groupe
	GroupsList caughtList = this.hasCaught(position,newGL);
	
	if(finalWriting){
		if(this.isCaught(newGL.getGroup(position))){
			if (!caughtList.isEmpty()) {
				atariGo.totalMoves++;
				if(stone==Stone.WHITE){
					atariGo.caughtBlack += caughtList.totalStones();
					atariGo.caughtWhite += newGL.getGroup(position).linkedStones.size();
					if(atariGo.caughtBlack>=atariGo.captureObjective){
						return Move.WIN;
					}
					else{
						// on doit aussi compter le nombre de pions pris dans le suicide
						
						return Move.NEUTRAL;
					}
				}
				else{
					atariGo.caughtWhite += caughtList.totalStones();
					atariGo.caughtBlack += newGL.getGroup(position).linkedStones.size();
					if(atariGo.caughtWhite>=atariGo.captureObjective){
						return Move.WIN;
					}
					else{
						return Move.NEUTRAL;
					}
				}
			}
			else{
				System.out.println("is caught !!");
				System.out.println("printing gList");
				groupsList.print();
				this.emptyCell(position);
				return Move.INVALID;
			}
		}
		
		groupsList = newGL;
		caughtList = this.hasCaught(position,groupsList);
		if (!caughtList.isEmpty()) {
			if(stone==Stone.WHITE){
				atariGo.caughtBlack += caughtList.totalStones();
				if(atariGo.caughtBlack>=atariGo.captureObjective){
					return Move.WIN;
				}
			}
			else{
				atariGo.caughtWhite += caughtList.totalStones();
				if(atariGo.caughtWhite>=atariGo.captureObjective){
					return Move.WIN;
				}
			}
			System.out.println(stone.toString()+" has won");
			//return Coup.GAGNANT;
		}
	}
	else{
		groupsList = newGL;
	}
	return Move.NEUTRAL;
}
 
 /**
  * @deprecated if we empty a stone we should also update groups of stones.
  * empty a square
  * @param position
  */
 public void emptyCell(Position position) {
		matrice[position.getLine()][position.getColumn()] = Stone.EMPTY;
	 }

 /**
  * indicate if the position belongs to the board.
  * @param position tested position
  */
 public boolean isValid(Position position) {
	//test si rentre dans le plateau
	final int LINE = position.getLine();
	final int COLUMN = position.getColumn();
	return LINE >= 0 && LINE < getLines() &
	    COLUMN >= 0 && COLUMN < getColumns();
 }

 /**
  * verify if the stone is placed in a square surrounded by oposite stones
  * basic form of a suicide
  * @param position tested position
  * @param position tested type of stone
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
  * test if the groupe is captured or not.
  * @param goup groupe to test
  * @return captured or not
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
  * indicate the number of liberties owned by the position
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
  * return if the move has caught stones or not.
  * @param position position to test
  * @param lg list of groups to test
  * @return if the move has caught or not
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
  * override to show the Goban in console mode
  */
 public String toString() {
	final int LINES = getLines();
	final int COLUMNS = getColumns();
	String goban = "";
	for (int i = 0; i < LINES; i ++) {
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

public ArrayList<Position> emptyCells(){
	 ArrayList<Position> emptyCellsList = new ArrayList<Position>();
	 
	 for(int i=0;i<getLines();i++){
		 for(int j=0;j<getColumns();j++){
			 if(matrice[i][j].equals(Stone.EMPTY))
				 emptyCellsList.add(new Position(i,j));
		 }
	 }
	 return emptyCellsList;
}

public ArrayList<Position> getCells(Stone stone){
	 ArrayList<Position> cellsList = new ArrayList<Position>();
	 
	 for(int i=0;i<getLines();i++){
		 for(int j=0;j<getColumns();j++){
			 if(matrice[i][j].equals(stone))
				 cellsList.add(new Position(i,j));
		 }
	 }
	 return cellsList;
}

public ArrayList<Goban> computeMoves(AtariGo atariGo,Stone stone,Stone currentPlayer){
	 ArrayList<Goban> gobanList = new ArrayList<Goban>();
	 boolean save=false;
	 for(Position position : getCells(Stone.EMPTY)){
		 if(stone==currentPlayer){
			 if(isSuicidal(position,currentPlayer)){
				 save=true;
			 }
		 }else
			 save=true;
		 if(save){
			 Goban newGoban = new Goban(this);
			 newGoban.writeCell(atariGo,position, stone,false);
			 gobanList.add(newGoban);
			 save=false;
		 }
	 }
	 Collections.shuffle(gobanList);
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

public int contact(Position pos,Stone opponentStone){
	 int cmpt=0;
	 
	 for(int i=0;i<2;i++)
		 for(int j=0;j<2;j++)
			 if(isValid(new Position(pos.getLine()+i,pos.getColumn()+j)) && (matrice[pos.getLine()+i][pos.getColumn()+j])==opponentStone)
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

public boolean canPlay(Stone color){
	Goban gobanTest;
	boolean peutJouer = false;
	boolean fini = false;
	int i = 0;
	int j = 0;
	Position postest;
	while(!fini){
		System.out.println("test sur "+i+","+j);
		postest = new Position(i,j);		gobanTest = new Goban(this);
		/*return gobanTest.writeCell(gobanTest,postest, color,true);
		
		if(){
			
		}	*/
		if(i==8){
			if(j==8){
				fini=true;
			}else{
				i=0;
				j++;
			}
		}
		else{
			i++;
		}
		
	}
	return false;
}

}
