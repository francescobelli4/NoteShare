package sessions;

import models.UserModel;

/**
 * This class contains the data and the reference to the user.
 */
public class UserSession {

    private static UserSession instance;
    private UserSession() {}
    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    private UserModel currentUser;

    public void setSessionUser(UserModel user) {
        currentUser = user;
    }

    public UserModel getCurrentUser() {
        return currentUser;
    }
}
