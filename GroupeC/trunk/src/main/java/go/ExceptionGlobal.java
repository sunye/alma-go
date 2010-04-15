package go;

/**
 * @author Frédéric Dumonceaux
 *
 */


@SuppressWarnings("serial")
public class ExceptionGlobal extends Throwable {

     /**
      * Variable content.
      */
     private final String content;

     /**
      * Constructor.
      * @param cnt message which is showing when an exception is thrown
      */
     public ExceptionGlobal(final String cnt) {
          super();
          content = cnt;
     }


     /**
      * print the error message.
      */
     public final void writeMessage() {
          System.out.println(content);
     }
}
