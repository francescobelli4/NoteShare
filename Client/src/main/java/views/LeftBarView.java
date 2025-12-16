package views;

import graphics_controllers.GraphicsController;
import graphics_controllers.LeftBarViewController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import locales.Locales;
import models.StudentUserModel;
import sessions.UserSession;
import views.components.ButtonWrapper;
import views.components.ImageViewWrapper;
import views.components.LabelWrapper;

import java.util.Objects;

public class LeftBarView implements View {

    private VBox menuOptionsContainer;
    private Button yourNotesButton;
    private Button browseNotesButton;
    private Button sharedNotesButton;

    private VBox bottomContainer;
    private Button accessButton;
    private ImageView userImage;
    private Label usernameLabel;
    private Label coinsLabel;


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

        menuOptionsContainer = new VBox();
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

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        bottomContainer = new VBox();
        bottomContainer.setAlignment(Pos.CENTER);

        root.getChildren().addAll(menuOptionsContainer, spacer, bottomContainer);

        graphicsController.setup();
    }

    @Override
    public void update() {
        //Not needed...
    }

    public void setAccessComponent() {
        bottomContainer.getChildren().clear();

        accessButton = new ButtonWrapper(Locales.get("access"), ViewNavigator.scaleValue(30), new Insets(ViewNavigator.scaleValue(20), 0, 0, 0));
        accessButton.getStyleClass().addAll("leftbar_button", "selected");
        accessButton.setMaxWidth(Double.MAX_VALUE);
        accessButton.setPrefWidth(Double.MAX_VALUE);
        bottomContainer.getChildren().add(accessButton);
    }

    public void setUserDataComponent() {
        bottomContainer.getChildren().clear();

        userImage = new ImageViewWrapper(Icon.USER.getPath(), ViewNavigator.scaleValue(200), ViewNavigator.scaleValue(150));
        usernameLabel = new LabelWrapper(UserSession.getInstance().getCurrentUser().getUsername(), ViewNavigator.scaleValue(40));
        HBox coinsRow = new HBox();
        coinsRow.setAlignment(Pos.CENTER);
        ImageView coinsImage = new ImageViewWrapper(Icon.COIN.getPath(), ViewNavigator.scaleValue(40), ViewNavigator.scaleValue(40));
        coinsLabel = new LabelWrapper(UserSession.getInstance().getCurrentUserAs(StudentUserModel.class).getCoins() + "", ViewNavigator.scaleValue(30));
        coinsRow.getChildren().addAll(coinsImage, coinsLabel);

        bottomContainer.getChildren().addAll(userImage, usernameLabel, coinsRow);
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

    public Button getAccessButton() {
        return accessButton;
    }

    public ImageView getUserImage() {
        return userImage;
    }

    public Label getCoinsLabel() {
        return coinsLabel;
    }

    public Label getUsernameLabel() {
        return usernameLabel;
    }
}
