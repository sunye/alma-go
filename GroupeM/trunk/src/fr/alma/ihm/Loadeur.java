package fr.alma.ihm;

import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

import fr.alma.controler.Controler;


public class Loadeur extends JDialog {

	/**
   * 
   */
	private static final long serialVersionUID = 5146830235988312604L;

	private JProgressBar prog;

	public Loadeur(Fenetre owner, Controler ctrl) {
		super(owner, "", true);

		this.prog = new JProgressBar(0, 100);
		prog.setIndeterminate(true);

		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		this.setSize(180, 90);
		setLocationRelativeTo(owner);
		this.setLayout(new FlowLayout());

		JButton quitterUrgence = new JButton("Quitter jeu");
			
		JButton forcerJouer= new JButton("Forcer � jouer");

		quitterUrgence.addActionListener(ctrl.getFactory().quitterListener());
		forcerJouer.addActionListener(ctrl.getFactory().forcerCoup());
		
		this.add(new JLabel("Coup en cours de calcul"));
		this.add(prog);
		this.add(forcerJouer);
		this.add(quitterUrgence);

	}

}
