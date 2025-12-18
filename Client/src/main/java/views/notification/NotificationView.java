package views.notification;

import graphics_controllers.GraphicsController;
import graphics_controllers.notification.NotificationViewController;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import views.Icon;
import views.Page;
import views.View;
import views.ViewNavigator;

import java.util.Objects;

public class NotificationView implements View {

    private final String title;
    private final String description;
    private final Icon icon;

    @FXML
    private HBox root;
    @FXML
    private ImageView image;
    @FXML
    private Label titleLabel;
    @FXML
    private Label descriptionLabel;


    private static final Page page = Page.NOTIFICATION;
    private final GraphicsController<NotificationView> graphicsController;

    public NotificationView(String title, String description, Icon icon) {

        this.title = title;
        this.description = description;
        this.icon = icon;

        graphicsController = new NotificationViewController(this);
        init();
    }

    @Override
    public void init() {

        titleLabel.setText(title);
        descriptionLabel.setText(description);
        image.setImage(new Image(Objects.requireNonNull(getClass().getResource(icon.getPath())).toExternalForm()));

        root.heightProperty().addListener((_, _, newH) -> {
            if (newH.doubleValue() > 0) {
                animate();
            }
        });

        animate();
    }

    private void animate() {
        StackPane parent = (StackPane) ViewNavigator.getActiveView().getRoot();
        StackPane.setAlignment(root, Pos.TOP_CENTER);

        root.setTranslateY(-root.getHeight());

        TranslateTransition slideIn = new TranslateTransition(Duration.millis(500), root);
        slideIn.setFromY(-root.getHeight());
        slideIn.setToY(ViewNavigator.getStage().getHeight()/25);
        slideIn.play();

        slideIn.setOnFinished(_ -> {

            PauseTransition delay = new PauseTransition(Duration.seconds(2));
            delay.setOnFinished(_ -> {

                TranslateTransition slideOut = new TranslateTransition(Duration.millis(500), root);
                slideOut.setFromY(ViewNavigator.getStage().getHeight()/25);
                slideOut.setToY(-root.getHeight());

                slideOut.setOnFinished(_ -> parent.getChildren().remove(root));
                slideOut.play();
            });
            delay.play();
        });
    }

    @Override
    public Parent getRoot() {
        return root;
    }

    @Override
    public Page getPage() {
        return page;
    }

    @Override
    public GraphicsController<NotificationView> getGraphicsController() {
        return graphicsController;
    }

    @Override
    public void update() {
        //Not needed...
    }
}
