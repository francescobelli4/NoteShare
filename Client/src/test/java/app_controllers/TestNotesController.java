package app_controllers;

import app.AppContext;
import app.ArgsParser;
import communication.dtos.user.UserType;
import models.user.StudentUserModel;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertNotNull;

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