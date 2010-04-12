/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alma.atarigo.ihm;

import alma.atarigo.AbstractPlayer;
import alma.atarigo.CellContent;
import alma.atarigo.CellPosition;
import alma.atarigo.IProgressMonitor;
import alma.atarigo.Level;
import alma.atarigo.Player;
import alma.atarigo.ia.AlphaBeta;
import alma.atarigo.ia.IA;
import alma.atarigo.ia.valuation.Random;

/**
 *
 * @author steg
 */
public class HumanPlayer extends AbstractPlayer implements Player{

	IA brain = null;
	
    public HumanPlayer(CellContent content) {
		super(content);		
		
		brain = new IA();
		brain.setAlgorithm(new AlphaBeta());
		brain.setValuation(new Random(Integer.MIN_VALUE, Integer.MAX_VALUE));
		brain.setLevel(Level.Debutant);
		
		metadata.put("class", "alma.atarigo.ihm.HumanPlayer");
	}

	public CellPosition nextPlay(IProgressMonitor progressMonitor) {
        return ((Game)owner).getView().getPositionOnMouseClick();
    }
	
	
}
