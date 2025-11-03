package app.bce.login;

import app.NetworkUser;
import app.bce.Boundary;
import app.bce.BoundaryManager;
import app.bce.Controller;
import app.bce.entities.UserModel;
import persistency.shared.dtos.UserDTO;
import utils.Utils;

/**
 * This controller should manage everything in the "Login" use case.
 * Its purpose is to divide the UI from the app functionalities' logic.
 */
public class LoginController extends Controller {

    @Override
    protected LoginBoundary getBoundary() {
        return (LoginBoundary) boundary;
    }

    public void performLogin(String username, String password) {

        if (username.length() < getBoundary().getMIN_USERNAME_LENGTH()) {
            getBoundary().handleLoginFailedResponse(LoginResult.USERNAME_TOO_SHORT);
            return;
        }

        if (username.length() > getBoundary().getMAX_USERNAME_LENGTH()) {
            getBoundary().handleLoginFailedResponse(LoginResult.USERNAME_TOO_LONG);
            return;
        }

        if (password.length() < getBoundary().getMIN_PASSWORD_LENGTH()) {
            getBoundary().handleLoginFailedResponse(LoginResult.PASSWORD_TOO_SHORT);
            return;
        }

        if (password.length() > getBoundary().getMAX_PASSWORD_LENGTH()) {
            getBoundary().handleLoginFailedResponse(LoginResult.PASSWORD_TOO_LONG);
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

        getBoundary().onLoginSuccess();

        BoundaryManager.getInstance().destroyLoginBoundary();
        BoundaryManager.getInstance().destroyRegisterBoundary();
    }
}
