package app.mvc.register;

import app.mvc.Boundary;
import persistency.dtos.UserDTO;

import java.util.ArrayList;

/**
 * This boundary should allow communication between user and Register use case.
 */
public class RegisterBoundary extends Boundary {

    /**
     * Data boundaries
     */
    private final int MIN_USERNAME_LENGTH = 5;
    private final int MAX_USERNAME_LENGTH = 20;
    private final int MIN_PASSWORD_LENGTH = 5;
    private final int MAX_PASSWORD_LENGTH = 20;

    /**
     * The arraylist of listeners. Every listener will be notified when events occur
     */
    private final ArrayList<Listener> listeners = new ArrayList<>();

    /**
     * Base constructor
     *
     * This constructor should just call the superclass' constructor
     * @param registerController
     */
    public RegisterBoundary(RegisterController registerController) {
        super(registerController);
    }

    /**
     * This function should add a listener to the listeners list
     * @param listener the new listener
     */
    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    /**
     * This function should make the user register. The boundary just calls the controller
     * which knows the logic to complete the use case
     * @param username selected username
     * @param password selected password
     * @param userType selecte usertype
     */
    public void performRegister(String username, String password, String userType) {
        getController().performRegister(username, password, userType);
    }

    /**
     * This function should call the controller to actually register the user and then notify
     * all the listener when the operation is complete.
     * @param userDTO the user data transfer object that will be used to populate the UserModel
     * @param token the user's access token that has been assigned to that user
     */
    public void onRegisterSuccess(UserDTO userDTO, String token) {
        getController().onRegisterSuccess(userDTO, token);

        for (Listener l : listeners) {
            l.onRegisterSuccess();
        }
    }

    /**
     * This function should notify the listeners when the registration failed
     * @param registerResult the reason for the fail
     */
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

    /**
     * This function should destroy the controller. The only reference to this boundary will
     * be destroyed by the BoundaryManager
     */
    @Override
    public void destroy() {
        controller = null;
    }

    /**
     * This function should return the correct subclass of this boundary's controller
     * @return a RegisterController reference
     */
    @Override
    protected RegisterController getController() {
        return (RegisterController) controller;
    }

    /**
     * The interface that all the listeners will implement to get notified on application
     * status changes
     */
    public interface Listener {
        void onRegisterSuccess();
        void onRegisterFailed(RegisterResult registerResult);
    }
}

