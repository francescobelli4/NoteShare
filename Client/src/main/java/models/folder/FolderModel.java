package models.folder;

import models.note.NoteModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class represents the actual state of a folder in the app. It should implement
 * methods for getting and setting the actual folder's state and domain-logic functions.
 *
 * A folder in demo mode is always only saved on the stack and the file system is simulated.
 * A folder in release mode should actually represent a folder in the user file system.
 * In release mode, a folder should be "parsed" from the user's file system using FolderDAO.
 *
 * TODO in release mode, gestire il fatto che una folder che è stata parsata dal file system
 * TODO potrebbe essere "salvata in app a livelli", cioè con un parametro di depth che dice
 * TODO quanti strati di subfolders l'app dovrebbe salvare su stack.
 */
public class FolderModel {

    protected String name;

    /**
     * This folder's path. In demo mode, the path is simulated and it's always relative
     * to the UserModel's rootFolder.
     *
     * TODO decidere per release mode come rappresentare il path
     */
    protected String path;

    /**
     * This folder's subfolders. These should depend on depth parameter is release mode...
     */
    protected List<FolderModel> subFolders = new ArrayList<>();

    /**
     * The notes contained in this folder.
     * TODO considerare l'idea di rendere le note dei file (nomenota).noteshare invece che
     * TODO semplici pdf.
     */
    protected List<NoteModel> notes = new ArrayList<>();

    /**
     * This folder's parent folder
     */
    protected FolderModel parentFolder;


    /**
     * Base constructor
     *
     * A basic folder should not have a parent folder. The parent folder is set only when
     * the folder is actually saved (using FolderDAO)
     * @param name the folder's name
     */
    public FolderModel(String name) {
        this.name = name;
        this.path = name;
    }

    /**
     * Search a subfolder.
     *
     * TODO in release mode può succedere che una cartella presente tra le sottocartelle di
     * TODO questa cartella nel file system non venga trovata da questa funzione.
     * TODO gestire questo caso con FolderDAO
     * @param path a path relative to this folder
     */
    public FolderModel searchSubFolder(String path) {
        if (this.getSubFolders().isEmpty()) return null;

        FolderModel temp = this;
        for (String folderName : path.split("/")) {

            boolean found = false;

            for (FolderModel subFolder : temp.getSubFolders()) {
                if (Objects.equals(subFolder.getName(), folderName)) {
                    temp = subFolder;
                    found = true;
                    break;
                }
            }

            if (!found) {
                return null;
            }
        }

        return temp;
    }

    public void addNote(NoteModel note) {
        this.notes.add(note);
        note.setParentFolder(this);
    }

    public void addSubFolder(FolderModel folder) {
        this.subFolders.add(folder);
        folder.setParentFolder(this);
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public List<FolderModel> getSubFolders() {
        return subFolders;
    }

    public List<NoteModel> getNotes() {
        return notes;
    }

    public FolderModel getParentFolder() {
        return parentFolder;
    }

    @SuppressWarnings("java:S1075")
    public void setParentFolder(FolderModel parentFolder) {
        this.parentFolder = parentFolder;
        this.path = parentFolder.getPath() + "/" + name;
    }
}
