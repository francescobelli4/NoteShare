package app.mvc.managenote;

import app.App;
import app.mvc.Controller;
import app.mvc.models.FolderModel;
import app.mvc.models.NoteModel;

import java.io.File;

public class ManageNoteController extends Controller {
    @Override
    protected ManageNoteBoundary getBoundary() {
        return (ManageNoteBoundary) boundary;
    }

    public NoteModel createNote(String name, File pdf) {

        if (name == null || name.isEmpty()) {
            getBoundary().handleNoteCreationFailed(ManageNoteResult.NOTE_NAME_NOT_SPECIFIED);
        }

        return new NoteModel(name, pdf);
    }

    public void saveNote(NoteModel note, FolderModel parentFolder) {
        if (App.getNoteDAO().searchByPath(note.getName(), parentFolder) != null) {
            getBoundary().handleNoteCreationFailed(ManageNoteResult.NOTE_ALREADY_EXISTS);
            return;
        }

        App.getNoteDAO().save(note, parentFolder);
        getBoundary().handleNoteSaved(note);
    }
}
