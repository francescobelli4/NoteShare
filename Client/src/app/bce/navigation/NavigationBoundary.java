package app.bce.navigation;

import app.bce.Boundary;
import persistency.shared.Folder;

import java.util.ArrayList;

public class NavigationBoundary extends Boundary {

    private final ArrayList<Listener> listeners = new ArrayList<>();

    public NavigationBoundary(NavigationController navigationController) {
        super(navigationController);
    }

    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    public void goToFolder(Folder folder) {
        getController().goToFolder(folder);
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
