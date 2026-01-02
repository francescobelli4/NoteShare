package views.home.folders_container;

import graphics_controllers.GraphicsController;
import graphics_controllers.home.folders_container.FolderElementViewController;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import models.folder.FolderModel;
import utils.Utils;
import views.Page;
import views.View;

public class FolderElementView implements View {

    @FXML
    private VBox root;
    @FXML
    private Label folderLabel;
    @FXML
    private Button optionsButton;

    private static final Page page = Page.FOLDER_ELEMENT;
    private final GraphicsController<FolderElementView> graphicsController;

    public FolderElementView(FolderModel folder) {
        graphicsController = new FolderElementViewController(this, folder);
        init();
    }

    @Override
    public void init() {
        Utils.scaleFonts(root);
    }

    @Override
    public void close() {
        //Nothing to do...
    }

    @Override
    public Parent getRoot() {
        return root;
    }

    @Override
    public Page getPage() {
        return page;
    }

    public Button getOptionsButton() {
        return optionsButton;
    }

    public Label getFolderLabel() {
        return folderLabel;
    }

    @Override
    public GraphicsController<FolderElementView> getGraphicsController() {
        return graphicsController;
    }
}
