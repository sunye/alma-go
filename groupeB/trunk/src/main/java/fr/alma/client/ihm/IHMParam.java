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
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import fr.alma.client.action.Context;
import fr.alma.client.action.ParamGame;
import fr.alma.common.ui.SpringUtilities;
import fr.alma.common.ui.Tools;
import fr.alma.server.core.Computer;
import fr.alma.server.core.Coordinator;
import fr.alma.server.core.Factory;
import fr.alma.server.core.IPlayer;
import fr.alma.server.core.IStateGame;
import fr.alma.server.core.IStrategy;
import fr.alma.server.core.Player;
import fr.alma.server.ia.IEvaluation;
import fr.alma.server.rule.Configuration;
import fr.alma.server.rule.RuleManager;


/**
 * IHM to define the game parameters
 */
@SuppressWarnings("serial")
public class IHMParam extends AbstractDialog {
	public static final String[] LIST_GRIDSIZE = new String[]{"6x6", "9x9"};
	public static final String[] LIST_COLOR = new String[]{"Black", "White"};
	public static final String[] LIST_PLAYER = new String[]{"Computer", "Player"};
	
	private JTextField tfTimeLimit;
	
	private JCheckBox chkbPossibilityInterruption;
	private JCheckBox chkbAssistant;
	private JComboBox cbGrid;
	private JComboBox cbColorComputer;
	private JComboBox cbOpponent;
	
	private JTextField tfTargetCaptureComputer;
	private JTextField tfTargetCapturePlayer;
	
	private Context context = null;
	private Goban goban = null;
	
	
	public IHMParam(Context context) {
		super(context.getMainFrame(), "New Game");
		this.context = context;
	}
	
	
	protected void initComponents() {
	   	Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
    	
    	SpringLayout layout = new SpringLayout();
    	JPanel panel = new JPanel(layout);
    	panel.setPreferredSize(new Dimension(410, 240));
    	panel.setBorder(loweredetched);
    	
    	getMainPanel().add(panel, BorderLayout.CENTER);
		
        JLabel labelTimeLimite = new JLabel("Time limit reflection Software : ");
        panel.add(labelTimeLimite);
        
        tfTimeLimit = new JTextField(5);
        tfTimeLimit.setText("15");
        tfTimeLimit.setToolTipText("in seconds");
        Tools.addFocusListener(tfTimeLimit);
        panel.add(tfTimeLimit);

        JLabel labelPossibilityInterruption = new JLabel("Possibility to interrupt the calculations : ");
        panel.add(labelPossibilityInterruption);
        
        chkbPossibilityInterruption = new JCheckBox();
        panel.add(chkbPossibilityInterruption);

        JLabel labelGridSize = new JLabel("Grid size : ");
        panel.add(labelGridSize);
        
        cbGrid= new JComboBox(LIST_GRIDSIZE);
        cbGrid.setSelectedIndex(1);
        panel.add(cbGrid);
        
        JLabel labelOpponent = new JLabel("Play against : ");
        panel.add(labelOpponent);
        
        cbOpponent = new JComboBox(LIST_PLAYER);
        cbOpponent.setSelectedIndex(0);
        panel.add(cbOpponent);

        JLabel labelColorComputer = new JLabel("Color opponent : ");
        panel.add(labelColorComputer);
        
        cbColorComputer = new JComboBox(LIST_COLOR);
        cbColorComputer.setSelectedIndex(0);
        panel.add(cbColorComputer);
        
        JLabel labelRoleComputer = new JLabel("Assistant : ");
        panel.add(labelRoleComputer);
        
        chkbAssistant = new JCheckBox();
        panel.add(chkbAssistant);

        JLabel labelTargetCaptureComputer = new JLabel("Target capture opponent : ");
        panel.add(labelTargetCaptureComputer);
        
        tfTargetCaptureComputer = new JTextField(5);
        tfTargetCaptureComputer.setText("1");
        Tools.addFocusListener(tfTargetCaptureComputer);
        panel.add(tfTargetCaptureComputer);

        JLabel labelTargetCapturePlayer = new JLabel("Target capture player : ");
        panel.add(labelTargetCapturePlayer);
        
        tfTargetCapturePlayer = new JTextField(5);
        tfTargetCapturePlayer.setText("1");
        Tools.addFocusListener(tfTargetCapturePlayer);
        panel.add(tfTargetCapturePlayer);

        SpringUtilities.makeCompactGrid(panel,
                8, 2,		 //rows, cols
                6, 6,        //initX, initY
                6, 6);       //xPad, yPad
        
        JPanel panelPied = new JPanel(new FlowLayout(FlowLayout.CENTER));
        getMainPanel().add(panelPied, BorderLayout.SOUTH);
        
        panelPied.add(getButtonOk("OK", 'O'));
        panelPied.add(getButtonCancel());
	}
	
	
	public void actionCancel() {
		setVisible(false);
	}
	
	
	public int getTimeLimit() {
		return new Integer(tfTimeLimit.getText()).intValue();
	}


	public boolean getPossibilityInterruption() {
		return chkbPossibilityInterruption.isSelected();
	}
	
	public int getGridSize() {
		if (cbGrid.getSelectedIndex() == 0) {
			return 6;
		} else {
			return 9;
		}
	}

	public boolean getColorComputer() {
		if (cbColorComputer.getSelectedIndex() == 0) {
			return Configuration.BLACK;
		} else {
			return Configuration.WHITE;
		}
	}

	
	public boolean getAssitant() {
		return  chkbAssistant.isSelected();
	}
	
	private boolean getOpponent() {
		if (cbOpponent.getSelectedIndex() == 0) {
			return true;
		} else {
			return false;
		}
	}

	public int getTargetCaptureComputer() {
		return new Integer(tfTargetCaptureComputer.getText()).intValue();
	}
	
	
	public int getTargetCapturePlayer() {
		return new Integer(tfTargetCapturePlayer.getText()).intValue();
	}
	
	
	/**
	 * Check the values seized
	 * Show a box with the mistakes
	 * 
	 * @return true if the values seized are valid
	 */
	public boolean controlSaisie() {
		try {
			if (getTimeLimit() < 0) {
				Tools.message(this, "Time Limite", "Invalid data", JOptionPane.ERROR_MESSAGE);
				return false;
			}
			
			if (getTargetCaptureComputer() <= 0) {
				Tools.message(this, "Target Capture Computer", "Invalid data", JOptionPane.ERROR_MESSAGE);
				return false;
			}

			if (getTargetCapturePlayer() <= 0) {
				Tools.message(this, "Target Capture player", "Invalid data", JOptionPane.ERROR_MESSAGE);
				return false;
			}
			
		} catch (Exception e) {
			Tools.message(this, "Exception", e.getLocalizedMessage(), JOptionPane.ERROR_MESSAGE);

			return false;
		}	
		return true;
	}

	
	/**
	 * @return a new ParamGame
	 */
	private ParamGame getParamGame() {
		ParamGame param = new ParamGame();
		param.setColorComputer(getColorComputer());
		param.setSizeGoban(getGridSize());
		param.setPossibilityInterruption(getPossibilityInterruption());
		param.setAssistant(getAssitant());
		param.setOpponent(getOpponent());
		param.setTargetCaptureComputer(getTargetCaptureComputer());
		param.setTargetCapturePlayer(getTargetCapturePlayer());
		param.setTimeLimite(getTimeLimit());
		return param;
	}


	@Override
	public void actionOk() {
		
		if (controlSaisie()) {
			initContext();
			this.setVisible(false);
			getContext().getCoordinator().startGame();
		}
	}
	
	/**
	 * Initialise the context of the game
	 */
	private void initContext() {
		
		if (getContext().getCoordinator() != null) {
			getContext().getCoordinator().cleanUp();
			getContext().getStateGame().cleanUp();
			getContext().getComputer().cleanUp();
			getContext().getPlayer().cleanUp();
		}
		
		getContext().setParamGame(getParamGame());
		
		IStateGame stateGame = Factory.getStateGame(getContext());
		getContext().setStateGame(stateGame);
		
		RuleManager ruleManager = Factory.getRuleManager(getContext());
		getContext().setRuleManager(ruleManager);
		
		Coordinator coordinator = new Coordinator(getContext());

		if (goban == null) {
			getContext().setGoban(getGoban());
			context.getMainFrame().add(goban);
		}
		goban.revalidate();
		
		IPlayer computer = new Computer("Computer", context);
		IPlayer player = new Player("Player", ! context.getParamGame().getColorComputer(), goban, stateGame);

		IEvaluation evaluation = Factory.getEvaluation(context);
		IStrategy strategy = Factory.getStrategy(context);
		computer.setStrategy(strategy);
		computer.setEvaluation(evaluation);
		getContext().setPlayer(player);
		getContext().setComputer(computer);
		
		context.setCoordinator(coordinator);
	}


	public Context getContext() {
		return context;
	}
	
	
	/**
	 * Singleton Pattern
	 * Necessary for good compatibility with Swing components 
	 * @return
	 */
	public Goban getGoban() {
		if (goban == null) {
			goban = Factory.getGoban(getContext());
		}
		return goban;			
	}
}

