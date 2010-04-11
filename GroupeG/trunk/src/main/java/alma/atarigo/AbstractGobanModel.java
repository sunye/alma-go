/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alma.atarigo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import alma.atarigo.model.CellImpl;
import alma.atarigo.model.CellPositionImpl;
import alma.atarigo.model.TerritoryImpl;

/**
 *
 * @author steg
 */
public abstract class AbstractGobanModel implements GobanModel {
    

	/**
	 * Obtenir le contenu d'une cellule
	 * @param position La position souhaitée
	 * @return Le CellContent de la cellule
	 * @see alma.atarigo.GobanModel#getCellContent(CellPosition)
	 */
    public CellContent getCellContent(CellPosition position){
        return getCellContent(position.getRow(),position.getColumn());
    }

    /**
     * Modifier le contenu d'une cellule.
     * Invoke la méthode <code> setCellContent(int,int,CellContent)</code>
     * qui est implémentée plus bas dans la hiérarchie.
     * @param position La position à mettre à jour
     * @param content Le nouveau contenu de la cellule
     * @see alma.atarigo.GobanModel#setCellContent(CellPosition, CellContent)
     */
    public void setCellContent(CellPosition position, CellContent content) {
        setCellContent(position.getRow(),position.getColumn(),content);
    }
    
    /**
     * Obtenir la liste de tous les térritoires du goban.
     * @return La <code>List</code> contenant le résultat
     */
    public List<Territory> getTerritories(){
        List<Territory> kuro = getTerritories(CellContent.Kuro);
        List<Territory> shiro = getTerritories(CellContent.Shiro);
        kuro.addAll(shiro);
        return kuro;
    }
    
    /**
     * Obtenir la liste des territoires pour une couleur particulières.
     * @param content La couleur souhaitée
     * @return La liste des téritoires occupés par content
     */
    public List<Territory> getTerritories(CellContent content){
        List<Territory> result = new ArrayList<Territory>();

        if(content!=null){
        	for(Cell cell : this){
        		if(content.equals(cell.getContent())){
                    if(findPosition(result,cell)<0){
                        result.add(buildTerritory(cell));
                    }
        		}
        	}
        }

        return result;
    }

    /**
     * Rechercher si une position appartient à un groupe de territoire
     * @param list La liste des territoire concernée
     * @param position La position recherchée
     * @return L'index du térritoire dans la liste des térritoires qui contient la position, -1 si non trouvé
     */
    public static int findPosition(List<Territory> list, CellPosition position){
        for(int i=0 ; i<list.size() ; ++i){
            if(list.get(i).contains(position)){
                return i;
            }
        }
        return -1;
    }

    /**
     * @see alma.atarigo.GobanModel#getLiberties(CellPosition)
     */
    public List<CellPosition> getLiberties(CellPosition position){
        List<CellPosition> liberties = new ArrayList<CellPosition>();
        for(CellPosition pos
                : Arrays.asList(
                         getSouthernCell(position)
                        ,getNorthernCell(position)
                        ,getWesternCell(position)                        
                        ,getEasternCell(position)))
        {
            if(pos!=null){
                CellContent content = getCellContent(position);
                CellContent cont = getCellContent(pos);
                if(cont!=null && (cont.isEmpty()
                        || (content.isKuro() && cont.isShiroSuicide())
                        || (content.isShiro() && cont.isKuroSuicide())))
                {
                    liberties.add(pos);
                }
            }
        }
        return liberties;
    }

    /**
     * Obtenir la liste des libertés d'un territoire
     * @see alma.atarigo.GobanModel#getLibertiesFor(Territory)
     */
    public List<CellPosition> getLibertiesFor(Territory territory){
        return getLibertiesFor(territory,null);
    }

    /**
     * @see alma.atarigo.GobanModel#getLibertiesFor(Territory, CellPosition)
     */
    public List<CellPosition> getLibertiesFor(Territory territory,CellPosition position){

        Set<CellPosition> liberties = new HashSet<CellPosition>();
        for(CellPosition pos : territory.getPositions()){
            liberties.addAll(getLiberties(pos));
        }
        List<CellPosition> result = new ArrayList<CellPosition>();
        result.addAll(liberties);
        if(position != null){
            result.remove(position);
        }
        return result;
    }
    
    /**
     * @see alma.atarigo.GobanModel#getLibertiesFor(CellPosition, List)
     */
    public List<CellPosition> getLibertiesFor(CellPosition position,List<Territory> territories){
        Set<CellPosition> liberties = new HashSet<CellPosition>();
    	for(Territory territory : territories){
    		liberties.addAll(getLibertiesFor(territory,position));
    	}
        List<CellPosition> result = new ArrayList<CellPosition>();
        result.addAll(liberties);
    	return result;
    }

    /**
     * @see alma.atarigo.GobanModel#getLibertiesFor(List)
     */
    public List<CellPosition> getLibertiesFor(List<Territory> territories){
    	return getLibertiesFor(null,territories);
    }
    
    /**
     * Construire le territoire à  partir d'une position
     * @see alma.atarigo.GobanModel#buildTerritory(CellPosition)
     */
    public Territory buildTerritory(CellPosition position) {
        CellContent content = getCellContent(position);
        if(content.isEmpty()){
            return new TerritoryImpl();
        }
        return buildTerritory(position,content);
    }

    /**
     * @see alma.atarigo.GobanModel#buildTerritory(CellPosition, CellContent)
     */
    public Territory buildTerritory(CellPosition position, CellContent content) {
        Territory territory = new TerritoryImpl(content);
        territory.addCellPosition(position);
        getNeighbours(position,territory,new ArrayList<CellPosition>(),content);
        return territory;
    }

    private void getNeighbours(CellPosition position
                                ,Territory territory
                                ,List<CellPosition> visited
                                ,CellContent current){
        visited.add(position);

        for(CellPosition neighbour
                : Arrays.asList(
                        getNorthernCell(position)
                        ,getWesternCell(position)
                        ,getSouthernCell(position)
                        ,getEasternCell(position)))
        {
//            System.out.println("Testing neighbour: "+neighbour);
            if(neighbour!=null
                    && current.isFriend(getCellContent(neighbour))
                    && !visited.contains(neighbour)){
                territory.addCellPosition(neighbour);
                getNeighbours(neighbour,territory,visited,current);
            }
//            System.out.println("-----------------------------");
        }
    }

    public CellPosition getNorthernCell(final CellPosition position){
        if(position.getRow()==1){
            return null;
        }
        return makePosition(position.getRow()-1,position.getColumn());
    }

    public CellPosition getSouthernCell(final CellPosition position){
        if(position.getRow()==getSize()){
            return null;
        }
        return makePosition(position.getRow()+1,position.getColumn());
    }

    /* (non-Javadoc)
     * @see alma.atarigo.GobanModel#getEasternCell(alma.atarigo.CellPosition)
     */
    public CellPosition getEasternCell(final CellPosition position){
        if(position.getColumn()==getSize()){
            return null;
        }
        return makePosition(position.getRow(),position.getColumn()+1);
    }

    /* (non-Javadoc)
     * @see alma.atarigo.GobanModel#getWesternCell(alma.atarigo.CellPosition)
     */
    public CellPosition getWesternCell(final CellPosition position){
        if(position.getColumn()==1){
            return null;
        }
        return makePosition(position.getRow(),position.getColumn()-1);
    }

    /**
     * Mettre toutes les cellules à <code>Empty</code>
     */
    protected void initializeBoard() {
        int length = getSize();
        for(int i=1 ; i<=length ; ++i){
            for(int j=1 ; j<=length ; ++j){
                setCellContent(i,j,CellContent.Empty);
            }
        }
    }

    /* (non-Javadoc)
     * @see alma.atarigo.GobanModel#getEmptyCells()
     */
    public List<CellPosition> getEmptyCells(){
    	return getPositionsFor(CellContent.Empty);
    }

    /* (non-Javadoc)
     * @see alma.atarigo.GobanModel#getPositionsFor(alma.atarigo.CellContent[])
     */
    public List<CellPosition> getPositionsFor(CellContent ... target){
    	List<CellContent> targets = Arrays.asList(target);
        List<CellPosition> contents = new ArrayList<CellPosition>();
        int length = getSize();
        for(int i=1 ; i<=length ; ++i){
            for(int j=1 ; j<=length ; ++j){
                CellContent content = getCellContent(i,j);
                if(targets.contains(content)){
                    contents.add(makePosition(i,j));
                }
            }
        }
        return contents;
    }
    
    /* (non-Javadoc)
     * @see alma.atarigo.GobanModel#getEnemies(alma.atarigo.CellPosition)
     */
    public List<CellPosition> getEnemies(CellPosition position) {
        return getEnemies(position,getCellContent(position));
    }

    /* (non-Javadoc)
     * @see alma.atarigo.GobanModel#getEnemies(alma.atarigo.CellPosition, alma.atarigo.CellContent)
     */
    public List<CellPosition> getEnemies(CellPosition position,CellContent content){
        List<CellPosition> result = new ArrayList<CellPosition>();
        for(CellPosition neighbour
                : Arrays.asList(
                         getNorthernCell(position)
                        ,getSouthernCell(position)
                        ,getEasternCell(position)
                        ,getWesternCell(position)))
        {
            if(neighbour!=null && content.isEnemy(getCellContent(neighbour))){
                result.add(neighbour);
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see alma.atarigo.GobanModel#getEnemiesFor(alma.atarigo.Territory, alma.atarigo.CellContent, alma.atarigo.CellPosition)
     */
    public List<CellPosition> getEnemiesFor(Territory territory,CellContent content,CellPosition position) {
        Set<CellPosition> enemies = new HashSet<CellPosition>();
        for(CellPosition pos : territory.getPositions()){
            enemies.addAll(getEnemies(pos,content==CellContent.Kuro?CellContent.Shiro:CellContent.Kuro));
        }
        if(position != null){
            enemies.add(position);
        }
        List<CellPosition> result = new ArrayList<CellPosition>();
        result.addAll(enemies);
        return result;
    }

    /**
     * Construire une <code>CellPosition</code>
     * @param row La ligne 
     * @param column La colonne
     * @return La nouvelle position
     */
    public static CellPosition makePosition(final int row,final int column){
        return new CellPositionImpl(row,column);
    }

    /* (non-Javadoc)
     * @see alma.atarigo.GobanModel#getContentCount(alma.atarigo.CellContent)
     */
    public int getContentCount(CellContent content) {
        int res = 0;
        int length = getSize();
        for(int i=1;i<=length;++i){
            for(int j=1;j<=length;++j){
                if(getCellContent(i,j).equals(content)){
                    ++res;
                }
            }
        }
        return res;
    }
    
    /* (non-Javadoc)
     * @see alma.atarigo.GobanModel#isAtari(alma.atarigo.Territory)
     */
    public boolean isAtari(Territory territory){
    	return getLibertiesFor(territory).size()==1;
    }

    /* (non-Javadoc)
     * @see alma.atarigo.GobanModel#getKuroCount()
     */
    public int getKuroCount(){
    	int count = 0;
    	for(Cell cell : this){
    		if(cell.getContent().isKuro()) ++count;
    	}
    	return count;
//        return kuroCount;
    }

    /* (non-Javadoc)
     * @see alma.atarigo.GobanModel#getShiroCount()
     */
    public int getShiroCount(){
    	int count = 0;
    	for(Cell cell : this){
    		if(cell.getContent().isShiro()) ++count;
    	}
    	return count;
//        return shiroCount;
    }

    /* (non-Javadoc)
     * @see alma.atarigo.GobanModel#getActualPlayer()
     */
    public CellContent getActualPlayer(){
    	int kCount = getKuroCount() + getKuroPrisoners().size();
        return (kCount%2==0)?CellContent.Shiro:CellContent.Kuro;
    }
    
    /* (non-Javadoc)
     * @see alma.atarigo.GobanModel#getNextPlayer()
     */
    public CellContent getNextPlayer(){
    	int kCount = getKuroCount() + getKuroPrisoners().size();
        return (kCount%2==0)?CellContent.Kuro:CellContent.Shiro;
    }

    /* (non-Javadoc)
     * @see java.lang.Iterable#iterator()
     */
    public Iterator<Cell> iterator(){
        
        return new Iterator<Cell>() {

            int row = 1, col = 1;
            final int size = (int) Math.pow(getSize(),2);

            public boolean hasNext() {
                return row*col < size;
            }

            public Cell next() {
                Cell next = makeCell(row,col,getCellContent(row,col));
                if(col == getSize()){
                    col = 1;
                    row++;
                }else{
                    col++;
                }
                return next;
            }

            public void remove() {
                throw new UnsupportedOperationException("Remove non supporte");
            }
        };
    }

    /**
     * Construire une nouvelle <code>Cell</code>
     * @param row La ligne
     * @param column La colonne
     * @param content Le contenue de la cellule
     * @return La nouvelle instance
     */
    public static Cell makeCell(final int row, final int column, final CellContent content){
    	return new CellImpl(row,column,content);
    }
    
    /* (non-Javadoc)
     * @see alma.atarigo.GobanModel#getBorderCellsFor(alma.atarigo.CellContent[])
     */
    public List<CellPosition> getBorderCellsFor(CellContent ... contents){
    	List<CellPosition> result = getPositionsFor(contents);
    	for(int i=0 ; i<result.size() ; ++i){
    		CellPosition position = result.get(i);
    		if(!isBorderCell(position)){
    			result.remove(position);
        		--i;
    		}
    	}
    	return result;
    }
    
    /**
     * Verifie que la cellule est une cellule du bord
     * @param position La position
     * @return true si position est au bord du goban
     */
    public boolean isBorderCell(CellPosition position){    	
    	int size = getSize();
    	int row = position.getRow();
    	int col = position.getColumn();
    	return  row==1 || row==size || col==1 || col==size;
    }

    

}
