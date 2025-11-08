package app.mvc.managefolder;

import app.mvc.Boundary;
import app.mvc.Controller;
import app.mvc.models.FolderModel;

import java.io.File;
import java.util.ArrayList;

/**
 * This boundary should allow the communication between the user and the "Manage Folder" use case
 */
public class ManageFolderBoundary extends Boundary {

    /**
     * This arraylist of listeners that will be notified when events occur
     */
    private final ArrayList<Listener> listeners = new ArrayList<>();

    /**
     * Base constructor
     *
     * This should only call the superclass' constructor
     * @param controller the controller associated with this boundary
     */
    public ManageFolderBoundary(Controller controller) {
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
     * This function should actually save the folder when the user creates a new folder in a
     * certain path. It calls the controller that implements all the logic for this.
     * @param name the name of the new folder
     * @param parentFolder the folder that contains this new folder.
     */
    public void saveFolder(String name, FolderModel parentFolder) {
        FolderModel folder = getController().createFolder(name);
        getController().saveFolder(folder, parentFolder);
    }

    /**
     * This function should notify all the listeners whenever a folder creation fails
     * @param manageFolderResult the reason for the fail
     */
    public void onFolderCreationFailed(ManageFolderResult manageFolderResult) {
        for (Listener listener : listeners)
            listener.onFolderCreationFailed(manageFolderResult);
    }

    /**
     * This function should notify the user whenever a folder is saved.
     * @param folder the saved folder
     */
    public void onFolderSaved(FolderModel folder) {
        for (Listener listener : listeners)
            listener.onFolderSaved(folder);
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
     * @return a ManageFolderController reference
     */
    @Override
    protected ManageFolderController getController() {
        return (ManageFolderController) controller;
    }

    /**
     * The interface that all the listeners will implement to get notified on application
     * status changes
     */
    public interface Listener {
        void onFolderSaved(FolderModel folder);
        void onFolderDeleted();
        void onFolderMoved();
        void onFolderCreationFailed(ManageFolderResult manageFolderResult);
    }
}
