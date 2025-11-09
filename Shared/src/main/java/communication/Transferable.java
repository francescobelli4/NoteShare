package communication;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import communication.events.NewMessageEvent;
import communication.requests.LoginRequest;
import communication.requests.RegisterRequest;
import communication.requests.TokenLoginRequest;
import communication.responses.*;

/**
 * This class creates a unified way to interact with messages sent client <---> server
 * Every message should have a unique id and a serialize/deserialize function
 */
public class Transferable {

    /**
     * This should be unique
     */
    protected int id;

    /**
     * This serializes the message to a JSON string
     * @return a string in JSON format
     */
    public String toJson() { return new Gson().toJson(this); }

    /**
     * This returns an instance of Message from a JSON string.
     * It's possible to use instanceof to find the original Message subclass
     * @param json a JSON formatted string
     * @return a Message subclass
     */
    public static Transferable fromJson(String json) {

        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        int msgId = jsonObject.get("id").getAsInt();
        Gson gson = new Gson();

        return switch (msgId) {
            case 1 -> gson.fromJson(json, RegisterRequest.class);
            case 2 -> gson.fromJson(json, LoginRequest.class);
            case 3 -> gson.fromJson(json, ErrorResponse.class);
            case 4 -> gson.fromJson(json, TokenLoginRequest.class);
            case 5 -> gson.fromJson(json, NewMessageEvent.class);
            case 100 -> gson.fromJson(json, RegisterSuccessResponse.class);
            case 101 -> gson.fromJson(json, LoginSuccessResponse.class);
            case 102 -> gson.fromJson(json, TokenLoginSuccessResponse.class);
            default -> null;
        };
    }

    public int getId() {
        return id;
    }
}
