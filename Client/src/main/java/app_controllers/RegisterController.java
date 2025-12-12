package app_controllers;

import communication.SocketMessage;
import communication.SocketMessageFactory;
import communication.SocketMessageType;
import communication.dtos.responses.login.RegisterFailureReason;
import communication.dtos.responses.login.RegisterSuccessResponseDTO;
import communication.user.UserType;
import exceptions.RegisterFailureException;
import mappers.UserMapper;
import services.ServerCommunicationService;
import sessions.UserSession;
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

            if (response.getSocketMessageType() == SocketMessageType.REGISTER_SUCCESS) {
                LOGGER.info("Register success! :D");
                RegisterSuccessResponseDTO<?> payload = (RegisterSuccessResponseDTO<?>) response.getPayload();
                Utils.saveAccessToken(payload.getAccessToken());
                UserSession.getInstance().setSessionUser(UserMapper.toModel(payload.getUserDTO()));
                UserSession.getInstance().getCurrentUser().setLoggedIn(true);
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