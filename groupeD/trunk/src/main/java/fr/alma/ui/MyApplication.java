/*   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package fr.alma.ui;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.JMenu;

import fr.alma.atarigo.AtariGo;
import fr.alma.atarigo.AbstractPlayer;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Represent the graphic application of the Atarigo game.
 * @author Vincent FERREIRA, Adrien GUILLE
 * 
 * @version 1.0
 * 
 * revision $Revision$
 */
class MyApplication extends JFrame {

/**
 * board of the atarigo
 */
 protected UIGoban uiGoban;
 protected AtariGo atarigo;
 protected InfoPanel panInfos;
 protected JOptionPane message;
 protected JMenuBar menBar;
 protected JToolBar toolBar;
 protected JMenu menu;
 protected NewGameAction newGameAction;
 protected AboutAction aboutAction;
 
 
/**
 * action that quit the application. 
 */
 protected QuitAction quitAction;

/**
 *  build the toobar and menus.  
 */
 protected void buildAll() {
	menBar = new JMenuBar();
	toolBar = new JToolBar();
	menu = new JMenu("Commandes");
	menu.setMnemonic('C');
	newGameAction = new NewGameAction(this);
	menu.add(newGameAction);
	aboutAction = new AboutAction(this);
	menu.add(aboutAction);
	menu.addSeparator();
	quitAction = new QuitAction(this);
	menu.add(quitAction);
	toolBar.add(newGameAction);
	toolBar.add(aboutAction);
	toolBar.addSeparator();
	toolBar.add(quitAction);
	menBar.add(menu);
	setJMenuBar(menBar);
	toolBar.setFloatable(false);
	getContentPane().add(toolBar, BorderLayout.NORTH);
 }

/**
 * logic constructor
 */
 public MyApplication() {

	// Construction of the menu and the tool bar.
	buildAll();
	atarigo = new AtariGo(9,9);
	atarigo.shutDown();	
	// Creation of a graphic instance of the goban.
	uiGoban = new UIGoban(this);
	uiGoban.setMaximumSize(new Dimension(360,360));
	getContentPane().add(uiGoban, BorderLayout.CENTER);
	panInfos = new InfoPanel();
	panInfos.buildAll();
	panInfos.setPreferredSize(new Dimension(270,450));
	getContentPane().add(panInfos,BorderLayout.EAST);
	// Name of the window.
	setTitle("Atarigo");
	// initial move at the center of the screen.
	setLocationRelativeTo(null);
	// default behavior if the window is closed : calls the Quit action.
	addWindowListener(new WindowAdapter() {
		public void windowClosing(WindowEvent evt) {
		    quitAction.quit();
		}
	    });

 }
 
 /**
  * start a game of Atarigo
  * @param player1 the type of player human or AI
  * @param player2 the type of player human or AI
  * @throws InterruptedException
  */
 public void startGame(AbstractPlayer player1, AbstractPlayer player2,int challengeNb) throws InterruptedException{
	 atarigo.end=true;
	 atarigo = new AtariGo(9,9,player1,player2);
	 atarigo.captureObjective=challengeNb;
	 uiGoban.newGame();
	 System.out.println("nouvelle partie");
 }


}