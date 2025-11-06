package app.mvc.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FolderModel {

    protected String name;
    protected String path;
    protected List<FolderModel> subFolders = new ArrayList<>();
    protected List<NoteModel> notes = new ArrayList<>();
    protected FolderModel parentFolder;

    public FolderModel(String name) {
        this.name = name;
        this.path = name;
    }

    /**
     * Search a subfolder
     * @param path a path relative to this folder
     */
    public FolderModel searchSubFolder(String path) {
        if (this.getSubFolders().isEmpty()) return null;

        FolderModel temp = this;
        for (String name : path.split("/")) {

            boolean found = false;

            for (FolderModel subFolder : temp.getSubFolders()) {
                if (Objects.equals(subFolder.getName(), name)) {
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

    public void setParentFolder(FolderModel parentFolder) {
        this.parentFolder = parentFolder;
        this.path = parentFolder.getPath() + "/" + name;
    }
}
