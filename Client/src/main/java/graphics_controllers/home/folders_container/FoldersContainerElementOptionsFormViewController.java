package graphics_controllers.home.folders_container;

import app_controllers.FoldersController;
import app_controllers.NotesController;
import graphics_controllers.GraphicsController;
import models.Controllable;
import models.folder.FolderModel;
import models.note.NoteModel;
import views.home.folders_container.FoldersContainerElementOptionsFormView;

public class FoldersContainerElementOptionsFormViewController extends GraphicsController<FoldersContainerElementOptionsFormView> {

    private final Controllable element;

    public FoldersContainerElementOptionsFormViewController(FoldersContainerElementOptionsFormView view, Controllable element) {
        super(view);

        this.element = element;
    }

    @Override
    public void loaded() {
        getView().getRoot().setOnMouseClicked(_ -> closeForm());
        getView().getCopyButton().setOnMouseClicked(_ -> copyButtonClicked());
        getView().getDeleteButton().setOnMouseClicked(_ -> deleteButtonClicked());
    }

    private void closeForm() {
        getView().close();
    }

    private void copyButtonClicked() {

        if (element instanceof NoteModel note) {
            NotesController.copyNote(note);
        }

        if (element instanceof FolderModel folder) {
            FoldersController.copyFolder(folder);
        }

        getView().close();
    }

    private void deleteButtonClicked() {
        if (element instanceof NoteModel note) {
            NotesController.deleteNote(note);
        }

        if (element instanceof FolderModel folder) {
            FoldersController.deleteFolder(folder);
        }

        getView().close();
    }
}
