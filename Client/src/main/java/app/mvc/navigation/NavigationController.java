package app.mvc.navigation;

import app.mvc.Controller;
import app.mvc.models.FolderModel;
import app.mvc.models.UserModel;

/**
 * This controller should implement all the logic needed to complete the "Navigate" use case.
 * It should allow the user to navigate in his folders.
 */
public class NavigationController extends Controller {

    /**
     * This function should update the user's active folder to the specified folder
     * @param folderModel the target folder
     */
    public void goToFolder(FolderModel folderModel) {
        UserModel.getInstance().setActiveFolder(folderModel);
        getBoundary().notifyActiveFolderUpdated();
    }

    /**
     * This function should go to the active folder's parent
     */
    public void goBack() {
        UserModel user = UserModel.getInstance();

        user.setActiveFolder(user.getActiveFolder().getParentFolder());
        getBoundary().notifyActiveFolderUpdated();
    }

    /**
     * This function should return the correct Boundary subclass for this controller
     * @return a NavigationBoundary reference
     */
    @Override
    protected NavigationBoundary getBoundary() {
        return (NavigationBoundary) boundary;
    }
}
