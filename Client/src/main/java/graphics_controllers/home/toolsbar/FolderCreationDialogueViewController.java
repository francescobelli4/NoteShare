package graphics_controllers.home.toolsbar;

import app.AppContext;
import app_controllers.FoldersController;
import exceptions.DuplicateFolderException;
import exceptions.InvalidFolderNameException;
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

        try {
            FoldersController.addSubFolder(getView().getFolderNameTextField().getText(), AppContext.getInstance().getCurrentUser().getActiveFolder());
            getView().close();
        } catch (DuplicateFolderException _) {
            ViewNavigator.displayNotification(title, Locales.get("folder_already_exists"), icon);
        } catch (InvalidFolderNameException e) {
            ViewNavigator.displayNotification(title, Locales.get(e.getMessage()), icon);
        }
    }
}
