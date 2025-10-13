package graphics;

import graphics.colored.Icons;
import graphics.colored.PageController;
import graphics.colored.Pages;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import messages.requests.RegisterMessage;
import messages.requests.TokenLoginMessage;
import user.User;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * This is the main GraphicsController. This manages both the colored and the black&white UI.
 * Every main Page needs to have a StackPane as root, because it allows to display floating pages (like notifications or
 * forms) on top of the main Page.
 * Secondary pages are displayed on top of primary pages.
 *
 * TODO MAKE AN INTERFACE TO MAKE THIS WORK BOTH IN COLORED AND BLACK&WHITE UI!
 */
public class GraphicsController extends Application {

    /**
     * The stage is the actual window
     */
    private static Stage stage;
    /**
     * Every page needs to have a StackPane as root
     */
    private static FXMLLoader mainPage;
    private static StackPane root;

    /**
     * This function launches the "start" function, that actually starts the UI
     */
    public static void setup() {

        System.out.println("Setting up Graphics controller");
        launch();
    }

    /**
     * This function displays a Main Page.
     * A Main Page is used as a basis for Secondary Pages.
     * @param page A main page
     */
    public static void displayMainPage(Pages page) {

        mainPage = new FXMLLoader(GraphicsController.class.getResource(page.getPath()));

        try {
            Parent loaded = mainPage.load();
            root = (StackPane) loaded;

            Scene scene = new Scene(root);
            stage.setScene(scene);

        } catch (IOException e) {
            //TODO
            e.printStackTrace();
        }
    }

    /**
     * This function displays a Floating Page.
     * A floating page is a secondary page
     * @param secondaryPage a secondary page
     * @param params nullable params
     */
    public static void displaySecondaryPage(Pages secondaryPage, Map<String, String> params) {

        FXMLLoader loader = new FXMLLoader(GraphicsController.class.getResource(secondaryPage.getPath()));

        try {
            Node secondaryPageRoot = loader.load();
            PageController controller = loader.getController();
            controller.setParams(params);

            PageController mainPageController = mainPage.getController();
            mainPageController.appendSecondaryPage(secondaryPageRoot);
        } catch (IOException e) {
            //TODO
            e.printStackTrace();
        }
    }

    /**
     * This function is a specification of displaySecondaryPage.
     * @param title notification title
     * @param description notification description
     * @param icon notification icon
     */
    public static void displayNotification(String title, String description, Icons icon) {

        Map<String, String> params = new HashMap<>();
        params.put("title", title);
        params.put("description", description);
        params.put("icon", icon.getPath());

        FXMLLoader loader = new FXMLLoader(GraphicsController.class.getResource(Pages.NOTIFICATION.getPath()));

        try {
            Node notification_root = loader.load();
            PageController controller = loader.getController();
            controller.setParams(params);

            root.getChildren().add(notification_root);
        } catch (IOException e) {
            //TODO
            e.printStackTrace();
        }
    }


    /**
     * This function actually starts the UI
     * @param stage the main app stage
     */
    @Override
    public void start(Stage stage) {

        GraphicsController.stage = stage;

        stage.setTitle("Note Share");

        if (User.getInstance().getUserDTO() == null) {
            displayMainPage(Pages.ACCESS);
        } else {
            displayMainPage(Pages.STUDENT_HOME);
        }


        stage.show();
    }

}
