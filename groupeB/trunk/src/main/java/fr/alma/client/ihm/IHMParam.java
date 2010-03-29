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


@SuppressWarnings("serial")
public class IHMParam extends AbstractDialog {
	public static final String[] LIST_GRIDSIZE = new String[]{"6x6", "9x9"};
	public static final String[] LIST_COLOR = new String[]{"Black", "White"};
	public static final String[] LIST_PLAYER = new String[]{"Computer", "Player"};
	
	JTextField tfTimeLimite;
	
	JCheckBox chkbPossibilityInterruption;
	JCheckBox chkbAssistant;
	JComboBox cbGrille;
	JComboBox cbColorComputer;
	JComboBox cbOpponent;
	
	JTextField tfTargetCaptureComputer;
	JTextField tfTargetCapturePlayer;
	
	Context context = null;
	Goban goban = null;
	
	
	public IHMParam(Context context) {
		super(context.getMainFrame(), "New Game");
		this.context = context;
	}
	
	
	protected void initComponents() {
	   	Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
    	
    	SpringLayout layout = new SpringLayout();
    	JPanel panel = new JPanel(layout);
    	panel.setPreferredSize(new Dimension(410, 220));
    	panel.setBorder(loweredetched);
    	
    	getMainPanel().add(panel, BorderLayout.CENTER);
		
        JLabel labelTimeLimite = new JLabel("Time limit reflection Software : ");
        panel.add(labelTimeLimite);
        
        tfTimeLimite = new JTextField(5);
        tfTimeLimite.setText("15");
        tfTimeLimite.setToolTipText("in seconds");
        Tools.addFocusListener(tfTimeLimite);
        panel.add(tfTimeLimite);

        JLabel labelPossibilityInterruption = new JLabel("Possibility to interrupt the calculations : ");
        panel.add(labelPossibilityInterruption);
        
        chkbPossibilityInterruption = new JCheckBox();
        panel.add(chkbPossibilityInterruption);

        JLabel labelGridSize = new JLabel("Grid size : ");
        panel.add(labelGridSize);
        
        cbGrille= new JComboBox(LIST_GRIDSIZE);
        cbGrille.setSelectedIndex(0);
        panel.add(cbGrille);
        
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
	
	
	public int getTimeLimite() {
		return new Integer(tfTimeLimite.getText()).intValue();
	}


	public boolean getPossibilityInterruption() {
		return chkbPossibilityInterruption.isSelected();
	}
	
	public int getGridSize() {
		if (cbGrille.getSelectedIndex() == 0) {
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
	 * Controle des elements saisis.
	 * Affiche une box en cas d'anomalie detectee.
	 * 
	 * @return true si les saisies sont correctes
	 */
	public boolean controlSaisie() {
		try {
			if (getTimeLimite() < 0) {
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

	
	private ParamGame getParamGame() {
		ParamGame param = new ParamGame();
		param.setColorComputer(getColorComputer());
		param.setGrille(getGridSize());
		param.setPossibilityInterruption(getPossibilityInterruption());
		param.setAssistant(getAssitant());
		param.setOpponent(getOpponent());
		param.setTargetCaptureComputer(getTargetCaptureComputer());
		param.setTargetCapturePlayer(getTargetCapturePlayer());
		param.setTimeLimite(getTimeLimite());
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
		
		RuleManager ruleManager = Factory.getRuleManager();
		getContext().setRuleManager(ruleManager);
		
		Coordinator coordinator = new Coordinator(getContext());

		if (goban == null) {
			getContext().setGoban(getGoban());
			context.getMainFrame().setContentPane(goban);
		}
		goban.revalidate();
		
		IPlayer computer = new Computer("Computer", context.getParamGame().getColorComputer(),
				context.getParamGame().getTimeLimite());
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
	
	public Goban getGoban() {
		if (goban == null) {
			goban = Factory.getGoban(getContext());
		}
		return goban;			
	}
}

