package models.user;

import communication.dtos.user.UserType;
import models.messages.MessageModel;

import java.util.ArrayList;
import java.util.List;

/**
 * This class should represent a user in the system.
 * Actually, a user can be a Student, a Teacher, or an Admin.
 * Every UserModel subclass inherits common functionalities from this class.
 */
public class UserModel {

    private List<MessageModel> messages = new ArrayList<>();

    public UserModel() {
        // Nothing to do...
    }

    private String username;
    private UserType userType;

    protected boolean loggedIn = false;

    private final ArrayList<LoginListener> loginListeners = new ArrayList<>();
    public void addUserLoginListener(LoginListener listener) {
        this.loginListeners.add(listener);
    }

    private final ArrayList<MessageListener> messageListeners = new ArrayList<>();
    public void addUserMessageListener(MessageListener listener) {
        this.messageListeners.add(listener);
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
            for (LoginListener l : loginListeners) {
                l.onLoggedIn();
            }
        }
    }

    public List<MessageModel> getMessages() {
        return messages;
    }

    public void addMessage(MessageModel message) {
        this.messages.add(message);

        for (MessageListener l : messageListeners) {
            l.onMessageAdded(message);
        }
    }

    public void setMessages(List<MessageModel> settingMessages) {

        for (MessageModel m : settingMessages) {
            this.messages.add(m);
        }

        for (MessageListener l : messageListeners) {
            l.onMessagesSet(settingMessages);
        }
    }

    public ArrayList<LoginListener> getLoginListeners() {
        return loginListeners;
    }

    public ArrayList<MessageListener> getMessageListeners() {
        return messageListeners;
    }

    public interface LoginListener {
        void onLoggedIn();
    }

    public interface MessageListener {
        void onMessagesSet(List<MessageModel> messages);
        void onMessageAdded(MessageModel message);
    }
}
