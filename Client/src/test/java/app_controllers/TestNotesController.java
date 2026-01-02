package app_controllers;

import app.AppContext;
import app.ArgsParser;
import communication.dtos.user.UserType;
import exceptions.DuplicateNoteException;
import exceptions.InvalidNoteNameException;
import exceptions.InvalidNotePDFException;
import models.note.NoteModel;
import models.user.UserModel;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class TestNotesController {

    @Test
    void createNote() {
        String[] args = {"demo", "en", "colored"};

        AppContext instance = AppContext.getInstance();
        instance.setOptions(ArgsParser.parseArgs(args));

        File f = new File("TMOOOO");

        instance.setCurrentUser(new UserModel("TMO", UserType.STUDENT));
        NotesController.createNote(":D", f);

        assertNotNull(instance.getCurrentUser().getActiveFolder().searchNote(":D"));
    }

    @Test
    void createNote_NameTooShort() {
        String[] args = {"demo", "en", "colored"};

        AppContext instance = AppContext.getInstance();
        instance.setOptions(ArgsParser.parseArgs(args));

        File f = new File("TMOOOO");

        instance.setCurrentUser(new UserModel("TMO", UserType.STUDENT));

        InvalidNoteNameException e = assertThrows(InvalidNoteNameException.class, () -> NotesController.createNote("", f));
        assertEquals("note_name_too_short", e.getMessage());
    }

    @Test
    void createNote_NameTooLong() {
        String[] args = {"demo", "en", "colored"};

        AppContext instance = AppContext.getInstance();
        instance.setOptions(ArgsParser.parseArgs(args));

        File f = new File("TMOOOO");

        instance.setCurrentUser(new UserModel("TMO", UserType.STUDENT));

        InvalidNoteNameException e = assertThrows(InvalidNoteNameException.class, () -> NotesController.createNote(":D:D:D:DD::D:D:D:D:D:D:D:D:D:D:D:D:D:D:", f));
        assertEquals("note_name_too_long", e.getMessage());
    }

    @Test
    void createNote_DuplicateNote() {
        String[] args = {"demo", "en", "colored"};

        AppContext instance = AppContext.getInstance();
        instance.setOptions(ArgsParser.parseArgs(args));

        File f = new File("TMOOOO");

        instance.setCurrentUser(new UserModel("TMO", UserType.STUDENT));
        NotesController.createNote(":D", f);
        assertThrows(DuplicateNoteException.class, () -> NotesController.createNote(":D", f));
    }

    @Test
    void createNote_NoPDFFile() {
        String[] args = {"demo", "en", "colored"};

        AppContext instance = AppContext.getInstance();
        instance.setOptions(ArgsParser.parseArgs(args));

        instance.setCurrentUser(new UserModel("TMO", UserType.STUDENT));
        assertThrows(InvalidNotePDFException.class, () -> NotesController.createNote(":D", null));
    }

    @Test
    void copyNote() {
        String[] args = {"demo", "en", "colored"};

        AppContext instance = AppContext.getInstance();
        instance.setOptions(ArgsParser.parseArgs(args));

        File f = new File("TMOOOO");

        instance.setCurrentUser(new UserModel("TMO", UserType.STUDENT));
        NotesController.createNote(":D", f);

        NoteModel note = instance.getCurrentUser().getActiveFolder().searchNote(":D");

        NotesController.copyNote(note);

        assertInstanceOf(NoteModel.class, instance.getCurrentUser().getCopiedElement());
    }

    @Test
    void deleteNote() {
        String[] args = {"demo", "en", "colored"};

        AppContext instance = AppContext.getInstance();
        instance.setOptions(ArgsParser.parseArgs(args));

        File f = new File("TMOOOO");

        instance.setCurrentUser(new UserModel("TMO", UserType.STUDENT));
        NotesController.createNote(":D", f);

        NoteModel note = instance.getCurrentUser().getActiveFolder().searchNote(":D");

        NotesController.deleteNote(note);

        assertNull(instance.getCurrentUser().getActiveFolder().searchNote(":D"));
    }
}