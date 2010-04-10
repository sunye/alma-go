/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alma.atarigo.ia;

import java.util.List;

import alma.atarigo.*;


/**
 *
 * @author gass-mouy
 */
public class ArtificialGoban extends AbstractGobanModel implements GobanModel {

    private GobanModel parent = null;
    private CellPosition position = null;
    private CellContent content;
//    private int value = 0;

    public ArtificialGoban(GobanModel parent, CellPosition position, CellContent content){
        this.parent = parent;
        this.position = position;
        this.content = content;
    }

//    public void setValue(int value){
//        this.value = value;
//    }
//
//    public int getValue(){
//        return value;
//    }

//    public CellPosition getCellPosition(){
//        return position;
//    }

//    public CellContent getCellContent(){
//        return content;
//    }

//    public GobanModel getParent(){
//        return parent;
//    }
    
    @Override
    public void setCellContent(int row, int column, CellContent content) {
        if(row==position.getRow() && column==position.getColumn()){
            this.content = content;
        }
    }
    
    @Override
    public CellContent getCellContent(int row, int column) {
        if(row==position.getRow() && column==position.getColumn()){
            return content;
        }
        return parent.getCellContent(row, column);
    }

    public int getSize() {
        return parent.getSize();
    }

	@Override
	public List<CellPosition> getKuroPrisoners() {
		return parent.getKuroPrisoners();
	}

	@Override
	public List<CellPosition> getShiroPrisoners() {
		return parent.getShiroPrisoners();
	}

	@Override
	public void makePrisoner(CellContent guard, Iterable<CellPosition> captured) {
	}


}
