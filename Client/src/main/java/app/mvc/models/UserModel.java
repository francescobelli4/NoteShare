package app.mvc.models;

import app.NetworkUser;

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

    private FolderModel rootFolder;
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

    public void login(String username, String password) {
        NetworkUser.getInstance().login(username, password);
    }

    public void tokenLogin() {
        NetworkUser.getInstance().tokenLogin();
    }

    public void register(String username, String password, String userType) {
        NetworkUser.getInstance().register(username, password, userType);
    }
}
