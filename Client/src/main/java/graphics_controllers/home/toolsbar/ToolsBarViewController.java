package graphics_controllers.home.toolsbar;

import app_controllers.FoldersController;
import graphics_controllers.GraphicsController;
import views.ViewNavigator;
import views.home.toolsbar.ToolsBarView;

public class ToolsBarViewController extends GraphicsController<ToolsBarView> {

    public ToolsBarViewController(ToolsBarView view) {
        super(view);
    }

    @Override
    public void loaded() {
        getView().getBackButton().setOnMouseClicked(_ -> backButtonClicked());
        getView().getFolderAddButton().setOnMouseClicked(_ -> folderAddButtonClicked());
        getView().getNoteAddButton().setOnMouseClicked(_ -> noteAddButtonClicked());
    }

    private void backButtonClicked() {
        FoldersController.goToParentFolder();
    }

    private void folderAddButtonClicked() {
        ViewNavigator.displayFolderCreationDialogueView();
    }

    private void noteAddButtonClicked() {
        ViewNavigator.displayNoteCreationDialogueView();
    }
}
