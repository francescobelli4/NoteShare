package app_controllers;

import app.AppContext;
import app.ArgsParser;
import communication.dtos.user.UserType;
import daos.folder.NPFolderDAO;
import daos.note.NPNoteDAO;
import exceptions.DuplicateFolderException;
import exceptions.InvalidFolderNameException;
import models.folder.FolderModel;
import models.note.NoteModel;
import models.user.UserModel;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import utils.PDFWrapper;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TestFoldersController {

    @Test
    void addSubFolder() {
        String[] args = {"demo", "en", "colored"};
        AppContext instance = AppContext.getInstance();
        instance.setOptions(ArgsParser.parseArgs(args));
        instance.setCurrentUser(new UserModel("TMO", UserType.STUDENT));


        FolderModel parent = new FolderModel(":D");
        FoldersController.addSubFolder("TMO", parent);
        assertNotNull(parent.searchSubFolder("TMO"));
    }

    @Test
    void addSubFolder_NameTooShort() {
        String[] args = {"demo", "en", "colored"};
        AppContext instance = AppContext.getInstance();
        instance.setOptions(ArgsParser.parseArgs(args));
        instance.setCurrentUser(new UserModel("TMO", UserType.STUDENT));

        FolderModel parent = new FolderModel(":D");
        InvalidFolderNameException e = assertThrows(InvalidFolderNameException.class, () -> FoldersController.addSubFolder("", parent));
        assertEquals("folder_name_too_short", e.getMessage());
    }

    @Test
    void addSubFolder_NameTooLong() {
        String[] args = {"demo", "en", "colored"};
        AppContext instance = AppContext.getInstance();
        instance.setOptions(ArgsParser.parseArgs(args));
        instance.setCurrentUser(new UserModel("TMO", UserType.STUDENT));

        FolderModel parent = new FolderModel(":D");
        InvalidFolderNameException e = assertThrows(InvalidFolderNameException.class, () -> FoldersController.addSubFolder("TMOTMOTMOTMOTMOTMOTMOTMOTMOTMO", parent));
        assertEquals("folder_name_too_long", e.getMessage());
    }

    @Test
    void addSubFolder_DuplicateFolder() {
        String[] args = {"demo", "en", "colored"};
        AppContext instance = AppContext.getInstance();
        instance.setOptions(ArgsParser.parseArgs(args));
        instance.setCurrentUser(new UserModel("TMO", UserType.STUDENT));

        FolderModel parent = new FolderModel(":D");
        FoldersController.addSubFolder("TMOOOO", parent);
        assertThrows(DuplicateFolderException.class, () -> FoldersController.addSubFolder("TMOOOO", parent));
    }

    @Test
    void goToFolder() {
        String[] args = {"demo", "en", "colored"};

        AppContext instance = AppContext.getInstance();
        instance.setOptions(ArgsParser.parseArgs(args));

        instance.setCurrentUser(new UserModel("TMO", UserType.STUDENT));
        FolderModel parent = new FolderModel(":D");
        FoldersController.goToFolder(parent);
        assertEquals(parent, instance.getCurrentUser().getActiveFolder());
        assertTrue(instance.getCurrentUser().getQuery().isBlank());
    }

    @Test
    void goToParentFolder_NOTHING() {

        AppContext mockAppContext = mock(AppContext.class);
        UserModel mockUser = mock(UserModel.class);

        when(mockAppContext.getCurrentUser()).thenReturn(mockUser);

        try (MockedStatic<AppContext> staticCTX = mockStatic(AppContext.class)) {

            staticCTX.when(AppContext::getInstance).thenReturn(mockAppContext);

            FolderModel rootFolder = mock(FolderModel.class);
            when(rootFolder.isRoot()).thenReturn(true);
            FolderModel folderA = mock(FolderModel.class);

            when(folderA.getParentFolder()).thenReturn(rootFolder);
            when(mockUser.getRootFolder()).thenReturn(rootFolder);
            when(mockUser.getActiveFolder()).thenReturn(folderA);

            FoldersController.goToParentFolder();

            verify(mockUser, never()).setActiveFolder(any());
        }
    }

    @Test
    void pasteCopiedElement_Null() {

        AppContext mockAppContext = mock(AppContext.class);
        UserModel mockUser = mock(UserModel.class);

        when(mockAppContext.getCurrentUser()).thenReturn(mockUser);

        try (MockedStatic<AppContext> staticCTX = mockStatic(AppContext.class)) {

            staticCTX.when(AppContext::getInstance).thenReturn(mockAppContext);

            FoldersController.pasteCopiedElement();

            verify(mockUser, times(1)).getCopiedElement();
        }
    }

    @Test
    void pasteCopiedElement_Folder() {

        AppContext mockAppContext = mock(AppContext.class);
        when(mockAppContext.getFolderDAO()).thenReturn(new NPFolderDAO());

        UserModel mockUser = mock(UserModel.class);
        when(mockUser.getActiveFolder()).thenReturn(new FolderModel("TMO"));
        when(mockUser.getCopiedElement()).thenReturn(new FolderModel(":D"));

        when(mockAppContext.getCurrentUser()).thenReturn(mockUser);

        try (MockedStatic<AppContext> staticCTX = mockStatic(AppContext.class)) {

            staticCTX.when(AppContext::getInstance).thenReturn(mockAppContext);

            assertInstanceOf(FolderModel.class, mockUser.getCopiedElement());

            FoldersController.pasteCopiedElement();

            verify(mockUser, times(2)).getCopiedElement();
            assertNotNull(mockUser.getActiveFolder().searchSubFolder(":D"));
        }
    }

    @Test
    void pasteCopiedElement_Note() {

        AppContext mockAppContext = mock(AppContext.class);
        when(mockAppContext.getFolderDAO()).thenReturn(new NPFolderDAO());
        when(mockAppContext.getNoteDAO()).thenReturn(new NPNoteDAO());

        UserModel mockUser = mock(UserModel.class);
        when(mockUser.getActiveFolder()).thenReturn(new FolderModel("TMO"));
        NoteModel mockedNote = mock(NoteModel.class);
        when(mockedNote.getName()).thenReturn(":D");
        PDFWrapper mockedPDFWrapper = mock(PDFWrapper.class);
        when(mockedPDFWrapper.getPdfFile()).thenReturn(new File("/test/pdf.pdf"));
        when(mockedNote.getPdf()).thenReturn(mockedPDFWrapper);
        when(mockUser.getCopiedElement()).thenReturn(mockedNote);

        when(mockAppContext.getCurrentUser()).thenReturn(mockUser);

        try (MockedStatic<AppContext> staticCTX = mockStatic(AppContext.class)) {

            staticCTX.when(AppContext::getInstance).thenReturn(mockAppContext);

            assertInstanceOf(NoteModel.class, mockUser.getCopiedElement());

            FoldersController.pasteCopiedElement();

            verify(mockUser, times(2)).getCopiedElement();
            assertNotNull(mockUser.getActiveFolder().searchNote(":D"));
        }
    }

    @Test
    void deleteFolder() {
        AppContext mockAppContext = mock(AppContext.class);
        when(mockAppContext.getFolderDAO()).thenReturn(new NPFolderDAO());

        UserModel mockUser = mock(UserModel.class);
        when(mockUser.getActiveFolder()).thenReturn(new FolderModel("TMO"));
        FolderModel toDelete = new FolderModel(":D");
        mockUser.getActiveFolder().addSubFolder(toDelete);

        when(mockAppContext.getCurrentUser()).thenReturn(mockUser);

        try (MockedStatic<AppContext> staticCTX = mockStatic(AppContext.class)) {

            staticCTX.when(AppContext::getInstance).thenReturn(mockAppContext);

            FoldersController.deleteFolder(toDelete);

            assertNull(mockUser.getActiveFolder().searchSubFolder(":D"));
        }
    }

    @Test
    void copyFolder() {

        String[] args = {"demo", "en", "colored"};

        AppContext instance = AppContext.getInstance();
        instance.setOptions(ArgsParser.parseArgs(args));

        instance.setCurrentUser(new UserModel("TMO", UserType.STUDENT));
        FolderModel parent = new FolderModel(":D");
        FoldersController.addSubFolder("TMO", parent);

        FoldersController.copyFolder(parent);
        assertInstanceOf(FolderModel.class, instance.getCurrentUser().getCopiedElement());
    }
}