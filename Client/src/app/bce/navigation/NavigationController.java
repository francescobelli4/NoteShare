package app.bce.navigation;

import app.bce.Controller;
import app.bce.entities.UserModel;
import persistency.shared.Folder;

public class NavigationController extends Controller {

    public void goToFolder(Folder folder) {
        UserModel.getInstance().setActiveFolder(folder);
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
