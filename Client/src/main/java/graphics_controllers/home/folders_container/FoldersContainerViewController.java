package graphics_controllers.home.folders_container;

import app.AppContext;
import graphics_controllers.GraphicsController;
import javafx.scene.Node;
import models.folder.FolderModel;
import models.note.NoteModel;
import views.ViewFactory;
import views.home.folders_container.FoldersContainerView;

import java.util.ArrayList;
import java.util.List;

public class FoldersContainerViewController extends GraphicsController<FoldersContainerView> {

    private String activeQuery = "";

    public FoldersContainerViewController(FoldersContainerView view) {
        super(view);
    }

    @Override
    public void loaded() {
        //Nothing to do...
    }

    public void updateDisplay(FolderModel folder, String filter) {
        List<Node> folders = new ArrayList<>();
        List<Node> notes = new ArrayList<>();

        this.activeQuery = filter;

        for (FolderModel f : folder.getSubFolders()) {
            if (this.activeQuery.isBlank() || f.getName().contains(this.activeQuery))
                folders.add(ViewFactory.getInstance().createFolderElementView(f).getRoot());
        }

        for (NoteModel n : folder.getNotes()) {
            if (this.activeQuery.isBlank() || n.getName().contains(this.activeQuery))
                notes.add(ViewFactory.getInstance().createNoteElementView(n).getRoot());
        }

        getView().displayElements(folders, notes);
    }

    public void updateDisplay(String filter) {
        updateDisplay(AppContext.getInstance().getCurrentUser().getActiveFolder(), filter);
    }

    public void updateDisplay(FolderModel folder) {
        updateDisplay(folder, this.activeQuery);
    }

    public void updateDisplay() {
        updateDisplay(AppContext.getInstance().getCurrentUser().getActiveFolder(), this.activeQuery);
    }
}
