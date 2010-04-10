/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alma.atarigo.model;

import alma.atarigo.Cell;
import alma.atarigo.CellContent;
import alma.atarigo.CellPosition;

/**
 *
 * @author gass-mouy
 */
public class CellImpl extends CellPositionImpl implements Cell {

    private CellContent content;

    public CellImpl(CellPosition pos,CellContent content){
    	this(pos.getRow(),pos.getColumn(),content);
    }
    
    public CellImpl(int row, int column, CellContent content){
    	super(row,column);
        this.content = content;
    }

    public CellContent getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o){
    	if(o instanceof Cell){
    		Cell c = (Cell)o;
    		return content.equals(c.getContent())
    				&& super.equals(c);
    	}
    	return false;
    }
    
    @Override
    public String toString(){
    	return String.format("[%s:%s]",super.toString(),content);
    }
}
