package app.bce.register;

import app.NetworkUser;
import persistency.shared.dtos.UserDTO;

/**
 * This boundary should allow communication between user and Register use case.
 */
public class RegisterBoundary {

    private int MIN_USERNAME_LENGTH = 5;
    private int MAX_USERNAME_LENGTH = 20;
    private int MIN_PASSWORD_LENGTH = 5;
    private int MAX_PASSWORD_LENGTH = 20;

    RegisterController registerController;
    Listener listener;

    public RegisterBoundary(RegisterController registerController) {
        this.registerController = registerController;
        this.registerController.setBoundary(this);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
        NetworkUser.getInstance().setRegisterBoundary(this);
    }

    public void performRegister(String username, String password, String userType) {
        this.registerController.performRegister(username, password, userType);
    }

    public void handleRegisterSuccessResponse(UserDTO userDTO, String token) {
        registerController.onRegisterSuccess(userDTO, token);
    }

    public void handleRegisterFailedResponse(RegisterResult registerResult) {
        onRegisterFailed(registerResult);
    }

    public void onRegisterSuccess() {
        if (listener != null)
            listener.onRegisterSuccess();
    }

    public void onRegisterFailed(RegisterResult registerResult) {
        if (listener != null)
            listener.onRegisterFailed(registerResult);
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

        void onRegisterSuccess();
        void onRegisterFailed(RegisterResult registerResult);
    }
}

