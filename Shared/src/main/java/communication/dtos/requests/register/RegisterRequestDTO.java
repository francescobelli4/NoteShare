package communication.dtos.requests.register;

import communication.dtos.user.UserType;

public class RegisterRequestDTO {

    private String username;
    private String password;
    private UserType userType;

    public RegisterRequestDTO(String username, String password, UserType userType) {
        this.username = username;
        this.password = password;
        this.userType = userType;
    }

    public UserType getUserType() {
        return userType;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
