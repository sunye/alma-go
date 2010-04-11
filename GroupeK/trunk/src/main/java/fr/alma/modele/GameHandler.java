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
	
	public GameHandler(Controler con){
		this.mode= ModeJeu.HumanVsHuman;
		this.ai= new SunTsu();
		plateau = new GoBan();
		coulAi=CouleurPion.BLANC;
		this.control=con;
	}
	
	

	public boolean ajouterPion(int gobanX, int gobanY){
			
		boolean result= plateau.addPion(gobanX,gobanY);
		if (plateau.getGagnant()!=CouleurPion.EMPTY){
			control.afficheGagnant(plateau.getGagnant());
		}
		
		
		if (result==true && mode==ModeJeu.AiVsHuman){
		
			new Thread (){
				public void run(){
				control.afficheLoader(true);
				}
			}.start();
			
			thinkspace= new Thread() {
			
			
			public void run(){
				
				ai.prepareNextMove(plateau, coulAi);
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
			
			control.afficheLoader(false);
			plateau.addPion(nextAiMove.getX(), nextAiMove.getY());
			if (plateau.getGagnant()!=CouleurPion.EMPTY){
				control.afficheGagnant(plateau.getGagnant());
			}
			
		}
		
		
		return result;
	}
	
	public void remiseZero(){
		plateau.remiseZero();
	}
	
	public CouleurPion getGagnant(){
		return plateau.getGagnant();
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
}
