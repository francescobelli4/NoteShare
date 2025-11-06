package persistency.shared.entities;

/**
 * This class represent the DB table Users (in default mode).
 * Some variables and functions are not used in demo mode, because users are
 * non-persistent.
 * This can be mapped to UserDTO.
 */
public class UserEntity {

    private String username;
    private String password;
    private String userType;
    private String token;
    private int coins;

    public UserEntity() {}

    public UserEntity(String username) {
        this.username = username;
    }

    public UserEntity(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
