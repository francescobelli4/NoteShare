package messages;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import messages.requests.RegisterMessage;

/**
 * This class creates a unified way to interact with messages sent client <---> server
 * Every message should have a unique id and a serialize/deserialize function
 */
public class Message {

    /**
     * This should be unique
     */
    public int id;

    /**
     * This serializes the message to a JSON string
     * @return
     */
    public String toJson() { return new Gson().toJson(this); }

    /**
     * This returns an instance of Message from a JSON string.
     * It's possible to use instanceof to find the original Message subclass
     * @param json
     * @return a Message subclass
     */
    public static Message fromJson(String json) {

        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        int msgId = jsonObject.get("id").getAsInt();
        Gson gson = new Gson();

        return switch (msgId) {
            case 1 -> gson.fromJson(json, RegisterMessage.class);
            default -> null;
        };
    }
}
