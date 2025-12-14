package views;

import graphics_controllers.GraphicsController;
import graphics_controllers.HomeViewController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import views.components.GridPaneWrapper;
import views.components.ImageViewWrapper;

import java.util.Objects;

public class HomeView implements View {

    private VBox leftBarSlot;

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

        HBox topBar = new HBox();
        topBar.setId("topbar");
        rightSection.add(topBar, 0, 0);

        gridPane.add(leftSection, 0, 0);

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
