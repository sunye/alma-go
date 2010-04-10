/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alma.atarigo.ihm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Logger;

import alma.atarigo.Controller;
import alma.atarigo.GobanModel;
import alma.atarigo.IProgressMonitor;
import alma.atarigo.Level;
import alma.atarigo.Player;
import alma.atarigo.ia.ArtificialPlayer;
import alma.atarigo.model.Goban;
import alma.atarigo.rule.Rules;

/**
 *
 * @author steg
 */
public class Game extends Controller implements Runnable{
    
    private final static String MODEL_IMPL = "alma.atarigo.model.Goban";
    private GameView view = new GameView(this);
    private final GobanModel initialModel;

    private Thread worker = null;
    /**
     * Creer un nouveau jeu
     * @param kuro Le joueur 1
     * @param shiro Le joueur 2
     * @param level Le niveau du jeu
     * @throws Throwable En cas d'erreur lors de la creation du jeu
     */
    public Game(Player kuro,Player shiro,Level level) throws Throwable{
        this(newGoban(),kuro,shiro,level);
    }

    /**
     * Créer un nouveau jeu
     * @param goban
     * @param kuro
     * @param shiro
     * @param level
     */
    public Game(GobanModel goban,Player kuro,Player shiro,Level level){
    	super(kuro,shiro,goban,level,Rules.ATARI_RULES);
    	kuro.setOwner(this);
    	shiro.setOwner(this);
    	initialModel = new Goban(goban);
    }
    
    private static GobanModel newGoban() throws Throwable{
        return (GobanModel) ClassLoader.getSystemClassLoader().loadClass(MODEL_IMPL).newInstance();
    }

    public GobanModel getInitialModel(){
    	return initialModel;
    }
    
    /**
     * Créer un nouveau jeu
     * @param kuro Le joueur qui debute la partier
     * @param shiro  L'adversaire
     * @throws Throwable En cas d'erreur
     */
    public Game(Player kuro,Player shiro) throws Throwable{
        this(kuro,shiro,Preferences.GENERAL_LEVEL);
    }

    /**
     * Debuter la partier
     */
    public void start(){
        if(worker==null){
        	view.setVisible(true);
            view.repaint();
            view.clear();
            worker = new Thread(this);
            worker.start();
        }
    }

    /**
     * Arreter la partie
     */
    public void stop(){
        if(worker!=null){
        	view.clear();
        	abortGame();
        	worker.interrupt();
        	worker = null;
        }
    }

    /**
     * Obtenir la vue du jeu
     * @return La vue graphique du jeu
     */
    public GameView getView(){
        return view;
    }

    public void save(File root,String name){
        File f = new File(root,name+(name.endsWith(".gogo")?"":".gogo"));
        try {
            super.saveTo(f);
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            GKit.error("Impossible de sauvegarder la partie courante");
        } catch(Throwable ex){
            Logger.getLogger(Game.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            GKit.error("Une erreur est survenue lors de la sauvegarde");
        }
    }

    public void  load(File f){
        boolean restartAfter = worker!=null;
        stop();
        try {
            super.loadFrom(f);
            GKit.info("La partie a été charger avec succès");
            restartAfter = true;
        } catch (FileNotFoundException ex){
            GKit.error("Le fichier "+f+" n' existe pas");
        } catch (Throwable ex) {
            Logger.getLogger(Game.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            GKit.error("Impossible de charger la partie : "+ex.getMessage());
        }

        if(restartAfter){
            start();
        }
    }

	@Override
	public void run() {
		playGame();
	}

	@Override
	public IProgressMonitor getMonitorFor(Player player) {
		if(player instanceof ArtificialPlayer){
			ProgressMonitor monitor =  null;
			
			if(getKuro()==player){
				monitor=view.kuroPanel.monitor;
			}else if(getShiro()==player){
				monitor=view.shiroPanel.monitor;			
			}			
			
			if(monitor!=null){
				monitor.clear();
			}
			
			return monitor;
		}
		return null;
	}
	
	@Override
	public void setGoban(GobanModel model){
		super.setGoban(model);
		if(view!=null){
			view.setModel(model);
		}
	}

}
