package app.bce.register;

import app.bce.Boundary;
import persistency.shared.dtos.UserDTO;

import java.util.ArrayList;

/**
 * This boundary should allow communication between user and Register use case.
 */
public class RegisterBoundary extends Boundary {

    private int MIN_USERNAME_LENGTH = 5;
    private int MAX_USERNAME_LENGTH = 20;
    private int MIN_PASSWORD_LENGTH = 5;
    private int MAX_PASSWORD_LENGTH = 20;

    private final ArrayList<Listener> listeners = new ArrayList<>();

    public RegisterBoundary(RegisterController registerController) {
        super(registerController);
    }

    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    public void performRegister(String username, String password, String userType) {
        getController().performRegister(username, password, userType);
    }

    public void handleRegisterSuccessResponse(UserDTO userDTO, String token) {
        getController().onRegisterSuccess(userDTO, token);
    }

    public void handleRegisterFailedResponse(RegisterResult registerResult) {
        onRegisterFailed(registerResult);
    }

    public void onRegisterSuccess() {
        for (Listener l : listeners) {
            l.onRegisterSuccess();
        }

    }

    public void onRegisterFailed(RegisterResult registerResult) {
        for (Listener l : listeners) {
            l.onRegisterFailed(registerResult);
        }
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
    protected void initialize() {
    }

    @Override
    public void destroy() {
        controller = null;
    }

    @Override
    protected RegisterController getController() {
        return (RegisterController) controller;
    }

    public interface Listener {
        void onRegisterSuccess();
        void onRegisterFailed(RegisterResult registerResult);
    }
}

