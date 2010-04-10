package fr.alma.atarigo.utils;

import fr.alma.atarigo.utils.exceptions.BadGobanStateException;

/**
 * This Class defines a modification on the Goban. A modification is a 4-uplet, defining coordinates (line and column), and the before and after values
 * @author judu
 */
public class Modif {

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

    public void revert(Goban goban) throws BadGobanStateException{
        if(goban.getCase(line, column) != this.after){
            throw new BadGobanStateException("Cannot apply this modification to the goban, since the after state is not right.");
        } else {
            goban.setCase(line, column, before);
        }
    }
    
    public void apply(Goban goban) throws BadGobanStateException{
        if(goban.getCase(line, column) != this.before){
            throw new BadGobanStateException("Cannot apply this modification to the goban, since the before state is not right.");
        } else {
            goban.setCase(line, column, after);
        }
    }

    public Stone getOldStone(){
        return new Stone(getBefore(), line, column);
    }

    public Stone getNewStone(){
        return new Stone(getAfter(), line, column);
    }

}
