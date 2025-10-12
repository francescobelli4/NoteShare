package dtos;

/**
 * This class is a transferable object with all the useful info
 * about a user.
 * This can be mapped to an UserEntity.
 */
public class UserDTO {

    private String username;
    private String userType;
    private int coins;

    public UserDTO() { }

    public UserDTO(String username) {
        this.username = username;
    }

    public UserDTO(String username, String userType, int coins) {
        this.username = username;
        this.userType = userType;
        this.coins = coins;
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
