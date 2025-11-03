package app.bce.entities;

import persistency.shared.Folder;

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

    private Folder rootFolder;
    private Folder activeFolder;

    public Folder getRootFolder() {
        return rootFolder;
    }
    public void setRootFolder(Folder rootFolder) {
        this.rootFolder = rootFolder;
    }

    public Folder getActiveFolder() {
        return activeFolder;
    }
    public void setActiveFolder(Folder activeFolder) {
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
}
