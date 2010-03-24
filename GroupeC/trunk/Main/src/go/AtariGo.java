package go;

import java.util.Random;


/**
 * @author Frédéric Dumonceaux
 *
 */


public class AtariGo implements Constants {

	protected int turn = BLACK;
	protected int black_handicap = 0;
	protected int white_handicap = 0;
	
	public FrameGoban monGoban;
	/**
	 * 
	 */
	
	public AtariGo(FrameGoban fg){
		//System.out.println(fg != null);
		//monGoban = fg;
	}
	
	
	/**
	 * 
	 */
	public void generate(int nb_stones, int depth){
		
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
	}
}
