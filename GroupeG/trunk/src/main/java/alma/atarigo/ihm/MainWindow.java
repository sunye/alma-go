/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alma.atarigo.ihm;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 *
 * @author steg
 */
public class MainWindow extends JFrame{

    private JMenuBar menuBar = new JMenuBar();
    private JToolBar toolBar = new JToolBar();
    private JTabbedPane gamePane = new JTabbedPane();
    private JToolBar statusBar = new JToolBar();

    private final static Properties properties = new Properties();

    static{
        try {
            properties.load(ClassLoader.getSystemClassLoader().getResourceAsStream("alma/atarigo/ihm/app.properties"));
        } catch (IOException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void configure(){
        setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));

        //===== Configuration de la fenetre
        setTitle(properties.getProperty("window.title","Go"));
        Dimension dim = new Dimension(
                 Integer.valueOf(properties.getProperty("window.width", "640"))
                ,Integer.valueOf(properties.getProperty("window.height", "480"))
                );
        setPreferredSize(dim);
        setMinimumSize(dim);
        setResizable(Boolean.valueOf(properties.getProperty("window.resizable", "true")));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //===== La barre de menu
        initMenuBar();
        setJMenuBar(menuBar);

        //===== La toolBar
        initToolBar();
        add(toolBar,BorderLayout.PAGE_START);

        //===== Le pane des jeux
        initGamePane();
        add(gamePane,BorderLayout.CENTER);

        //===== Le pied de page
        initStatusBar();
        add(statusBar,BorderLayout.PAGE_END);
    }

    private void initMenuBar(){
        JMenu gameMenu = new JMenu("Jeu");
        JMenu levelMenu = new JMenu("Niveau");
        JMenu helpMenu = new JMenu("Aide");
        JMenu aboutMenu = new JMenu("?");

        menuBar.add(gameMenu);
        menuBar.add(helpMenu);
        menuBar.add(aboutMenu);

        //==== game Menu
        gameMenu.add(GKit.doItem("Nouvelle partie", KeyStroke.getKeyStroke(KeyEvent.VK_N,KeyEvent.CTRL_MASK)));
        gameMenu.add(GKit.doItem("Recommencer", KeyStroke.getKeyStroke(KeyEvent.VK_R,KeyEvent.CTRL_MASK)));
        gameMenu.add(GKit.doItem("Abandonner", KeyStroke.getKeyStroke(KeyEvent.VK_G,KeyEvent.CTRL_MASK)));
        gameMenu.add(GKit.doItem("Quitter", KeyStroke.getKeyStroke(KeyEvent.VK_Q,KeyEvent.CTRL_MASK)));

        //==== Le menu d'aide
        helpMenu.add(levelMenu);
        helpMenu.add(GKit.doItem("Annuler", KeyStroke.getKeyStroke(KeyEvent.VK_Z,KeyEvent.CTRL_MASK)));
        helpMenu.add(GKit.doItem("Auto jeu", KeyStroke.getKeyStroke(KeyEvent.VK_H,KeyEvent.CTRL_MASK)));

        //==== about go rafa
        aboutMenu.add(GKit.doItem("A propos",KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0)));

//        //==== game Menu
//        gameMenu.add(GKit.doItem("Nouvelle partie", KeyStroke.getKeyStroke(KeyEvent.VK_N,KeyEvent.CTRL_MASK)),ActionHandler.NEW_GAME);
//        gameMenu.add(GKit.doItem("Recommencer", KeyStroke.getKeyStroke(KeyEvent.VK_R,KeyEvent.CTRL_MASK)),ActionHandler.RESTART_GAME);
//        gameMenu.add(GKit.doItem("Abandonner", KeyStroke.getKeyStroke(KeyEvent.VK_G,KeyEvent.CTRL_MASK)),ActionHandler.END_GAME);
//        gameMenu.add(GKit.doItem("Quitter", KeyStroke.getKeyStroke(KeyEvent.VK_Q,KeyEvent.CTRL_MASK)),ActionHandler.EXIT);
//
//        //==== Le menu d'aide
//        helpMenu.add(levelMenu);
//        helpMenu.add(GKit.doItem("Annuler", KeyStroke.getKeyStroke(KeyEvent.VK_Z,KeyEvent.CTRL_MASK)),ActionHandler.CANCEL_LAST_MOVE);
//        helpMenu.add(GKit.doItem("Auto jeu", KeyStroke.getKeyStroke(KeyEvent.VK_H,KeyEvent.CTRL_MASK)),ActionHandler.AUTOPLAY);
//
//        //==== about go rafa
//        aboutMenu.add(GKit.doItem("A propos",KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0)),ActionHandler.ABOUT);

    }

    private void initToolBar(){

    }

    private void initGamePane(){

    }

    private void initStatusBar(){

    }

    private MainWindow(){
        configure();
    }



    //================= Singleton
    private static MainWindow mainWindow = new MainWindow();

    public static MainWindow getWindow(){
        return mainWindow;
    }

}
