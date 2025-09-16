package excepciones;

public class ArchivoNoExisteException extends Exception {
    public ArchivoNoExisteException(String message) {
        super(message);
    }
}
