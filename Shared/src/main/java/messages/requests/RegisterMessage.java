package messages.requests;

import messages.Message;

/**
 * This message is sent from client to server to make the server register the user.
 */
public class RegisterMessage extends Message {

    public String username;
    public String password;
    public String userType;

    public RegisterMessage(String username, String password, String userType) {

        id = 1;

        this.username = username;
        this.password = password;
        this.userType = userType;
    }
}
