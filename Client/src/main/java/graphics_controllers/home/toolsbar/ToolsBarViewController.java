package graphics_controllers.home.toolsbar;

import app_controllers.FoldersController;
import exceptions.DuplicateFolderException;
import exceptions.DuplicateNoteException;
import graphics_controllers.GraphicsController;
import locales.Locales;
import views.Icon;
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
        getView().getPasteButton().setOnMouseClicked(_ -> pasteButtonClicked());
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

    private void pasteButtonClicked() {

        String title = Locales.get("error");
        Icon icon = Icon.ERROR;

        try {
            FoldersController.pasteCopiedElement();
            getView().close();
        } catch (DuplicateFolderException _) {
            ViewNavigator.displayNotification(title, Locales.get("folder_already_exists"), icon);
        } catch (DuplicateNoteException _) {
            ViewNavigator.displayNotification(title, Locales.get("note_already_exists"), icon);
        }
    }
}
