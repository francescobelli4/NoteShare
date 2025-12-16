package views;

import graphics_controllers.GraphicsController;
import graphics_controllers.HomeViewController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import locales.Locales;
import views.components.ButtonWrapper;
import views.components.GridPaneWrapper;
import views.components.ImageViewWrapper;
import views.components.TextFieldWrapper;

import java.util.Objects;

public class HomeView implements View {

    private VBox leftBarSlot;
    private TextField searchBar;
    private Button notificationButton;

    private final GraphicsController<HomeView> graphicsController;
    private StackPane root;

    public HomeView() {

        graphicsController = new HomeViewController(this);
        init();
    }

    @Override
    public void init() {

        setUpRoot();

        GridPane gridPane = new GridPaneWrapper(new float[]{25.0f, 75.0f}, new float[]{100.0f});

        assert root != null;

        GridPane rightSection = new GridPaneWrapper(new float[]{100.0f}, new float[]{15.0f, 10.0f, 75.0f});
        rightSection.setId("rightsection");
        rightSection.setEffect(new InnerShadow());
        gridPane.add(rightSection, 1, 0);

        VBox leftSection = new VBox();
        leftSection.setPadding(new Insets(ViewNavigator.scaleValue(30)));
        leftSection.setAlignment(Pos.TOP_CENTER);
        ImageView leftBarTopIcon = new ImageViewWrapper(Icon.APPICON.getPath(), ViewNavigator.scaleValue(150), ViewNavigator.scaleValue(200));
        leftBarSlot = new VBox();
        leftBarSlot.prefHeight(ViewNavigator.scaleValue(200));
        VBox.setVgrow(leftBarSlot, Priority.ALWAYS);
        leftBarSlot.setEffect(new DropShadow());
        leftSection.getChildren().addAll(leftBarTopIcon, leftBarSlot);
        gridPane.add(leftSection, 0, 0);

        VBox topBarContainer = new VBox();
        rightSection.add(topBarContainer, 0, 0);
        HBox topBar = new HBox();
        VBox.setVgrow(topBar, Priority.ALWAYS);
        topBar.setId("topbar");
        topBarContainer.getChildren().add(topBar);
        searchBar = new TextFieldWrapper(Locales.get("search_for_a_note"), ViewNavigator.scaleValue(35));
        searchBar.setId("searchbar");
        HBox.setHgrow(searchBar, Priority.ALWAYS);
        notificationButton = new ButtonWrapper(new ImageViewWrapper(Icon.NOTIFICATION.getPath(), ViewNavigator.scaleValue(100), ViewNavigator.scaleValue(50)));
        topBar.getChildren().addAll(searchBar, notificationButton);

        root.getChildren().add(gridPane);
        graphicsController.setup();
    }

    @Override
    public void update() {
        //Not needed...
    }

    private void setUpRoot() {
        root = new StackPane();

        root.getStyleClass().add("root");
        root.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/colored/styles/HomePage.css")).toExternalForm());
    }

    public void setLeftBar(Parent node) {
        leftBarSlot.getChildren().clear();
        leftBarSlot.getChildren().add(node);
    }

    public Parent getRoot() {
        return root;
    }
}
