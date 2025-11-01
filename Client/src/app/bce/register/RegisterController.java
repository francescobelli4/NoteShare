package app.bce.register;

import app.NetworkUser;
import app.bce.entities.UserModel;
import persistency.shared.dtos.UserDTO;
import utils.Utils;

/**
 * This controller should manage everything in the "Register" use case.
 * Its purpose is to divide the UI from the app functionalities' logic.
 */
public class RegisterController {

    private RegisterBoundary registerBoundary;

    public void setBoundary(RegisterBoundary registerBoundary) {
        this.registerBoundary = registerBoundary;
    }

    public void performRegister(String username, String password, String userType) {

        if (username.length() < registerBoundary.getMIN_USERNAME_LENGTH()) {
            registerBoundary.onRegisterFailed(RegisterResult.USERNAME_TOO_SHORT);
            return;
        }

        if (username.length() > registerBoundary.getMAX_USERNAME_LENGTH()) {
            registerBoundary.onRegisterFailed(RegisterResult.USERNAME_TOO_LONG);
            return;
        }

        if (password.length() < registerBoundary.getMIN_PASSWORD_LENGTH()) {
            registerBoundary.onRegisterFailed(RegisterResult.PASSWORD_TOO_SHORT);
            return;
        }

        if (password.length() > registerBoundary.getMAX_PASSWORD_LENGTH()) {
            registerBoundary.onRegisterFailed(RegisterResult.PASSWORD_TOO_LONG);
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

        registerBoundary.onRegisterSuccess();
    }
}
