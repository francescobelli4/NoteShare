package daos.folder;


import models.folder.FolderModel;
import models.user.UserModel;

public class PFolderDAO implements FolderDAO {

    @Override
    public FolderModel getRootFolder() {
        return null;
    }

    @Override
    public FolderModel getUserFolder(UserModel user) {
        return null;
    }

    @Override
    public void save(FolderModel folder, FolderModel parentFolder) {
        //TODO
    }

    @Override
    public FolderModel searchByPath(String path, FolderModel parentFolder) {
        return null;
    }
}
