package graphics_controllers.home.folders_container;

import graphics_controllers.GraphicsController;
import javafx.scene.input.MouseEvent;
import models.note.NoteModel;
import views.ViewNavigator;
import views.home.folders_container.NoteElementView;

public class NoteElementViewController extends GraphicsController<NoteElementView> {

    private final NoteModel note;

    public NoteElementViewController(NoteElementView view, NoteModel note) {
        super(view);

        this.note = note;
        setupUI();
    }

    @Override
    public void loaded() {
        getView().getRoot().setOnMouseClicked(_ -> noteClicked());
        getView().getOptionsButton().setOnMouseClicked(this::optionsButtonClicked);
    }

    private void setupUI() {
        getView().getNoteLabel().setText(note.getName());
    }

    private void noteClicked() {
        ViewNavigator.displayViewNoteView(note);
    }

    private void optionsButtonClicked(MouseEvent clickEvent) {
        ViewNavigator.displayFoldersContainerElementOptionsFormView(note, clickEvent.getScreenX(), clickEvent.getScreenY());
    }
}
