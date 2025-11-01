package app.bce.login;

import app.NetworkUser;
import app.bce.entities.UserModel;
import persistency.shared.dtos.UserDTO;
import utils.Utils;

/**
 * This controller should manage everything in the "Login" use case.
 * Its purpose is to divide the UI from the app functionalities' logic.
 */
public class LoginController {

    private LoginBoundary loginBoundary;

    public void setBoundary(LoginBoundary loginBoundary) {
        this.loginBoundary = loginBoundary;
    }

    public void performLogin(String username, String password) {

        if (username.length() < loginBoundary.getMIN_USERNAME_LENGTH()) {
            loginBoundary.onLoginFailed(LoginResult.USERNAME_TOO_SHORT);
            return;
        }

        if (username.length() > loginBoundary.getMAX_USERNAME_LENGTH()) {
            loginBoundary.onLoginFailed(LoginResult.USERNAME_TOO_LONG);
            return;
        }

        if (password.length() < loginBoundary.getMIN_PASSWORD_LENGTH()) {
            loginBoundary.onLoginFailed(LoginResult.PASSWORD_TOO_SHORT);
            return;
        }

        if (password.length() > loginBoundary.getMAX_PASSWORD_LENGTH()) {
            loginBoundary.onLoginFailed(LoginResult.PASSWORD_TOO_LONG);
            return;
        }

        NetworkUser.getInstance().login(username, password);
    }

    public void performTokenLogin() {
        NetworkUser.getInstance().tokenLogin();
    }

    public void onLoginSuccess(UserDTO userDTO, String token) {
        UserModel.getInstance().setUsername(userDTO.getUsername());
        UserModel.getInstance().setUserType(userDTO.getUserType());
        UserModel.getInstance().setCoins(userDTO.getCoins());
        UserModel.getInstance().setToken(token);

        Utils.saveAccessToken(token);

        loginBoundary.onLoginSuccess();
    }
}
