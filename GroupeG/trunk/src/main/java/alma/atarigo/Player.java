/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alma.atarigo;

import java.util.Properties;

/**
 *
 * @author steg
 */
public interface Player {
	/**
	 * Objectif minimum pour un joueur
	 */
	public final static int MIN_OBJECTIVE = 1;
	/**
	 * Objectif maximum pour un joueur
	 */
	public final static int MAX_OBJECTIVE = 15;
	
	/**
	 * @return La couleur du joueur Kuro ou Shiro
	 */
	public abstract CellContent getColor();
	
    /**
     * Le joueur joue son prochain coup
     * @return : la position de la cellule souhaitée
     */
    public abstract CellPosition nextPlay(IProgressMonitor progressMonitor);
    
    /**
     * @return Le nombre de pion a capturer par le joueur pour gagner
     */
    public abstract int getObjective();
    
    /**
     * @param newOne Le nouvel objectif de capture du joueur
     */
    public abstract void setObjective(int newOne);
    
    /**
     * Renseigner le controleur pour se joueur
     * @param controller
     */
    public abstract void setOwner(Controller controller);
    
    /**
     * 
     * @return Le jeu auquel appartient le joueur
     */
    public abstract Controller getOwner();
    
    /**
     * 
     * @return Les informations qui permettent de recréer le joueur
     */
    public abstract Properties getMetadata();
}
