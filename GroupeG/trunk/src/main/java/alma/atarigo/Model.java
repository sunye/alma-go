/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alma.atarigo;

import java.util.*;

/**
 *
 * @author gass-mouy
 */
public class Model implements Goban {

    private int size = 9;
    private Cell[][] board;

    public Model(){
        board = new GoCell[size][size];
        initializeBoard();
    }
    
    public Model(int size){
        this.size = size;
        board = new GoCell[size][size];
        initializeBoard();
    }

    public void setCellContent(CellPosition position, CellContent content){
        board[position.getRow()][position.getColumn()].setCellContent(content);
    }

    public void setCellContent(int row, int column, CellContent content){
        board[row][column].setCellContent(content);
    }

    public CellContent getCellContent(CellPosition position){
        return board[position.getRow()][position.getColumn()].getContent();
    }

    public CellContent getCellContent(int row, int column) {
        return board[row][column].getContent();
    }

    public List<CellPosition> getLiberties(CellPosition position) {
        List<CellPosition> result = new ArrayList<CellPosition>();
        Cell neighbour = null;
        Cell current = board[position.getRow()][position.getColumn()];
        
        //cellule Nord
        if(position.getRow()+1 < size){
            neighbour = board[position.getRow()+1][position.getColumn()];
            if(neighbour.getContent().equals(CellContent.Empty)){
                result.add(neighbour);
            }
            else if(neighbour.getContent().equals(current.getContent())){
                result = getLiberties(neighbour);
            }
        }

        //cellule Sud
        if(position.getRow()-1 >= 0){
            neighbour = board[position.getRow()-1][position.getColumn()];
            if(neighbour.getContent().equals(CellContent.Empty)){
                result.add(neighbour);
            }
            else if(neighbour.getContent().equals(current.getContent())){
                result = getLiberties(neighbour);
            }
        }

        //cellule Est
        if(position.getColumn()+1 < size){
            neighbour = board[position.getRow()][position.getColumn()+1];
            if(neighbour.getContent().equals(CellContent.Empty)){
                result.add(neighbour);
            }
            else if(neighbour.getContent().equals(current.getContent())){
                result = getLiberties(neighbour);
            }
        }
        
        //cellule Ouest
        if(position.getColumn() >= 0){
            neighbour = board[position.getRow()-1][position.getColumn()-1];
            if(neighbour.getContent().equals(CellContent.Empty)){
                result.add(neighbour);
            }
            else if(neighbour.getContent().equals(current.getContent())){
                result = getLiberties(neighbour);
            }
        }

        return result;
    }

    public List<CellPosition> getTerritoty(CellPosition position) {
        List<CellPosition> result = new ArrayList<CellPosition>();
        Cell neighbour = null;
        Cell current = board[position.getRow()][position.getColumn()];
        result.add(current);

        //cellule Nord
        if(position.getRow()+1 < size){
            neighbour = board[position.getRow()+1][position.getColumn()];
            if(neighbour.getContent().equals(current.getContent())){
                result = getLiberties(neighbour);
            }
        }

        //cellule Sud
        if(position.getRow()-1 >= 0){
            neighbour = board[position.getRow()-1][position.getColumn()];
            if(neighbour.getContent().equals(current.getContent())){
                result = getLiberties(neighbour);
            }
        }

        //cellule Est
        if(position.getColumn()+1 < size){
            neighbour = board[position.getRow()][position.getColumn()+1];
            if(neighbour.getContent().equals(current.getContent())){
                result = getLiberties(neighbour);
            }
        }

        //cellule Ouest
        if(position.getColumn() >= 0){
            neighbour = board[position.getRow()-1][position.getColumn()-1];
            if(neighbour.getContent().equals(current.getContent())){
                result = getLiberties(neighbour);
            }
        }
        
        return result;
    }

    private void initializeBoard() {
        for(int i=0 ; i<board.length ; ++i){
            for(int j=0 ; j<board.length ; ++j){
                setCellContent(i,j,CellContent.Empty);
            }
        }
    }
    
}
