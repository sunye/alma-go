/*
 * IA Project ATARI-GO
 * UNIVERSITY OF NANTES
 * MASTER ALMA 1
 * 2009 - 2010
 * Version 1.0
 * @author Romain Gournay & Bruno Belin
 * 
 * Copyright 2010 Romain Gournay & Bruno Belin, All rights reserved.
 * Use is subject to license terms.
 */
package fr.alma.client.ihm;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import fr.alma.common.ui.Tools;


/**
 * Represent a standard dialog window 
 *
 */
@SuppressWarnings("serial")
public abstract class AbstractDialog extends JDialog {
	protected JPanel mainPanel = null;
	protected JButton btnOk = null;
	protected JButton btnCancel = null;
	

    public AbstractDialog(JFrame parent, String title) {
    	super(parent, title);
        setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(true); //content panes must be opaque
        setContentPane(mainPanel);

        initComponents();
        
        pack();
        setResizable(false);
        Tools.center(this);
    }
    
    
    public JButton getButtonCancel() {
    	if (btnCancel == null) {
    		btnCancel = new JButton("Cancel");
    		btnCancel.setMnemonic('C');
    		btnCancel.addActionListener(new ActionListener() {
    			
    			@Override
    			public void actionPerformed(ActionEvent e) {
    				actionCancel();
    			}
    		});
    	}
    	return btnCancel;
    }
	

    public JButton getButtonOk(String texte, char mnemonic) {
    	if (btnOk == null) {
    		btnOk = new JButton(texte);
    		btnOk.setMnemonic(mnemonic);
    		btnOk.addActionListener(new ActionListener() {
    			
    			@Override
    			public void actionPerformed(ActionEvent e) {
    				actionOk();
    			}
    		});
    	}
    	return btnOk;
    }

    
    public abstract void actionCancel();
    
    public abstract void actionOk();
    
    protected abstract void initComponents();
    
    
	public JPanel getMainPanel() {
		return mainPanel;
	}


	public void setMainPanel(JPanel mainPanel) {
		this.mainPanel = mainPanel;
	}
}
