package views;

import graphics_controllers.GraphicsController;
import graphics_controllers.LeftBarViewController;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import locales.Locales;
import views.components.ButtonWrapper;
import views.components.ImageViewWrapper;

import java.awt.*;
import java.util.Objects;

public class LeftBarView implements View {

    private Button yourNotesButton;
    private Button browseNotesButton;
    private Button sharedNotesButton;

    private final GraphicsController<LeftBarView> graphicsController;
    private VBox root;

    public LeftBarView() {

        graphicsController = new LeftBarViewController(this);
        init();
    }

    @Override
    public void init() {

        root = new VBox();
        root.setId("leftbar");
        root.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/colored/styles/StudentHomePageForm.css")).toExternalForm());

        VBox.setVgrow(root, Priority.ALWAYS);
        assert root != null;

        VBox menuOptionsContainer = new VBox();
        VBox.setVgrow(menuOptionsContainer, Priority.ALWAYS);

        yourNotesButton = new ButtonWrapper(new ImageViewWrapper(Icon.NOTE_PAD.getPath(), ViewNavigator.scaleValue(50), ViewNavigator.scaleValue(50)), Locales.get("your_notes"), ViewNavigator.scaleValue(30));
        yourNotesButton.getStyleClass().addAll("leftbar_button", "selected");
        yourNotesButton.setMaxWidth(Double.MAX_VALUE);
        yourNotesButton.setPrefWidth(Double.MAX_VALUE);

        browseNotesButton = new ButtonWrapper(new ImageViewWrapper(Icon.WEB.getPath(), ViewNavigator.scaleValue(50), ViewNavigator.scaleValue(50)), Locales.get("browse_notes"), ViewNavigator.scaleValue(30), new Insets(ViewNavigator.scaleValue(20), 0, 0, 0));
        browseNotesButton.getStyleClass().addAll("leftbar_button");
        browseNotesButton.setMaxWidth(Double.MAX_VALUE);
        browseNotesButton.setPrefWidth(Double.MAX_VALUE);

        sharedNotesButton = new ButtonWrapper(new ImageViewWrapper(Icon.TEACHER.getPath(), ViewNavigator.scaleValue(50), ViewNavigator.scaleValue(50)), Locales.get("shared_notes"), ViewNavigator.scaleValue(30), new Insets(ViewNavigator.scaleValue(20), 0, 0, 0));
        sharedNotesButton.getStyleClass().addAll("leftbar_button");
        sharedNotesButton.setMaxWidth(Double.MAX_VALUE);
        sharedNotesButton.setPrefWidth(Double.MAX_VALUE);

        menuOptionsContainer.getChildren().addAll(yourNotesButton, browseNotesButton, sharedNotesButton);

        javafx.scene.layout.Region spacer = new javafx.scene.layout.Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        Button accessButton = new ButtonWrapper(Locales.get("access"), ViewNavigator.scaleValue(30), new Insets(ViewNavigator.scaleValue(20), 0, 0, 0));
        accessButton.getStyleClass().addAll("leftbar_button", "selected");
        accessButton.setMaxWidth(Double.MAX_VALUE);
        accessButton.setPrefWidth(Double.MAX_VALUE);

        root.getChildren().addAll(menuOptionsContainer, spacer, accessButton);
        graphicsController.setup();
    }

    @Override
    public void update() {
        //Not needed...
    }

    public VBox getRoot() {
        return root;
    }

    public Button getBrowseNotesButton() {
        return browseNotesButton;
    }

    public Button getSharedNotesButton() {
        return sharedNotesButton;
    }

    public Button getYourNotesButton() {
        return yourNotesButton;
    }
}
