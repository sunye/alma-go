/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alma.atarigo.ihm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JToolBar;

import org.jdesktop.swingx.JXPanel;

import alma.atarigo.CellEvent;
import alma.atarigo.CellListener;
import alma.atarigo.CellPosition;
import alma.atarigo.EndOfGameEvent;
import alma.atarigo.EndOfGameListener;
import alma.atarigo.GobanModel;
import alma.atarigo.Level;
import alma.atarigo.Player;
import alma.atarigo.RuleViolationEvent;
import alma.atarigo.RuleViolationListener;
import alma.atarigo.Territory;

/**
 *
 * @author steg
 */
public class GameView extends JXPanel implements CellListener,RuleViolationListener,EndOfGameListener{

    /**
	 * 
	 */
	private static final long serialVersionUID = 3227599036498892699L;

    /**
     * Le jeu concerné par la vue
     */
    private final Game game;

    /**
     * Le chrometre pour un tour de jeu
     */
    Chronometer chrono = new Chronometer(Level.Debutant.getTimeToPlay());

    /**
     * La zone perdante en cas de fin de parite
     */
    java.util.List<CellPosition> loosingArea = new ArrayList<CellPosition>();

    /**
     * Moniteur utiliser pour bloquer la souris en attente d'un click
     */

    JLabel winnersLabel = new JLabel("Winners");
    JToolBar toolBar = new JToolBar();
    
    PlayerPanel kuroPanel = null;
    PlayerPanel shiroPanel = null;
    BanPanel banPanel = null;
    
    
    public GameView(final Game game){
        this.game = game;
        this.game.addCellListener(this);
        this.game.addRuleViolationListener(this);
        this.game.addEndOfGameListener(this);
        

        //======= La fenetre
        setLayout(new BorderLayout());
        //setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
        setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        
        //======= On configure la toolBar
        toolBar.setLayout(new BorderLayout());
        toolBar.setRollover(true);
        toolBar.setFloatable(false);
        toolBar.setBorderPainted(false);

        Player kuro = game.getKuro();
        Player shiro = game.getShiro();
        GobanModel model = game.getGoban();

        banPanel = new BanPanel(model);
        banPanel.add(toolBar,BorderLayout.PAGE_START);

        
        kuroPanel = new PlayerPanel(banPanel,kuro,"Kuro");
        kuroPanel.updateScore(model.getKuroCount());
        kuroPanel.updateCapture(model.getKuroPrisoners().size());
        kuroPanel.updateObjective(kuro.getObjective());
        
        shiroPanel = new PlayerPanel(banPanel,shiro,"Shiro");
        shiroPanel.updateScore(model.getShiroCount());
        shiroPanel.updateCapture(model.getShiroPrisoners().size());
        shiroPanel.updateObjective(shiro.getObjective());
        
        add(kuroPanel,BorderLayout.WEST);
        add(banPanel,BorderLayout.CENTER);
        add(shiroPanel,BorderLayout.EAST);
        
        chrono.setStringPainted(true);
        chrono.setString(" GO ");
        toolBar.add(chrono,BorderLayout.CENTER);

        //on met en evidence celui qui commence
        enhancePlayerVisibility();

    }

    /**
     * Met en evidence le joueur qui doi joueur
     */
    public void enhancePlayerVisibility(){
        chrono.setValue(0);
        if(game.isKuroTurn()){
            chrono.setBackground(banPanel.getKuroColor());
            chrono.setForeground(banPanel.getShiroColor());
            chrono.setString("Kuro's Turn");
            //StatusBar.setMessage("Kuro");           
        }
        else if(game.isShiroTurn()){
            chrono.setBackground(banPanel.getShiroColor());
            chrono.setForeground(banPanel.getKuroColor());
            chrono.setString("Shiro's Turn");
            //StatusBar.setMessage("Shiro");
        }

    }


    public void clear(){
    	chrono.stop();
    	chrono.setTimeOutAction(null);
    	loosingArea.clear();
    	banPanel.clear();
    }
    
    /**
     * Utiliser pour notifier un changement sur la cellule
     * @param event
     */
    public void cellChanged(final CellEvent event) {
        //enhancePlayerVisibility();
        GobanModel model = game.getGoban();
        
        kuroPanel.updateScore(model.getKuroCount());
        kuroPanel.updateCapture(model.getKuroPrisoners().size());
        
        shiroPanel.updateScore(model.getShiroCount());
        shiroPanel.updateCapture(model.getShiroPrisoners().size());

        shiroPanel.monitor.close();
        kuroPanel.monitor.close();
        
        if(game.isKuroTurn()){
            chrono.setBackground(banPanel.getKuroColor());
            chrono.setForeground(banPanel.getShiroColor());
            chrono.setString("Kuro's Turn");
            //StatusBar.setMessage("Kuro");           
        }
        else if(game.isShiroTurn()){
            chrono.setBackground(banPanel.getShiroColor());
            chrono.setForeground(banPanel.getKuroColor());
            chrono.setString("Shiro's Turn");
            //StatusBar.setMessage("Shiro");
        }
        
        banPanel.clearHints();
        banPanel.repaint();
    }

    /**
     * Utiliser pour notifier la violation d'une regle
     * @param event
     */
    public void ruleViolated(final RuleViolationEvent event) {
        chrono.pause();
        GKit.info(event.getRuleName());
    }

    /**
     * 
     * @param e  Evenement de fin de jeu
     */
    public void gameOver(final EndOfGameEvent e) {
        final String gameOverMsg = "Game Over: "+(e.getWinner()==game.getKuro()?"Kuro Wins":"Shiro Wins");

        GobanModel model = game.getGoban();
        
        kuroPanel.updateCapture(model.getKuroPrisoners().size());        
        shiroPanel.updateCapture(model.getShiroPrisoners().size());
        
        kuroPanel.monitor.close();
        shiroPanel.monitor.close();
        
        //==== on arrete le chrono
        chrono.stop();
        chrono.setString(gameOverMsg);

        //==== on met a jour les zone montrant la situation de fin de jeu
        final Color wColor = game.isKuroTurn()?banPanel.getKuroColor():banPanel.getShiroColor();
        for(Territory ter : e.getLosingArea()){
        	loosingArea.addAll(ter.getPositions());
        }
        banPanel.surroundArea(loosingArea, wColor);

        GKit.info(gameOverMsg);        
    }

    public CellPosition getPositionOnMouseClick(){
    	if(game.isKuroTurn()){
    		shiroPanel.monitor.close();
    	}else{
    		kuroPanel.monitor.close();
    	}
    	return banPanel.getPositionOnUIEvent();
    }
    
    public void setModel(GobanModel model){
    	banPanel.clear();
    	banPanel.model = model;
    	banPanel.repaint();
    }
    
    
}
