package persistency.shared;

import persistency.shared.entities.NoteEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * A folder is an object representing a folder. This class can be inherited to implement a persistent and
 * a non-persistent version of a folder.
 * This class is actually a mix of a FolderDAO and a FolderEntity class.
 * I chose to mix those classes because a folder is not an exchangeable object, so no DTO is needed, so it
 * seems reasonable to create a unique class that works both as a DAO and as an Entity.
 */
public abstract class Folder {

    protected String name;
    protected String path;
    protected List<Folder> subFolders = new ArrayList<>();
    protected List<NoteEntity> notes = new ArrayList<>();
    protected Folder parentFolder;

    /**
     * Search a subfolder
     * @param path a path relative to this folder
     */
    public abstract Folder searchSubFolder(String path);

    public abstract void addNote(NoteEntity note);

    public abstract void addSubFolder(String name);

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public List<Folder> getSubFolders() {
        return subFolders;
    }

    public List<NoteEntity> getNotes() {
        return notes;
    }

    public Folder getParentFolder() {
        return parentFolder;
    }
}
