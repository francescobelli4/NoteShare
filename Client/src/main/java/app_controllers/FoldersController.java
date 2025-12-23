package app_controllers;

import app.AppContext;
import models.folder.FolderModel;

public class FoldersController {

    private FoldersController() {}

    public static void addSubFolder(String name, FolderModel parentFolder) {
        parentFolder.addSubFolder(new FolderModel(name));
    }

    public static void goToFolder(FolderModel folder) {
        AppContext.getInstance().getCurrentUser().setActiveFolder(folder);
    }

    public static void goToParentFolder() {
        if (AppContext.getInstance().getCurrentUser().getActiveFolder().getParentFolder() != AppContext.getInstance().getCurrentUser().getRootFolder())
            goToFolder(AppContext.getInstance().getCurrentUser().getActiveFolder().getParentFolder());
    }
}