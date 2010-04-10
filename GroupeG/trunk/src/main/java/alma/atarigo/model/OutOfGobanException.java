/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alma.atarigo.model;
import alma.atarigo.*;

/**
 *
 * @author gass-mouy
 */
public class OutOfGobanException extends Throwable implements CellPosition{
    int row=-1,column=1;
    public OutOfGobanException(String message){
        super(message);
    }

    public OutOfGobanException(){
        this("Federer is BigShit(null) ! ");
    }

    public OutOfGobanException(Throwable ex){
        this(ex.getMessage());
        setStackTrace(ex.getStackTrace());
    }

    public OutOfGobanException(CellPosition position,Neighbour neighbour){
        String message="ccco";
        if(neighbour.equals(Neighbour.East)){
            message = "La cellule ("+position.getRow()+","+position.getColumn()+" n'a pas de cellule à l'"+neighbour;
        }
        else{
            message = "La cellule ("+position.getRow()+","+position.getColumn()+" n'a pas de cellule au "+neighbour;
        }
//        this(message);
//        super(message);
    }

    public OutOfGobanException(int row,int column) {
        this("La cellule ("+row+","+column+" n'existe pas");

//        this(message);
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

}
