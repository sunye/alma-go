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
 * Class used to get action listener
 * @author Manoël Fortun et Anthony "Bambinôme" Caillaud
 *	
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
	


	
	public ActionListener getNewGameListener(){
		return new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controler.newGame();
				
			}
		};
	}
	
	
	public ActionListener getQuitListener(){
		return new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		};
	}
	



	public MouseAdapter getClicBoardListener() {
		
		return  new MouseAdapter() {
			 public void mouseClicked(MouseEvent e) {
				   controler.clicBoard(e.getX(), e.getY());
		        }
		};
	}
	
	public ActionListener getForcePlayListener(){
		
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controler.forceToPlay();
				
			}
		};
	}
	
	public ActionListener getShowAboutListener(){
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controler.showMessage("Super Goban de la mort qui tue deux fois, \n developpé par Anthony Caillaud et Manoël Fortun");
				
			}
		};
	}
	
	public ActionListener getEnableChoiceAiListener(){
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controler.enableAiChoice(true);
			}
		};
	}
	
	
	public ActionListener getDisableChoiceAiListener(){
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controler.enableAiChoice(false);
			}
		};
	}
	
	public ActionListener getShowDiagNewGameListener(){
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controler.showNewGameDial(true);
				
			}
		};
	}
	
	
	public ActionListener getUnshowDiagNewGameListener(){
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controler.showNewGameDial(false);
				
			}
		};
	}
	
}
