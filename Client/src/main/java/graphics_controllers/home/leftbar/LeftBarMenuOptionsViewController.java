package graphics_controllers.home.leftbar;

import graphics_controllers.GraphicsController;
import views.home.leftbar.LeftBarMenuOptionsView;

public class LeftBarMenuOptionsViewController extends GraphicsController<LeftBarMenuOptionsView> {

    public LeftBarMenuOptionsViewController(LeftBarMenuOptionsView view) {
        super(view);
    }

    @Override
    public void loaded() {

        getView().getYourNotesButton().setOnMouseClicked(_ -> yourNotesButtonClick());
        getView().getBrowseNotesButton().setOnMouseClicked(_ -> browseNotesButtonClick());
        getView().getSharedNotesButton().setOnMouseClicked(_ -> sharedNotesButtonClick());
    }

    private void yourNotesButtonClick() {

    }

    private void browseNotesButtonClick() {

    }

    private void sharedNotesButtonClick() {

    }
}
