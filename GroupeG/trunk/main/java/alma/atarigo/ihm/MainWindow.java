/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alma.atarigo.ihm;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;

import org.jdesktop.swingx.JXFrame;
import org.jdesktop.swingx.JXPanel;

/**
 *
 * @author steg
 */
public class MainWindow extends JXFrame implements WindowStateListener,WindowListener{

    /**
	 * 
	 */
	private static final long serialVersionUID = -5955441102048537344L;
	private MainMenuBar menuBar = new MainMenuBar();
    private ToolBar toolBar = ToolBar.getToolBar();
    private GameTabs gamesPane = GameTabs.instance;
//    private StatusBar statusBar = StatusBar.getStatusBar();
    private JXPanel contentPane = new JXPanel();

    private final static Properties properties = new Properties();

    static{
        try {
            properties.load(ClassLoader.getSystemClassLoader().getResourceAsStream("alma/atarigo/ihm/app.properties"));
        } catch (IOException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void configure(){
        setContentPane(contentPane);
        //contentPane.setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
        contentPane.setLayout(new BorderLayout());

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
        setLocationRelativeTo(null);
        try {
            BufferedImage img = GKit.loadImage("icons/go.png");
            if(img!=null){
                setIconImage(img);
            }
        } catch (IOException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }

        //===== La barre de menu
        setJMenuBar(menuBar);

        //===== La toolBar
        //contentPane.
                add(toolBar,BorderLayout.PAGE_START);
        toolBar.setAlignmentX(CENTER_ALIGNMENT);

        //===== Le pane des jeux
        contentPane.add(gamesPane,BorderLayout.CENTER);

        //===== Le pied de page
        //contentPane.
//                add(statusBar,BorderLayout.PAGE_END);

        this.addWindowListener(this);
        this.addWindowStateListener(this);
    }
   
    
    private MainWindow(){
        configure();
    }



    //================= Singleton
    private static MainWindow mainWindow = new MainWindow();

    public static MainWindow getWindow(){
        return mainWindow;
    }

    public void windowStateChanged(WindowEvent e) {
    }

    public void windowOpened(WindowEvent e) {
    }

    public void windowClosing(WindowEvent e) {
    }

    public void windowClosed(WindowEvent e) {
    }

    public void windowIconified(WindowEvent e) {
    }

    public void windowDeiconified(WindowEvent e) {
    }

    public void windowActivated(WindowEvent e) {
    }

    public void windowDeactivated(WindowEvent e) {
    }

}
