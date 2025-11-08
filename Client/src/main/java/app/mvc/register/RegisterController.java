package app.mvc.register;

import app.NetworkUser;
import app.mvc.Controller;
import app.mvc.mappers.UserMapper;
import persistency.dtos.UserDTO;
import utils.Utils;

/**
 * This controller should manage everything in the "Register" use case.
 * Its purpose is to divide the UI from the app functionalities' logic.
 */
public class RegisterController extends Controller {

    /**
     * This function should check the parameters inserted by the user and then
     * use the UserModel to register the user.
     * @param username the selected username
     * @param password the selected password
     * @param userType the selected userType
     */
    public void performRegister(String username, String password, String userType) {

        if (username.length() < Utils.getMinUsernameLength()) {
            getBoundary().onRegisterFailed(RegisterResult.USERNAME_TOO_SHORT);
            return;
        }

        if (username.length() > Utils.getMaxUsernameLength()) {
            getBoundary().onRegisterFailed(RegisterResult.USERNAME_TOO_LONG);
            return;
        }

        if (password.length() < Utils.getMinPasswordLength()) {
            getBoundary().onRegisterFailed(RegisterResult.PASSWORD_TOO_SHORT);
            return;
        }

        if (password.length() > Utils.getMaxPasswordLength()) {
            getBoundary().onRegisterFailed(RegisterResult.PASSWORD_TOO_LONG);
            return;
        }

        if (userType == null) {
            getBoundary().onRegisterFailed(RegisterResult.USER_TYPE_NOT_SELECTED);
            return;
        }

        NetworkUser.getInstance().register(username, password, userType);
    }

    /**
     * This function should implement the app logic to register the user
     * @param userDTO the user's dto representation
     * @param token the token that has been assigned to this user by the server
     */
    public void onRegisterSuccess(UserDTO userDTO, String token) {
        UserMapper.mapToModel(userDTO, token);
        Utils.saveAccessToken(token);
    }

    /**
     * This function should return the correct Boundary subclass for this controller
     * @return a RegisterBoundary reference
     */
    @Override
    protected RegisterBoundary getBoundary() {
        return (RegisterBoundary) boundary;
    }
}
