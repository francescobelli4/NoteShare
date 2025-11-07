package persistency.shared.daos;

import app.mvc.models.FolderModel;

public interface FolderDAO {

    FolderModel getRootFolder();
    void save(FolderModel folder, FolderModel parentFolder);
    FolderModel searchByPath(String path, FolderModel parentFolder);
    FolderModel searchByPath(String path);
}
