/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alma.atarigo;

import java.util.List;

/**
 *
 * @author gassMouy()
 */
public interface Territory extends Iterable<CellPosition>{

    /**
     * ajouter une nouvelle position dans le territoire
     * @param position : la position
     */
    public void addCellPosition(CellPosition position);

    /**
     * fusionner deux territoires entre eux
     * @param territory : le deuxieme territoire
     * @return : le nouveau territoire
     */
    public Territory fusionWith(Territory territory);

    /**
     * acceder aux positions composant le territoire
     * @return : une liste de positions
     */
    public List<CellPosition> getPositions();

    /**
     * acceder au propriétaire du territoire
     * @return : le content
     */
    public CellContent getCellContent();

    /**
     * teste si le territoire possede une cellule
     * @param position : la cellule
     * @return : un boolean
     */
    public boolean contains(CellPosition position);

    /**
     * teste si un l'ensemble est inclus (en sens large) dans un autre
     * @param territory : l'autre territoire
     * @return : un boolean
     */
    public boolean isIncludedIn(Territory territory);

    /**
     * teste si un l'ensemble est strictement inclus dans un autre
     * @param territory : l'autre ensemble
     * @return : un boolean
     */
    public boolean isStrictlyIncludedIn(Territory territory);

    /**
     * teste si l'ensemble est égal a un autre territoire
     * @param territory : l'autre territoire
     * @return : un boolean
     */
    public boolean equals(Territory territory);

    /**
     * teste si le territoire est ennemi avec un autre
     * @param territory : l'autre territoire
     * @return : un boolean
     */
    public boolean isEnemyWith(Territory territory);

    /**
     * supprimer une cellule du territoire
     * @param position : la position
     */
    public void remove(CellPosition position);

    /**
     * @return La taille du territoire
     */
    public int size();
    
    /**
     * Tester si le territoire ne contient qu'un element 
     * @return true <code> size()==1 </code>
     */
    public boolean isSingleton();
}
