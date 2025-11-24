package app;

import appControllers.LoginController;
import javafx.application.Application;
import javafx.stage.Stage;
import java.util.logging.Logger;

public class Launcher extends Application {

    private static final Logger LOGGER = Logger.getLogger("Launcher");

    public static void launchApp() {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        LOGGER.info("Starting application!");

        if (LoginController.loginUsingToken()) {

        } else {

        }
    }
}
