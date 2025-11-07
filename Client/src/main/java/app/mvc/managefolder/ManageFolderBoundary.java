package app.mvc.managefolder;

import app.mvc.Boundary;
import app.mvc.Controller;
import app.mvc.models.FolderModel;

import java.io.File;
import java.util.ArrayList;

public class ManageFolderBoundary extends Boundary {

    private final ArrayList<Listener> listeners = new ArrayList<>();

    public ManageFolderBoundary(Controller controller) {
        super(controller);
    }

    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    public void saveFolder(String name, FolderModel parentFolder) {
        FolderModel folder = getController().createFolder(name);
        getController().saveFolder(folder, parentFolder);
    }

    public void handleFolderCreationFailed(ManageFolderResult manageFolderResult) {
        onFolderCreationFailed(manageFolderResult);
    }

    public void handleFolderSaved(FolderModel folder) {
        onFolderSaved(folder);
    }

    public void onFolderCreationFailed(ManageFolderResult manageFolderResult) {
        for (Listener listener : listeners)
            listener.onFolderCreationFailed(manageFolderResult);
    }

    public void onFolderSaved(FolderModel folder) {
        for (Listener listener : listeners)
            listener.onFolderSaved(folder);
    }

    @Override
    public void destroy() {
        controller = null;
    }

    @Override
    protected ManageFolderController getController() {
        return (ManageFolderController) controller;
    }

    public interface Listener {
        void onFolderSaved(FolderModel folder);
        void onFolderDeleted();
        void onFolderMoved();
        void onFolderCreationFailed(ManageFolderResult manageFolderResult);
    }
}
