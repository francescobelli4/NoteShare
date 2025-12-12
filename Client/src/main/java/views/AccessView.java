package views;

import graphics_controllers.GraphicsController;
import graphics_controllers.AccessViewController;
import javafx.scene.Parent;
import javafx.scene.layout.*;
import views.components.GridPaneWrapper;

import java.util.Objects;

public class AccessView implements View {

    private VBox secondaryPageSlot;

    private final GraphicsController<AccessView> graphicsController;
    private StackPane root;

    public AccessView() {

        graphicsController = new AccessViewController(this);
        init();
    }

    @Override
    public void init() {

        setUpRoot();

        GridPane gridPane = new GridPaneWrapper(new float[]{30.0f, 40.0f, 30.0f}, new float[]{15.0f, 70.0f, 15.0f});

        assert root != null;
        root.getChildren().add(gridPane);
        secondaryPageSlot = new VBox();
        gridPane.add(secondaryPageSlot, 1, 1);

        graphicsController.setup();
    }

    @Override
    public void update() {
        //Not needed...
    }

    private void setUpRoot() {
        root = new StackPane();

        root.getStyleClass().add("root");
        root.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/colored/styles/AccessPage.css")).toExternalForm());
        root.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/colored/styles/AccessForm.css")).toExternalForm());
    }

    public void setContent(Parent node) {
        secondaryPageSlot.getChildren().clear();
        secondaryPageSlot.getChildren().add(node);
    }

    public Parent getRoot() {
        return root;
    }
}
