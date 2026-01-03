package daos.folder;

import models.folder.FolderModel;
import models.user.UserModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TestNPFolderDAO {

    private static FolderDAO folderDAO;
    private static FolderModel rootFolder;
    private static UserModel mockedUser;

    @BeforeAll
    static void setDAO() {
        folderDAO = new NPFolderDAO();
        rootFolder = new FolderModel("NoteShare");
        mockedUser = mock(UserModel.class);
        when(mockedUser.getUsername()).thenReturn("TMO");
    }

    @Test
    void getRootFolder() {
        FolderModel folderModel = folderDAO.getRootFolder();
        assertEquals("NoteShare", folderModel.getName());
        assertTrue(folderModel.isRoot());
    }

    @Test
    void getUserFolder() {
        FolderModel folderModel = folderDAO.getUserFolder(mockedUser);
        assertEquals("TMO", folderModel.getName());
    }

    @Test
    void save() {
        FolderModel folderModel = new FolderModel(":D");
        folderDAO.save(folderModel, rootFolder);
        assertNotNull(rootFolder.searchSubFolder(":D"));
    }

    @Test
    void searchByPath() {
        FolderModel folderModel = new FolderModel(":D");
        folderDAO.save(folderModel, rootFolder);
        assertNotNull(folderDAO.searchByPath(":D", rootFolder));
    }
}