package communication;


public enum SocketMessageType {

    // Client -> Server
    LOGIN_REQUEST(SocketMessageCategory.REQUEST),
    LOGIN_USING_TOKEN_REQUEST(SocketMessageCategory.REQUEST),
    REGISTER_REQUEST(SocketMessageCategory.REQUEST),

    // Server -> Client
    ACCESS_SUCCESS(SocketMessageCategory.RESPONSE),
    LOGIN_FAILURE(SocketMessageCategory.RESPONSE),
    REGISTER_FAILURE(SocketMessageCategory.RESPONSE),
    SET_MESSAGES(SocketMessageCategory.NOTIFICATION),
    ADD_MESSAGE(SocketMessageCategory.NOTIFICATION);

    private final SocketMessageCategory category;

    SocketMessageType(SocketMessageCategory category) {
        this.category = category;
    }

    public SocketMessageCategory getCategory() {
        return category;
    }

    public enum SocketMessageCategory {
        REQUEST,
        RESPONSE,
        NOTIFICATION
    }
}
