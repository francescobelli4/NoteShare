package communication.dtos.requests.login;

public class LoginUsingTokenRequestDTO {

    private String token;

    public LoginUsingTokenRequestDTO(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
