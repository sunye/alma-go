/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alma.atarigo;

/**
 *
 * @author gass-mouy
 */
public class GoCell implements Cell {

    private CellContent content;
    private int row;
    private int column;

    public CellContent getContent() {
        return content;
    }

    public void setCellContent(CellContent content) {
        this.content = content;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

}
