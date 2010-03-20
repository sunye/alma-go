package fr.alma.atarigo.utils.exceptions;

public class BadCouleurException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = -2416508440769689377L;

    public BadCouleurException(Throwable cause) {
        super(cause);
    }

    public BadCouleurException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadCouleurException(String message) {
        super(message);
    }

    public BadCouleurException() {
    }
}
