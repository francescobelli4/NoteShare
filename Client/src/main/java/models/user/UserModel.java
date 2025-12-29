package models.user;

import app.AppContext;
import communication.dtos.user.UserType;
import models.folder.FolderModel;
import models.messages.MessageModel;
import models.user.roles.AdminRole;
import models.user.roles.Role;
import models.user.roles.StudentRole;
import models.user.roles.TeacherRole;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * https://martinfowler.com/apsupp/roles.pdf Page 13 "Turning the roles into separate objects":
 *
 * "The implementation looks very similar to using the State Object pattern, the difference lies in
 * the interface. When using Role Subtype with State Object, the state objects are entirely hidden
 * from the user of the class. To find a manager’s budget a user asks the person object, which then
 * makes the appropriate delegation. When using Role Object, however, the user asks the person object
 * for its manager role, and then asks that role for the budget. In other words the roles are public
 * knowledge"
 *
 * None of GoF design patterns seemed correct to represent the fact that a User can be specified at runtime
 * in Student, Teacher or Admin.
 * Obviously, the "State Pattern" was the most correct pattern, but it didn't support well the fact that
 * different states of User (Student, Teacher, Admin) had completely different methods: the State Pattern
 * only offers communication with "User", but not with his states (Student, Teacher, Admin).
 * So I chose a pattern from this paper, which is actually a State Pattern with public access to the
 * User's role.
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
    private Role role;
    protected boolean loggedIn = false;

    private final List<MessageModel> messages = new ArrayList<>();

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
        this("guest", null);
    }

    public UserModel(String username, UserType userType) {

        this.username = username;

        role = switch (userType) {
            case STUDENT -> new StudentRole(this);
            case TEACHER -> new TeacherRole(this);
            case ADMINISTRATOR -> new AdminRole(this);
            case null -> new StudentRole(this);
        };

        setRootFolder(AppContext.getInstance().getFolderDAO().getRootFolder());
        setActiveFolder(AppContext.getInstance().getFolderDAO().getUserFolder(this));
        AppContext.getInstance().getFolderDAO().save(getActiveFolder(), getRootFolder());
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Role getRole() {
        return role;
    }

    public <R extends Role> Optional<R> as(Class<R> type) {
        return type.isInstance(role) ? Optional.of(type.cast(role)) : Optional.empty();
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;

        setRootFolder(AppContext.getInstance().getFolderDAO().getRootFolder());
        setActiveFolder(AppContext.getInstance().getFolderDAO().getUserFolder(this));
        AppContext.getInstance().getFolderDAO().save(getActiveFolder(), getRootFolder());

        if (loggedIn) {
            for (LoginListener l : List.copyOf(loginListeners)) {
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
