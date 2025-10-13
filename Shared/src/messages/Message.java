package messages;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import messages.requests.LoginMessage;
import messages.requests.RegisterMessage;
import messages.requests.TokenLoginMessage;
import messages.responses.ErrorMessage;
import messages.responses.LoginSuccessMessage;
import messages.responses.RegisterSuccessMessage;
import messages.responses.TokenLoginSuccessMessage;

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
     * @return a string in JSON format
     */
    public String toJson() { return new Gson().toJson(this); }

    /**
     * This returns an instance of Message from a JSON string.
     * It's possible to use instanceof to find the original Message subclass
     * @param json a JSON formatted string
     * @return a Message subclass
     */
    public static Message fromJson(String json) {

        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        int msgId = jsonObject.get("id").getAsInt();
        Gson gson = new Gson();

        return switch (msgId) {
            case 1 -> gson.fromJson(json, RegisterMessage.class);
            case 2 -> gson.fromJson(json, LoginMessage.class);
            case 3 -> gson.fromJson(json, ErrorMessage.class);
            case 4 -> gson.fromJson(json, TokenLoginMessage.class);
            case 100 -> gson.fromJson(json, RegisterSuccessMessage.class);
            case 101 -> gson.fromJson(json, LoginSuccessMessage.class);
            case 102 -> gson.fromJson(json, TokenLoginSuccessMessage.class);
            default -> null;
        };
    }
}
