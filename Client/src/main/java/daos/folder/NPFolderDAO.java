package daos.folder;


import app.App;
import communication.dtos.user.UserType;
import models.folder.FolderModel;
import models.user.StudentUserModel;

public class NPFolderDAO implements FolderDAO {

    @Override
    public FolderModel getRootFolder() {

        if (App.getUser().getUserType() == UserType.STUDENT) {

            StudentUserModel studentUserModel = App.getUserAs(StudentUserModel.class);

            if (studentUserModel.getRootFolder() == null) {
                studentUserModel.setRootFolder(new FolderModel("NoteShare"));
            }

            return studentUserModel.getRootFolder();
        }

        return null;
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
