package fr.alma.modele;


import fr.alma.controler.Controler;
import fr.alma.modele.intelligence.Difficulte;
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
	private Thread naturalFlow;
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
	
		control.repaintBoard();
		
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
		
		ai.terminerTraitement();
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
		thinkspace = new Thread() {
			public void run() {
				ai.prepareNextMove(coulAi);
			}
		};
		
		naturalFlow=new Thread (){
			public void run(){
			
				Coordonnee nextAiMove=ai.getPlay();
				System.out.println("rr");
				try {
					sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				plateau.addPion(nextAiMove);
				control.repaintBoard();
				control.afficheLoader(false);
			}
		};
			
		
		
		
		naturalFlow.start();
		thinkspace.start();
		control.afficheLoader(true);
	}



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
