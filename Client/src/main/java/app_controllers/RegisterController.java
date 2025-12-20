package app_controllers;

import app.App;
import communication.SocketMessage;
import communication.SocketMessageFactory;
import communication.SocketMessageType;
import communication.dtos.responses.login.AccessSuccessResponseDTO;
import communication.dtos.responses.login.RegisterFailureReason;
import communication.dtos.user.UserType;
import exceptions.RegisterFailureException;
import mappers.UserMapper;
import services.ServerCommunicationService;
import utils.Hashing;
import utils.Utils;

import java.io.IOException;
import java.net.SocketException;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

public class RegisterController {

    private static final Logger LOGGER = Logger.getLogger("RegisterController");

    private RegisterController() {}

    public static void register(String username, String password, UserType userType) throws RegisterFailureException, SocketException {

        if (username.length() < Utils.getMinUsernameLength()) {
            throw new RegisterFailureException(RegisterFailureReason.USERNAME_TOO_SHORT);
        }

        if (username.length() > Utils.getMaxUsernameLength()) {
            throw new RegisterFailureException(RegisterFailureReason.USERNAME_TOO_LONG);
        }

        if (password.length() < Utils.getMinPasswordLength()) {
            throw new RegisterFailureException(RegisterFailureReason.PASSWORD_TOO_SHORT);
        }

        if (password.length() > Utils.getMaxPasswordLength()) {
            throw new RegisterFailureException(RegisterFailureReason.PASSWORD_TOO_LONG);
        }

        SocketMessage response;

        try {
            response = ServerCommunicationService.getInstance().sendSync(SocketMessageFactory.createRegisterRequest(username, Hashing.hashString(password), userType));

            if (response.getSocketMessageType() == SocketMessageType.ACCESS_SUCCESS) {
                LOGGER.info("Register success! :D");
                AccessSuccessResponseDTO<?> payload = (AccessSuccessResponseDTO<?>) response.getPayload();
                Utils.saveAccessToken(payload.getAccessToken());
                App.setUser(UserMapper.populateModel(App.getUser(), payload.getUserDTO()));
                App.getUser().setLoggedIn(true);
            } else if (response.getSocketMessageType() == SocketMessageType.REGISTER_FAILURE) {
                throw new RegisterFailureException(RegisterFailureReason.USERNAME_ALREADY_TAKEN);
            }

        } catch (IOException | ExecutionException e) {
            throw new SocketException("Failed communicating with server: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new SocketException("Failed communicating with server" + e.getMessage());
        }
    }
}