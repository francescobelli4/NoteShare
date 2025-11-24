package communication;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import communication.dtos.requests.login.LoginRequestDTO;
import communication.dtos.requests.login.LoginUsingTokenRequestDTO;
import communication.dtos.requests.register.RegisterRequestDTO;
import communication.dtos.responses.login.LoginFailureResponseDTO;

/**
 * <a href="https://www.tutorialspoint.com/gson/gson_custom_adapters.htm">Taken from here :D</a>
 */
public class SocketMessageTypeAdapter extends TypeAdapter<SocketMessage> {

    private final Gson gson = new Gson();

    @Override
    public void write(JsonWriter jsonWriter, SocketMessage socketMessage) {
        gson.toJson(socketMessage, SocketMessage.class, jsonWriter);
    }

    @Override
    public SocketMessage read(JsonReader jsonReader) {

        JsonObject jsonObject = JsonParser.parseReader(jsonReader).getAsJsonObject();

        SocketMessageType socketMessageType = SocketMessageType.valueOf(jsonObject.get("socketMessageType").getAsString());
        String socketMessageID = jsonObject.get("socketMessageID").getAsString();
        JsonElement payloadElement = jsonObject.get("payload");

        Class<?> payloadType;
        switch (socketMessageType) {

            case LOGIN_REQUEST -> payloadType = LoginRequestDTO.class;
            case LOGIN_USING_TOKEN_REQUEST -> payloadType = LoginUsingTokenRequestDTO.class;
            case REGISTER_REQUEST -> payloadType = RegisterRequestDTO.class;
            case LOGIN_FAILURE -> payloadType = LoginFailureResponseDTO.class;
            default -> payloadType = null;
        }

        Object payload = gson.fromJson(payloadElement, payloadType);

        return new SocketMessage(socketMessageType, socketMessageID, payload);
    }
}
