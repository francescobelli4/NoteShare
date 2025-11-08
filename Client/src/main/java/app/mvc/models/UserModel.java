package app.mvc.models;

/**
 * This class (a model) represents the actual state of the user in the app. It should implement
 * methods for getting and setting the actual user state and domain-logic functions.
 */
public class UserModel {

    /**
     * Singleton
     */
    private static UserModel instance;
    private UserModel() {}
    public static UserModel getInstance() {
        if (instance == null) {
            instance = new UserModel();
        }
        return instance;
    }

    private String username;
    private String userType;
    private String token;
    private int coins;

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

    public FolderModel getRootFolder() {
        return rootFolder;
    }
    public void setRootFolder(FolderModel rootFolder) {
        this.rootFolder = rootFolder;
    }

    public FolderModel getActiveFolder() {
        return activeFolder;
    }
    public void setActiveFolder(FolderModel activeFolder) {
        this.activeFolder = activeFolder;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    /**
     * public void canBuy(int price)
     *
     * SOLO SE STRETTAMENTE NECESSARIO. DI BASE QUESTO VORREI FARLO NEI BOUNDARY E BASTA
     *  public interface Listener {
     *      public void onCoinsChanged()
     *      public void on onUsernameChanged()
     *  }
     */
}
