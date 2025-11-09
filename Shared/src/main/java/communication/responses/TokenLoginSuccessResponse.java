package communication.responses;

import communication.Transferable;
import persistency.dtos.MessageDTO;
import persistency.dtos.UserDTO;

import java.util.List;

/**
 * This message is sent from server to client to notify the user that the
 * registration was successful.
 *
 * It sends a UserDTO so the client can set up the user profile and an access token
 * to allow the automatic login
 */
public class TokenLoginSuccessResponse extends Transferable {

    public UserDTO userDTO;
    public String token;
    public List<MessageDTO> messages;

    public TokenLoginSuccessResponse(UserDTO userDTO, List<MessageDTO> messages, String token) {

        id = 102;
        this.userDTO = userDTO;
        this.messages = messages;
        this.token = token;
    }
}
