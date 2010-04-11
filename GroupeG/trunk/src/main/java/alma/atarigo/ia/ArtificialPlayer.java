/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alma.atarigo.ia;

import alma.atarigo.AbstractPlayer;
import alma.atarigo.CellContent;
import alma.atarigo.CellPosition;
import alma.atarigo.IProgressMonitor;
import alma.atarigo.Player;
import alma.atarigo.ihm.Game;

/**
 * Joueur représentant la machine
 * @author gass-mouy
 */
public class ArtificialPlayer extends AbstractPlayer implements Player {

   
    public ArtificialPlayer(CellContent content){
    	super(content);
		metadata.put("class", "alma.atarigo.ia.ArtificialPlayer");        
    }


    public CellPosition nextPlay(IProgressMonitor progressMonitor) {
    	if(brain == null){
        	brain = new IA(owner);
    	}
        try{
        	((Game)owner).getView().enhancePlayerVisibility();
        	return brain.run(progressMonitor);
        }finally{
        }
    }

}
