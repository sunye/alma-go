package fr.alma.modele;


import fr.alma.controler.Controler;
import fr.alma.modele.intelligence.Difficulte;
import fr.alma.modele.intelligence.SunTsu;
/*$Author$ 
 * $Date$ 
 * $Revision$  
 *  
 * license
 * 
 * */
/**
 * @author Manoël Fortun et Anthony "Bambinôme" Caillaud
 * Class use to handle the game, to manage the AI when needed
 * manage game's mode, etc 
 */
public class GameHandler {

	/**
	 * The game mode
	 */
	private ModeJeu mode;
	
	/**
	 * the Ai
	 */
	private SunTsu ai;
	
	/**
	 * the board
	 */
	private GoBan plateau;
	
	/**
	 * the color of the AI
	 */
	private CouleurPion coulAi;
	
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
	private int objectif;
	
	/**
	 * is the game is over
	 */
	private boolean termine;
	
	/**
	 * Initial and construct the game
	 * @param con
	 */
	public GameHandler(Controler con){
		this.mode= ModeJeu.HumanVsHuman;
		plateau = new GoBan();
		this.ai= new SunTsu(plateau);
		coulAi=CouleurPion.BLANC;
		this.control=con;
		this.objectif=1;
		
		
	}
	
	/**
	 * Add a stone to board
	 * This method handle the AI if the Ai is enable
	 * @param gobanX
	 * @param gobanY
	 * @return
	 */
	public boolean ajouterPion(int gobanX, int gobanY){
		if (termine) {
			return false;
		}

		Coordonnee cood = new Coordonnee(gobanX, gobanY);
		boolean result = plateau.addPion(cood);
		control.repaintBoard();

		if (isWhoWinner() != CouleurPion.EMPTY) {
			control.afficheGagnant(isWhoWinner());
			this.termine = true;
		} else {
			if (result == true && mode == ModeJeu.AiVsHuman) {
				launchAi();
				if (isWhoWinner() != CouleurPion.EMPTY) {
					control.afficheGagnant(isWhoWinner());
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
	public void remiseZero(){
		this.termine=false;
		plateau.remiseZero();
	}
	
	
	/**
	 * get the matrix size
	 * @return the matrix size
	 */
	public int tailleMatrice(){
		return GoBan.TAILLE_GO_BAN;
	}

	/**
	 * the matrix that represent the board
	 * @return the matrix
	 */
	public Pion[][] getMatricePlateau() {
		
		return plateau.getGoban();
	}

	/**
	 * set AivsHuman mode
	 */
	public void setModeAiVsHuman(){
		this.mode=ModeJeu.AiVsHuman;
	}
	/**
	 * set humanvshuman mode
	 */
	public void setModeHumanVsHuman(){
		this.mode= ModeJeu.HumanVsHuman;
	}
	
	/**
	 * force the ai to play
	 */
	public void forcerCoup(){
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.thinkspace.interrupt();
		//naturalFlow.interrupt();
		
		ai.terminerTraitement();
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		control.afficheLoader(false);
		
		/*try {
			naturalFlow.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/
	}
	
		
	
	/**
	 * @return the objectif
	 */
	public int getObjectif() {
		return objectif;
	}

	/**
	 * @param objectif the objectif to set
	 */
	public void setObjectif(int objectif) {
		this.objectif = objectif;
	}


	/**
	 * Calculate if there is a winner
	 * @return the color of the winner or Empty if not
	 */
	private CouleurPion isWhoWinner(){
		if ( plateau.getPtsBlanc()>=objectif){
			return CouleurPion.BLANC;
		} else if( plateau.getPtsNoir()>=objectif){
			return CouleurPion.NOIR;
		}	
		
		return CouleurPion.EMPTY;
		
	}
	

	
	/**
	 * Initialise the thread for the AI search and get the result
	 */
	private void launchAi(){

		thinkspace = new Thread() {
			public void run() {
				ai.prepareNextMove(coulAi);
			}
		};
		
		naturalFlow=new Thread (){
			public void run(){
			
				Coordonnee nextAiMove=ai.getPlay();
				
				plateau.addPion(nextAiMove);
				control.repaintBoard();
				control.afficheLoader(false);
			}
		};
		
		
		
		naturalFlow.start();
		thinkspace.start();
		control.afficheLoader(true);
	}


	/**
	 * set the difficulty
	 * @param difficulte 0 easy, 1 medium, 2 hard
	 */
	public void setDifficulte(int difficulte) {

		switch (difficulte) {
		case 1:
			ai.setDiff(Difficulte.Moyen);
			break;

		case 2:
			ai.setDiff(Difficulte.Difficile);
			break;

		default:
			ai.setDiff(Difficulte.Debutant);
			break;

		}
	}
}
