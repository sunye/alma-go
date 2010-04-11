/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alma.atarigo;

import java.util.*;

/**
 *
 * @author steg
 */
public interface GobanModel extends Iterable<Cell> {

    /**
     * Met à jour une cellule
     * @param position : la position de la cellule
     * @param content : le nouvel état de la cellule
     */
    public void setCellContent(CellPosition position,CellContent content);

    /**
     * Surcharge : met à jour une cellule
     * @param row : le numéro de la ligne de la cellule
     * @param column : le numéro de la colonne de la cellule
     * @param content : le nouvel état de la cellule
     */
    public void setCellContent(int row,int column,CellContent content);

    /**
     * Recupere l'état d'une cellule
     * @param position : la position de la cellule
     * @return : l'état
     */
    public CellContent getCellContent(CellPosition position);

    /**
     * Surcharge : recupere l'état d'une cellule
     * @param row : le numéro de la ligne de la cellule
     * @param column : le numéro de la colonne de la cellule
     * @return : l'état
     */
    public CellContent getCellContent(int row,int column);

    /**
     * Recupere toutes les libertés d'une cellule
     * @param position : la position de la cellule en question
     * @return : une liste de cellules
     */
    public List<CellPosition> getLiberties(CellPosition position);

    /**
     * Recupere les libertes de tout un territoire
     * @param territory : le territoire
     * @param content : la position de la cellule jouée
     * @return : la liste des libertés
     */
    public List<CellPosition> getLibertiesFor(Territory territory,CellPosition position);

    /**
     * Recupere les libertes de tout un territoire
     * @param territory : le territoire
     * @return : la liste des libertés
     */
    public List<CellPosition> getLibertiesFor(Territory territory);
    
    /**
     * Recupere les libertés d'une liste de territoires
     * @param territories La liste des territoires
     * @return La liste des libertés
     */
    public List<CellPosition> getLibertiesFor(List<Territory> territories);

    public List<CellPosition> getLibertiesFor(CellPosition position,List<Territory>territories);

    /**
     * Recupere un territoire à partir d'une cellule
     * @param position : la position de la cellule
     * @return : une liste de cellule
     */
    public Territory buildTerritory(CellPosition position,CellContent content);

    /**
     * Recupere un territoire à partir d'une cellule
     * @param position : la position de la cellule
     * @return : une liste de cellule
     */
    public Territory buildTerritory(CellPosition position);

    /**
     * Recupere les voisins enemis d'une cellule
     * @param position : la cellule
     * @return : une liste de cellules ennemies
     */
    public List<CellPosition> getEnemies(CellPosition position,CellContent content);

    /**
     * Recupere les ennemis d'un territoire entier
     * @param territory : le territoire
     * @param content : le content
     * @param position : la position souhaitee
     * @return : la liste des ennemis
     */
    public List<CellPosition> getEnemiesFor(Territory territory, CellContent content,CellPosition position);

    /**
     * Recupere les voisins enemis d'une cellule
     * @param position : la cellule
     * @return : une liste de cellules ennemies
     */
    public List<CellPosition> getEnemies(CellPosition position);

    /**
     * Obtenir toutes les positions vides du goban
     * @return
     */
    public List<CellPosition> getEmptyCells();

    /**
     * 
     * @param content
     * @return Le nombre de cellule ayant content
     */
    public int getContentCount(CellContent content);

    /**
     * 
     * @param content Le content a rechercher
     * @return La liste des positions ayant content
     */
    public List<CellPosition> getPositionsFor(CellContent ... content);
    
    /**
     * Obtenir la taille du goban
     * @return
     */
    public int getSize();

    /**
     * Retourne la liste des territoires pour une couleur
     * @param content La couleur
     * @return
     */
    public List<Territory> getTerritories(CellContent content);

    /**
     * Indique si un territoire est en Atari
     * @param territory : le territoire
     * @return : un boolean
     */
    public boolean isAtari(Territory territory);

    /**
     * Obtenir le nombre de cellule de Kuro
     * @return : le nombre de cellule de Kuro
     */
    public int getKuroCount();

    /**
     * Obtenir le nombre de cellule de Shiro
     * @return : le nombre de cellules de Shiro
     */
    public int getShiroCount();

    /**
     * retourne le prochain joueur
     * @return : le content
     */
    public CellContent getNextPlayer();

    /**
     * Retourne le joueur actuel
     * @return : le content
     */
    public CellContent getActualPlayer();

    /**
     * 
     * @param position La position considérée
     * @return La position au nord de position, null si non existante
     */
    public CellPosition getNorthernCell(final CellPosition position);

    /**
     * @param position La position considérée
     * @return La position au sud de position, null si non existante
     */
    public CellPosition getSouthernCell(final CellPosition position);

    /**
     * @param position La position considérée
     * @return La position au east de position, null si non existante
     */
    public CellPosition getEasternCell(final CellPosition position);

    /**
     * @param position La position considérée
     * @return La position à l'ouest de position, null si non existante
     */
    public CellPosition getWesternCell(final CellPosition position);

    /**
     * @return La liste des positions capturés par shiro
     */
    public List<CellPosition> getKuroPrisoners();
    
    /**
     * 
     * @return La liste des positions capturés par kuro
     */
    public List<CellPosition> getShiroPrisoners();
    
    /**
     * Marquer un pion comme capturé.
     * La position devient un suicide pour le pion capturé.
     * @param guard Celui qui capture
     * @param captured La list de position capturée
     */
    public void makePrisoner(CellContent guard,Iterable<CellPosition> captured);

    /**
     * Rechercher les cellules du bord ayant un certain contenu
     * @param contents Les contenus souhaités
     * @return La liste des positions trouvées
     */
    public List<CellPosition> getBorderCellsFor(CellContent ... contents);
    
}
