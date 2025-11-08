package app.mvc.models;

import java.io.File;

/**
 * This class represents the actual state of a note in the app. It should implement
 * methods for getting and setting the actual note's state and domain-logic functions.
 */
public class NoteModel {

    String name;
    String path;
    /** The folder that contains this note. This is set when a note gets saved */
    FolderModel parentFolder;
    /** The pdf file associated with this note */
    File pdfFile;

    /**
     * Base constructor
     *
     * Every note, to be created, needs to have a name and a pdf file.
     * @param name the selected name
     * @param pdfFile the selected pdf file
     */
    public NoteModel(String name, File pdfFile) {
        this.name = name;
        this.pdfFile = pdfFile;
    }

    public String getName() {
        return name;
    }

    public FolderModel getParentFolder() {
        return parentFolder;
    }

    public File getPdfFile() {
        return pdfFile;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * When a note's parent folder is set, also the note's path should be updated
     * @param parentFolder the folder that will contain this note
     */
    public void setParentFolder(FolderModel parentFolder) {
        this.parentFolder = parentFolder;
        this.path = parentFolder.getPath() + "/" + name;
    }

    public void setPdf(File pdfFile) {
        this.pdfFile = pdfFile;
    }
}
