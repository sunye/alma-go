package fr.alma.controler;

import fr.alma.ihm.Fenetre;
import fr.alma.modele.CouleurPion;
import fr.alma.modele.GameHandler;
import fr.alma.modele.Pion;


public class Controler {

	private GameHandler gm;
	private ActionListenerFactory factory;
	private Fenetre jeu;
	
	public Controler(){
		this.gm= new GameHandler(this);
		this.factory= new ActionListenerFactory(this);
		this.jeu= new Fenetre(this);

	}

	public GameHandler getGm() {
		return gm;
	}

	public ActionListenerFactory getFactory() {
		return factory;
	}

	public boolean ajouterPion(int gobanX, int gobanY){
				
		return gm.ajouterPion(gobanX, gobanY);
	}
	
	public void remiseZero(){
		gm.remiseZero();
	}
	
	public CouleurPion getGagnant(){
		return gm.getGagnant();
	}
	
	
	public int tailleMatrice(){
		return gm.tailleMatrice();
	}

	public Pion[][] getMatricePlateau() {
		
		return gm.getMatricePlateau();
	}

	public void setModeAiVsHuman(){
		gm.setModeAiVsHuman();
	}
	
	public void setModeHumanVsHuman(){
		gm.setModeHumanVsHuman();
	}

	public void GO() {
	this.jeu.setVisible(true);
		
	}
	
	public void newGame(){
		//TODO
	}
	
	public void afficheGagnant(CouleurPion coul){
		jeu.affichageVainqueur(coul);
	}

	public void clicBoard(int x, int y) {
		ajouterPion(x/jeu.getColSize(), y/jeu.getRowSize())	;	
		jeu.repaintBoard();
		
		
	}
	
	
}
