package models;

import communication.user.UserType;

import java.util.ArrayList;
import java.util.List;

/**
 * This class should represent a user in the system.
 * Actually, a user can be a Student, a Teacher, or an Admin.
 * Every UserModel subclass inherits common functionalities from this class.
 */
public class UserModel {

    public UserModel() {
        // Nothing to do...
    }

    private final ArrayList<Listener> listeners = new ArrayList<>();

    private String username;
    private UserType userType;

    private boolean loggedIn = false;

    public List<Listener> getListeners() {
        return listeners;
    }

    public void addListener(Listener listener) {
        this.listeners.add(listener);
    }

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

        if (loggedIn) {
            for (Listener l : listeners) {
                l.onLoggedIn();
            }
        }
    }

    public interface Listener {
        void onLoggedIn();
    }
}
