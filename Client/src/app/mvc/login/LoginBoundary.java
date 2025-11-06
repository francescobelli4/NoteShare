package app.mvc.login;

import app.mvc.Boundary;
import persistency.dtos.UserDTO;

import java.util.ArrayList;

/**
 * This boundary should allow communication between the user and the Login use case.
 */
public class LoginBoundary extends Boundary {

    private int MIN_USERNAME_LENGTH = 5;
    private int MAX_USERNAME_LENGTH = 20;
    private int MIN_PASSWORD_LENGTH = 5;
    private int MAX_PASSWORD_LENGTH = 20;

    private final ArrayList<Listener> listeners = new ArrayList<>();

    public LoginBoundary(LoginController controller) {
        super(controller);
    }


    @Override
    protected void initialize() {

    }

    @Override
    public void destroy() {
        controller = null;
    }

    @Override
    protected LoginController getController() {
        return (LoginController) controller;
    }

    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    public void performLogin(String username, String password) {
        getController().performLogin(username, password);
    }

    public void performTokenLogin() {
        getController().performTokenLogin();
    }

    public void handleLoginSuccessResponse(UserDTO userDTO, String token) {
        getController().onLoginSuccess(userDTO, token);
    }

    public void handleLoginFailedResponse(LoginResult loginResult) {
        onLoginFailed(loginResult);
    }

    public void onLoginSuccess() {
        for (Listener l : listeners)
            l.onLoginSuccess();
    }

    public void onLoginFailed(LoginResult loginResult) {
        for (Listener l : listeners)
            l.onLoginFailed(loginResult);
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

