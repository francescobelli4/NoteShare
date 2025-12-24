package models.user;

import app.AppContext;
import communication.dtos.user.UserType;
import models.folder.FolderModel;
import models.messages.MessageModel;

import java.util.ArrayList;
import java.util.List;

/**
 * This class should represent a user in the system.
 * Actually, a user can be a Student, a Teacher, or an Admin.
 * Every UserModel subclass inherits common functionalities from this class.
 */
public class UserModel {

    /** This should always be the app's local folder out of demo mode.
     *  In demo mode, the root folder (just like all the other folders) is saved on the
     *  stack.
     */
    private FolderModel rootFolder;

    /**
     * The folder that the user is actually working on. This can be a persistent one
     * or not. In general, this should contain the notes and subfolders that the user
     * needs to see.
     */
    private FolderModel activeFolder;

    private String username;
    private UserType userType;
    private final List<MessageModel> messages = new ArrayList<>();

    protected boolean loggedIn = false;

    private final List<LoginListener> loginListeners = new ArrayList<>();
    public void addUserLoginListener(LoginListener listener) {
        this.loginListeners.add(listener);
    }
    public void removeUserLoginListener(LoginListener listener) {
        this.loginListeners.remove(listener);
    }

    private final List<MessageListener> messageListeners = new ArrayList<>();
    public void addUserMessageListener(MessageListener listener) {
        this.messageListeners.add(listener);
    }
    public void removeUserMessageListener(MessageListener listener) {
        this.messageListeners.remove(listener);
    }

    private final List<ActiveFolderListener> activeFolderListeners = new ArrayList<>();
    public void addUserActiveFolderListener(ActiveFolderListener listener) {
        this.activeFolderListeners.add(listener);
    }
    public void removeUserActiveFolderListener(ActiveFolderListener listener) {
        this.activeFolderListeners.remove(listener);
    }

    public UserModel() {
        // Default user (not logged in)
        this("guest", UserType.STUDENT);
    }

    public UserModel(String username, UserType userType) {

        this.username = username;
        this.userType = userType;

        setRootFolder(AppContext.getInstance().getFolderDAO().getRootFolder());
        setActiveFolder(AppContext.getInstance().getFolderDAO().getUserFolder(this));
        AppContext.getInstance().getFolderDAO().save(getActiveFolder(), getRootFolder());
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

    public void addMessage(MessageModel message) {
        this.messages.add(message);

        for (MessageListener l : messageListeners) {
            l.onMessageAdded(message);
        }
    }

    public void setMessages(List<MessageModel> settingMessages) {

        this.messages.addAll(settingMessages);

        for (MessageListener l : messageListeners) {
            l.onMessagesSet(settingMessages);
        }
    }

    public List<LoginListener> getLoginListeners() {
        return loginListeners;
    }

    public List<MessageListener> getMessageListeners() {
        return messageListeners;
    }

    public List<ActiveFolderListener> getActiveFolderListeners() {
        return activeFolderListeners;
    }

    public List<MessageModel> getMessages() {
        return messages;
    }

    public FolderModel getActiveFolder() {
        return activeFolder;
    }

    public void setActiveFolder(FolderModel activeFolder) {
        if (this.activeFolder != null)
            this.activeFolder.clearListeners();

        this.activeFolder = activeFolder;

        for (ActiveFolderListener l : activeFolderListeners) {
            l.activeFolderSet(activeFolder);
        }
    }

    public FolderModel getRootFolder() {
        return rootFolder;
    }

    public void setRootFolder(FolderModel rootFolder) {
        this.rootFolder = rootFolder;
    }

    public interface LoginListener {
        void onLoggedIn();
    }

    public interface MessageListener {
        void onMessagesSet(List<MessageModel> messages);
        void onMessageAdded(MessageModel message);
    }

    public interface ActiveFolderListener {
        void activeFolderSet(FolderModel folder);
    }
}
