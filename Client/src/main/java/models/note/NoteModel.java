package models.note;

import models.Controllable;
import models.folder.FolderModel;
import utils.PDFWrapper;

import java.io.File;

/**
 * This class represents the actual state of a note in the app. It should implement
 * methods for getting and setting the actual note's state and domain-logic functions.
 */
public class NoteModel implements Controllable {

    private String name;
    /** Path to the config file */
    private String path;
    /** The folder that contains this note. This is set when a note gets saved */
    private FolderModel parentFolder;
    /** The pdf file associated with this note */
    private PDFWrapper pdf;

    /**
     * Base constructor
     *
     * Every note, to be created, needs to have a name and a pdf file.
     * @param name the selected name
     * @param pdf the selected pdf file
     */
    public NoteModel(String name, PDFWrapper pdf) {
        this.name = name;
        this.pdf = pdf;
    }

    public NoteModel(File configFile) {
        //TODO
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public FolderModel getParentFolder() {
        return parentFolder;
    }

    public PDFWrapper getPdf() {
        return pdf;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * When a note's parent folder is set, also the note's path should be updated
     * @param parentFolder the folder that will contain this note
     */
    @SuppressWarnings("java:S1075")
    public void setParentFolder(FolderModel parentFolder) {
        this.parentFolder = parentFolder;
        this.path = parentFolder.getPath() + "/" + name;
    }

    public void setPdf(PDFWrapper pdf) {
        this.pdf = pdf;
    }

    private void createConfigFile() {
        //TODO
    }

    @Override
    public Controllable copy() {
        return new NoteModel(this.name, this.pdf);
    }
}
