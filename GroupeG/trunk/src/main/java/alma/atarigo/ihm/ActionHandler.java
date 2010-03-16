/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alma.atarigo.ihm;

import java.awt.event.*;

/**
 *
 * @author steg
 */
public class ActionHandler implements ActionListener{

    public static String NEW_GAME = "NEW-GAME";
    public static String RESTART_GAME = "RESTART-GAME";
    public static String CANCEL_LAST_MOVE = "CANCEL_LAST_MOVE";
    public static String END_GAME = "GIVE-UP";
    public static String EXIT = "EXIT";
    public static String AUTOPLAY = "AUTO-PLAY";
    public static String ABOUT = "ABOUT";

    private static ActionHandler actionHandler = new ActionHandler();

    private ActionHandler(){
    }

    public void actionPerformed(ActionEvent e) {
        if(e!=null){
            if(NEW_GAME.equals(e.getActionCommand())){

            }
            else if(RESTART_GAME.equals(e.getActionCommand())){

            }
            else if(CANCEL_LAST_MOVE.equals(e.getActionCommand())){

            }
            else if(END_GAME.equals(e.getActionCommand())){

            }
            else if(EXIT.equals(e.getActionCommand())){
                System.exit(0);
            }
        }
    }


    public static ActionHandler getHandler(){
        return actionHandler;
    }

}
