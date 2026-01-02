package app_controllers;

import app.AppContext;
import exceptions.DuplicateFolderException;
import exceptions.DuplicateNoteException;
import exceptions.InvalidFolderNameException;
import models.folder.FolderModel;
import models.note.NoteModel;
import models.user.UserModel;

public class FoldersController {

    private FoldersController() {}

    public static void addSubFolder(String name, FolderModel parentFolder) throws InvalidFolderNameException, DuplicateFolderException {
        addSubFolder(new FolderModel(name), parentFolder);
    }

    public static void addSubFolder(FolderModel folder, FolderModel parentFolder) throws InvalidFolderNameException, DuplicateFolderException {

        if (folder.getName().isEmpty()) {
            throw new InvalidFolderNameException("folder_name_too_short");
        }

        if (folder.getName().length() > 15) {
            throw new InvalidFolderNameException("folder_name_too_long");
        }

        if (parentFolder.searchSubFolder(folder.getName()) != null) {
            throw new DuplicateFolderException();
        }

        AppContext.getInstance().getFolderDAO().save(folder, parentFolder);
    }

    public static void goToFolder(FolderModel folder) {
        AppContext.getInstance().getCurrentUser().setActiveFolder(folder);
    }

    public static void goToParentFolder() {
        if (AppContext.getInstance().getCurrentUser().getActiveFolder().getParentFolder() != AppContext.getInstance().getCurrentUser().getRootFolder())
            goToFolder(AppContext.getInstance().getCurrentUser().getActiveFolder().getParentFolder());
    }

    public static void copyFolder(FolderModel folder) {
        AppContext.getInstance().getCurrentUser().setCopiedElement(folder.copy());
    }

    public static void pasteCopiedElement() throws DuplicateFolderException, DuplicateNoteException {

        UserModel user = AppContext.getInstance().getCurrentUser();

        switch (user.getCopiedElement()) {
            case FolderModel folder -> addSubFolder(folder, user.getActiveFolder());
            case NoteModel note -> NotesController.createNote(note.getName(), note.getPdf().getPdfFile());
            case null, default -> {}
        }
    }

    public static void deleteFolder(FolderModel folder) {
        AppContext.getInstance().getFolderDAO().delete(folder);
    }
}

