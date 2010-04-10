/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alma.atarigo.ia;

/**
 *
 * @author gass-mouy
 */
public class Pair<First,Second> {

    private First first;
    private Second second;

    
    public Pair(First first, Second second){
        this.first = first;
        this.second = second;
    }

    static public<F,S> Pair<F,S> make(F f, S s){
        return new Pair<F,S>(f,s);
    }

    public First getFirst(){
        return first;
    }

    public Second getSecond(){
        return  second;
    }

    public void setFirst(First first) {
        this.first = first;
    }

    public void setSecond(Second second){
        this.second = second;
    }

}
