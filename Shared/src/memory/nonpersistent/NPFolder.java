package memory.nonpersistent;

import memory.shared.Folder;
import memory.shared.entities.NoteEntity;

import java.util.Objects;

public class NPFolder extends Folder {

    /**
     * Root folder
     */
    public NPFolder() {
        this.path = "NoteShare";
        this.name = "NoteShare";
    }

    private NPFolder(Folder parentFolder, String name) {
        this.name = name;
        this.parentFolder = parentFolder;
        this.path = parentFolder.getPath() + "/" + name;
    }

    @Override
    public Folder searchSubFolder(String path) {

        if (this.getSubFolders().isEmpty()) return null;

        Folder temp = this;
        for (String name : path.split("/")) {

            boolean found = false;

            for (Folder subFolder : temp.getSubFolders()) {
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

    @Override
    public Folder createRootFolder() {
        return new NPFolder();
    }

    @Override
    public void addNote(NoteEntity note) {
        this.getNotes().add(note);
    }

    @Override
    public void addSubFolder(String name) {
        this.getSubFolders().add(new NPFolder(this, name));
    }
}
