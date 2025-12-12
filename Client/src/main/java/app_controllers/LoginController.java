package app_controllers;

import communication.SocketMessage;
import communication.SocketMessageFactory;
import communication.SocketMessageType;
import communication.dtos.responses.login.LoginSuccessResponseDTO;
import mappers.UserMapper;
import sessions.UserSession;
import services.ServerCommunicationService;
import utils.Utils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginController {

    private static final Logger LOGGER = Logger.getLogger("LoginController");

    private LoginController() {}


    /**
     * This function should try to log in using the access token (if the user has one)
     */
    public static boolean loginUsingToken() {

        if (LOGGER.isLoggable(Level.INFO))
            LOGGER.info("Trying login using access token");
        File accessTokenFile = Utils.findFile(Utils.getOSLocalPath() + "access_token.txt");

        if (accessTokenFile == null) {
            return false;
        }

        try {

            String accessToken = Utils.readFile(accessTokenFile);

            SocketMessage response = ServerCommunicationService.getInstance().sendSync(SocketMessageFactory.createLoginUsingTokenRequest(accessToken));

            if (response.getSocketMessageType() == SocketMessageType.LOGIN_SUCCESS) {
                LoginSuccessResponseDTO<?> loginSuccessResponse = (LoginSuccessResponseDTO<?>) response.getPayload();
                UserSession.getInstance().setSessionUser(UserMapper.toModel(loginSuccessResponse.getUserDTO()));
                UserSession.getInstance().getCurrentUser().setLoggedIn(true);
                return true;
            }
        } catch (IOException ioException) {
            LOGGER.warning(String.format("Failed reading access_token.txt file: %s", ioException.getMessage()));
        } catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
            LOGGER.warning(String.format("Failed attempting login using token (error in sending the request): %s", interruptedException.getMessage()));
        } catch (ExecutionException executionException) {
            LOGGER.warning(String.format("Failed attempting login using token (error in sending the request): %s", executionException.getMessage()));
        }

        return false;
    }
}
