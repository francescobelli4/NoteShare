package app.mvc.navigation;

import app.mvc.Boundary;
import app.mvc.models.FolderModel;

import java.util.ArrayList;

/**
 * This boundary should manage the navigation use case. It should allow the user to navigate
 * between folders in foldersContainer.
 */
public class NavigationBoundary extends Boundary {

    /**
     * The arraylist of listeners. Every listener will be notified when events occur
     */
    private final ArrayList<Listener> listeners = new ArrayList<>();

    /**
     * Base constructor
     *
     * It should just call the superclass' constructor
     * @param navigationController the controller associated with this boundary
     */
    public NavigationBoundary(NavigationController navigationController) {
        super(navigationController);
    }

    /**
     * This function should add a class instance to the listeners
     * @param listener the listening class
     */
    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    /**
     * This function should navigate the user to a provided folder
     * @param folderModel the new active folder
     */
    public void goToFolder(FolderModel folderModel) {
        getController().goToFolder(folderModel);
    }

    /**
     * This function should make the user's active folder go to the parent folder
     */
    public void goBack() {
        getController().goBack();
    }

    /**
     * This function should notify the listeners whenever a user changes the active directory
     */
    public void notifyActiveFolderUpdated() {
        for (Listener l : listeners)
            l.onActiveFolderUpdated();
    }

    /**
     * This function should destroy the associated controller and this boundary's only
     * reference will be deleted by the BoundaryManager
     */
    @Override
    public void destroy() {
        controller = null;
    }

    /**
     * This function should return the correct subclass of this boundary's controller
     * @return a RegisterController reference
     */
    @Override
    protected NavigationController getController() {
        return (NavigationController) controller;
    }

    /**
     * The interface that all the listeners will implement to get notified on application
     * status changes
     */
    public interface Listener {
        void onActiveFolderUpdated();
    }
}
