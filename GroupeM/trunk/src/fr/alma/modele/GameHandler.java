package fr.alma.modele;

import fr.alma.controler.Controler;
import fr.alma.modele.intelligence.SunTsu;

/**
 * Class use to handle the game, to manage the AI when needed
 * manage game's mode, etc 
 */
public class GameHandler {

	private ModeJeu mode;
	private SunTsu ai;
	private GoBan plateau;
	private CouleurPion coulAi;
	private Controler control;
	private Thread thinkspace;
	private int objectif;
	private boolean termine;
	
	public GameHandler(Controler con){
		this.mode= ModeJeu.HumanVsHuman;
		plateau = new GoBan();
		this.ai= new SunTsu(plateau);
		coulAi=CouleurPion.BLANC;
		this.control=con;
		this.objectif=1;
	}
	
	

	public boolean ajouterPion(int gobanX, int gobanY){
		if (termine){
			return false;
		}
		
		
			Coordonnee cood= new Coordonnee(gobanX, gobanY);
		boolean result= plateau.addPion(cood);
		/*if (plateau.getGagnant()!=CouleurPion.EMPTY){
			control.afficheGagnant(plateau.getGagnant());
		}*/
		
		control.repaintBoard();
		
		if (isWhoWinner()!=CouleurPion.EMPTY){
			control.afficheGagnant(isWhoWinner());
			this.termine=true;
		}else{
			if (result==true && mode==ModeJeu.AiVsHuman){
				launchAi();
				if (isWhoWinner()!=CouleurPion.EMPTY){
					control.afficheGagnant(isWhoWinner());
					this.termine=true;
				}
			}
		}
	
		
		return result;
	}
	
	public void remiseZero(){
		this.termine=false;
		plateau.remiseZero();
	}
	
	
	
	public int tailleMatrice(){
		return GoBan.TAILLE_GO_BAN;
	}

	public Pion[][] getMatricePlateau() {
		
		return plateau.getGoban();
	}

	public void setModeAiVsHuman(){
		this.mode=ModeJeu.AiVsHuman;
	}
	
	public void setModeHumanVsHuman(){
		this.mode= ModeJeu.HumanVsHuman;
	}
	
	public void forcerCoup(){
		this.thinkspace.interrupt();
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



	private CouleurPion isWhoWinner(){
		if ( plateau.getPtsBlanc()>=objectif){
			return CouleurPion.BLANC;
		} else if( plateau.getPtsNoir()>=objectif){
			return CouleurPion.NOIR;
		}	
		
		return CouleurPion.EMPTY;
		
	}
	
	private void launchAi(){
		
		new Thread (){
			public void run(){
			control.afficheLoader(true);
			}
		}.start();
		
		thinkspace= new Thread() {
		
		
		public void run(){
			
			ai.prepareNextMove(coulAi);
			try {
				sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		};
		thinkspace.start();
	
		Coordonnee nextAiMove=ai.getPlay();
		
		
		plateau.addPion(nextAiMove);
		
		
		control.repaintBoard();
		control.afficheLoader(false);
	}
}
