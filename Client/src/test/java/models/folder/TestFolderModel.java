package models.folder;

import models.note.NoteModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TestFolderModel {

    private FolderModel testFolder;

    @BeforeEach
    void setupFolderModel() {
        testFolder = new FolderModel("TMO");
    }

    @Test
    void addListener() {
        FolderModel.Listener mockedListener = mock(FolderModel.Listener.class);
        testFolder.addListener(mockedListener);
        assertTrue(testFolder.getListeners().contains(mockedListener));
    }

    @Test
    void removeListener() {
        FolderModel.Listener mockedListener = mock(FolderModel.Listener.class);
        testFolder.addListener(mockedListener);
        testFolder.removeListener(mockedListener);
        assertFalse(testFolder.getListeners().contains(mockedListener));
    }

    @Test
    void clearListeners() {
        FolderModel.Listener mockedListener = mock(FolderModel.Listener.class);
        testFolder.addListener(mockedListener);
        testFolder.clearListeners();
        assertTrue(testFolder.getListeners().isEmpty());
    }

    @Test
    void searchSubFolder_Empty() {
        assertNull(testFolder.searchSubFolder(":D"));
    }

    @Test
    void searchSubFolder_OnlyName() {
        FolderModel fold = new FolderModel(":D");
        testFolder.addSubFolder(fold);
        testFolder.addSubFolder(new FolderModel("D:"));
        fold.addSubFolder(new FolderModel(":)"));

        assertNotNull(testFolder.searchSubFolder(":D"));
    }

    @Test
    void searchSubFolder_Path() {
        FolderModel fold = new FolderModel(":D");
        testFolder.addSubFolder(fold);
        testFolder.addSubFolder(new FolderModel("D:"));
        fold.addSubFolder(new FolderModel(":)"));

        assertNotNull(testFolder.searchSubFolder(":D/:)"));
    }

    @Test
    void addNote() {
        NoteModel mockedNote = mock(NoteModel.class);
        FolderModel.Listener listener = mock(FolderModel.Listener.class);
        testFolder.addListener(listener);
        testFolder.addNote(mockedNote);

        assertTrue(testFolder.getNotes().contains(mockedNote));

        for (FolderModel.Listener l : testFolder.getListeners()) {
            verify(l, times(1)).folderUpdated();
        }
    }

    @Test
    void searchNote_Empty() {
        assertNull(testFolder.searchNote(":D"));
    }

    @Test
    void searchNote() {

        NoteModel mockedNote = mock(NoteModel.class);
        when(mockedNote.getName()).thenReturn(":D");
        testFolder.addNote(mockedNote);
        assertNotNull(testFolder.searchNote(":D"));
    }

    @Test
    void addSubFolder() {

        FolderModel.Listener listener = mock(FolderModel.Listener.class);
        testFolder.addListener(listener);
        FolderModel f = new FolderModel(":D");
        testFolder.addSubFolder(f);

        assertTrue(testFolder.getSubFolders().contains(f));

        for (FolderModel.Listener l : testFolder.getListeners()) {
            verify(l, times(1)).folderUpdated();
        }
    }

    @Test
    void getName() {
        assertEquals("TMO", testFolder.getName());
    }

    @Test
    void getPath() {
        assertEquals("TMO", testFolder.getPath());
    }

    @Test
    void getSubFolders() {
        assertTrue(testFolder.getSubFolders().isEmpty());

        FolderModel f = new FolderModel(":D");
        testFolder.addSubFolder(f);

        assertFalse(testFolder.getSubFolders().isEmpty());
        assertTrue(testFolder.getSubFolders().contains(f));
    }

    @Test
    void getNotes() {

        assertTrue(testFolder.getNotes().isEmpty());

        NoteModel mockedNote = mock(NoteModel.class);
        when(mockedNote.getName()).thenReturn(":D");
        testFolder.addNote(mockedNote);

        assertFalse(testFolder.getNotes().isEmpty());
        assertTrue(testFolder.getNotes().contains(mockedNote));
    }

    @Test
    void getParentFolder() {
        assertNull(testFolder.getParentFolder());

        FolderModel f = new FolderModel(":D");
        testFolder.addSubFolder(f);

        assertEquals(testFolder, f.getParentFolder());
    }

    @Test
    void setParentFolder() {
        FolderModel f = new FolderModel(":D");
        f.setParentFolder(testFolder);

        assertEquals(testFolder, f.getParentFolder());
        assertEquals("TMO/:D", f.getPath());
    }
}