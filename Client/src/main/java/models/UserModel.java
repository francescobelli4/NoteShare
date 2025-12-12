package models;

import communication.user.UserType;

/**
 * This class should represent a user in the system.
 * Actually, a user can be a Student, a Teacher, or an Admin.
 * Every UserModel subclass inherits common functionalities from this class.
 */
public class UserModel {

    protected UserModel() {}

    private String username;
    private UserType userType;

    private boolean loggedIn = false;

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
}
