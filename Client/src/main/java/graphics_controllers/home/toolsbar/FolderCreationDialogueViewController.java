package graphics_controllers.home.toolsbar;

import app.App;
import app_controllers.FoldersController;
import graphics_controllers.GraphicsController;
import locales.Locales;
import views.Icon;
import views.ViewNavigator;
import views.home.toolsbar.FolderCreationDialogueView;

public class FolderCreationDialogueViewController extends GraphicsController<FolderCreationDialogueView> {

    public FolderCreationDialogueViewController(FolderCreationDialogueView view) {
        super(view);
    }

    @Override
    public void loaded() {
        getView().getCloseButton().setOnMouseClicked(_ -> getView().close());
        getView().getCreateFolderButton().setOnMouseClicked(_ -> createFolderButtonClicked());
    }

    private void createFolderButtonClicked() {

        if (getView().getFolderNameTextField().getText().isEmpty()) {
            ViewNavigator.displayNotification(Locales.get("error"), Locales.get("folder_name_too_short"), Icon.ERROR);
            return;
        }

        if (getView().getFolderNameTextField().getText().length() > 15) {
            ViewNavigator.displayNotification(Locales.get("error"), Locales.get("folder_name_too_long"), Icon.ERROR);
            return;
        }

        if (App.getUser().getActiveFolder().searchSubFolder(getView().getFolderNameTextField().getText()) != null) {
            ViewNavigator.displayNotification(Locales.get("error"), Locales.get("folder_already_exists"), Icon.ERROR);
            return;
        }

        FoldersController.addSubFolder(getView().getFolderNameTextField().getText(), App.getUser().getActiveFolder());
        getView().close();
    }
}
