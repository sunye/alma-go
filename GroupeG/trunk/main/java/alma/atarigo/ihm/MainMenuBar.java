/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alma.atarigo.ihm;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import alma.atarigo.Level;

/**
 *
 * @author steg
 */
public class MainMenuBar extends JMenuBar implements MenuListener,ItemListener{

    JMenu gameMenu = new JMenu("Jeu");
    JMenu levelMenu = new JMenu("Niveau");
    JMenu helpMenu = new JMenu("Aide");
    JMenu aboutMenu = new JMenu("?");
    JMenu newGame = new JMenu("Partie rapide");
    JMenuItem customGame = GKit.doItem(
    		"Partie personalisée"
    		,ActionHandler.CUSTOM_GAME
    		,KeyStroke.getKeyStroke(KeyEvent.VK_N,KeyEvent.CTRL_MASK));

    JMenuItem restartGame = GKit.doItem("Recommencer", ActionHandler.RESTART_GAME,KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_MASK));

    public MainMenuBar(){
        configure();
    }
    
    private void configure(){
        add(gameMenu);
        add(helpMenu);
        add(aboutMenu);

        //==== game Menu
        newGame.add(GKit.doItem(" 1P Vs 2P",ActionHandler.NEW_GAME_1P_vs_2P));
        newGame.add(GKit.doItem(" 1P Vs COM",ActionHandler.NEW_GAME_1P_vs_COM));
        newGame.add(GKit.doItem("COM Vs 2P",ActionHandler.NEW_GAME_COM_vs_2P));
        newGame.add(GKit.doItem("COM Vs COM",ActionHandler.NEW_GAME_COM_vs_COM));

        gameMenu.add(newGame);
        gameMenu.add(customGame);
        gameMenu.add(restartGame);
        gameMenu.add(GKit.doItem("Abandonner",ActionHandler.END_GAME, KeyStroke.getKeyStroke(KeyEvent.VK_G,KeyEvent.CTRL_MASK)));
        gameMenu.add(GKit.doItem("Quitter",ActionHandler.EXIT, KeyStroke.getKeyStroke(KeyEvent.VK_Q,KeyEvent.CTRL_MASK)));

        //==== Le menu d'aide
        //======= Affichage des Indices
        final JCheckBoxMenuItem hintItem = new JCheckBoxMenuItem("Indice visuels");
        hintItem.addItemListener(new ItemListener() {			
			@Override
			public void itemStateChanged(ItemEvent e) {
				Preferences.SHOW_HINTS = hintItem.isSelected();
			}
		});
        hintItem.setSelected(Preferences.SHOW_HINTS);

        helpMenu.add(levelMenu);
        helpMenu.add(GKit.doItem("Annuler",ActionHandler.CANCEL_LAST_MOVE, KeyStroke.getKeyStroke(KeyEvent.VK_Z,KeyEvent.CTRL_MASK)));
        helpMenu.add(GKit.doItem("Auto jeu",ActionHandler.AUTOPLAY, KeyStroke.getKeyStroke(KeyEvent.VK_H,KeyEvent.CTRL_MASK)));
        helpMenu.add(hintItem);

        //==== about go rafa
        aboutMenu.add(GKit.doItem("A propos",ActionHandler.ABOUT,KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0)));

        //==== Le niveau general du jeu
        for(Level level : Level.values()){
            JCheckBoxMenuItem levelItem = new JCheckBoxMenuItem(level.toString());
            levelItem.addItemListener(this);
            levelItem.setActionCommand(level+"_LEVEL");
            if(level.equals(Preferences.GENERAL_LEVEL)){
                levelItem.setSelected(true);
            }
            levelMenu.add(levelItem);
        }
                
        //========== evenement sur les menus
        gameMenu.addMenuListener(this);

    }

    public void menuSelected(MenuEvent e) {
//        restartGame.setEnabled(GameController.getGameTabs().getActiveGame()!=null);
    }

    public void menuDeselected(MenuEvent e) {
    }

    public void menuCanceled(MenuEvent e) {
    }

    public void itemStateChanged(ItemEvent e) {
        if(e.getStateChange()==ItemEvent.SELECTED){
            JCheckBoxMenuItem item = (JCheckBoxMenuItem) e.getSource();
            for(int i=0;i<levelMenu.getItemCount();++i){
                JCheckBoxMenuItem menuItem = (JCheckBoxMenuItem) levelMenu.getItem(i);
                if(item!=menuItem){
                    menuItem.setSelected(false);
                }
            }
            Preferences.GENERAL_LEVEL = Level.valueOf(item.getText());
        }
    }

}
