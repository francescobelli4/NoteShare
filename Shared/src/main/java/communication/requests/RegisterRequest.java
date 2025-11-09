package communication.requests;

import communication.Transferable;

/**
 * This message is sent from client to server to make the server register the user.
 */
public class RegisterRequest extends Transferable {

    private final String username;
    private final String password;
    private final String userType;

    public RegisterRequest(String username, String password, String userType) {

        id = 1;

        this.username = username;
        this.password = password;
        this.userType = userType;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getUserType() {
        return userType;
    }
}
