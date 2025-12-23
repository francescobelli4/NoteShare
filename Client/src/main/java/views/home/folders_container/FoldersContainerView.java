package views.home.folders_container;

import app.App;
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
        App.getUser().getActiveFolder().addListener(this);
        App.getUser().addUserActiveFolderListener(this);

        displayActiveFolder();
    }

    @Override
    public void close() {
        //Nothing to do...
    }

    private void displayActiveFolder() {
        foldersContainer.getChildren().clear();
        for (FolderModel f : App.getUser().getActiveFolder().getSubFolders()) {
            appendFolder(f);
        }

        for (NoteModel n : App.getUser().getActiveFolder().getNotes()) {
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
