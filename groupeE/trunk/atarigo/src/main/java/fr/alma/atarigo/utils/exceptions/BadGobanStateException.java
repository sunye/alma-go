package fr.alma.atarigo.utils.exceptions;

/**
 *
 * @author judu
 */
public class BadGobanStateException extends Exception {
    public BadGobanStateException() {}

    public BadGobanStateException(String message) {
        super(message);
    }

    public BadGobanStateException(Throwable cause) {
        super(cause);
    }

    public BadGobanStateException(String message, Throwable cause) {
        super(message, cause);
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
