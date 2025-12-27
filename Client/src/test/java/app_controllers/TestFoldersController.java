package app_controllers;

import app.AppContext;
import app.ArgsParser;
import communication.dtos.user.UserType;
import models.folder.FolderModel;
import models.user.StudentUserModel;
import models.user.UserModel;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

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

    /**
     * CASO 2: La condizione è FALSA.
     * Siamo diretti figli della root (Root -> A).
     * Il genitore è Root.
     * L'IF dice: se parent != root... ma qui parent == root, quindi è FALSO.
     * Non succede nulla.
     */
    @Test
    void goToParentFolder_NOTHING() {

        AppContext mockAppContext = mock(AppContext.class);
        UserModel mockUser = mock(UserModel.class);

        when(mockAppContext.getCurrentUser()).thenReturn(mockUser);

        try (MockedStatic<AppContext> staticCTX = mockStatic(AppContext.class)) {

            staticCTX.when(AppContext::getInstance).thenReturn(mockAppContext);

            FolderModel rootFolder = mock(FolderModel.class);
            FolderModel folderA = mock(FolderModel.class);

            when(folderA.getParentFolder()).thenReturn(rootFolder);
            when(mockUser.getRootFolder()).thenReturn(rootFolder);
            when(mockUser.getActiveFolder()).thenReturn(folderA);

            FoldersController.goToParentFolder();

            verify(mockUser, never()).setActiveFolder(any());
        }
    }
}