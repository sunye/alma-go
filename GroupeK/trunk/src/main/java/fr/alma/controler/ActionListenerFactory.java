package fr.alma.controler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class ActionListenerFactory {

	private Controler controler;
	
	public ActionListenerFactory(Controler control){
		this.controler= control;
	}
	

	public ActionListener modeAi(){
		
		return new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controler.setModeAiVsHuman();
				
			}
		};
	}
	
	public ActionListener newGameListener(){
		return new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controler.newGame();
				
			}
		};
	}
	
	
	public ActionListener quitterListener(){
		return new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		};
	}
	



	public MouseAdapter clicBoardListener() {
		
		return  new MouseAdapter() {
			 public void mouseClicked(MouseEvent e) {
		          controler.clicBoard(e.getX(), e.getY());
		        }
		};
	}
	
	public ActionListener forcerCoup(){
		
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controler.forcerJouer();
				
			}
		};
	}
	
	public ActionListener AfficheAPropos(){
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controler.afficheMessage("Super Goban de la mort qui tue deux fois, \n developpé par Anthony Caillaud et Manoël Fortun");
				
			}
		};
	}
	
	public ActionListener enableChoiceIa(){
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controler.enableAiChoice(true);
			}
		};
	}
	
	
	public ActionListener disableChoiceIa(){
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controler.enableAiChoice(false);
			}
		};
	}
	
	public ActionListener afficheDiagNewGame(){
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controler.afficheNouvellePartie(true);
				
			}
		};
	}
	
	
	public ActionListener desAfficheDiagNewGame(){
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controler.afficheNouvellePartie(false);
				
			}
		};
	}
	
}
