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
    

    private int kuroCount = 0;
    private int shiroCount = 0;    

    public CellContent getCellContent(CellPosition position){
        return getCellContent(position.getRow(),position.getColumn());
    }

    public void setCellContent(CellPosition position, CellContent content) {
        if(content.equals(CellContent.Kuro)){
            kuroCount++;
        }else if(content.equals(CellContent.Shiro)){
            shiroCount++;
        }
        setCellContent(position.getRow(),position.getColumn(),content);
    }
    
    public List<Territory> getTerritories(){

        List<Territory> kuro = getTerritories(CellContent.Kuro);
        List<Territory> shiro = getTerritories(CellContent.Shiro);
        kuro.addAll(shiro);
        return kuro;
    }
    
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

    public static int findPosition(List<Territory> list, CellPosition position){
        for(int i=0 ; i<list.size() ; ++i){
            if(list.get(i).contains(position)){
                return i;
            }
        }
        return -1;
    }

//    public void addTerritory(Territory territory){
//        for(int i=0 ; i<territories.size() ; ++i){
//            Territory ter = territories.get(i);
//            if(ter.isStrictlyIncludedIn(territory)){
//                territories.remove(ter);
//                --i;
//            }
//        }
//        territories.add(territory);
//    }

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
//                		|| cont.equals(CellContent.KuroWins)
//                		|| cont.equals(CellContent.ShiroWins)
                        || (content.isKuro() && cont.isShiroSuicide())
                        || (content.isShiro() && cont.isKuroSuicide())))
                {
                    liberties.add(pos);
                }
            }
        }
        return liberties;
    }

    public List<CellPosition> getLibertiesFor(Territory territory){
        return getLibertiesFor(territory,null);
    }

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
    
    public List<CellPosition> getLibertiesFor(CellPosition position,List<Territory> territories){
        Set<CellPosition> liberties = new HashSet<CellPosition>();
    	for(Territory territory : territories){
    		liberties.addAll(getLibertiesFor(territory,position));
    	}
        List<CellPosition> result = new ArrayList<CellPosition>();
        result.addAll(liberties);
    	return result;
    }

    public List<CellPosition> getLibertiesFor(List<Territory> territories){
    	return getLibertiesFor(null,territories);
    }
    
    public Territory buildTerritory(CellPosition position) {
        CellContent content = getCellContent(position);
        if(content.isEmpty()){
            return new TerritoryImpl();
        }
        return buildTerritory(position,content);
    }

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

    public CellPosition getEasternCell(final CellPosition position){
        if(position.getColumn()==getSize()){
            return null;
        }
        return makePosition(position.getRow(),position.getColumn()+1);
    }

    public CellPosition getWesternCell(final CellPosition position){
        if(position.getColumn()==1){
            return null;
        }
        return makePosition(position.getRow(),position.getColumn()-1);
    }

    protected void initializeBoard() {
        int length = getSize();
        for(int i=1 ; i<=length ; ++i){
            for(int j=1 ; j<=length ; ++j){
                setCellContent(i,j,CellContent.Empty);
            }
        }
    }

    public List<CellPosition> getEmptyCells(){
    	return getPositionsFor(CellContent.Empty);
    }

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
    
    public List<CellPosition> getEnemies(CellPosition position) {
        return getEnemies(position,getCellContent(position));
    }

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

    public static CellPosition makePosition(final int row,final int column){
        return new CellPositionImpl(row,column);
    }

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
    
    public boolean isAtari(Territory territory){
    	return getLibertiesFor(territory).size()==1;
    }

    public int getKuroCount(){
    	int count = 0;
    	for(Cell cell : this){
    		if(cell.getContent().isKuro()) ++count;
    	}
    	return count;
//        return kuroCount;
    }

    public int getShiroCount(){
    	int count = 0;
    	for(Cell cell : this){
    		if(cell.getContent().isShiro()) ++count;
    	}
    	return count;
//        return shiroCount;
    }

    public CellContent getActualPlayer(){
    	int kCount = getKuroCount();
        return (kCount%2==0)?CellContent.Shiro:CellContent.Kuro;
    }
    
    public CellContent getNextPlayer(){
    	int kCount = getKuroCount();
        return (kCount%2==0)?CellContent.Kuro:CellContent.Shiro;
    }

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

    public static Cell makeCell(final int row, final int column, final CellContent content){
    	return new CellImpl(row,column,content);
    }
    
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
    
    public boolean isBorderCell(CellPosition position){
    	boolean result = false;
    	for(CellPosition neighbour
                : Arrays.asList(
                         getNorthernCell(position)
                        ,getSouthernCell(position)
                        ,getEasternCell(position)
                        ,getWesternCell(position)))
        {
            if(neighbour==null){
            	result = true;
            }
        }
    	return result;
    }

    

}
