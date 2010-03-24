package go;

/**
 * @author Fr�d�ric Dumonceaux
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
	 * l'erreur qui sera affich�
	 */
	public void writeMessage() {
		System.out.println(content);
	}
}