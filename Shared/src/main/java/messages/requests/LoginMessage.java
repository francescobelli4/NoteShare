package messages.requests;

import messages.Message;

/**
 * This message is sent from client to server to log in.
 */
public class LoginMessage extends Message {

    public String username;
    public String password;


    public LoginMessage(String username, String password) {

        id = 2;

        this.username = username;
        this.password = password;
    }
}
