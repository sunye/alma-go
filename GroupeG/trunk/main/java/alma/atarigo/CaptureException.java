/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alma.atarigo;

import java.util.*;

/**
 *
 * @author steg
 */
public class CaptureException extends Throwable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2279378992935235647L;
	private List<CellPosition> winnigArea;
    private List<Territory> losingArea;
    
    public CaptureException(String message,List<CellPosition> winnings,List<Territory> losings){
        super(message);
        this.winnigArea = winnings;
        this.losingArea = losings;
    }

    public List<CellPosition> getWinningArea(){
        return this.winnigArea;
    }

    public List<Territory> getLosingArea(){
        return this.losingArea;
    }
    
}
