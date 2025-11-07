package app.mvc.login;

import app.mvc.Controller;
import app.mvc.mappers.UserMapper;
import app.mvc.models.UserModel;
import persistency.dtos.UserDTO;
import utils.Utils;

/**
 * This controller should manage everything in the "Login" use case.
 * Its purpose is to divide the UI from the app functionalities' logic.
 */
public class LoginController extends Controller {

    /**
     * This function should implement the logic needed to perform the login.
     * It should also check that the input data is correct and then call the
     * UserModel to perform the login.
     * @param username selected username
     * @param password selected password
     */
    public void performLogin(String username, String password) {

        if (username.length() < getBoundary().getMIN_USERNAME_LENGTH()) {
            getBoundary().onLoginFailed(LoginResult.USERNAME_TOO_SHORT);
            return;
        }

        if (username.length() > getBoundary().getMAX_USERNAME_LENGTH()) {
            getBoundary().onLoginFailed(LoginResult.USERNAME_TOO_LONG);
            return;
        }

        if (password.length() < getBoundary().getMIN_PASSWORD_LENGTH()) {
            getBoundary().onLoginFailed(LoginResult.PASSWORD_TOO_SHORT);
            return;
        }

        if (password.length() > getBoundary().getMAX_PASSWORD_LENGTH()) {
            getBoundary().onLoginFailed(LoginResult.PASSWORD_TOO_LONG);
            return;
        }

        UserModel.getInstance().login(username, password);
    }

    /**
     * This function should perform the login with the token using UserModel
     */
    public void performTokenLogin() {
        UserModel.getInstance().tokenLogin();
    }

    /**
     * This function should be triggered by the boundary whenever the server responds with
     * a login success message
     * @param userDTO the received user data transfer object
     * @param token the received access token
     */
    public void onLoginSuccess(UserDTO userDTO, String token) {

        UserMapper.mapToModel(userDTO, token);
        Utils.saveAccessToken(token);
    }

    /**
     * This function should return the correct Boundary subclass for this controller
     * @return a LoginBoundary reference
     */
    @Override
    protected LoginBoundary getBoundary() {
        return (LoginBoundary) boundary;
    }
}
