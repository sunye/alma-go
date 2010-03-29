package go;

/**
 * @author Frédéric Dumonceaux
 *
 */


@SuppressWarnings("serial")
public class ExceptionGlobal extends Throwable {
	
	private String content;
	
	
	/**
	 * @param cnt le message de l'exception
	 */
	public ExceptionGlobal(String cnt) { 
		content = cnt;
	}
	
	
	/**
	 * l'erreur qui sera affiché
	 */
	public void writeMessage() {
		System.out.println(content);
	}
}