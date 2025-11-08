package app.mvc.managenote;

import app.mvc.Boundary;
import app.mvc.Controller;
import app.mvc.models.FolderModel;
import app.mvc.models.NoteModel;

import java.io.File;
import java.util.ArrayList;

/**
 * This boundary should allow the communication between the "Manage Note" use case and
 * the user.
 */
public class ManageNoteBoundary extends Boundary {

    /**
     * The arraylist of listeners that will be notified when an event occurs
     */
    private ArrayList<Listener> listeners = new ArrayList<>();

    /**
     * Base constructor
     *
     * This should only call the superclass' constructor
     * @param controller the right controller for this boundary
     */
    public ManageNoteBoundary(Controller controller) {
        super(controller);
    }

    /**
     * This function should add a new listener
     * @param listener a new class instance that will be notified
     */
    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    /**
     * This function should save a new note. It calls the controller who knows all the logic
     * needed to complete this use case.
     * @param name the note name
     * @param pdf the selected pdf file
     * @param parentFolder the folder that contains this note
     */
    public void saveNote(String name, File pdf, FolderModel parentFolder) {
        NoteModel note = getController().createNote(name, pdf);
        getController().saveNote(note, parentFolder);
    }

    /**
     * This function should notify the listeners when a note creation fails
     * @param manageNoteResult the reason for fail
     */
    public void onNoteCreationFailed(ManageNoteResult manageNoteResult) {
        for (Listener l : listeners)
            l.onNoteCreationFailed(manageNoteResult);
    }

    /**
     * This function should notify the listeners when a note is saved
     * @param note the saved note
     */
    public void onNoteSaved(NoteModel note) {
        for (Listener l : listeners)
            l.onNoteSaved(note);
    }

    /**
     * This function should destroy the associated controller. The reference to this
     * boundary is deleted by the BoundaryManager
     */
    @Override
    public void destroy() {
        controller = null;
    }

    /**
     * This function should return the correct subclass of this boundary's controller
     * @return a ManageNoteController reference
     */
    @Override
    protected ManageNoteController getController() {
        return (ManageNoteController) controller;
    }

    /**
     * The interface that all the listeners will implement to get notified on application
     * status changes
     */
    public interface Listener {
        void onNoteSaved(NoteModel note);
        void onNoteDeleted();
        void onNoteMoved();
        void onNoteCreationFailed(ManageNoteResult manageNoteResult);
    }
}
