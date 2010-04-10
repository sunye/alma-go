/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alma.atarigo.model;

import java.util.*;

import alma.atarigo.*;

/**
 *
 * @author gassMouy()
 */
public class TerritoryImpl implements Territory {

    private CellContent content;
    private List<CellPosition> positions = new ArrayList<CellPosition>();

    public TerritoryImpl(){
    }

    public TerritoryImpl(CellContent content){
        this.content = content;
    }

    public TerritoryImpl(CellPosition position,CellContent content){
        this.positions.add(position);
        this.content = content;
    }
    public TerritoryImpl(List<CellPosition> positions,CellContent content){
        this.positions.addAll(positions);
    }

    public List<CellPosition> getPositions(){
        return positions;
    }

    public CellContent getCellContent(){
        return content;
    }

    public void addCellPosition(CellPosition position){
        if(!positions.contains(position)){
            positions.add(position);
        }
    }

    public Territory fusionWith(Territory territory) {
        if(this.equals(territory)){
            return this;
        }
        Set<CellPosition> withoutPairs = new HashSet<CellPosition>();
        withoutPairs.addAll(positions);
        withoutPairs.addAll(territory.getPositions());
        positions.clear();
        positions.addAll(withoutPairs);
        return this;
    }

    public boolean contains(CellPosition position) {
        return positions.contains(position);
    }
    
    public boolean isIncludedIn(Territory territory){
        for(CellPosition position : positions){
            if(!territory.getPositions().contains(position)){
                return false;
            }
        }
        return true;
    }

    public boolean isStrictlyIncludedIn(Territory territory){
        return this.isIncludedIn(territory) && !this.equals(territory);
    }

    public boolean equals(Territory territory) {
        return this.isIncludedIn(territory) && territory.isIncludedIn(this);
    }

    public boolean isEnemyWith(Territory territory) {
        return content != territory.getCellContent();
    }

    @Override
    public boolean equals(Object object){
        if(Territory.class.isInstance(object)){
        	Territory territory = (TerritoryImpl) object;
        	return positions.equals(territory.getPositions());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public String toString(){
        return positions.toString();
    }

    public void remove(CellPosition position) {

        positions.remove(position);
    }

	@Override
	public Iterator<CellPosition> iterator() {
		return positions.iterator();
	}

	@Override
	public int size() {
		return positions.size();
	}
	
	public boolean isSingleton(){
		return size()==1;
	}
	
	public boolean isEmpty(){
		return size()==0;
	}

}
