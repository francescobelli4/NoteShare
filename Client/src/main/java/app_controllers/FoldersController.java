package app_controllers;

import app.App;
import models.folder.FolderModel;

public class FoldersController {

    private FoldersController() {}

    public static void addSubFolder(String name, FolderModel parentFolder) {
        parentFolder.addSubFolder(new FolderModel(name));
    }

    public static void goToFolder(FolderModel folder) {
        App.getUser().setActiveFolder(folder);
    }

    public static void goToParentFolder() {
        if (App.getUser().getActiveFolder().getParentFolder() != App.getUser().getRootFolder())
            goToFolder(App.getUser().getActiveFolder().getParentFolder());
    }
}