package app.mvc.login;

import app.mvc.Boundary;
import persistency.dtos.UserDTO;

import java.util.ArrayList;

/**
 * This boundary should allow communication between the user and the Login use case.
 */
public class LoginBoundary extends Boundary {

    /**
     * Data boundaries
     */
    private int MIN_USERNAME_LENGTH = 5;
    private int MAX_USERNAME_LENGTH = 20;
    private int MIN_PASSWORD_LENGTH = 5;
    private int MAX_PASSWORD_LENGTH = 20;

    /**
     * The list of listeners that will be notified when events occur
     */
    private final ArrayList<Listener> listeners = new ArrayList<>();

    /**
     * Base constructor
     *
     * This should just call the superclass' constructor
     * @param controller the associated controller
     */
    public LoginBoundary(LoginController controller) {
        super(controller);
    }

    /**
     * This function should add a new listener
     * @param listener the new listener
     */
    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    /**
     * This function should call the controller to make the user login
     * @param username the selected username
     * @param password the selected password
     */
    public void performLogin(String username, String password) {
        getController().performLogin(username, password);
    }

    /**
     * This function should call the controller to perform the login with token
     */
    public void performTokenLogin() {
        getController().performTokenLogin();
    }

    /**
     * This function should call the controller to perform the operations needed after the
     * login success and then notify all the listeners
     * @param userDTO the user data transfer object received
     * @param token the received access token
     */
    public void onLoginSuccess(UserDTO userDTO, String token) {
        getController().onLoginSuccess(userDTO, token);

        for (Listener l : listeners)
            l.onLoginSuccess();
    }

    /**
     * This function should notify the listeners whenever the login fails
     * @param loginResult the cause of fail
     */
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

    @Override
    public void destroy() {
        controller = null;
    }

    @Override
    protected LoginController getController() {
        return (LoginController) controller;
    }

    /**
     * The interface that all the listeners will implement to get notified on application
     * status changes
     */
    public interface Listener {
        void onLoginSuccess();
        void onLoginFailed(LoginResult loginResult);
    }
}

