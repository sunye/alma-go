/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alma.atarigo.model;

import alma.atarigo.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author gass-mouy
 */
public class Goban extends AbstractGobanModel implements GobanModel {

    private int size = 9;
    private CellContent[][] board;
    private List<CellPosition> kuroPrisoners = new ArrayList<CellPosition>();
    private List<CellPosition> shiroPrisoners = new ArrayList<CellPosition>();
    

    public Goban(){
        board = new CellContent[size][size];
        initializeBoard();
    }
    
    public Goban(int size){
        this.size = size;
        board = new CellContent[size][size];
        initializeBoard();
    }

    public Goban(GobanModel goban){
    	this.size = goban.getSize();
    	board = new CellContent[size][size];
    	initializeBoard();
    	for(Cell cell : goban){
    		setCellContent(cell,cell.getContent());
    	}
    	kuroPrisoners.addAll(goban.getKuroPrisoners());
    	shiroPrisoners.addAll(goban.getShiroPrisoners());    	
    }
    
    public void setCellContent(int row, int column, CellContent content) {
        board[row-1][column-1] = content;
    }

    public CellContent getCellContent(final int row, final int column) {
        try{
            return board[row-1][column-1];
        }catch(IndexOutOfBoundsException ex){
            Logger.getLogger(Goban.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        return null;
    }
    public int getSize(){
        return size;
    }

    public List<Territory> getArtificialTerritories(CellContent content) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

	public List<CellPosition> getKuroPrisoners() {
		return kuroPrisoners;
	}

	public List<CellPosition> getShiroPrisoners() {
		return shiroPrisoners;
	}

	public void makePrisoner(CellContent guard, Iterable<CellPosition> captured) {
		if(guard.isKuro()){
			for(CellPosition p : captured){
				kuroPrisoners.add(p);
				setCellContent(p,CellContent.ShiroSuicide);
			}
		}else if(guard.isShiro()){
			for(CellPosition p : captured){
				shiroPrisoners.add(p);
				setCellContent(p,CellContent.KuroSuicide);
			}
		}
	}

	public void clear(){
		kuroPrisoners.clear();
		shiroPrisoners.clear();
		initializeBoard();
	}
    
	
}


