package fr.alma.atarigo.utils.exceptions;

public class BadPlaceException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = -7651675431627421683L;

    public BadPlaceException() {}

    public BadPlaceException(String message) {
        super(message);
    }

    public BadPlaceException(Throwable cause) {
        super(cause);
    }

    public BadPlaceException(String message, Throwable cause) {
        super(message, cause);
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
