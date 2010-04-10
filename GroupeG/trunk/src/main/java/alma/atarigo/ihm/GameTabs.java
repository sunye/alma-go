/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alma.atarigo.ihm;

import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import alma.atarigo.CellContent;
import alma.atarigo.Player;
import alma.atarigo.ia.ArtificialPlayer;

/**
 *
 * @author steg
 */
public class GameTabs extends JTabbedPane{

    //private List<Game> games = new ArrayList<Game>();

    private HashMap<Component,Game> games = new HashMap<Component, Game>();
    
    /**
     * Obtenir le controlleur du jeu courant
     * @return Le jeu actif
     */
    public Game getActiveGame(){
    	Component c = this.getSelectedComponent();
    	if(c!=null){
    		return games.get(c);
    	}
    	return null;
    }

    /**
     * Fermer la vue du jeu passer en parametre
     * @param game
     */
    public void removeGame(Game game){
    	Component c = null;
    	for(Entry<Component,Game> e : games.entrySet()){
    		if(game==e.getValue()){
    			c = e.getKey();
    			this.remove(c);
    		}
    	}
    	games.remove(c);
    }


    public void addGame(Game game){
    	Component c = new JScrollPane(game.getView());
    	int newIndex = this.getTabCount();
    	this.addTab("Goban "+newIndex,c);
    	this.setSelectedComponent(c);
    	games.put(c, game);
    }
    
    //======== on en fait un singleton
    private GameTabs(){
    }

    public final static GameTabs instance = new GameTabs();

}

