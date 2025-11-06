package app.mvc.login;

import app.mvc.BoundaryManager;
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

        UserModel.getInstance().login(username, password);
    }

    public void performTokenLogin() {
        UserModel.getInstance().tokenLogin();
    }

    public void onLoginSuccess(UserDTO userDTO, String token) {

        UserMapper.mapToModel(userDTO, token);

        Utils.saveAccessToken(token);

        getBoundary().onLoginSuccess();

        BoundaryManager.getInstance().destroyLoginBoundary();
        BoundaryManager.getInstance().destroyRegisterBoundary();
    }
}
