package app.mvc.managenote;

import app.App;
import app.mvc.Controller;
import app.mvc.models.FolderModel;
import app.mvc.models.NoteModel;

import java.io.File;

/**
 * This class should implement all the logic needed to complete the "Manage Note" use case.
 */
public class ManageNoteController extends Controller {

    /**
     * This function should create a new NoteModel instance
     * @param name the name of the note
     * @param pdf the selected pdf file
     * @return a new NoteModel entity
     */
    public NoteModel createNote(String name, File pdf) {

        if (name == null || name.isEmpty()) {
            getBoundary().onNoteCreationFailed(ManageNoteResult.NOTE_NAME_NOT_SPECIFIED);
        }

        return new NoteModel(name, pdf);
    }

    /**
     * This function should save a created note, in the specified parent folder.
     * @param note the note that has to be saved
     * @param parentFolder the folder that will contain that note
     */
    public void saveNote(NoteModel note, FolderModel parentFolder) {
        if (App.getNoteDAO().searchByPath(note.getName(), parentFolder) != null) {
            getBoundary().onNoteCreationFailed(ManageNoteResult.NOTE_ALREADY_EXISTS);
            return;
        }

        App.getNoteDAO().save(note, parentFolder);
        getBoundary().onNoteSaved(note);
    }

    /**
     * This function should return the correct Boundary subclass for this controller
     * @return a ManageNoteBoundary reference
     */
    @Override
    protected ManageNoteBoundary getBoundary() {
        return (ManageNoteBoundary) boundary;
    }
}
