package views.colored.notifications;

import app.App;
import views.colored.Icon;
import views.colored.Page;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.util.Objects;

/**
 * Class that represents a generic notification.
 *
 * Every Notification should manage its own lifecycle.
 */
public class ScreenColoredGenericNotification extends ScreenColoredNotification{

    /**
     * FXML Elements
     */
    @FXML
    ImageView icon;
    @FXML
    Label title;
    @FXML
    Label description;
    @FXML
    StackPane notificationContainer;
    @FXML
    HBox notification;

    /**
     * Notification data
     */
    private final String titleLabel;
    private final String descriptionLabel;
    private final Icon iconType;

    /**
     *  Notification constructor
     *
     *  This constructor actually loads the FXMLLoader and sets the controller for the page.
     *
     * @param title the notification's title
     * @param description the notification's description
     * @param icon the notification's icon
     */
    public ScreenColoredGenericNotification(String title, String description, Icon icon) {
        super(Page.NOTIFICATION);

        this.titleLabel = title;
        this.descriptionLabel = description;
        this.iconType = icon;

        this.loader.setController(this);
        this.root = App.getGraphicsController().loadFXMLLoader(loader);
    }

    /**
     * In the initialize function, I set all the labels and I start the animation
     */
    @FXML
    public void initialize() {
        title.setText(titleLabel);
        description.setText(descriptionLabel);
        icon.setImage(new Image(Objects.requireNonNull(getClass().getResource(iconType.getPath())).toExternalForm()));

        animate();
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
                slideOut.setOnFinished(event -> {
                    if (notificationContainer.getParent() instanceof javafx.scene.layout.Pane parent) {
                        parent.getChildren().remove(notificationContainer);
                    }
                });
                slideOut.play();
            });
            delay.play();
        });
    }
}
