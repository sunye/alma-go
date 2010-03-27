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
import fr.alma.server.rule.Configuration;


@SuppressWarnings("serial")
public class IHMParam extends AbstractDialog {
	public static final String[] LIST_GRIDSIZE = new String[]{"6x6", "9x9"};
	public static final String[] LIST_COLOR = new String[]{"Black", "White"};
	public static final String[] LIST_ROLE = new String[]{"Player", "Assistant"};
	
	JTextField tfTimeLimite;
	JCheckBox chkbPossibilityInterruption;
	JComboBox cbGrille;
	JComboBox cbColorComputer;
	JComboBox cbRoleComputer;
	JTextField tfTargetCaptureComputer;
	JTextField tfTargetCapturePlayer;
	
	Context context = null;
	
	
	
	public IHMParam(Context context) {
		super(context.getMainFrame(), "New Game");
		this.context = context;
	}
	
	
	protected void initComponents() {
	   	Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
    	
    	SpringLayout layout = new SpringLayout();
    	JPanel panel = new JPanel(layout);
    	panel.setPreferredSize(new Dimension(400, 200));
    	panel.setBorder(loweredetched);
    	
    	getMainPanel().add(panel, BorderLayout.CENTER);
		
        JLabel labelTimeLimite = new JLabel("Time limit reflection Software : ");
        panel.add(labelTimeLimite);
        
        tfTimeLimite = new JTextField(5);
        tfTimeLimite.setText("15");
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

        JLabel labelColorComputer = new JLabel("Color computer : ");
        panel.add(labelColorComputer);
        
        cbColorComputer = new JComboBox(LIST_COLOR);
        cbColorComputer.setSelectedIndex(0);
        panel.add(cbColorComputer);

        JLabel labelRoleComputer = new JLabel("Role computer : ");
        panel.add(labelRoleComputer);
        
        cbRoleComputer = new JComboBox(LIST_ROLE);
        cbRoleComputer.setSelectedIndex(0);
        panel.add(cbRoleComputer);

        JLabel labelTargetCaptureComputer = new JLabel("Target capture computer : ");
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
                7, 2,		 //rows, cols
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
	
	
	public short getTimeLimite() {
		return new Short(tfTimeLimite.getText()).shortValue();
	}


	public boolean getPossibilityInterruption() {
		return chkbPossibilityInterruption.isSelected();
	}
	
	public short getGridSize() {
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

	
	public String getRoleComputer() {
		return (String)cbGrille.getSelectedItem();
	}

	public short getTargetCaptureComputer() {
		return new Short(tfTargetCaptureComputer.getText()).shortValue();
	}
	
	
	public short getTargetCapturePlayer() {
		return new Short(tfTargetCapturePlayer.getText()).shortValue();
	}
	
	
	/**
	 * Controle des elements saisis.
	 * Affiche une box en cas d'anomalie detectee.
	 * 
	 * @return true si les saisies sont correctes
	 */
	public boolean controlSaisie() {
		try {
			if (getTimeLimite() <= 0) {
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
		param.setRoleComputer(getRoleComputer());
		param.setTargetCaptureComputer(getTargetCaptureComputer());
		param.setTargetCapturePlayer(getTargetCapturePlayer());
		param.setTimeLimite(getTimeLimite());
		return param;
	}

	@Override
	public void actionOk() {
		
		if (controlSaisie()) {
			getContext().setParamGame(getParamGame());
			this.setVisible(false);
			getContext().getCoordinator().startGame();
		}
	}


	public Context getContext() {
		return context;
	}
}

