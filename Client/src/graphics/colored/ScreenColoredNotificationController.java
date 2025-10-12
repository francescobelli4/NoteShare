package graphics.colored;

import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.File;
import java.util.Map;
import java.util.Objects;

/**
 * This controller manages a displayed notification.
 * Params:
 *      String   title
 *      String   description
 *      Icons    icon
 */
public class ScreenColoredNotificationController implements PageController {

    @FXML
    ImageView icon;

    @FXML
    Label title;

    @FXML
    Label description;

    @FXML
    StackPane notification_container;

    @FXML
    HBox notification;

    /**
     * Setting up the notification parameters.
     * @param params notification details
     */
    @Override
    public void setParams(Map<String, String> params) {
        title.setText(params.get("title"));
        description.setText(params.get("description"));

        Image image = new Image(Objects.requireNonNull(getClass().getResource(params.get("icon"))).toExternalForm());
        icon.setImage(image);
    }

    @Override
    public void appendSecondaryPage(Node secondaryPage) {

    }

    /**
     * This function is called before the FXML element is actually loaded, so I need to add a listener to start the
     * animation only when newScene is ready.
     */
    @FXML
    public void initialize() {

        // I should animate only if the scene elements are actually loaded!
        notification.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                Platform.runLater(() -> {
                    notification.applyCss();
                    notification.layout();
                    animate();
                });
            }
        });
    }

    /**
     * Slide-in animation... auto-explicative
     */
    private void animate() {

        notification.setTranslateY(-2*notification.prefHeight(-1));

        // Slide in
        TranslateTransition tt = new TranslateTransition(Duration.millis(500), notification);
        tt.setFromY(-2*notification.prefHeight(-1));
        tt.setToY(0);
        tt.play();

        tt.setOnFinished(e -> {

            // Pause
            PauseTransition delay = new PauseTransition(Duration.seconds(2));
            delay.setOnFinished(ev -> {

                // Slide out
                TranslateTransition slideOut = new TranslateTransition(Duration.millis(500), notification);
                slideOut.setFromY(notification.getTranslateY());
                slideOut.setToY(-2*notification.prefHeight(-1));
                slideOut.setOnFinished(ev2 -> {
                    if (notification_container.getParent() instanceof javafx.scene.layout.Pane parent) {
                        parent.getChildren().remove(notification_container);
                    }
                });
                slideOut.play();
            });
            delay.play();
        });
    }
}
