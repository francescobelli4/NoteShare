package communication.requests;

import communication.Transferable;

/**
 * This message is sent from client to server to make the server register the user.
 */
public class RegisterRequest extends Transferable {

    public String username;
    public String password;
    public String userType;

    public RegisterRequest(String username, String password, String userType) {

        id = 1;

        this.username = username;
        this.password = password;
        this.userType = userType;
    }
}
