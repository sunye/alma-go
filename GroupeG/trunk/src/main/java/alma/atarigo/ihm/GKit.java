package alma.atarigo.ihm;

import java.awt.Component;
import java.awt.Dimension;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.InputStream;
import javax.imageio.ImageIO;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author steg
 */
public class GKit{

    public static BufferedImage loadImage(String imageUrl) throws IOException{
            if(imageUrl==null){
                throw new FileNotFoundException("No such resource: "+imageUrl);
            }
            InputStream imageStream = ClassLoader.getSystemClassLoader().getResourceAsStream(imageUrl);
            if(imageStream==null){
                throw new FileNotFoundException("No such resource: "+imageUrl);
            }
            return ImageIO.read(imageStream);
    }

    public static ImageIcon loadIcon(String iconUrl) {
        try {
            return new ImageIcon(loadImage(iconUrl));
        } catch (IOException ex) {
            Logger.getLogger(GKit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static JButton doButton(String url,String alt,String toolTipText,ActionListener action)
    {
        JButton button = new JButton();
        button.setToolTipText(toolTipText);

        //chargement de l'icon
        Icon icon =loadIcon(url);
        if(icon==null) {
            button.setText(alt);
        }else{
            button.setIcon(icon);
        }

        if(action!=null) {
            button.addActionListener(action);
        }

        return button;
    }

    public static JButton doButton(String iconUrl ,String iconText,String tooltipText){
        return doButton(iconUrl,iconText,tooltipText,ActionHandler.getHandler());
    }

    public static JMenuItem doItem(String alt,ActionListener action,KeyStroke stroke,String actionCommand)
    {
        JMenuItem button = new JMenuItem(alt);

        if(stroke!=null) {
            button.setAccelerator(stroke);
        }

        if(action!=null) {
            button.addActionListener(action);
        }

        if(actionCommand!=null){
            button.setActionCommand(actionCommand);
        }

        return button;
    }

    public static JMenuItem doItem(String alt,KeyStroke stroke){
        return doItem(alt,ActionHandler.getHandler(),stroke,null);
    }

    public static JMenuItem doItem(String alt,ActionListener action)
    {
        return doItem(alt,action,null,null);
    }

    public static JMenuItem doItem(String alt){
        return doItem(alt,ActionHandler.getHandler(),null,null);
    }

    public static JMenuItem doItem(String alt,KeyStroke stroke,String actionCommand){
        return doItem(alt,ActionHandler.getHandler(),stroke,actionCommand);
    }

    public static JMenuItem doItem(String alt,ActionListener action,String actionCommand)
    {
        return doItem(alt,action,null,actionCommand);
    }

    public static JMenuItem doItem(String alt,String actionCommand){
        return doItem(alt,ActionHandler.getHandler(),null,actionCommand);
    }
    
    public static void error(Component parent,String message) {
        JOptionPane.showMessageDialog(parent,message,"Erreur",JOptionPane.ERROR_MESSAGE);
    }

    public static void error(String message){
        error(MainWindow.getWindow(),message);
    }

    public static void warning(Component parent,String message) {
        JOptionPane.showMessageDialog(parent,message,"Attention",JOptionPane.WARNING_MESSAGE);
    }

    public static void warning(String message){
        warning(MainWindow.getWindow(),message);
    }

    public static void info(Component parent,String message) {
        JOptionPane.showMessageDialog(parent,message,"Information",JOptionPane.INFORMATION_MESSAGE);
    }

    public static void info(String message){
        info(MainWindow.getWindow(),message);
    }

    public static void popup(JDialog dialog){
        popup(MainWindow.getWindow(),dialog);
    }

    public static void popup(Component parent,final JDialog dialog){
        if(dialog!=null){
            dialog.setLocationRelativeTo((parent instanceof JDialog)?parent:MainWindow.getWindow());
            dialog.addComponentListener(new ComponentListener() {
                public void componentResized(ComponentEvent e) {}
                public void componentMoved(ComponentEvent e) {}
                public void componentShown(ComponentEvent e) {dialog.toFront();}
                public void componentHidden(ComponentEvent e) {}
            });
            dialog.setVisible(true);
        }
    }

}
