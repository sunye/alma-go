package fr.alma.atarigo.utils;

import fr.alma.atarigo.utils.exceptions.BadGobanStateException;

/**
 * This Class defines a modification on the Goban. A modification is a 4-uplet, defining coordinates (line and column), and the before and after values
 * @author judu
 */
class Modif {

    private int line;
    private int column;
    private PionVal before;
    private PionVal after;

    public Modif(int line, int column, PionVal before, PionVal after) {
        this.line = line;
        this.column = column;
        this.before = before;
        this.after = after;
    }

    public PionVal getAfter() {
        return after;
    }

    public PionVal getBefore() {
        return before;
    }

    public int getColumn() {
        return column;
    }

    public int getLine() {
        return line;
    }

    public Modif getReverted(){
        return new Modif(line, column, after, before);
    }

    public void revert(PionVal[][] goban) throws BadGobanStateException{
        
    }
    
    void apply(PionVal[][] goban) throws BadGobanStateException{
        if(goban[line][column] != this.before){
            throw new BadGobanStateException("Cannot apply this modification to the goban, since the before state is not right.");
        } else {
            goban[line][column] = this.after;
        }
    }

}
