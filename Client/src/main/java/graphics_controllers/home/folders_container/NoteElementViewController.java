package graphics_controllers.home.folders_container;

import graphics_controllers.GraphicsController;
import views.home.folders_container.NoteElementView;

public class NoteElementViewController extends GraphicsController<NoteElementView> {

    public NoteElementViewController(NoteElementView view) {
        super(view);
    }

    @Override
    public void loaded() {
        getView().getRoot().setOnMouseClicked(_ -> noteClicked());
    }

    private void noteClicked() {

    }
}
