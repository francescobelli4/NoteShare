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

        String title = Locales.get("error");
        Icon icon = Icon.ERROR;

        if (getView().getFolderNameTextField().getText().isEmpty()) {
            ViewNavigator.displayNotification(title, Locales.get("folder_name_too_short"), icon);
            return;
        }

        if (getView().getFolderNameTextField().getText().length() > 15) {
            ViewNavigator.displayNotification(title, Locales.get("folder_name_too_long"), icon);
            return;
        }

        if (App.getUser().getActiveFolder().searchSubFolder(getView().getFolderNameTextField().getText()) != null) {
            ViewNavigator.displayNotification(title, Locales.get("folder_already_exists"), icon);
            return;
        }

        FoldersController.addSubFolder(getView().getFolderNameTextField().getText(), App.getUser().getActiveFolder());
        getView().close();
    }
}
