package views.home.folders_container;

import graphics_controllers.GraphicsController;
import graphics_controllers.home.folders_container.FoldersContainerElementOptionsFormViewController;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import models.Controllable;
import utils.Utils;
import views.Page;
import views.View;

public class FoldersContainerElementOptionsFormView implements View {

    @FXML
    private AnchorPane root;
    @FXML
    private Button copyButton;
    @FXML
    private Button deleteButton;
    @FXML
    private VBox optionsContainer;

    private final double x;
    private final double y;

    private static final Page page = Page.FOLDERS_CONTAINER_ELEMENT_OPTIONS_FORM;
    private final GraphicsController<FoldersContainerElementOptionsFormView> graphicsController;

    public FoldersContainerElementOptionsFormView(Controllable element, double x, double y) {
        graphicsController = new FoldersContainerElementOptionsFormViewController(this, element);
        this.x = x;
        this.y = y;
        init();
    }


    @Override
    public void init() {
        Utils.scaleFonts(root);

        AnchorPane.setLeftAnchor(optionsContainer, x);
        AnchorPane.setTopAnchor(optionsContainer, y);
    }

    @Override
    public void close() {
        ((StackPane)root.getParent()).getChildren().remove(root);
    }

    public Button getCopyButton() {
        return copyButton;
    }

    public Button getDeleteButton() {
        return deleteButton;
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
    public GraphicsController<FoldersContainerElementOptionsFormView> getGraphicsController() {
        return graphicsController;
    }
}
