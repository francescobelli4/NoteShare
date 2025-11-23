package app;

import communication.SocketMessage;
import communication.SocketMessageFactory;
import communication.SocketMessageType;
import javafx.application.Application;
import javafx.stage.Stage;
import services.ServerCommunicationService;
import utils.Utils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

public class Launcher extends Application {

    private static final Logger LOGGER = Logger.getLogger("Launcher");

    public static void launchApp() {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        LOGGER.info("Starting application!");

        attemptLoginUsingToken();
    }

    /**
     * This function should try to log in using the access token (if the user has one)
     */
    private void attemptLoginUsingToken() {
        LOGGER.info("Trying login using access token");
        File accessTokenFile = Utils.findFile(Utils.getOSLocalPath() + "access_token.txt");

        if (accessTokenFile == null) {
            return;
        }

        try {

            String accessToken = Utils.readFile(accessTokenFile);

            SocketMessage response = ServerCommunicationService.getInstance().sendSync(SocketMessageFactory.createLoginUsingTokenRequest(accessToken));

            if (response.getSocketMessageType() == SocketMessageType.LOGIN_SUCCESS) {
                //TODO
            }
        } catch (IOException ioException) {
            LOGGER.warning(String.format("Failed reading access_token.txt file: %s", ioException.getMessage()));
        } catch (InterruptedException | ExecutionException exc) {
            LOGGER.warning(String.format("Failed attempting login using token (error in sending the request): %s", exc.getMessage()));
        }
    }
}
