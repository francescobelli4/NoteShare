package app_controllers;

import app.AppContext;
import app.ArgsParser;
import communication.dtos.user.UserType;
import models.folder.FolderModel;
import models.note.NoteModel;
import models.user.StudentUserModel;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TestNotesController {

    @Test
    void createNote() {
        String[] args = {"demo", "en", "colored"};

        AppContext instance = AppContext.getInstance();
        instance.setOptions(ArgsParser.parseArgs(args));

        File f = new File("TMOOOO");

        instance.setCurrentUser(new StudentUserModel("TMO", UserType.STUDENT));
        NotesController.createNote(":D", f);

        assertNotNull(instance.getCurrentUser().getActiveFolder().searchNote(":D"));
    }
}