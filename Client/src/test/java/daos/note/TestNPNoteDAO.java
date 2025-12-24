package daos.note;

import daos.folder.FolderDAO;
import daos.folder.NPFolderDAO;
import models.folder.FolderModel;
import models.note.NoteModel;
import models.user.UserModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TestNPNoteDAO {

    private static FolderDAO folderDAO;
    private static NoteDAO noteDAO;
    private static FolderModel rootFolder;
    private static UserModel mockedUser;
    private static NoteModel mockedNote;

    @BeforeAll
    static void setDAO() {
        noteDAO = new NPNoteDAO();
        folderDAO = new NPFolderDAO();
        rootFolder = folderDAO.getRootFolder();

        mockedUser = mock(UserModel.class);
        when(mockedUser.getUsername()).thenReturn("TMO");

        mockedNote = mock(NoteModel.class);
        when(mockedNote.getName()).thenReturn(":D");
    }

    @BeforeEach
    void resetRootFolder() {
        rootFolder = folderDAO.getRootFolder();
    }

    @Test
    void save() {
        noteDAO.save(mockedNote, rootFolder);
        assertNotNull(rootFolder.searchNote(":D"));
    }

    @Test
    void searchByPath() {
        noteDAO.save(mockedNote, rootFolder);
        noteDAO.searchByPath(mockedNote.getName(), rootFolder);
        assertNotNull(rootFolder.searchNote(":D"));
    }
}