/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.alma.atarigo.ihm;

import java.awt.Frame;
import javax.swing.JDialog;

/**
 *
 * @author judu
 */
public class OptionWindow extends JDialog {

    public OptionWindow(Frame owner) {
        super(owner);
        this.setTitle("Options");
        this.setSize(200, 80);
        
        
        //La position
        this.setLocationRelativeTo(null);
        //La boîte ne devra pas être redimensionnable
        this.setResizable(false);
        //Enfin on l'affiche
        this.setVisible(true);
    }
}
