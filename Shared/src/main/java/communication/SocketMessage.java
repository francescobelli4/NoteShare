package communication;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.UUID;

public class SocketMessage {

    private SocketMessageType socketMessageType;
    /** This ID can be used to associate async answers with the request. (if needed...) */
    private String socketMessageID;
    private Object payload;

    /**
     * This constructor creates a new SocketMessage
     * @param socketMessageType the message type
     * @param payload the payload of the message
     */
    public SocketMessage(SocketMessageType socketMessageType, Object payload) {
        this(socketMessageType, UUID.randomUUID().toString(), payload);
    }

    /**
     * (SHOULD NOT BE USED OUT OF SocketMessageTypeAdapter)
     *
     * This constructor is only used to convert an existing SocketMessage to a SocketMessage with
     * castable payload.
     *
     * @param socketMessageType the message type
     * @param socketMessageID the message ID
     * @param payload the payload of the message
     */
    public SocketMessage(SocketMessageType socketMessageType, String socketMessageID, Object payload) {
        this.socketMessageType = socketMessageType;
        this.socketMessageID = socketMessageID;
        this.payload = payload;
    }

    public SocketMessageType getSocketMessageType() {
        return socketMessageType;
    }

    public void setSocketMessageType(SocketMessageType socketMessageType) {
        this.socketMessageType = socketMessageType;
    }

    public String getSocketMessageID() {
        return socketMessageID;
    }

    public void setSocketMessageID(String socketMessageID) {
        this.socketMessageID = socketMessageID;
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    /**
     * Need a custom Gson object to be able to cast payload to a desired type!
     * <a href="https://www.tutorialspoint.com/gson/gson_custom_adapters.htm">Taken from here :D</a>
     */
    public static SocketMessage fromJson(String json) {

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(SocketMessage.class, new SocketMessageTypeAdapter())
                .create();

        return gson.fromJson(json, SocketMessage.class);
    }
}
