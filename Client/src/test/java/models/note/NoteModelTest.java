package models.note;

import models.folder.FolderModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.PDFWrapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;

class NoteModelTest {

    private NoteModel testNote;
    private PDFWrapper mockedPDFWrapper;

    @BeforeEach
    void setupNote() {
        mockedPDFWrapper = mock(PDFWrapper.class);
        testNote = new NoteModel("TMO", mockedPDFWrapper);
    }

    @Test
    void getName() {
        assertEquals("TMO", testNote.getName());
    }

    @Test
    void getPath() {
        assertNull(testNote.getPath());

        FolderModel f = new FolderModel(":D");
        testNote.setParentFolder(f);

        assertEquals(":D/TMO", testNote.getPath());
    }

    @Test
    void getParentFolder() {
        assertNull(testNote.getParentFolder());

        FolderModel f = new FolderModel(":D");
        testNote.setParentFolder(f);

        assertEquals(f, testNote.getParentFolder());
    }

    @Test
    void getPdf() {
        assertEquals(mockedPDFWrapper, testNote.getPdf());
    }

    @Test
    void setName() {
        testNote.setName(":D");
        assertEquals(":D", testNote.getName());
    }

    @Test
    void setParentFolder() {
        FolderModel f = new FolderModel(":D");
        testNote.setParentFolder(f);

        assertEquals(f, testNote.getParentFolder());
        assertEquals(":D/TMO", testNote.getPath());
    }

    @Test
    void setPdf() {

        PDFWrapper mockedPDF = mock(PDFWrapper.class);
        testNote.setPdf(mockedPDF);
        assertEquals(mockedPDF, testNote.getPdf());
    }
}