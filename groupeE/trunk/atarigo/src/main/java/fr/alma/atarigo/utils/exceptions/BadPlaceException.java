package fr.alma.atarigo.utils.exceptions;

public class BadPlaceException extends Exception {

    public BadPlaceException(Throwable cause) {
        super(cause);
    }

    public BadPlaceException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadPlaceException(String message) {
        super(message);
    }

    public BadPlaceException() {
    }

	

	/**
	 * 
	 */
	private static final long serialVersionUID = -7651675431627421683L;

}
