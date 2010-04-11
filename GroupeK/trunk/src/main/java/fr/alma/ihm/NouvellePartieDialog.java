package fr.alma.ihm;

import java.awt.FlowLayout;


import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import fr.alma.controler.Controler;

public class NouvellePartieDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JRadioButton radioAiHuman;
	private JRadioButton radioHumanHuman;
	private JRadioButton radioFacile;
	private JRadioButton radioMoyen;
	private JRadioButton radioDur;
	private JRadioButton radionCaptun;
	private JRadioButton radionCapttrois;
	private JRadioButton radionCaptcinq;
	private ButtonGroup typeJeu;
	private ButtonGroup difficulte;
	private ButtonGroup capture;
	private JButton cancel;
	private JButton okButton;

	public NouvellePartieDialog(Fenetre owner, Controler ctrl) {
		super(owner, "Le choix des options", true);

		
		
		cancel = new JButton("Annuler");
		okButton = new JButton("Ok/Valider");
		
		radionCaptun= new JRadioButton("Une pierrre", true);
		radionCapttrois= new JRadioButton("Trois pierres");
		radionCaptcinq= new JRadioButton("Cinq pierres");
		
		radioHumanHuman= new JRadioButton("Humain vs Humain", true);
		radioAiHuman= new JRadioButton("Humain vs IA");
		
		radioFacile= new JRadioButton("Facile", true);
		
		radioMoyen= new JRadioButton("Moyen");
		radioDur= new JRadioButton("Dur");
		
		typeJeu = new ButtonGroup();
		difficulte = new ButtonGroup();
		capture= new ButtonGroup();
		
		typeJeu.add(radioAiHuman);
		typeJeu.add(radioHumanHuman);
		
		difficulte.add(radioFacile);
		difficulte.add(radioMoyen);
		difficulte.add(radioDur);
		
		capture.add(radionCaptun);
		capture.add(radionCapttrois);
		capture.add(radionCaptcinq);
		
		JLabel choixJeu= new JLabel("Choix du mode");
		JLabel choixObjectif= new JLabel("Choix Objectif de Capture");
		JLabel choixDifficulteAi= new JLabel("Choix de la difficulté de L'ia");
		
		
		
		JPanel panel= new JPanel();
		this.add(panel);
		
		FlowLayout flow = new FlowLayout(FlowLayout.LEFT);
		panel.setLayout(flow);
		
		panel.add(choixJeu);
		panel.add(radioHumanHuman);
		panel.add(radioAiHuman);
		
		panel.add(choixDifficulteAi);
		panel.add(radioFacile);
		panel.add(radioMoyen);
		panel.add(radioDur);
		
		panel.add(choixObjectif);
		panel.add(radionCaptun);
		panel.add(radionCapttrois);
		panel.add(radionCaptcinq);
		
		
		panel.add(okButton);
		panel.add(cancel);
		
		this.radioHumanHuman.addActionListener(ctrl.getFactory().disableChoiceIa());
		this.radioAiHuman.addActionListener(ctrl.getFactory().enableChoiceIa());
		
		this.cancel.addActionListener(ctrl.getFactory().desAfficheDiagNewGame());
		this.okButton.addActionListener(ctrl.getFactory().newGameListener());
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setSize(190,295);
		this.setResizable(false);
		this.reset();
	}

	public void affichage(boolean visible){
		this.reset();
		this.setVisible(visible);
	}
	
	public int getNbObjectifSelected(){
		if (radionCapttrois.isSelected()){
			return 3;
		}else if (radionCaptcinq.isSelected()){
			return 5;
		}
		return 1;
	}
	
	public int getDifficulte(){
		if( radioMoyen.isSelected()){
			return 1;
		}else if (radioDur.isSelected()) {
			return 2;
		}
		
		
		return 0;
	}
	
	
	public boolean isAiVsHuman(){
		return radioAiHuman.isSelected();
	}
	
	public void enableChoiceDifficulte(boolean enab){
		radioFacile.setEnabled(enab);
		radioMoyen.setEnabled(enab);
		radioDur.setEnabled(enab);
		
	}
	
	
	public void reset(){
		radioFacile.setSelected(true);
		radioMoyen.setSelected(false);
		radioDur.setSelected(false);
		
		radioAiHuman.setSelected(false);
		radioHumanHuman.setSelected(true);
		
		radionCapttrois.setSelected(false);
		radionCaptun.setSelected(true);
		radionCaptcinq.setSelected(false);
		this.enableChoiceDifficulte(false);
	}
}
