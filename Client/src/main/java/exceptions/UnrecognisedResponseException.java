package exceptions;

public class UnrecognisedResponseException extends RuntimeException {
    public UnrecognisedResponseException(String messageID) {
        super("Received response with ID " + messageID + ", but no matching pending request (CompletableFuture) was found.");
    }
}
