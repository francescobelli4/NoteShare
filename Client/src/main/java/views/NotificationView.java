package views;

import graphics_controllers.GraphicsController;
import graphics_controllers.NotificationController;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Duration;
import views.components.ImageViewWrapper;
import views.components.LabelWrapper;

import java.util.Objects;


public class NotificationView implements View {

    private final String title;
    private final String description;
    private final Icon icon;

    private final GraphicsController<NotificationView> graphicsController;
    private HBox root;

    public NotificationView(String title, String description, Icon icon) {
        this.title = title;
        this.description = description;
        this.icon = icon;

        graphicsController = new NotificationController(this);
        init();
    }

    @Override
    public void init() {

        root = new HBox();

        root.getStyleClass().add("root");
        root.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/colored/styles/Notification.css")).toExternalForm());
        assert root != null;
        root.setId("notification_container");
        root.setAlignment(Pos.TOP_CENTER);
        root.setSpacing(ViewNavigator.scaleValue(10));
        root.setMinSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        root.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        root.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

        StackPane.setAlignment(root, Pos.TOP_CENTER);

        ImageView imageView = new ImageViewWrapper(icon.getPath(), ViewNavigator.scaleValue(150), ViewNavigator.scaleValue(200));

        VBox dataContainer = new VBox();
        dataContainer.setPrefWidth(ViewNavigator.scaleValue(500));
        dataContainer.setMaxWidth(ViewNavigator.scaleValue(500));
        HBox.setHgrow(dataContainer, Priority.ALWAYS);

        Label titleLabel = new LabelWrapper(title, ViewNavigator.scaleValue(40), new Insets(0, 0, ViewNavigator.scaleValue(10), 0));
        Label descriptionLabel = new LabelWrapper(description, ViewNavigator.scaleValue(25));
        descriptionLabel.setWrapText(true);

        dataContainer.getChildren().addAll(titleLabel, descriptionLabel);
        root.getChildren().addAll(imageView, dataContainer);

        root.heightProperty().addListener((_, _, newVal) -> {
            if (newVal.doubleValue() > 0) {
                root.setTranslateY(-newVal.doubleValue());

                animate(newVal.doubleValue());
            }
        });

        graphicsController.setup();
    }

    @Override
    public void update() {
        //Nothing to do...
    }

    private void animate(double containerHeight) {
        TranslateTransition slideDown = new TranslateTransition(Duration.millis(400), root);
        slideDown.setToY(0);
        slideDown.play();

        slideDown.setOnFinished(_ -> {

            PauseTransition delay = new PauseTransition(Duration.seconds(2));
            delay.play();

            delay.setOnFinished(_ -> {
                TranslateTransition slideUp = new TranslateTransition(Duration.millis(400), root);
                slideUp.setToY(-containerHeight);
                slideUp.play();

                slideUp.setOnFinished(_ -> ViewNavigator.getInstance().getMainLayout().getChildren().remove(root));
            });
        });
    }

    public HBox getRoot() {
        return root;
    }
}
