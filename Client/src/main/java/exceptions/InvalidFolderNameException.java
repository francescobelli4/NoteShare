package exceptions;

public class InvalidFolderNameException extends RuntimeException {
    public InvalidFolderNameException(String message) {
        super(message);
    }
}
