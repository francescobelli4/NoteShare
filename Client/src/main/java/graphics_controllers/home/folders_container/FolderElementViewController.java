package graphics_controllers.home.folders_container;

import app_controllers.FoldersController;
import graphics_controllers.GraphicsController;
import views.home.folders_container.FolderElementView;

public class FolderElementViewController extends GraphicsController<FolderElementView> {

    public FolderElementViewController(FolderElementView view) {
        super(view);
    }

    @Override
    public void loaded() {
        getView().getRoot().setOnMouseClicked(_ -> folderClicked());
    }

    private void folderClicked() {
        FoldersController.goToFolder(getView().getFolder());
    }
}
