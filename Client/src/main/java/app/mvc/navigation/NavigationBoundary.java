package app.mvc.navigation;

import app.mvc.Boundary;
import app.mvc.models.FolderModel;

import java.util.ArrayList;

public class NavigationBoundary extends Boundary {

    private final ArrayList<Listener> listeners = new ArrayList<>();

    public NavigationBoundary(NavigationController navigationController) {
        super(navigationController);
    }

    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    public void goToFolder(FolderModel folderModel) {
        getController().goToFolder(folderModel);
    }

    public void goBack() {
        getController().goBack();
    }

    public void goToChild(String name) {
        getController().goToChild(name);
    }

    public void notifyActiveFolderUpdated() {
        for (Listener l : listeners)
            l.onActiveFolderUpdated();
    }

    @Override
    protected void initialize() {

    }

    @Override
    public void destroy() {
        controller = null;
    }

    @Override
    protected NavigationController getController() {
        return (NavigationController) controller;
    }

    public interface Listener {
        void onActiveFolderUpdated();
    }
}
