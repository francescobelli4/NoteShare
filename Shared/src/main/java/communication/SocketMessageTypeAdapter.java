package communication;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import communication.dtos.requests.login.LoginRequestDTO;
import communication.dtos.requests.login.LoginUsingTokenRequestDTO;
import communication.dtos.requests.register.RegisterRequestDTO;
import communication.dtos.responses.login.LoginFailureResponseDTO;
import communication.dtos.responses.login.LoginSuccessResponseDTO;
import communication.dtos.responses.login.RegisterFailureResponseDTO;
import communication.dtos.responses.login.RegisterSuccessResponseDTO;
import communication.user.*;

import java.lang.reflect.Type;

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
    public SocketMessage read(JsonReader jsonReader) throws JsonParseException {

        JsonObject jsonObject = JsonParser.parseReader(jsonReader).getAsJsonObject();
        SocketMessageType socketMessageType = SocketMessageType.valueOf(jsonObject.get("socketMessageType").getAsString());
        String socketMessageID = jsonObject.get("socketMessageID").getAsString();
        JsonElement payloadElement = jsonObject.get("payload");

        Type payloadType = getPayloadType(payloadElement, socketMessageType);
        Object payload = gson.fromJson(payloadElement, payloadType);

        return new SocketMessage(socketMessageType, socketMessageID, payload);
    }

    private Type getPayloadType(JsonElement payloadElement, SocketMessageType messageType) throws JsonParseException {

        return switch (messageType) {

            case LOGIN_REQUEST -> LoginRequestDTO.class;
            case LOGIN_SUCCESS -> {

                /*
                    Parsing userType from the json string
                 */
                JsonObject payloadObj = payloadElement.getAsJsonObject();
                JsonObject userDTOObj = payloadObj.get("userDTO").getAsJsonObject();
                String type = userDTOObj.get("userType").getAsString();
                UserType userType = UserType.valueOf(type);

                /*
                    Choosing the right UserDTO subclass
                 */
                Class<? extends UserDTO> parsedDTO = switch (userType) {
                    case STUDENT -> UserStudentDTO.class;
                    case TEACHER -> UserTeacherDTO.class;
                    case ADMINISTRATOR -> UserAdminDTO.class;
                };

                /*
                    TypeToken is used to avoid the Type Erasure: it allows to correctly parse the RegisterRequestDTO
                    and the right generic parameter.
                 */
                yield TypeToken.getParameterized(LoginSuccessResponseDTO.class, parsedDTO).getType();
            }
            case LOGIN_USING_TOKEN_REQUEST -> LoginUsingTokenRequestDTO.class;
            case LOGIN_FAILURE -> LoginFailureResponseDTO.class;
            case REGISTER_REQUEST -> {

                /*
                    Parsing userType from the json string
                 */
                JsonObject payloadObj = payloadElement.getAsJsonObject();
                JsonObject userDTOObj = payloadObj.get("userDTO").getAsJsonObject();
                String type = userDTOObj.get("userType").getAsString();
                UserType userType = UserType.valueOf(type);

                /*
                    Choosing the right UserDTO subclass
                 */
                Class<? extends UserDTO> parsedDTO = switch (userType) {
                    case STUDENT -> UserStudentDTO.class;
                    case TEACHER -> UserTeacherDTO.class;
                    case ADMINISTRATOR -> UserAdminDTO.class;
                };

                /*
                    TypeToken is used to avoid the Type Erasure: it allows to correctly parse the RegisterRequestDTO
                    and the right generic parameter.
                 */
                yield TypeToken.getParameterized(RegisterRequestDTO.class, parsedDTO).getType();
            }
            case REGISTER_SUCCESS -> RegisterSuccessResponseDTO.class;
            case REGISTER_FAILURE -> RegisterFailureResponseDTO.class;
            default -> throw new JsonParseException("SocketMessage Payload type not found");
        };
    }
}
