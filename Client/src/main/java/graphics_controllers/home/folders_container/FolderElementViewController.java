package graphics_controllers.home.folders_container;

import app_controllers.FoldersController;
import graphics_controllers.GraphicsController;
import models.folder.FolderModel;
import views.home.folders_container.FolderElementView;

public class FolderElementViewController extends GraphicsController<FolderElementView> {

    private final FolderModel folder;

    public FolderElementViewController(FolderElementView view, FolderModel folder) {
        super(view);

        this.folder = folder;
        setupUI();
    }

    @Override
    public void loaded() {

        getView().getRoot().setOnMouseClicked(_ -> folderClicked());
    }

    private void setupUI() {
        getView().getFolderLabel().setText(folder.getName());
    }

    private void folderClicked() {
        FoldersController.goToFolder(folder);
    }
}
