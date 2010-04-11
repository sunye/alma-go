/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alma.atarigo.model;

import alma.atarigo.CellPosition;


/**
 *
 * @author steg
 */
public class CellPositionImpl implements CellPosition{

    private final int row;
    private final int column;

    /**
     * Construire une position par se coordonnées
     * @param row ligne de la position
     * @param column abcisse de la position
     */
    public CellPositionImpl(int row,int column){
        this.row = row;
        this.column = column;
    }

    public CellPositionImpl(String desc){
        if(desc==null || !desc.matches("[A-Za-z][1-9A-Za-z]")){
            this.row = -1;
            this.column = -1;
        }else{
            this.column = desc.charAt(0) - (desc.startsWith("A")?'A':'a') + 1;
            if(desc.matches(".[1-9]")){
                this.row = desc.charAt(1) - '1' + 1;
            }else if(desc.matches(".[A-Z]")){
                this.row = desc.charAt(1) - 'A' +1;
            }else{
                this.row = desc.charAt(1) - 'a' +1;
            }
        }
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public String toString(){
        if(row<=0 || column<=0){
            return "#NAN#";
        }
        return String.format("%c%d", 'A'+column-1,row);
    }

    /**
     *
     * @param object
     * @return
     */
    @Override
    public boolean equals(Object object){
        if(object!=null && object instanceof CellPosition){
            CellPosition position = (CellPosition) object;
            return position.getRow()==row && position.getColumn()==column;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + this.row;
        hash = 41 * hash + this.column;
        return hash;
    }

}
