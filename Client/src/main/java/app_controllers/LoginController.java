package app_controllers;

import communication.SocketMessage;
import communication.SocketMessageFactory;
import communication.SocketMessageType;
import communication.dtos.responses.login.*;
import exceptions.LoginFailureException;
import exceptions.RegisterFailureException;
import mappers.UserMapper;
import sessions.UserSession;
import services.ServerCommunicationService;
import utils.Hashing;
import utils.Utils;

import java.io.File;
import java.io.IOException;
import java.net.SocketException;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginController {

    private static final Logger LOGGER = Logger.getLogger("LoginController");

    private LoginController() {}

    public static void login(String username, String password) throws LoginFailureException, SocketException {

        if (username.length() < Utils.getMinUsernameLength()) {
            throw new LoginFailureException(LoginFailureReason.USERNAME_TOO_SHORT);
        }

        if (username.length() > Utils.getMaxUsernameLength()) {
            throw new LoginFailureException(LoginFailureReason.USERNAME_TOO_LONG);
        }

        if (password.length() < Utils.getMinPasswordLength()) {
            throw new LoginFailureException(LoginFailureReason.PASSWORD_TOO_SHORT);
        }

        if (password.length() > Utils.getMaxPasswordLength()) {
            throw new LoginFailureException(LoginFailureReason.PASSWORD_TOO_LONG);
        }

        SocketMessage response;

        try {
            response = ServerCommunicationService.getInstance().sendSync(SocketMessageFactory.createLoginRequest(username, password));

            if (response.getSocketMessageType() == SocketMessageType.LOGIN_SUCCESS) {
                LOGGER.info("Login success! :D");
                LoginSuccessResponseDTO<?> payload = (LoginSuccessResponseDTO<?>) response.getPayload();
                Utils.saveAccessToken(payload.getAccessToken());
                UserSession.getInstance().setSessionUser(UserMapper.toModel(payload.getUserDTO()));
                UserSession.getInstance().getCurrentUser().setLoggedIn(true);
            } else if (response.getSocketMessageType() == SocketMessageType.LOGIN_FAILURE) {
                LoginFailureResponseDTO payload = (LoginFailureResponseDTO) response.getPayload();
                throw new LoginFailureException(payload.getLoginFailureReason());
            }

        } catch (IOException | ExecutionException e) {
            throw new SocketException("Failed communicating with server: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new SocketException("Failed communicating with server" + e.getMessage());
        }
    }

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
