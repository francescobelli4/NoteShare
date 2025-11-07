package app.mvc.models;

import java.io.File;

public class NoteModel {

    String name;
    String path;
    FolderModel parentFolder;
    File pdfFile;

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

    public void setParentFolder(FolderModel parentFolder) {
        this.parentFolder = parentFolder;
        this.path = parentFolder.getPath() + "/" + name;

    }

    public void setPdf(File pdfFile) {
        this.pdfFile = pdfFile;
    }
}
