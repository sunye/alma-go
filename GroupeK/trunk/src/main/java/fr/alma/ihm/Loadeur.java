package fr.alma.ihm;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

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
 * 
 * @author Manoël Fortun et Anthony "Bambinôme" Caillaud 
 * A dialog with a progress bar to indicate that something is in progress
 */
public class Loadeur extends JDialog {

	/**
   * 
   */
	private static final long serialVersionUID = 5146830235988312604L;

	private JProgressBar prog;

	/**
	 * construct and initialise the dialog
	 * @param owner the windows that own the loadeur
	 * @param ctrl the controler
	 */
	public Loadeur(Fenetre owner, Controler ctrl) {
		super(owner, "", true);

		this.prog = new JProgressBar(0, 100);
		prog.setIndeterminate(true);

		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		this.setSize(180, 150);
		setLocationRelativeTo(owner);
		this.setLayout(new FlowLayout());

		JButton quitterUrgence = new JButton("Quitter jeu");
			
		JButton forcerJouer= new JButton("Forcer à jouer");

		quitterUrgence.addActionListener(ctrl.getFactory().quitterListener());
		forcerJouer.addActionListener(ctrl.getFactory().forcerCoup());
		
		this.add(new JLabel("Coup en cours de calcul"));
		this.add(prog);
		this.add(forcerJouer);
		this.add(quitterUrgence);

	}

}
