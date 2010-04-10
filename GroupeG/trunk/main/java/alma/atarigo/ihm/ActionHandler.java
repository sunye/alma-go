/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alma.atarigo.ihm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import alma.atarigo.CellContent;
import alma.atarigo.Level;

/**
 *
 * @author steg
 */
public class ActionHandler {
    private static JFileChooser gofileChooser = new JFileChooser();

    static {
        gofileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        gofileChooser.addChoosableFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f!=null && f.getName().endsWith(".gogo");
            }

            @Override
            public String getDescription() {
                return ".gogo : Fichier de jeu de go";
            }
        });
       gofileChooser.setMultiSelectionEnabled(false);
    }

    public static String SAVE = "SAVE-GAME";
    public static String LOAD_GAME = "LOAD-SAVED-GAME";
    public static String NEW_GAME_1P_vs_2P = "NEW-GAME-1P-VS-2P";
    public static String NEW_GAME_COM_vs_2P = "NEW-GAME-COM-VS-2P";
    public static String NEW_GAME_1P_vs_COM = "NEW-GAME-1P-VS-COM";
    public static String NEW_GAME_COM_vs_COM = "NEW-GAME-COM-VS-COM";
    public static String CUSTOM_GAME = "NEW-CUSTOM-GAME";
    public static String RESTART_GAME = "RESTART-GAME";
    public static String CANCEL_LAST_MOVE = "CANCEL_LAST_MOVE";
    public static String END_GAME = "GIVE-UP";
    public static String EXIT = "EXIT";
    public static String AUTOPLAY = "AUTO-PLAY";
    public static String ABOUT = "ABOUT";
    private static String GENERAL_LEVEL = "GENERAL-LEVEL";
    public static String GENERAL_LEVEL_BEGINNER = "GENERAL-LEVEL-BEGINNER";
    public static String GENERAL_LEVEL_REGULAR = "GENERAL-LEVEL-REGULAR";
    public static String GENERAL_LEVEL_EXPERT = "GENERAL-LEVEL-EXPERT";
    private static String CURRENT_GAME_LEVEL = "CURRENT-GAME-LEVEL";
    public static String CURRENT_GAME_LEVEL_BEGINNER = "CURRENT-GAME-LEVEL-BEGINNER";
    public static String CURRENT_GAME_LEVEL_REGULAR = "CURRENT-GAME-LEVEL-REGULAR";
    public static String CURRENT_GAME_LEVEL_EXPERT = "CURRENT-GAME-LEVEL-EXPERT";

   public static void handleAction(String actionID){
       //Creer un jeu humain contre human
       if(NEW_GAME_1P_vs_2P.equals(actionID)){
    	   Game game = GameFactory.newHumanVsHumanGame();
           if(game!=null){
        	   GameTabs.instance.addGame(game);
               game.start();
           }
        }

       else if(NEW_GAME_1P_vs_COM.equals(actionID)){
    	   Game game = GameFactory.newHumanVsComputerGame();
           if(game!=null){
        	   GameTabs.instance.addGame(game);
               game.start();
           }
        }

        else if(NEW_GAME_COM_vs_2P.equals(actionID)){
     	   Game game = GameFactory.newComputerVsHumanGame();
           if(game!=null){
        	   GameTabs.instance.addGame(game);
               game.start();
           }
        }

        else if(NEW_GAME_COM_vs_COM.equals(actionID)){
     	   Game game = GameFactory.newComputerVsComputerGame();
           if(game!=null){
        	   GameTabs.instance.addGame(game);
               game.start();
           }
        }
       
        else if(CUSTOM_GAME.equals(actionID)){
        	CustomGamePanel.showDialog(MainWindow.getWindow());
        }
       
        else if(RESTART_GAME.equals(actionID)){
           Game game = GameTabs.instance.getActiveGame();
           if(game!=null){
        	   game.stop();
        	   GameTabs.instance.removeGame(game);
        	   
        	   game = GameFactory.newGame(game);
        	   GameTabs.instance.addGame(game);
               
        	   game.start();
           }
        }

       //abandonner la partie courante
        else if(END_GAME.equals(actionID)){
            Game game = GameTabs.instance.getActiveGame();            
            if(game!=null){
                game.stop();
                GameTabs.instance.removeGame(game);               
            }
        }

        //modifier le niveau genEral
        else if(actionID.startsWith(GENERAL_LEVEL)){
        	for(Level l : Level.values()){
        		if(actionID.endsWith(l.toString().toUpperCase())){
        			Preferences.GENERAL_LEVEL = l;
        			break;
        		}
        	}
        }

        //modifier le niveau du jeu actif
        else if(actionID.startsWith(CURRENT_GAME_LEVEL)){
            Game game = GameTabs.instance.getActiveGame();
            if(game!=null){
            	for(Level l : Level.values()){
            		if(actionID.endsWith(l.toString().toUpperCase())){
            			game.setLevel(l);
            			break;
            		}
            	}
            }
        }

        //quitter le programme
        else if(EXIT.equals(actionID)){
            System.exit(0);
        }

        else if(SAVE.equals(actionID)){
            Game g = GameTabs.instance.getActiveGame();
            if(g!=null){
                if(gofileChooser.showSaveDialog(g.getView())==JFileChooser.APPROVE_OPTION){
                    File f = gofileChooser.getSelectedFile();
                    g.save(f.getParentFile(), f.getName());
                }
            }
        }

        else if(LOAD_GAME.equals(actionID)){
            if(gofileChooser.showOpenDialog(MainWindow.getWindow())==JFileChooser.APPROVE_OPTION){
            	Game game = GameFactory.newGameFromFile(gofileChooser.getSelectedFile());
            	if(game!=null){
            		GameTabs.instance.addGame(game);
            		game.start();
            	}
            }
        }
       
        else{
            GKit.info(actionID+" non implementee");
        }
       
    }

    public static ActionListener newActionListener(final String actionCommand){
        return new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                handleAction(actionCommand);
            }

        };
    }
    
    public static void fireLevelChange(Level level){
    	handleAction(String.format("%s-%s",GENERAL_LEVEL,level.toString().toUpperCase()));
    	handleAction(String.format("%s-%s",CURRENT_GAME_LEVEL,level.toString().toUpperCase()));
    }
    

}
