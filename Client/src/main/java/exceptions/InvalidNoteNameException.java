package exceptions;

public class InvalidNoteNameException extends RuntimeException {
    public InvalidNoteNameException(String message) {
        super(message);
    }
}
