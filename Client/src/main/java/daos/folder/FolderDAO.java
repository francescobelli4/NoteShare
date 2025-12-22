package daos.folder;

import models.folder.FolderModel;
import models.user.UserModel;

public interface FolderDAO {

    FolderModel getRootFolder();
    FolderModel getUserFolder(UserModel user);
    void save(FolderModel folder, FolderModel parentFolder);
    FolderModel searchByPath(String path, FolderModel parentFolder);
    FolderModel searchByPath(String path);
}
