package views.home.folders_container;

import app.AppContext;
import graphics_controllers.GraphicsController;
import graphics_controllers.home.folders_container.FoldersContainerViewController;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import models.folder.FolderModel;
import models.user.UserModel;
import utils.Utils;
import views.Page;
import views.View;

import java.util.List;

public class FoldersContainerView implements View, FolderModel.Listener, UserModel.ActiveFolderListener, UserModel.SearchQueryListener {

    @FXML
    private VBox root;
    @FXML
    private FlowPane foldersContainer;

    private static final Page page = Page.FOLDERS_CONTAINER;
    private final FoldersContainerViewController graphicsController;

    public FoldersContainerView() {
        graphicsController = new FoldersContainerViewController(this);
        init();
    }

    @Override
    public void init() {
        Utils.scaleFonts(root);
        AppContext.getInstance().getCurrentUser().getActiveFolder().addListener(this);
        AppContext.getInstance().getCurrentUser().addUserActiveFolderListener(this);
        AppContext.getInstance().getCurrentUser().addUserSearchQueryListener(this);

        graphicsController.updateDisplay();
    }

    @Override
    public void close() {
        //Nothing to do...
    }

    public void displayElements(List<Node> folders, List<Node> notes) {
        foldersContainer.getChildren().clear();
        for (Node f : folders) {
            foldersContainer.getChildren().add(f);
        }
        for (Node n : notes) {
            foldersContainer.getChildren().add(n);
        }
    }

    @Override
    public void activeFolderSet(FolderModel folder) {
        folder.addListener(this);
        graphicsController.updateDisplay(folder);
    }

    @Override
    public void folderUpdated() {
        graphicsController.updateDisplay();
    }

    @Override
    public void queryUpdated(String query) {
        graphicsController.updateDisplay(query);
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
    public GraphicsController<FoldersContainerView> getGraphicsController() {
        return graphicsController;
    }
}
