/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alma.atarigo.ihm;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JToolBar;

import alma.atarigo.IProgressMonitor;

/**
 *
 * @author E055983B
 */
public class StatusBar extends JToolBar{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1037071723902638079L;

	private final static DateFormat FORMAT = new SimpleDateFormat("HH:mm:ss");
    
    private JLabel timeLabel = new JLabel(getDate());
    private ProgressMonitor progressMonitor = new ProgressMonitor();

    public StatusBar(){
        setLayout(new BorderLayout());
        setMaximumSize(new Dimension(1920,80));

        
        add(timeLabel,BorderLayout.WEST);
        add(progressMonitor,BorderLayout.EAST);

        setFloatable(false);
        setRollover(false);

    }
    
    private static String getDate(){
    	return FORMAT.format(new java.util.Date(System.currentTimeMillis()));
    }
    
    private static StatusBar singleton = null;
    
    public static StatusBar getStatusBar(){
    	if(singleton==null){
    		singleton = new StatusBar();
    	}
		return singleton;
    }
    
//    public static void setMessage(String message){
//    	getStatusBar().progressMonitor.setString(message);
//    }
    
//    public static void showActivity(){
//    	getStatusBar().progressMonitor.setIndeterminate(true);
//    }
//    
//    public static void hideActivity(){
//    	getStatusBar().progressMonitor.setIndeterminate(false);
//    }
    
    public IProgressMonitor getProgressMonitor(){
    	progressMonitor.clear();
    	return progressMonitor;
    }

 
 
}

