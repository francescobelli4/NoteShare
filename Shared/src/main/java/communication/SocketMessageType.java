package communication;


public enum SocketMessageType {

    // Client -> Server
    LOGIN_REQUEST(SocketMessageCategory.REQUEST),
    LOGIN_USING_TOKEN_REQUEST(SocketMessageCategory.REQUEST),
    REGISTER_REQUEST(SocketMessageCategory.REQUEST),

    // Server -> Client
    LOGIN_SUCCESS(SocketMessageCategory.RESPONSE),
    LOGIN_FAILURE(SocketMessageCategory.RESPONSE),
    REGISTER_SUCCCESS(SocketMessageCategory.RESPONSE),
    REGISTER_FAILURE(SocketMessageCategory.RESPONSE);

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
