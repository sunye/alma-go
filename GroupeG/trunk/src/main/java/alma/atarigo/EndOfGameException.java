/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alma.atarigo;

/**
 *
 * @author steg
 */
public class EndOfGameException extends Throwable {

    public EndOfGameException(String message){
        super(message);
    }

    public EndOfGameException(){
        this("Hasta la vista, rafa");
    }

    public EndOfGameException(Throwable ex){
        this(ex.getMessage());
        setStackTrace(ex.getStackTrace());
    }

}
