package communication.user;

public class UserDTO {

    protected String username;
    protected UserType userType;

    protected UserDTO() { }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
