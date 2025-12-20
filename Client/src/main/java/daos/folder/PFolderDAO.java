package daos.folder;


import models.folder.FolderModel;

public class PFolderDAO implements FolderDAO {

    @Override
    public FolderModel getRootFolder() {
        return null;
    }

    @Override
    public void save(FolderModel folder, FolderModel parentFolder) {

    }

    @Override
    public FolderModel searchByPath(String path, FolderModel parentFolder) {
        return null;
    }

    @Override
    public FolderModel searchByPath(String path) {
        return null;
    }
}
