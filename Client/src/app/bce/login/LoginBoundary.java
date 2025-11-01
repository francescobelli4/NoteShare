package app.bce.login;

import app.NetworkUser;
import persistency.shared.dtos.UserDTO;

/**
 * This boundary should allow communication between the user and the Login use case.
 */
public class LoginBoundary {

    private int MIN_USERNAME_LENGTH = 5;
    private int MAX_USERNAME_LENGTH = 20;
    private int MIN_PASSWORD_LENGTH = 5;
    private int MAX_PASSWORD_LENGTH = 20;

    LoginController loginController;
    LoginBoundary.Listener listener;

    public LoginBoundary(LoginController loginController) {
        this.loginController = loginController;
        this.loginController.setBoundary(this);
    }

    public void setListener(LoginBoundary.Listener listener) {
        this.listener = listener;
        NetworkUser.getInstance().setLoginBoundary(this);
    }

    public void performLogin(String username, String password) {
        this.loginController.performLogin(username, password);
    }

    public void performTokenLogin() {
        this.loginController.performTokenLogin();
    }

    public void handleLoginSuccessResponse(UserDTO userDTO, String token) {
        loginController.onLoginSuccess(userDTO, token);
    }

    public void handleLoginFailedResponse(LoginResult loginResult) {
        onLoginFailed(loginResult);
    }

    public void onLoginSuccess() {
        if (listener != null)
            listener.onLoginSuccess();
    }

    public void onLoginFailed(LoginResult loginResult) {
        if (listener != null)
            listener.onLoginFailed(loginResult);
    }

    public int getMAX_PASSWORD_LENGTH() {
        return MAX_PASSWORD_LENGTH;
    }

    public int getMAX_USERNAME_LENGTH() {
        return MAX_USERNAME_LENGTH;
    }

    public int getMIN_PASSWORD_LENGTH() {
        return MIN_PASSWORD_LENGTH;
    }

    public int getMIN_USERNAME_LENGTH() {
        return MIN_USERNAME_LENGTH;
    }

    public interface Listener {
        void onLoginSuccess();
        void onLoginFailed(LoginResult loginResult);
    }
}

