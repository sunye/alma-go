package fr.alma.modele;


import fr.alma.controler.Controler;
import fr.alma.modele.intelligence.Difficulty;
import fr.alma.modele.intelligence.SunTsu;
/*$Author$ 
 * $Date$ 
 * $Revision$  
 * 
 *Copyright (C) 2010  Fortun Manoël & Caillaud Anthony
 *
 *This program is free software: you can redistribute it and/or modify
 *it under the terms of the GNU General Public License as published by
 *the Free Software Foundation, either version 3 of the License, or
 *(at your option) any later version.
 *
 *This program is distributed in the hope that it will be useful,
 *but WITHOUT ANY WARRANTY; without even the implied warranty of
 *MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *GNU General Public License for more details.
 *
 *You should have received a copy of the GNU General Public License
 *along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * */
/**
 * Class use to handle the game, to manage the AI when needed manage game's
 * mode, etc
 * 
 * @author Manoël Fortun et Anthony "Bambinôme" Caillaud
 */
public class GameHandler {

	/**
	 * The game mode
	 */
	private GameMode mode;
	
	/**
	 * the Ai
	 */
	private SunTsu brillantAI;
	
	/**
	 * the board
	 */
	private GoBan board;
	
	/**
	 * the color of the AI
	 */
	private StoneColor aiColor;
	
	/**
	 * The controler
	 */
	private Controler control;
	
	/**
	 * A thread used to manage the nextmove search
	 */
	private Thread thinkspace;
	
	/**
	 * The thread that get the solution calculate by the AI
	 */
	private Thread naturalFlow;
	
	/**
	 * the capture goal
	 */
	private int goal;
	
	/**
	 * is the game is over
	 */
	private boolean termine;
	
	/**
	 * Initial and construct the game
	 * @param con
	 */
	public GameHandler(Controler con){
		this.mode= GameMode.HumanVsHuman;
		board = new GoBan();
		this.brillantAI= new SunTsu(board);
		aiColor=StoneColor.WHITE;
		this.control=con;
		this.goal=1;
		
		
	}
	
	/**
	 * Add a stone to board
	 * This method handle the AI if the Ai is enable
	 * @param gobanX
	 * @param gobanY
	 * @return
	 */
	public boolean addStone(int gobanX, int gobanY){
		if (termine) {
			return false;
		}

		Coordinate cood = new Coordinate(gobanX, gobanY);
		boolean result = board.addPion(cood);
		control.repaintBoard();

		if (isWhoWinner() != StoneColor.EMPTY) {
			control.showWinner(isWhoWinner());
			this.termine = true;
		} else {
			if (result == true && mode == GameMode.AiVsHuman) {
				launchAi();
				if (isWhoWinner() != StoneColor.EMPTY) {
					control.showWinner(isWhoWinner());
					this.termine = true;
				}
			}
		}

		control.repaintBoard();

		return result;
	}
	
	/**
	 * reset the game setting
	 */
	public void reset(){
		this.termine=false;
		board.reset();
	}
	
	
	/**
	 * get the matrix size
	 * @return the matrix size
	 */
	public int getMatrixSize(){
		return GoBan.GO_BAN_SIZE;
	}

	/**
	 * the matrix that represent the board
	 * @return the matrix
	 */
	public Stone[][] getMatrixBoard() {
		
		return board.getGoban();
	}

	/**
	 * set AivsHuman mode
	 */
	public void setModeAiVsHuman(){
		this.mode=GameMode.AiVsHuman;
	}
	/**
	 * set humanvshuman mode
	 */
	public void setModeHumanVsHuman(){
		this.mode= GameMode.HumanVsHuman;
	}
	
	/**
	 * force the ai to play
	 */
	public void forceToPlay(){
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.thinkspace.interrupt();
		//naturalFlow.interrupt();
		
		brillantAI.endWithTheAlphaBeta();
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		control.showLoader(false);
		
		/*try {
			naturalFlow.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/
	}
	
		
	
	/**
	 * @return the objectif
	 */
	public int getGoal() {
		return goal;
	}

	/**
	 * @param objectif the objectif to set
	 */
	public void setGoal(int objectif) {
		this.goal = objectif;
	}


	/**
	 * Calculate if there is a winner
	 * @return the color of the winner or Empty if not
	 */
	private StoneColor isWhoWinner(){
		if ( board.getWhiteScore()>=goal){
			return StoneColor.WHITE;
		} else if( board.getBlackScore()>=goal){
			return StoneColor.BLACK;
		}	
		
		return StoneColor.EMPTY;
		
	}
	

	
	/**
	 * Initialise the thread for the AI search and get the result
	 */
	private void launchAi(){

		thinkspace = new Thread() {
			public void run() {
				brillantAI.prepareNextMove(aiColor);
			}
		};
		
		naturalFlow=new Thread (){
			public void run(){
			
				Coordinate nextAiMove=brillantAI.getPlay();
				
				board.addPion(nextAiMove);
				control.repaintBoard();
				control.showLoader(false);
			}
		};
		
		
		
		naturalFlow.start();
		thinkspace.start();
		control.showLoader(true);
	}


	/**
	 * set the difficulty
	 * @param difficulte 0 easy, 1 medium, 2 hard
	 */
	public void setDifficulty(int difficulte) {

		switch (difficulte) {
		case 1:
			brillantAI.setDiff(Difficulty.MEDIUM);
			break;

		case 2:
			brillantAI.setDiff(Difficulty.HARD);
			break;

		default:
			brillantAI.setDiff(Difficulty.EASY);
			break;

		}
	}
}
