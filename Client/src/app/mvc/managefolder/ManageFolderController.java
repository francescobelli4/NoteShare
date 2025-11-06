package app.mvc.managefolder;

import app.App;
import app.mvc.Controller;
import app.mvc.models.FolderModel;
import app.mvc.models.UserModel;

public class ManageFolderController extends Controller {

    @Override
    protected ManageFolderBoundary getBoundary() {
        return (ManageFolderBoundary) boundary;
    }

    public FolderModel createFolder(String name) {

        if (name == null || name.isEmpty()) {
            getBoundary().handleFolderCreationFailed(ManageFolderResult.FOLDER_NAME_NOT_SPECIFIED);
        }

        return new FolderModel(name);
    }

    public void saveFolder(FolderModel folder, FolderModel parentFolder) {

        if (App.getFolderDAO().searchByPath(folder.getName(), parentFolder) != null) {
            getBoundary().handleFolderCreationFailed(ManageFolderResult.FOLDER_ALREADY_EXISTS);
            return;
        }

        App.getFolderDAO().save(folder, parentFolder);
        getBoundary().handleFolderSaved(folder);
    }
}
