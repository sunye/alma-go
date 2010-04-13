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

    /**
     * Reverse-patches the given goban from <em>after</em> to <em>before</em>.
     * If goban[line][column] does not contains the <em>after</em> value, there is an exception.
     * @param goban
     * @throws BadGobanStateException
     */
    public void revert(Goban goban) throws BadGobanStateException{
        if(goban.getCase(line, column) != this.after){
            throw new BadGobanStateException("Cannot apply this modification to the goban, since the after state is not right.");
        } else {
            goban.setCase(line, column, before);
        }
    }

    /**
     * Patches the given goban from <em>before</em> to <em>after</em>.
     * If goban[line][column] does not contains the <em>before</em> value, there is an exception.
     * @param goban
     * @throws BadGobanStateException
     */
    public void apply(Goban goban) throws BadGobanStateException{
        if(goban.getCase(line, column) != this.before){
            throw new BadGobanStateException("Cannot apply this modification to the goban, since the before state is not right.");
        } else {
            goban.setCase(line, column, after);
        }
    }

    /**
     * Creates a Stone with <em>line</em>, <em>column</em> and <em>before</em>.
     * @return
     */
    public Stone getOldStone(){
        return new Stone(getBefore(), line, column);
    }

    /**
     * Creates a Stone with <em>line</em>, <em>column</em> and <em>after</em>.
     * @return
     */
    public Stone getNewStone(){
        return new Stone(getAfter(), line, column);
    }

    @Override
    public String toString() {
        StringBuilder strb = new StringBuilder();
        return strb.append("{ ").append("line : ").append(line)
                .append(", column : ").append(column)
                .append(", before : ").append(before)
                .append(", after : ").append(after)
                .append(" }").toString();
    }




}
