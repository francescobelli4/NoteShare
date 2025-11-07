package persistency.nonpersistent.daos;

import app.mvc.models.FolderModel;
import app.mvc.models.UserModel;
import persistency.shared.daos.FolderDAO;

public class NPFolderDAO implements FolderDAO {

    @Override
    public FolderModel getRootFolder() {
        UserModel user = UserModel.getInstance();
        if (user.getRootFolder() == null) {
            user.setRootFolder(new FolderModel("NoteShare"));
        }
        return user.getRootFolder();
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
