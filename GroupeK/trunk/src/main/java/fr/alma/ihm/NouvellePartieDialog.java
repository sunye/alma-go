package fr.alma.ihm;

import java.awt.FlowLayout;


import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import fr.alma.controler.Controler;
/*$Author$ 
 * $Date$ 
 * $Revision$  
 * 
 *Copyright (C) 2010  Fortun Manoël & Caillaud Anthony
 *
 *This program is free software: you can redistribute it and/or modify
 *it under the terms of the GNU General Public License as published by
 *the Free Software Foundation, either version 3 of the License, or
 *(at your option) any later version.
 *
 *This program is distributed in the hope that it will be useful,
 *but WITHOUT ANY WARRANTY; without even the implied warranty of
 *MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *GNU General Public License for more details.
 *
 *You should have received a copy of the GNU General Public License
 *along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * */
/**
 * @author Manoël Fortun et Anthony "Bambinôme" Caillaud
 * Class that represent the option dialog 
 */
public class NouvellePartieDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	
	/**
	 * radio button for the aivshuman mode
	 */
	private JRadioButton radioAiHuman;
	
	/**
	 * radio button for the humanvshuman mode
	 */
	private JRadioButton radioHumanHuman;
	
	/**
	 * radio button for the ai easy mode
	 */
	private JRadioButton radioFacile;
	
	/**
	 * radio button for the ai medium mode
	 */
	private JRadioButton radioMoyen;
	
	/**
	 * radio button for the ai hard mode
	 */
	private JRadioButton radioDur;
	
	/**
	 * radio button for the one stone capture goal mode
	 */
	private JRadioButton radionCaptun;
	
	/**
	 * radio button for the three stone capture goal mode
	 */
	private JRadioButton radionCapttrois;
	
	/**
	 * radio button for the five stone capture goal mode
	 */
	private JRadioButton radionCaptcinq;


	/**
	 * Construct and initialise the dialog
	 * @param owner the owner
	 * @param ctrl the controler
	 */
	public NouvellePartieDialog(Fenetre owner, Controler ctrl) {
		super(owner, "Le choix des options", true);

		JButton cancel = new JButton("Annuler");
		JButton okButton = new JButton("Ok/Valider");

		radionCaptun = new JRadioButton("Une pierrre", true);
		radionCapttrois = new JRadioButton("Trois pierres");
		radionCaptcinq = new JRadioButton("Cinq pierres");

		radioHumanHuman = new JRadioButton("Humain vs Humain", true);
		radioAiHuman = new JRadioButton("Humain vs IA");

		radioFacile = new JRadioButton("Facile", true);

		radioMoyen = new JRadioButton("Moyen");
		radioDur = new JRadioButton("Dur");

		ButtonGroup typeJeu = new ButtonGroup();
		ButtonGroup difficulte = new ButtonGroup();
		ButtonGroup capture = new ButtonGroup();

		typeJeu.add(radioAiHuman);
		typeJeu.add(radioHumanHuman);

		difficulte.add(radioFacile);
		difficulte.add(radioMoyen);
		difficulte.add(radioDur);

		capture.add(radionCaptun);
		capture.add(radionCapttrois);
		capture.add(radionCaptcinq);

		JLabel choixJeu = new JLabel("Choix du mode");
		JLabel choixObjectif = new JLabel("Choix Objectif de Capture");
		JLabel choixDifficulteAi = new JLabel("Choix de la difficulté de L'ia");

		JPanel panel = new JPanel();
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

		this.radioHumanHuman.addActionListener(ctrl.getFactory()
				.disableChoiceIa());
		this.radioAiHuman.addActionListener(ctrl.getFactory().enableChoiceIa());

		cancel.addActionListener(ctrl.getFactory().desAfficheDiagNewGame());
		okButton.addActionListener(ctrl.getFactory().newGameListener());
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setSize(190, 295);
		this.setResizable(false);
		this.reset();
	}

	/**
	 * Show or not the dialog. It reset the option if true
	 * @param visible
	 */
	public void affichage(boolean visible) {
		if(visible){
			this.reset();
		}
		this.setVisible(visible);
	}

	/**
	 * Get the capture goal selected 
	 * @return goal number
	 */
	public int getNbObjectifSelected() {
		if (radionCapttrois.isSelected()) {
			return 3;
		} else if (radionCaptcinq.isSelected()) {
			return 5;
		}
		return 1;
	}

	/**
	 * Get the difficulty selected
	 * @return 1 easy, 2 medium, 3 hard
	 */
	public int getDifficulte() {
		if (radioMoyen.isSelected()) {
			return 1;
		} else if (radioDur.isSelected()) {
			return 2;
		}

		return 0;
	}

	/**
	 * is the AivsHuman mode is selected
	 * @return
	 */
	public boolean isAiVsHuman() {
		return radioAiHuman.isSelected();
	}

	/**
	 * enable or disable the choice of the Ai difficulty
	 * @param enab 
	 */
	public void enableChoiceDifficulte(boolean enab) {
		radioFacile.setEnabled(enab);
		radioMoyen.setEnabled(enab);
		radioDur.setEnabled(enab);

	}

	/**
	 * reset the option
	 */
	public void reset() {
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
