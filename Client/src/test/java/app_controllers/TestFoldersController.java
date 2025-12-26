package app_controllers;

import app.AppContext;
import app.ArgsParser;
import communication.dtos.user.UserType;
import models.folder.FolderModel;
import models.user.StudentUserModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TestFoldersController {

    @Test
    void addSubFolder() {
        FolderModel parent = new FolderModel(":D");
        FoldersController.addSubFolder("TMO", parent);
        assertNotNull(parent.searchSubFolder("TMO"));
    }

    @Test
    void goToFolder() {
        String[] args = {"demo", "en", "colored"};

        AppContext instance = AppContext.getInstance();
        instance.setOptions(ArgsParser.parseArgs(args));

        instance.setCurrentUser(new StudentUserModel("TMO", UserType.STUDENT));
        FolderModel parent = new FolderModel(":D");
        FoldersController.goToFolder(parent);
        assertEquals(parent, instance.getCurrentUser().getActiveFolder());
    }
}