package app.mvc.managefolder;

import app.App;
import app.mvc.Controller;
import app.mvc.models.FolderModel;
import app.mvc.models.UserModel;

/**
 * This class should implement all the logic needed to complete the "Manage Note" use case.
 */
public class ManageFolderController extends Controller {

    /**
     * This folder should implement the logic needed to create a folder (without saving it).
     * It should also check the folder's name
     * @param name the provided name for that folder
     * @return a new FolderModel
     */
    public FolderModel createFolder(String name) {

        if (name == null || name.isEmpty()) {
            getBoundary().onFolderCreationFailed(ManageFolderResult.FOLDER_NAME_NOT_SPECIFIED);
        }

        return new FolderModel(name);
    }

    /**
     * This function should actually save the folder using the FolderDAO, which will
     * decide the correct saving method (persistent or non-persistent).
     * It should also notify the listeners.
     * @param folder the folder that needs to be saved
     * @param parentFolder the folder that contains that folder
     */
    public void saveFolder(FolderModel folder, FolderModel parentFolder) {

        if (App.getFolderDAO().searchByPath(folder.getName(), parentFolder) != null) {
            getBoundary().onFolderCreationFailed(ManageFolderResult.FOLDER_ALREADY_EXISTS);
            return;
        }

        App.getFolderDAO().save(folder, parentFolder);
        getBoundary().onFolderSaved(folder);
    }

    /**
     * This function should return the correct Boundary subclass for this controller
     * @return a ManageFolderBoundary reference
     */
    @Override
    protected ManageFolderBoundary getBoundary() {
        return (ManageFolderBoundary) boundary;
    }
}
