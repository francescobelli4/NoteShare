package daos.folder;


import models.folder.FolderModel;
import models.user.UserModel;

public class NPFolderDAO implements FolderDAO {

    @Override
    public FolderModel getRootFolder() {
        return new FolderModel("NoteShare");
    }

    @Override
    public FolderModel getUserFolder(UserModel user) {
        return new FolderModel(user.getUsername());
    }

    @Override
    public void save(FolderModel folder, FolderModel parentFolder) {
        parentFolder.addSubFolder(folder);
    }

    @Override
    public FolderModel searchByPath(String path, FolderModel parentFolder) {
        return parentFolder.searchSubFolder(path);
    }

    @Override
    public FolderModel searchByPath(String path) {
        return getRootFolder().searchSubFolder(path);
    }
}
