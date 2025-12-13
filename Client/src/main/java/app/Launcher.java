package app;

import app_controllers.LoginController;
import javafx.application.Application;
import javafx.stage.Stage;
import views.ViewNavigator;

import java.util.logging.Logger;

public class Launcher extends Application {

    private static final Logger LOGGER = Logger.getLogger("Launcher");

    public static void launchApp() {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        LOGGER.info("Starting application!");

        ViewNavigator viewNavigator = ViewNavigator.getInstance();
        viewNavigator.setupView(primaryStage);

        if (LoginController.loginUsingToken()) {
            viewNavigator.displayHomeView();
        } else {
            viewNavigator.displayAccessView();
        }
    }
}
