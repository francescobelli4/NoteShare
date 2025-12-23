package views.home.folders_container;

import app.App;
import app.AppContext;
import graphics_controllers.GraphicsController;
import graphics_controllers.home.folders_container.FoldersContainerViewController;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import models.folder.FolderModel;
import models.note.NoteModel;
import models.user.UserModel;
import utils.Utils;
import views.Page;
import views.View;
import views.ViewFactory;

public class FoldersContainerView implements View, FolderModel.Listener, UserModel.ActiveFolderListener {

    @FXML
    private VBox root;
    @FXML
    private FlowPane foldersContainer;

    private static final Page page = Page.FOLDERS_CONTAINER;
    private final GraphicsController<FoldersContainerView> graphicsController;

    public FoldersContainerView() {
        graphicsController = new FoldersContainerViewController(this);
        init();
    }

    @Override
    public void init() {
        Utils.scaleFonts(root);
        AppContext.getInstance().getCurrentUser().getActiveFolder().addListener(this);
        AppContext.getInstance().getCurrentUser().addUserActiveFolderListener(this);

        displayActiveFolder();
    }

    @Override
    public void close() {
        //Nothing to do...
    }

    private void displayActiveFolder() {
        foldersContainer.getChildren().clear();
        for (FolderModel f : AppContext.getInstance().getCurrentUser().getActiveFolder().getSubFolders()) {
            appendFolder(f);
        }

        for (NoteModel n : AppContext.getInstance().getCurrentUser().getActiveFolder().getNotes()) {
            appendNote(n);
        }
    }

    public void appendFolder(FolderModel folder) {
        foldersContainer.getChildren().add(ViewFactory.getInstance().createFolderElementView(folder).getRoot());
    }

    public void appendNote(NoteModel note) {
        foldersContainer.getChildren().add(ViewFactory.getInstance().createNoteElementView(note).getRoot());
    }

    @Override
    public void activeFolderSet(FolderModel folder) {
        folder.addListener(this);
        displayActiveFolder();
    }

    @Override
    public void folderUpdated() {
        displayActiveFolder();
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
