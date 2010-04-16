/*
 *   
 *   IA Project ATARI-GO
 *   UNIVERSITY OF NANTES
 *   MASTER ALMA 1
 *   2009 - 2010
 * 	 Version 1.0
 *   
 *   $Date$
 *   $Author$
 * 	 $Revision$
 * 
 *   Copyright (C) 2010 Romain Gournay & Bruno Belin, All rights reserved.
 * 
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
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
	
	private JCheckBox chkbInterruption;
	private JCheckBox chkbAssistant;
	private JComboBox cbGrid;
	private JComboBox cbColorComputer;
	private JComboBox cbOpponent;
	
	private JTextField tfmaxTargC;
	private JTextField tfmaxTargP;
	
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
		
        JLabel labelTimeLimite = new JLabel("Time limit reflection Software (seconds) : ");
        panel.add(labelTimeLimite);
        
        tfTimeLimit = new JTextField(5);
        tfTimeLimit.setText("15");
        tfTimeLimit.setToolTipText("in seconds");
        Tools.addFocusListener(tfTimeLimit);
        panel.add(tfTimeLimit);

        JLabel lblInterruption = new JLabel("Possibility to interrupt the calculations : ");
        panel.add(lblInterruption);
        
        chkbInterruption = new JCheckBox();
        panel.add(chkbInterruption);

        JLabel labelGridSize = new JLabel("Grid size : ");
        panel.add(labelGridSize);
        
        cbGrid= new JComboBox(LIST_GRIDSIZE);
        cbGrid.setSelectedIndex(1);
        panel.add(cbGrid);
        
        JLabel labelOpponent = new JLabel("Play against : ");
        panel.add(labelOpponent);
        
        cbOpponent = new JComboBox(LIST_PLAYER);
        cbOpponent.setSelectedIndex(0);
        cbOpponent.setEnabled(false);
        panel.add(cbOpponent);

        JLabel lblColorC = new JLabel("Color opponent : ");
        panel.add(lblColorC);
        
        cbColorComputer = new JComboBox(LIST_COLOR);
        cbColorComputer.setSelectedIndex(0);
        panel.add(cbColorComputer);
        
        JLabel labelRoleComputer = new JLabel("Assistant : ");
        panel.add(labelRoleComputer);
        
        chkbAssistant = new JCheckBox();
        chkbAssistant.setEnabled(false);
        panel.add(chkbAssistant);

        JLabel lblTargCatchC = new JLabel("Target capture opponent : ");
        panel.add(lblTargCatchC);
        
        tfmaxTargC = new JTextField(5);
        tfmaxTargC.setText("1");
        tfmaxTargC.setEnabled(false);
        Tools.addFocusListener(tfmaxTargC);
        panel.add(tfmaxTargC);

        JLabel lblmaxCatchPlayer = new JLabel("Target capture player : ");
        panel.add(lblmaxCatchPlayer);
        
        tfmaxTargP = new JTextField(5);
        tfmaxTargP.setText("1");
        tfmaxTargP.setEnabled(false);
        
        Tools.addFocusListener(tfmaxTargP);
        panel.add(tfmaxTargP);

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


	public boolean isPossibilityInterruption() {
		return chkbInterruption.isSelected();
	}
	
	public int getGridSize() {
		if (cbGrid.getSelectedIndex() == 0) {
			return 6;
		} else {
			return 9;
		}
	}

	public boolean isColorComputer() {
		if (cbColorComputer.getSelectedIndex() == 0) {
			return Configuration.BLACK;
		} else {
			return Configuration.WHITE;
		}
	}

	
	public boolean isAssitant() {
		return  chkbAssistant.isSelected();
	}
	
	private boolean isOpponent() {
		if (cbOpponent.getSelectedIndex() == 0) {
			return true;
		} else {
			return false;
		}
	}

	public int getTargetCaptureComputer() {
		return new Integer(tfmaxTargC.getText()).intValue();
	}
	
	
	public int getTargetCapturePlayer() {
		return new Integer(tfmaxTargP.getText()).intValue();
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
		param.setColorComputer(isColorComputer());
		param.setSizeGoban(getGridSize());
		param.setPossibilityInterruption(isPossibilityInterruption());
		param.setAssistant(isAssitant());
		param.setOpponent(isOpponent());
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
		IPlayer player = new Player("Player", ! context.getParamGame().isColorComputer(), goban, stateGame);

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

