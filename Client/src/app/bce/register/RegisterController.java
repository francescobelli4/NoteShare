package app.bce.register;

import app.NetworkUser;
import app.bce.Boundary;
import app.bce.BoundaryManager;
import app.bce.Controller;
import app.bce.entities.UserModel;
import persistency.shared.dtos.UserDTO;
import utils.Utils;

/**
 * This controller should manage everything in the "Register" use case.
 * Its purpose is to divide the UI from the app functionalities' logic.
 */
public class RegisterController extends Controller {

    public void performRegister(String username, String password, String userType) {

        if (username.length() < getBoundary().getMIN_USERNAME_LENGTH()) {
            getBoundary().onRegisterFailed(RegisterResult.USERNAME_TOO_SHORT);
            return;
        }

        if (username.length() > getBoundary().getMAX_USERNAME_LENGTH()) {
            getBoundary().onRegisterFailed(RegisterResult.USERNAME_TOO_LONG);
            return;
        }

        if (password.length() < getBoundary().getMIN_PASSWORD_LENGTH()) {
            getBoundary().onRegisterFailed(RegisterResult.PASSWORD_TOO_SHORT);
            return;
        }

        if (password.length() > getBoundary().getMAX_PASSWORD_LENGTH()) {
            getBoundary().onRegisterFailed(RegisterResult.PASSWORD_TOO_LONG);
            return;
        }

        if (userType == null) {
            getBoundary().onRegisterFailed(RegisterResult.USER_TYPE_NOT_SELECTED);
            return;
        }

        NetworkUser.getInstance().register(username, password, userType);
    }

    public void onRegisterSuccess(UserDTO userDTO, String token) {
        UserModel.getInstance().setUsername(userDTO.getUsername());
        UserModel.getInstance().setUserType(userDTO.getUserType());
        UserModel.getInstance().setCoins(userDTO.getCoins());
        UserModel.getInstance().setToken(token);

        Utils.saveAccessToken(token);

        getBoundary().onRegisterSuccess();

        BoundaryManager.getInstance().destroyLoginBoundary();
        BoundaryManager.getInstance().destroyRegisterBoundary();
    }

    @Override
    protected RegisterBoundary getBoundary() {
        return (RegisterBoundary) boundary;
    }
}
