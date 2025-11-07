package app.mvc.navigation;

import app.mvc.Controller;
import app.mvc.models.FolderModel;
import app.mvc.models.UserModel;

public class NavigationController extends Controller {

    public void goToFolder(FolderModel folderModel) {
        UserModel.getInstance().setActiveFolder(folderModel);
        getBoundary().notifyActiveFolderUpdated();
    }

    public void goBack() {
        UserModel user = UserModel.getInstance();

        user.setActiveFolder(user.getActiveFolder().getParentFolder());
        getBoundary().notifyActiveFolderUpdated();
    }

    public void goToChild(String name) {
        UserModel user = UserModel.getInstance();

        if (user.getActiveFolder().getParentFolder() == null)
            return;

        user.setActiveFolder(user.getActiveFolder().searchSubFolder(name));
        getBoundary().notifyActiveFolderUpdated();
    }

    @Override
    protected NavigationBoundary getBoundary() {
        return (NavigationBoundary) boundary;
    }
}
