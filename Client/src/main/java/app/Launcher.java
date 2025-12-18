package app;

import app_controllers.LoginController;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
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

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setMinHeight(screenBounds.getHeight());
        primaryStage.setMinWidth(screenBounds.getWidth());
        primaryStage.setTitle("NoteShare");

        ViewNavigator.setStage(primaryStage);

        LoginController.loginUsingToken();
        ViewNavigator.displayHomeView();

        primaryStage.show();
    }
}
