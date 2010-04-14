package fr.alma.controler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
 * 
 * @author Manoël Fortun et Anthony "Bambinôme" Caillaud
 *	Class used to get action listener
 *
 */
public class ActionListenerFactory {
	/**
	 * The controler which receive order from the listener provided form this class
	 */
	private Controler controler;
	
	/**
	 * Constructor with the controler that is required
	 * @param control
	 */
	public ActionListenerFactory(Controler control){
		this.controler= control;
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
