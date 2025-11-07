package app.mvc.managenote;

import app.mvc.Boundary;
import app.mvc.Controller;
import app.mvc.models.FolderModel;
import app.mvc.models.NoteModel;

import java.io.File;
import java.util.ArrayList;

public class ManageNoteBoundary extends Boundary {

    private ArrayList<Listener> listeners = new ArrayList<>();

    public ManageNoteBoundary(Controller controller) {
        super(controller);
    }

    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    public void saveNote(String name, File pdf, FolderModel parentFolder) {
        NoteModel note = getController().createNote(name, pdf);
        getController().saveNote(note, parentFolder);
    }

    public void handleNoteCreationFailed(ManageNoteResult manageNoteResult) {
        onNoteCreationFailed(manageNoteResult);
    }

    public void handleNoteSaved(NoteModel note) {
        onNoteSaved(note);
    }

    public void onNoteCreationFailed(ManageNoteResult manageNoteResult) {
        for (Listener l : listeners)
            l.onNoteCreationFailed(manageNoteResult);
    }

    public void onNoteSaved(NoteModel note) {
        for (Listener l : listeners)
            l.onNoteSaved(note);
    }

    @Override
    public void destroy() {
        controller = null;
    }

    @Override
    protected ManageNoteController getController() {
        return (ManageNoteController) controller;
    }

    public interface Listener {
        void onNoteSaved(NoteModel note);
        void onNoteDeleted();
        void onNoteMoved();
        void onNoteCreationFailed(ManageNoteResult manageNoteResult);
    }
}
